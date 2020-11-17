package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.events.ServerStartEvent;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.framework.blockrestore.BlockRestoreData;
import me.trae.smp.framework.update.UpdateEvent;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerListener extends MainListener {

    private final String[] tips;
    private int count;

    public ServerListener(final Main instance) {
        super(instance, true);
        this.tips = new String[]{
                "Want to have more decoration? Type '" + ChatColor.AQUA + "/dye" + ChatColor.GRAY + "' to dye items.",
                "Want to check yours or others Playtime? Type '" + ChatColor.AQUA + "/playtime" + ChatColor.GRAY + "' to check Playtime.",
                "Type '" + ChatColor.AQUA + "/help" + ChatColor.GRAY + "' for a list of commands.",
                "Want to check a player's statistics? Type '" + ChatColor.AQUA + "/stats" + ChatColor.GRAY + "' to check Player Statistics."
        };
        this.count = 0;
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
        for (final BlockRestoreData data : getInstance().getBlockRestoreUtilities().getDataList()) {
            if (data != null) {
                data.restore();
            }
        }
        getInstance().getRepository().setLastServerStop();
        UtilMessage.log("SMP-Core", ChatColor.RED + "Plugin Disabled!");
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(getInstance(), 20L);
    }

    @EventHandler
    public void onUpdate(final UpdateEvent e) {
        if (e.getType() == Updater.UpdateType.MIN_05) {
            if (Bukkit.getOnlinePlayers().size() <= 0) {
                return;
            }
            if (this.count >= this.tips.length - 1) {
                this.count = 0;
            }
            if (this.tips.length > 0) {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 2.0F);
                    UtilMessage.message(player, "Tips", tips[count]);
                }
            }
            this.count++;
        }
    }
}