package me.trae.smp.config;

import me.trae.smp.Main;

import java.util.*;

public class ConfigManager {

    private final Map<ConfigType, Config> configs = new HashMap<>();

    private final Main instance;

    public ConfigManager(final Main instance) {
        this.instance = instance;
        Arrays.asList(ConfigType.values()).forEach(this::setup);
    }

    public void setup(final ConfigType type) {
        final Config config = new Config(instance.getDataFolder(), type.getName());
        if (!(config.fileExists())) {
            if (type.equals(ConfigType.MAIN_CONFIG)) {
                config.getConfig().set("Booleans.Game.Always-Day", false);
                config.getConfig().set("Booleans.Game.Always-Night", false);
                config.getConfig().set("Booleans.Game.Weather", false);
                config.getConfig().set("Booleans.Game.PvP", true);
                config.getConfig().set("Strings.Server.MOTD", "&6A Minecraft Server.");
            } else if (type.equals(ConfigType.MAIN_DATA)) {
                config.getConfig().set("Last-Server-Start", 0);
                config.getConfig().set("Last-Server-Stop", 0);
            }
            config.getConfig().options().copyDefaults(true);
            config.createFile();
        }
        config.loadFile();
        config.saveFile();
        configs.put(type, config);
    }

    public final Config getConfig(final ConfigType type) {
        return configs.get(type);
    }

    public final Set<ConfigType> getConfigTypes() {
        return new HashSet<>(configs.keySet());
    }

    public enum ConfigType {

        MAIN_CONFIG("config"),
        MAIN_DATA("data"),
        CLIENTS_DATA("clients"),
        GAMERS_DATA("gamers");

        private final String name;

        ConfigType(final String name) {
            this.name = name;
        }

        public final String getName() {
            return name;
        }
    }
}