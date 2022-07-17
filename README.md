## Тестирование JDBC SQL postgresql
* JDBC SQL
* Java 17 / Maven
* Junit5
* Allure

## запуск всех тестов
mvn clean test

## запуск тестов выборочно по названиям тегов
mvn clean test -Dgroups="перечень через запятую"

## построение отчета Allure
mvn allure:serve

## настройки в классе DataProvider
параметризация тестов

## настройки в проперти:
* props.properties - url и login БД
* psw.properties - psw БД

## тест-кейсы:
### ТК1 - таблицы students и workgroups
* Подключиться к БД
* Удалить обе таблицы от прошлого сеанса: drop
* Создать обе таблицы: create
* Заполнить тестовыми данными обе таблицы: insert
* Выполнить 6 выборок и приаттачить результаты в отчет Allure:
  * select * from students
  * select * from workgroups
  * select name from students s<br>
    where workgroupid is null
  * select s.name, s.age<br> 
    case when s.workgroupid is null then '---' else w.name end<br>
    from students s<br>
    left join workgroups w on s.workgroupid = w.id
  * select w.name from workgroups w<br>
    where w.id not in(select s.workgroupid from students s<br>
       where s.workgroupid is not null<br>
       group by s.workgroupid)
  * select w.name, count(s.name) from students s<br>
    join workgroups w on s.workgroupid = w.id<br>
    group by w.name 
* Отключиться от БД
