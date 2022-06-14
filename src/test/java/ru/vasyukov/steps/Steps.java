package ru.vasyukov.steps;

import io.qameta.allure.Step;
import ru.vasyukov.custom.Utils;

import java.sql.SQLException;

public class Steps {
    @Step("Drop")
    public static void dropTables() throws SQLException {
        Utils.assertDropTable("students");
        Utils.assertDropTable("workgroups");
    }

    @Step("Create")
    public static void createTables() throws SQLException {
        Utils.assertCreateTable("workgroups",
                "(id bigserial primary key, " +
                        "name varchar(100) not null)");
        Utils.assertCreateTable("students",
                "(id bigserial primary key," +
                        "name varchar(100) not null," +
                        "age int not null," +
                        "workgroupid int references workgroups(id))");
    }
}
