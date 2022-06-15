package ru.vasyukov.custom;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.vasyukov.hooks.PropsHooks.conn;

public class Util {
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
