package ru.vasyukov.custom;

import io.qameta.allure.Attachment;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("UnusedReturnValue")
public class Attaches {
    @Attachment(value = "Выборка", type = "application/json")
    public static byte[] attachResult(String res) {
        return res.getBytes(StandardCharsets.UTF_8);
    }
}
