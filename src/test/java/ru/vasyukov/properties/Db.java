package ru.vasyukov.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:db.properties"
})
public interface Db extends Config {
    @Key("db.url")
    String dbUrl();

    @Key("db.user")
    String dbUser();
}
