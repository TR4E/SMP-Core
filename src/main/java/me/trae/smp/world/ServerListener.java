package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.events.ServerStartEvent;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerListener extends MainListener {

    public ServerListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onServerStart(final ServerStartEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                getInstance().setStarted(true);
                getInstance().getRepository().setLastServerStart();
                UtilMessage.log("SMP", ChatColor.GREEN + "Plugin Enabled!");
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
        Bukkit.shutdown();
    }
}