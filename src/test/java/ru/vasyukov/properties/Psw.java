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
public interface Psw extends Config {
    @Key("db.psw")
    String dbPsw();
}
