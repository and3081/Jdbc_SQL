package ru.vasyukov.properties;

import org.aeonbits.owner.ConfigFactory;

public class TestData {
    public static Db db = ConfigFactory.create(Db.class);
    public static Psw psw = ConfigFactory.create(Psw.class);
}
