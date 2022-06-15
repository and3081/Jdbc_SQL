package ru.vasyukov.custom;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.vasyukov.hooks.Hooks.conn;

/**
 * Класс утилит
 */
public class Utils {
    /**
     * Запрос имен таблиц БД
     * @return List имен таблиц
     * @throws SQLException .
     */
    public static List<String> TableNames() throws SQLException {
        ResultSet res = conn.getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"});
        //Attaches.attachResult(stringResult(res));
        List<String> names = new ArrayList<>();
        while (res.next()) {
            names.add(res.getString("TABLE_NAME"));
        }
        return names;
    }

    /**
     * Проверна наличия таблицы в БД
     * @param name имя таблицы
     * @return     true- есть таблица
     * @throws SQLException .
     */
    public static boolean isTableName(String name) throws SQLException {
        return TableNames().stream().anyMatch(el -> lowerRu(name).equals(lowerRu(el)));
        }

    /**
     * Запрос количества строк в результатах
     * @param res объект ResultSet
     * @return    количество строк
     * @throws SQLException .
     */
    public static int countResult(ResultSet res) throws SQLException {
        res.last();
        return res.getRow();
    }

    /**
     * toString для объекта результатов
     * @param res объект ResultSet
     * @return    строка в виде таблицы результатов с заголовками колонок и авто-шириной колонок
     * @throws SQLException .
     */
    public static String toStringResult(ResultSet res) throws SQLException {
        StringBuilder str = new StringBuilder(200);
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
            str.append("строк:").append(res.getRow())
                    .append("   колонок:").append(col)
                    .append("\n");
            str.append(" п/п |");
            for (int i=1; i<=col; i++) {
                str.append(String.format("%-" +len[i-1]+ "s|", info.getColumnLabel(i)));
            }
            str.append("\n").append("-----+");
            for (int i=1; i<=col; i++) {
                str.append(String.format("%-" +len[i-1]+ "s+", "-").replace(" ","-"));
            }
            str.append("\n");
            res.beforeFirst();
            while (res.next()) {
                str.append(String.format("%-5d|", res.getRow()));
                for (int i=1; i<=col; i++) {
                    str.append(String.format("%-" +len[i-1]+ "s|", res.getString(i)));
                }
                str.append("\n");
            }
            res.beforeFirst();
        }
        return str.toString();
    }

    /**
     * LowerCase строки с локалью RU
     * @param  text строка
     * @return LowerCase строки
     */
    public static String lowerRu(String text) {
        return text.toLowerCase(new Locale("RU"));
    }
}
