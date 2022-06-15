package ru.vasyukov.custom;

import io.qameta.allure.Attachment;

import java.nio.charset.StandardCharsets;

/**
 * Класс аттачей для Аллюра
 */
@SuppressWarnings("UnusedReturnValue")
public class Attaches {
    /**
     * Аттач строки результатов в табличном виде
     * @param res строка результатов
     * @return    аттач
     */
    @Attachment(value = "Выборка", type = "application/json")
    public static byte[] attachResult(String res) {
        return res.getBytes(StandardCharsets.UTF_8);
    }
}
