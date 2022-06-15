package ru.vasyukov.steps;

import io.qameta.allure.Step;
import ru.vasyukov.custom.Stats;

import java.sql.SQLException;
import java.util.List;

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
    public static void selectTables(List<Integer> counts, List<String> selects) throws SQLException {
        for (int i=0; i<counts.size(); i++) {
            Stats.assertSelectTable(counts.get(i), selects.get(i));
        }
    }
}
