import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Main {

    private static Connection conn;

    public static void main(String[] args) throws CsvException, ClassNotFoundException, SQLException {

        Create();
        String fName = "src/Школы.csv";
        Path path = Paths.get(fName);
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        Class.forName("org.sqlite.JDBC");
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/schooldb.db");

            try (var bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                 var reader = new CSVReaderBuilder(bufferedReader).withCSVParser(parser).build()) {

                List<String[]> lines = reader.readAll();
                lines.remove(0);

                Statement stm = conn.createStatement();
                ResultSet resultSet = stm.executeQuery("SELECT COUNT(*) FROM schools");

                if (resultSet.getInt(1) != lines.size()) {
                    ModelToDB(lines);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        firstMission();
        secondMission();
        thirdMission();
    }

    private static void firstMission() {
        Map<String, Integer> res;

        try {
            Statement stm = conn.createStatement();
            res = getDataForFirst(stm);

            int i = 0;
            var data = new DefaultCategoryDataset();
            for (var key : res.keySet()) {
                i += 1;
                data.addValue(res.get(key), key, "");
                if (i == 10) {
                    break;
                }
            }

            var bar = ChartFactory.createBarChart(
                    "Зависимость среднего кол-ва студентов от страны",
                    "Страна",
                    "Среднее кол-во студентов",
                    data,
                    PlotOrientation.VERTICAL,
                    true, false, false);
            try {
                ChartUtils.saveChartAsPNG(new File("src/graph_1.png"), bar, 640, 520);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void secondMission() {
        Map<String, Float> res;

        try {
            Statement stm = conn.createStatement();
            res = getDataForSecond(stm);
            System.out.println("## 2-ое задание ##");
            System.out.println("Среднее кол-во расходов:");
            for (var entry : res.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void thirdMission() {
        String[] res;

        try {
            Statement stm = conn.createStatement();
            res = getDataForThird(stm);
            System.out.println("## 3-е задание ##");
            System.out.println("Учебное заведение, с количеством студентов " +
                    "от 5000 до 7500 и максимальным показателем по математике: " + res[0]);
            System.out.println("Учебное заведение, с количеством студентов " +
                    "от 10000 до 11000 и максимальным показателем по математике: " + res[1]);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Integer> getDataForFirst(Statement stm) {
        Map<String, Integer> res = new HashMap<>();

        try {
            ResultSet dataQuery = stm.executeQuery("SELECT county, students FROM schools");
            while (dataQuery.next()) {
                res.put(dataQuery.getString(1),
                        res.containsKey(dataQuery.getString(1))
                                ? dataQuery.getInt(2) + res.get(dataQuery.getString(1))
                                : dataQuery.getInt(2));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new TreeMap<>(res);
    }

    private static Map<String, Float> getDataForSecond(Statement stm) {
        Map<String, Float> res = new HashMap<>();

        try {
            ResultSet dataQuery = stm.executeQuery("SELECT county, expenditure " +
                                                        "FROM schools " +
                                                        "WHERE county = 'Fresno' " +
                                                        "OR county = 'Contra Costa' " +
                                                        "OR county = 'El Dorado' " +
                                                        "OR county = 'Glenn'");
            while (dataQuery.next()) {
                if (dataQuery.getFloat(2) > 10) {
                    res.put(dataQuery.getString(1),
                            res.containsKey(dataQuery.getString(1))
                                    ? dataQuery.getFloat(2) + res.get(dataQuery.getString(1))
                                    : dataQuery.getFloat(2));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new TreeMap<>(res);
    }

    private static String[] getDataForThird(Statement stm) {
        String resFirst;
        String resSecond;

        try {
            ResultSet dataQuery = stm.executeQuery("SELECT school " +
                                                        "FROM schools " +
                                                        "WHERE students >= 5000 " +
                                                        "AND students <= 7500 " +
                                                        "AND math = (SELECT MAX(math) " +
                                                        "FROM schools " +
                                                        "WHERE students >= 5000 " +
                                                        "AND students <= 7500)");
            resFirst = dataQuery.getString(1);
            ResultSet dataQuerySecond = stm.executeQuery("SELECT school " +
                                                                "FROM schools " +
                                                                "WHERE students >= 10000 " +
                                                                "AND students <= 11000 " +
                                                                "AND math = (SELECT MAX(math) " +
                                                                "FROM schools " +
                                                                "WHERE students >= 10000 " +
                                                                "AND students <= 11000)");
            resSecond = dataQuerySecond.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new String[]{resFirst, resSecond};
    }

    private static void Create() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:src/schooldb.db");
        String query = "CREATE TABLE IF NOT EXISTS schools " +
                        "(id INT, district INT, school TEXT, county TEXT, " +
                        "grades TEXT, students INT, teachers FLOAT, calworks FLOAT, " +
                        "lunch FLOAT, computer INT, expenditure FLOAT, income FLOAT, " +
                        "english FLOAT, read FLOAT, math FLOAT) ";

        Statement stm = conn.createStatement();
        stm.execute(query);
    }

    private static void ModelToDB(List<String[]> lines) {
        List<Model> schoolsList = new ArrayList<>();

        for (var i = 0; i < lines.size(); i++) {
            Model schools = new Model();
            String[] row = lines.get(i);

            schools.id = Integer.parseInt(row[0]);
            schools.district = Integer.parseInt(row[1]);
            schools.school = row[2];
            schools.county = row[3];
            schools.grades = row[4];
            schools.students = Integer.parseInt(row[5]);
            schools.teachers = Float.parseFloat(row[6]);
            schools.calworks = Float.parseFloat(row[7]);
            schools.lunch = Float.parseFloat(row[8]);
            schools.computer = Integer.parseInt(row[9]);
            schools.expenditure = Float.parseFloat(row[10]);
            schools.income = Float.parseFloat(row[11]);
            schools.english = Float.parseFloat(row[12]);
            schools.read = Float.parseFloat(row[13]);
            schools.math = Float.parseFloat(row[14]);

            schoolsList.add(schools);

            addSchool(schoolsList.get(i));
            System.out.println(i + ". School added");
        }
    }

    private static void addSchool(Model school) {
        try {
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO schools(id, district, school, county, " +
                            "grades, students, teachers, calworks, lunch, " +
                            "computer, expenditure, income, english, read, math) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insert.setInt(1, school.district);
            insert.setInt(2, school.district);
            insert.setString(3, school.school);
            insert.setString(4, school.county);
            insert.setString(5, school.grades);
            insert.setInt(6, school.students);
            insert.setFloat(7, school.teachers);
            insert.setFloat(8, school.calworks);
            insert.setFloat(9, school.lunch);
            insert.setInt(10, school.computer);
            insert.setFloat(11, school.expenditure);
            insert.setFloat(12, school.income);
            insert.setFloat(13, school.english);
            insert.setFloat(14, school.read);
            insert.setFloat(15, school.math);

            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}