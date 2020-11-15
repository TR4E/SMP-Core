package me.trae.smp.config;

import me.trae.smp.Main;

import java.util.Set;

public abstract class IRepository {

    public final Config config;
    private final Main instance;

    public IRepository(final Main instance, final ConfigManager.ConfigType configType) {
        this.instance = instance;
        this.config = instance.getConfigManager().getConfig(configType);
        load();
    }

    public final Main getInstance() {
        return instance;
    }

    public final Config getConfig(final ConfigManager.ConfigType type) {
        return getInstance().getConfigManager().getConfig(type);
    }

    public final Set<ConfigManager.ConfigType> getConfigTypes() {
        return getInstance().getConfigManager().getConfigTypes();
    }

    public abstract void load();

    public abstract void reload();
}