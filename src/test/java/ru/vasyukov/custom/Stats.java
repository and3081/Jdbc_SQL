package ru.vasyukov.custom;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.vasyukov.hooks.Hooks.conn;

/**
 * Класс sql Statement
 */
public class Stats {
    private static Statement stat;

    static {
        try {
            stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stat.setQueryTimeout(5);
        } catch (SQLException e) {
            Assertions.fail("SQLException: " + e);
        }
    }

    @Step("{query}")
    public static int execUpdate(String query) throws SQLException {
        return stat.executeUpdate(query);
    }

    @Step("{query}")
    public static ResultSet execQuery(String query) throws SQLException {
        return stat.executeQuery(query);
    }

    @Step("Таблица {name}")
    public static void assertDropTable(String name) throws SQLException {
        execUpdate("drop table if exists " + name);
        Assertions.assertFalse(Utils.isTableName(name), "Таблица " + name + " не удалена");
    }

    @Step("Таблица {name}")
    public static void assertCreateTable(String name, String fields) throws SQLException {
        execUpdate("create table if not exists " + name + fields);
        Assertions.assertTrue(Utils.isTableName(name), "Таблица " + name + " не создана");
    }

    @Step("Таблица {name}")
    public static void assertInsertTable(String name, int count, String fields) throws SQLException {
        int updated = execUpdate("insert into " + name + fields);
        Assertions.assertEquals(count, updated, "Записи в таблицу " + name + " не вставлены");
    }

    @Step("Строк {count}")
    public static void assertSelectTable(int count, String query) throws SQLException {
        ResultSet res = Stats.execQuery(query);
        String resStr = Utils.toStringResult(res);
        System.out.println(resStr);
        //noinspection ResultOfMethodCallIgnored
        Attaches.attachResult(resStr);
        Assertions.assertEquals(count, Utils.countResult(res), "Количество строк выборки Select неправильно");
    }
}
