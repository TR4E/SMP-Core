package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.events.ServerStartEvent;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerListener extends MainListener {

    public ServerListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onServerListPing(final ServerListPingEvent e) {
        e.setMaxPlayers(100);
        if (getInstance().getRepository().getServerMOTD().length() > 0) {
            e.setMotd(ChatColor.translateAlternateColorCodes('&', getInstance().getRepository().getServerMOTD()));
        }
    }

    @EventHandler
    public void onServerStart(final ServerStartEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                getInstance().setStarted(true);
                getInstance().getRepository().setLastServerStart();
                UtilMessage.log("Server", "The Server was last online " + ChatColor.GREEN + UtilTime.getTime(System.currentTimeMillis() - getInstance().getRepository().getLastServerStop(), UtilTime.TimeUnit.BEST, 1) + ChatColor.GRAY + " ago.");
                UtilMessage.log("SMP-Core", ChatColor.GREEN + "Plugin Enabled!");
            }
        }.runTaskLater(getInstance(), 10L);
    }

    @EventHandler
    public void onServerStop(final ServerStopEvent e) {
        getInstance().setStarted(false);
        if (getInstance().isStoppedByForce()) {
            Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer(ChatColor.WHITE + "Server is stopping or restarting!"));
        }
        getInstance().getRepository().setLastServerStop();
        UtilMessage.log("SMP-Core", ChatColor.RED + "Plugin Disabled!");
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(getInstance(), 10L);
    }
}