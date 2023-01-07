<h1 align="center">Проект по курсу Java на Ulearn</h1>
<h2 align="center">Вариант №2. Школы</h2>
<h3>Реализация модели</h3>
<p>Для упрощения задачи, связанной с реализацией БД, ее заполнения и так далее, решил использовать модель школы.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142069-c62ee38c-c7c0-46f6-9547-bd7b8524b51c.png" style="max-width: 100%;">
<h3>Парсер CSV</h3>
<p>Было необходимо создать парсер csv файла для дальнейшей работы с данными из него. В процессе использовал библиотеку OpenCSV</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142219-e1cde93e-7eff-451d-b38c-99420a8467df.png" style="max-width: 100%;"><h3>Реализация данных в БД</h3>
<p>Мной было принято решение о создании алгоритма, который будет проверять количество строк в БД и в CSV файле. Это необходимо для контроля записи данных в БД. Программа проверят равенство строк, если оно отсутствует, то идет перезапись, логика которой выполнена в методах ModelToDB и AddSchool</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142408-6a2fc613-033c-4816-9733-b0dfd65d5a7e.png" style="max-width: 100%;">
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142414-ca957016-e8ca-4b5a-b636-b37e9d2f3410.png" style="max-width: 100%;">
<h3>Первая задача</h3>
<p>Для реализации первой задачи я использовал библиотеку jfree. Создал запрос в БД, получил нужные данные, раскидал их в диаграмме и все получилось.
Библиотека довольно обширна, и если разобраться, можно создавать не только информативные, но и стильные графики.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142513-046898d4-cb55-43cd-b835-faef8cc1a4e1.png" style="max-width: 100%;">
<h3>Вторая задача</h3>
<p>По моему мнению вторая задача проще первой, так как вспомогательных библиотек не нужно, а я люблю создавать выводы в консоль. Так же получил данные из БД и аккуратно вывел их в консоль.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142665-4b61b43d-9234-4758-9e33-82fb76d138ef.png" style="max-width: 100%;">
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142790-2fc8c4bd-e079-43e8-b63f-8cad83050065.png" style="max-width: 100%;">


<h3>Третья задача</h3>
<p>Задача так же связана с выводом в консоль, тут все аналогично, отличается лишь условие выбора данных.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142713-1e713403-3bf2-4391-a95f-4966c4ad45b1.png" style="max-width: 100%;">
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211142798-def72ec1-db5e-4165-ab23-7cf430032b55.png" style="max-width: 100%;">

<h3>Итог по задачам</h3>
<p>Задачи были довольно просты в плане реализации, но подумать над решением конечно пришлось. Для каждой задачи я создал по два метода: один для получения данных, второй для реализации показа(график или вывод в консоль).</p>

<h3>SQL запросы</h3>
<p>Запрос на количесвто строк в таблице, для проверки актуальности данных.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143052-2ae0a7a7-28f4-49fc-bae2-64eb65ad5357.png" style="max-width: 100%;">

<p>Запрос для первой задачи.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143059-a68b9953-ff41-4a5e-93f2-3ce5117207ef.png" style="max-width: 100%;">

<p>Запрос для второй задачи.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143086-9f901b0c-68bb-476f-bea8-77e1bd965516.png" style="max-width: 100%;">

<p>Запросы для третьей задачи.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143102-e7b745cd-f240-41ea-8101-8b843845d21b.png" style="max-width: 100%;">

<p>Запросы на создание таблицы, если таковой нет.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143112-916ec098-ace1-4f84-86be-a61aaa336937.png" style="max-width: 100%;">

<p>Запросы на добавление данных в таблицу.</p>
<img src="https://github.com/RuslanAbbasov/FinalJavaProjectSchools/blob/main/screens/211143169-bf94f0b2-0ec4-4d86-b1da-7b8f410b6ef0.png" style="max-width: 100%;">



