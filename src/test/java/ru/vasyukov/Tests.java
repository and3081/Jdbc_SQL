package ru.vasyukov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vasyukov.hooks.DbHooks;

import java.sql.*;
import java.util.Properties;

public class Tests extends DbHooks {
    @DisplayName("Тестирование Jdbc")
    @Test
    public void testDb() {
        try {
            Assertions.assertTrue(conn.isValid(1));
            Assertions.assertFalse(conn.isClosed());
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
