package me.trae.smp.module;

import me.trae.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class MainListener implements Listener {

    private final Main instance;
    private final boolean enabled;

    public MainListener(final Main instance, final boolean enabled) {
        this.instance = instance;
        this.enabled = enabled;
        if (enabled) {
            Bukkit.getServer().getPluginManager().registerEvents(this, instance);
        }
    }

    protected final Main getInstance() {
        return instance;
    }

    protected final boolean isEnabled() {
        return enabled;
    }
}