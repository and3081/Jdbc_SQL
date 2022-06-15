package ru.vasyukov;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Класс провайдера данных для тестов
 */
@SuppressWarnings("unused")
public class DataProvider {
    /**
     * Метод-провайдер для тест-кейса testDb()
     * @return  стрим аргументов:
     *                            список имен БД,
     *                            список полей Create,
     *                            список количества строк Insert,
     *                            список полей Insert,
     *                            список количества строк выборок Select,
     *                            список команд выборок Select
     */
    protected static Stream<Arguments> providerSelect() {
        return Stream.of(arguments(
                List.of("workgroups", "students"),
                List.of("(id bigserial primary key, " +
                                "name varchar(100) not null)",
                        "(id bigserial primary key," +
                                "name varchar(100) not null," +
                                "age int not null," +
                                "workgroupid int references workgroups(id))"),
                List.of(4, 4),
                List.of("(name) values ('Chess'), ('Boxing'), ('Swimming'), ('Skiing')",
                        "(name, age, workgroupid) values" +
                                "('Andrey',  10, 1)," +
                                "('Yuri',     9, 2)," +
                                "('Alexandr',11, 2)," +
                                "('Nikita',  12, NULL)"),
                List.of(4, 4 ,1, 4, 2, 2),
                List.of("select * " +
                                "from students",
                        "select * " +
                                "from workgroups",
                        "select name " +
                                "from students s " +
                                "where workgroupid is null",
                        "select s.name, s.age," +
                                "case when s.workgroupid is null then '---' else w.name end " +
                                "from students s " +
                                "left join workgroups w on s.workgroupid = w.id",
                        "select w.name " +
                                "from workgroups w " +
                                "where w.id not in(select s.workgroupid from students s " +
                                "where s.workgroupid is not null " +
                                "group by s.workgroupid)",
                        "select w.name, count(s.name) " +
                                "from students s " +
                                "join workgroups w on s.workgroupid = w.id " +
                                "group by w.name")));
    }
}
