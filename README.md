#Тестирование JDBC

#запуск всех тестов
mvn clean test

#построение отчета Allure
mvn allure:serve

#настройки в проперти:
props.properties - url и login БД<br>
psw.properties - psw БД

