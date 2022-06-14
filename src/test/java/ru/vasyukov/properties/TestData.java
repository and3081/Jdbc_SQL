package ru.vasyukov.properties;

import org.aeonbits.owner.ConfigFactory;

public class TestData {
    public static Props props = ConfigFactory.create(Props.class);
    public static Psw psw = ConfigFactory.create(Psw.class);
}
