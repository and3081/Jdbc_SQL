package ru.vasyukov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vasyukov.hooks.Hooks;
import ru.vasyukov.steps.Steps;

import java.sql.SQLException;
import java.util.List;

public class Tests extends Hooks {
    @DisplayName("Тестирование Jdbc")
    @ParameterizedTest(name = ".")
    @MethodSource("ru.vasyukov.DataProvider#providerSelect")
    public void testDb(List<String> dbNames, List<String> fieldsCreate,
                       List<Integer> countsInsert, List<String> fieldsInsert,
                       List<Integer> countsSelect, List<String> selects) {
        try {
            Steps.dropTables(dbNames);
            Steps.createTables(dbNames, fieldsCreate);
            Steps.insertTables(dbNames, countsInsert, fieldsInsert);
            Steps.selectTables(countsSelect, selects);
        }
        catch (SQLException e) {
            Assertions.fail("SQLException: " + e);
        }
    }

}
