package org.spigotmc;

import java.io.File;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpigotConfig {

    public static YamlConfiguration config;
    static int version;
    static Map<String, Command> commands;

    public static void init(File configFile) {
    }

    public static void registerCommands() {
    }

    static void readConfig(Class<?> clazz, Object instance) {
    }

}