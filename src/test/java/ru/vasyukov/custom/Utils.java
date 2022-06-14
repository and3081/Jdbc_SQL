package ru.vasyukov.custom;

import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.vasyukov.hooks.DbHooks.conn;

public class Utils {
    private static Statement stat;

    static {
        try {
            stat = conn.createStatement();
            stat.setQueryTimeout(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int execUpdate(String query) throws SQLException {
        return stat.executeUpdate(query);
    }

    public static ResultSet execQuery(String query) throws SQLException {
        return stat.executeQuery(query);
    }

    public static void assertDropTable(String name) throws SQLException {
        Utils.execUpdate("drop table if exists " + name);
        Assertions.assertFalse(Utils.isTableName(name), "БД " + name + " не удалена");
    }

    public static void assertCreateTable(String name, String fields) throws SQLException {
        Utils.execUpdate("create table if not exists " + name + fields);
        Assertions.assertTrue(Utils.isTableName(name), "БД " + name + " не создана");
    }

    public static List<String> TableNames() throws SQLException {
        ResultSet res = conn.getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"});
        List<String> names = new ArrayList<>();
        while (res.next()) {
            names.add(res.getString("TABLE_NAME"));
        }
        return names;
    }

    public static boolean isTableName(String name) throws SQLException {
        return TableNames().stream().anyMatch(el -> lowerRu(name).equals(lowerRu(el)));
    }

    /**
     * LowerCase строки с локалью RU
     * @param text строка
     * @return LowerCase строки
     */
    public static String lowerRu(String text) {
        return text.toLowerCase(new Locale("RU"));
    }
}
