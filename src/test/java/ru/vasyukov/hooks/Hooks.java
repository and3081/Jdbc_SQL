package ru.vasyukov.hooks;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import ru.vasyukov.properties.TestData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс хуков для подключения и отключения коннекта к БД для каждого теста
 */
public class Hooks {
    public static Connection conn;

    /**
     * Подключение и ассерты
     * @throws SQLException .
     */
    @Step("Connect")
    @BeforeEach
    public void init() throws SQLException {
        conn = getNewConnection();
        Assertions.assertTrue(conn.isValid(1), "Подключение к БД не корректно");
        Assertions.assertFalse(conn.isClosed(), "БД уже закрыта");
    }

    /**
     * Отключение
     * @throws SQLException .
     */
    @Step("Disconnect")
    @AfterEach
    public void close() throws SQLException {
        if(conn != null) {
            conn.close();
            conn = null;
        }
    }

    /**
     * Создание коннекта к БД с данными из проперти
     * @return коннект
     * @throws SQLException .
     */
    private Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(TestData.props.dbUrl(),
                TestData.props.dbUser(),
                TestData.psw.dbPsw());
    }
}
