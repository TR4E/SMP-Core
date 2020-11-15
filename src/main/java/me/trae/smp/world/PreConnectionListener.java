package me.trae.smp.world;

import me.trae.smp.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PreConnectionListener implements Listener {

    private final Main instance;

    public PreConnectionListener(final Main instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPreLogin(final PlayerLoginEvent e) {
        if (!(instance.hasStarted())) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.WHITE + "Server has not finished starting up.");
        }
    }
}