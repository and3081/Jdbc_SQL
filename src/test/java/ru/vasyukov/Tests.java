package ru.vasyukov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vasyukov.hooks.PropsHooks;
import ru.vasyukov.steps.Steps;

import java.sql.SQLException;
import java.util.List;

public class Tests extends PropsHooks {
    @DisplayName("Тестирование Jdbc")
    @ParameterizedTest(name = "")
    @MethodSource("ru.vasyukov.DataProvider#providerSelect")
    public void testDb(List<Integer> counts, List<String> selects) {
        try {
            Assertions.assertTrue(conn.isValid(1), "Подключение к БД не корректно");
            Assertions.assertFalse(conn.isClosed(), "БД уже закрыта");
            Steps.dropTables();
            Steps.createTables();
            Steps.insertTables();
            Steps.selectTables(counts, selects);
        }
        catch (SQLException e) {
            Assertions.fail("SQLException: " + e);
        }
    }

}
