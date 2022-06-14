package ru.vasyukov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vasyukov.hooks.PropsHooks;
import ru.vasyukov.steps.Steps;

import java.sql.SQLException;

public class Tests extends PropsHooks {
    @DisplayName("Тестирование Jdbc")
    @Test
    public void testDb() {
        try {
            Assertions.assertTrue(conn.isValid(1), "Подключение к БД не корректно");
            Assertions.assertFalse(conn.isClosed(), "БД уже закрыта");
            Steps.dropTables();
            Steps.createTables();
            Steps.insertTables();
            Steps.selectTables();
        }
        catch (SQLException e) {
            Assertions.fail("SQLException: " + e);
        }
    }

}
