package me.trae.smp.gamer;

import me.trae.smp.Main;
import me.trae.smp.module.MainListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GamerManager extends MainListener {

    public GamerManager(final Main instance) {
        super(instance, true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        final Player player = e.getPlayer();
        if (!(player.getGameMode().equals(GameMode.SURVIVAL))) {
            return;
        }
        getInstance().getGamerUtilities().incBlocksBroken(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(final BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        final Player player = e.getPlayer();
        if (!(player.getGameMode().equals(GameMode.SURVIVAL))) {
            return;
        }
        getInstance().getGamerUtilities().incBlocksPlaced(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(final PlayerDeathEvent e) {
        final Player player = e.getEntity().getPlayer();
        if (player != null && player.getGameMode().equals(GameMode.SURVIVAL)) {
            getInstance().getGamerUtilities().incDeaths(player.getUniqueId());
        }
        final Player killer = e.getEntity().getKiller();
        if (killer != null && killer.getGameMode().equals(GameMode.SURVIVAL)) {
            getInstance().getGamerUtilities().incKills(killer.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        getInstance().getGamerUtilities().incJoins(player.getUniqueId());
    }
}