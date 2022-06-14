package ru.vasyukov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vasyukov.hooks.DbHooks;

import java.sql.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class Tests extends DbHooks {
    @DisplayName("Тестирование выборки в Сбер-АСТ")
    @Test
    public void testDb() {
        try {
            Enumeration<Driver> dr = DriverManager.getDrivers();
            Iterator<Driver> it = dr.asIterator();
            while (it.hasNext()) {
                Driver el = it.next();
                System.out.println(el.getClass().getName());
            }
            System.out.println("valid: "+conn.isValid(1));
            System.out.println("close: "+conn.isClosed());
            Properties pr = conn.getClientInfo();
            pr.forEach((i,j)-> System.out.println(i+" | "+j));
            ResultSet res = execQuery("select * from students");
            if (res != null) {
                System.out.println(res.getRow());
                while (res.next()) {
                    System.out.println(res.getRow());
                    System.out.println(res.getString("name"));
                }
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private static ResultSet execQuery(String query) throws SQLException {
        Statement stat= conn.createStatement();
        //return stat.executeUpdate();
        return stat.execute(query) ? stat.getResultSet() : null;
    }
}
