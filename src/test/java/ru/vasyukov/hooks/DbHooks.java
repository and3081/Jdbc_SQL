package ru.vasyukov.hooks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.vasyukov.properties.TestData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHooks {
    public static Connection conn;

    @BeforeEach
    public void init() throws SQLException {
        conn = getNewConnection();
    }

    @AfterEach
    public void close() throws SQLException {
        if(conn != null) {
            conn.close();
            conn = null;
        }
    }

    private Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(TestData.props.dbUrl(),
                TestData.props.dbUser(),
                TestData.props.dbPsw());
    }
}
