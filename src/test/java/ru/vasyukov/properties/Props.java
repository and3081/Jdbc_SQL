package ru.vasyukov.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:props.properties"
})
public interface Props extends Config {
    @Key("db.url")
    String dbUrl();

    @Key("db.user")
    String dbUser();

    @Key("db.psw")
    String dbPsw();
}
