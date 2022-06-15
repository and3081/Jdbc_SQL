package ru.vasyukov.steps;

import io.qameta.allure.Step;
import ru.vasyukov.custom.Stats;

import java.sql.SQLException;

public class Steps {
    @Step("Drop")
    public static void dropTables() throws SQLException {
        Stats.assertDropTable("students");
        Stats.assertDropTable("workgroups");
    }

    @Step("Create")
    public static void createTables() throws SQLException {
        Stats.assertCreateTable("workgroups",
                "(id bigserial primary key, " +
                        "name varchar(100) not null)");
        Stats.assertCreateTable("students",
                "(id bigserial primary key," +
                        "name varchar(100) not null," +
                        "age int not null," +
                        "workgroupid int references workgroups(id))");
    }

    @Step("Insert")
    public static void insertTables() throws SQLException {
        Stats.assertInsertTable("workgroups", 4,
                "(name) values ('Chess'), ('Boxing'), ('Swimming'), ('Skiing')");
        Stats.assertInsertTable("students", 4,
                "(name, age, workgroupid) values" +
                        "('Andrey',  10, 1)," +
                        "('Yuri',     9, 2)," +
                        "('Alexandr',11, 2)," +
                        "('Nikita',  12, NULL)");
    }

    @Step("Select")
    public static void selectTables() throws SQLException {
        Stats.assertSelectTable(4, "select * " +
                "from students");
        Stats.assertSelectTable(4, "select * " +
                "from workgroups");
        Stats.assertSelectTable(1, "select name " +
                "from students s " +
                "where workgroupid is null");
        Stats.assertSelectTable(4, "select s.name, s.age," +
                "case when s.workgroupid is null then '---' else w.name end " +
                "from students s " +
                "left join workgroups w on s.workgroupid = w.id");
        Stats.assertSelectTable(2, "select w.name " +
                "from workgroups w " +
                "where w.id not in(select s.workgroupid from students s " +
                "where s.workgroupid is not null " +
                "group by s.workgroupid)");
        Stats.assertSelectTable(2, "select w.name, count(s.name) " +
                "from students s " +
                "join workgroups w on s.workgroupid = w.id " +
                "group by w.name");
    }
}
