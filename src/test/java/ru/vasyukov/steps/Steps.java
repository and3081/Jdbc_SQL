package ru.vasyukov.steps;

import io.qameta.allure.Step;
import ru.vasyukov.custom.Stats;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс степов
 */
public class Steps {
    @Step("Drop")
    public static void dropTables(List<String> dbNames) throws SQLException {
        for (int i=dbNames.size()-1; i>=0; i--) {  // в обратном порядке
            Stats.assertDropTable(dbNames.get(i));
        }
    }

    @Step("Create")
    public static void createTables(List<String> dbNames, List<String> fieldsCreate) throws SQLException {
        for (int i=0; i<dbNames.size(); i++) {
            Stats.assertCreateTable(dbNames.get(i), fieldsCreate.get(i));
        }
    }

    @Step("Insert")
    public static void insertTables(List<String> dbNames, List<Integer> countsInsert,
                                    List<String> fieldsInsert) throws SQLException {
        for (int i=0; i<dbNames.size(); i++) {
            Stats.assertInsertTable(dbNames.get(i), countsInsert.get(i), fieldsInsert.get(i));
        }
    }

    @Step("Select")
    public static void selectTables(List<Integer> counts, List<String> selects) throws SQLException {
        for (int i=0; i<counts.size(); i++) {
            Stats.assertSelectTable(counts.get(i), selects.get(i));
        }
    }
}
