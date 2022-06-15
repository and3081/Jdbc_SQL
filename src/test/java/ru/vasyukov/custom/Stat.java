package ru.vasyukov.custom;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.vasyukov.hooks.PropsHooks.conn;

public class Stat {
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
        //System.out.println(query);
        return stat.executeUpdate(query);
    }

    @Step("{query}")
    public static ResultSet execQuery(String query) throws SQLException {
        return stat.executeQuery(query);
    }

    @Step("БД {name}")
    public static void assertDropTable(String name) throws SQLException {
        execUpdate("drop table if exists " + name);
        Assertions.assertFalse(Util.isTableName(name), "БД " + name + " не удалена");
    }

    @Step("БД {name}")
    public static void assertCreateTable(String name, String fields) throws SQLException {
        execUpdate("create table if not exists " + name + fields);
        Assertions.assertTrue(Util.isTableName(name), "БД " + name + " не создана");
    }

    @Step("БД {name}")
    public static void assertInsertTable(String name, int count, String fields) throws SQLException {
        int updated = execUpdate("insert into " + name + fields);
        Assertions.assertEquals(count, updated, "Записи в БД " + name + " не вставлены");
    }

    @Step("Строк {count}")
    public static void assertSelectTable(int count, String query) throws SQLException {
        ResultSet res = Stat.execQuery(query);
        Util.browseResult(res);
        System.out.println(res);
        Assertions.assertEquals(count, Util.countResult(res), "Количество строк выборки Select неправильно");
    }
}
