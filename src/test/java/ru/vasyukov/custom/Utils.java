package ru.vasyukov.custom;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.vasyukov.hooks.PropsHooks.conn;

public class Utils {
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
        Assertions.assertFalse(isTableName(name), "БД " + name + " не удалена");
    }

    @Step("БД {name}")
    public static void assertCreateTable(String name, String fields) throws SQLException {
        execUpdate("create table if not exists " + name + fields);
        Assertions.assertTrue(isTableName(name), "БД " + name + " не создана");
    }

    @Step("БД {name}")
    public static void assertInsertTable(String name, int count, String fields) throws SQLException {
        int updated = execUpdate("insert into " + name + fields);
        Assertions.assertEquals(count, updated, "Записи в БД " + name + " не вставлены");
    }

    @Step("Строк {count}")
    public static void assertSelectTable(int count, String query) throws SQLException {
        ResultSet res = Utils.execQuery(query);
        Utils.browseResult(res);
        System.out.println(res);
        Assertions.assertEquals(count, countResult(res), "Количество строк выборки Select неправильно");
    }

    public static List<String> TableNames() throws SQLException {
        ResultSet res = conn.getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"});
        //browseResult(res);
        List<String> names = new ArrayList<>();
        while (res.next()) {
            names.add(res.getString("TABLE_NAME"));
        }
        return names;
    }

    public static boolean isTableName(String name) throws SQLException {
        return TableNames().stream().anyMatch(el -> lowerRu(name).equals(lowerRu(el)));
        }

    public static int countResult(ResultSet res) throws SQLException {
        res.last();
        return res.getRow();
    }

    public static void browseResult(ResultSet res) throws SQLException {
        ResultSetMetaData info = res.getMetaData();
        int col = info.getColumnCount();
        if (col > 0) {
            int[] len = new int[col];
            for (int i=1; i<=col; i++) {
                len[i-1] = Math.max(info.getColumnLabel(i).trim().length(), info.getColumnTypeName(i).trim().length());
                res.beforeFirst();
                while (res.next()) {
                    if (res.getString(i) != null) {
                        len[i - 1] = Math.max(len[i - 1], res.getString(i).trim().length());
                    }
                }
            }
            res.last();
            System.out.println("кат." + info.getCatalogName(1) +
                    "   схема " + info.getSchemaName(1) +
                    "   табл." + info.getTableName(1) +
                    "   стр." + res.getRow() +
                    "   кол." + col);
            System.out.print("     |");
            for (int i=1; i<=col; i++) {
                System.out.printf("%-" +len[i-1]+ "s|", info.getColumnLabel(i));
            }
            System.out.println();
            System.out.print("тип  |");
            for (int i=1; i<=col; i++) {
                System.out.printf("%-" +len[i-1]+ "s|", info.getColumnTypeName(i));
            }
            System.out.println();
            System.out.println("--------------------");
            res.beforeFirst();
            while (res.next()) {
                System.out.printf("%5d|", res.getRow());
                for (int i=1; i<=col; i++) {
                    System.out.printf("%-" +len[i-1]+ "s|", res.getString(i));
                }
                System.out.println();
            }
            res.beforeFirst();
        }
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
