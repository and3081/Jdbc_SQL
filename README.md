-- тестовое собеса иннотех

--drop table if exists public.students;
--drop table if exists workgroups;

--create table if not exists workgroups (
--	id bigserial primary key,
--	name varchar(100) not null );

create table if not exists students (	id bigserial primary key,	name varchar(100) not null,	age int not null,	workgroupid int references workgroups(id) );
--
--insert into workgroups (name) values
--	('Chess'), ('Boxing'), ('Swimming'), ('Skiing');
--
--insert into students (name, age, workgroupid) values
--	('Andrey',  10, 1),
--	('Yuri',     9, 2),
--	('Alexandr',11, 2),
--	('Nikita',  12, NULL);
--
--select name from students s
--where workgroupid is null;
--
--select s.name, s.age,
--	case when s.workgroupid is null then '---' else w.name end
--from students s
--left join workgroups w on s.workgroupid = w.id ;
--
--select w.name from workgroups w
--where w.id not in
--	(select s.workgroupid from students s
--	 where s.workgroupid is not null
--	 group by s.workgroupid);
--	 
--select w.name, count(s.name)
--from students s
--join workgroups w on s.workgroupid = w.id
--group by w.name;
Тестирование выборки в Сбер-АСТ - Selenide

# запуск всех тестов
mvn clean test

# построение отчета Allure
mvn allure:serve

# настройки в классе DataProvider
параметризация тестов

# настройки в проперти:
browser.properties - настройки браузеров, Selenoid (описание в файле)

listener.properties - настройки листенера скриншотов для Allure (описание в файле)

