package ru.vasyukov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vasyukov.custom.Utils;
import ru.vasyukov.hooks.DbHooks;
import ru.vasyukov.steps.Steps;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tests extends DbHooks {
    @DisplayName("Тестирование Jdbc")
    @Test
    public void testDb() {
        try {
            Assertions.assertTrue(conn.isValid(1),
                    "Подключение к БД не корректно");
            Assertions.assertFalse(conn.isClosed(),
                    "БД уже закрыта");
            Steps.dropTables();
            Steps.createTables();

            ResultSet res = Utils.execQuery("select * from students");
            while (res.next()) {
                System.out.println(res.getRow() + " | " + res.getString("name"));
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

}
