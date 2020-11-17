package me.trae.smp.config;

import me.trae.smp.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class Repository extends IRepository {

    private boolean alwaysDay, alwaysNight, weather, pvp, explosionHeal;
    private String serverMOTD;
    private long lastServerStart, lastServerStop;

    public Repository(final Main instance) {
        super(instance, ConfigManager.ConfigType.MAIN_CONFIG);
    }

    @Override
    public synchronized void load() {
        for (final ConfigManager.ConfigType type : getConfigTypes()) {
            final Config config = getConfig(type);
            config.loadFile();
            config.saveFile();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (type.equals(ConfigManager.ConfigType.MAIN_CONFIG)) {
                        alwaysDay = config.getConfig().getBoolean("Booleans.Game.Always-Day");
                        alwaysNight = config.getConfig().getBoolean("Booleans.Game.Always-Night");
                        weather = config.getConfig().getBoolean("Booleans.Game.Weather");
                        pvp = config.getConfig().getBoolean("Booleans.Game.PvP");
                        explosionHeal = config.getConfig().getBoolean("Booleans.Game.Explosion-Heal");
                        serverMOTD = config.getConfig().getString("Strings.Server.MOTD");
                    } else if (type.equals(ConfigManager.ConfigType.MAIN_DATA)) {
                        lastServerStart = config.getConfig().getLong("Last-Server-Start");
                        lastServerStop = config.getConfig().getLong("Last-Server-Stop");
                    }
                }
            }.runTaskAsynchronously(getInstance());
        }
    }

    @Override
    public void reload() {
        load();
    }

    public final boolean isAlwaysDay() {
        return alwaysDay;
    }

    public final boolean isAlwaysNight() {
        return alwaysNight;
    }

    public final boolean isWeather() {
        return weather;
    }

    public final boolean isPvP() {
        return pvp;
    }

    public final boolean isExplosionHeal() {
        return explosionHeal;
    }

    public final String getServerMOTD() {
        return serverMOTD;
    }

    public final long getLastServerStart() {
        return lastServerStart;
    }

    public void setLastServerStart() {
        final long time = System.currentTimeMillis();
        this.lastServerStart = time;
        final Config config = this.getConfig(ConfigManager.ConfigType.MAIN_DATA);
        config.loadFile();
        config.getConfig().set("Last-Server-Start", time);
        config.saveFile();
    }

    public final long getLastServerStop() {
        return lastServerStop;
    }

    public void setLastServerStop() {
        final long time = System.currentTimeMillis();
        this.lastServerStop = time;
        final Config config = this.getConfig(ConfigManager.ConfigType.MAIN_DATA);
        config.loadFile();
        config.getConfig().set("Last-Server-Stop", time);
        config.saveFile();
    }
}