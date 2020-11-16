package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.framework.update.UpdateEvent;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.gamer.Gamer;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener extends MainListener {

    public WorldListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onUpdate(final UpdateEvent e) {
        if (e.getType() == Updater.UpdateType.SEC_01) {
            if (getInstance().getRepository().isAlwaysDay()) {
                for (final World world : Bukkit.getWorlds()) {
                    if (world != null) {
                        world.setTime(0L);
                    }
                }
            } else if (getInstance().getRepository().isAlwaysNight()) {
                for (final World world : Bukkit.getWorlds()) {
                    if (world != null) {
                        world.setTime(18000L);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            e.setCancelled(true);
            UtilMessage.message(player, "Game", "You are not allowed to break blocks.");
            return;
        }
        final Gamer gamer = getInstance().getGamerUtilities().getGamer(player.getUniqueId());
        if (gamer == null) {
            return;
        }
        gamer.setBlocksBroken(gamer.getBlocksBroken() + 1);
        getInstance().getGamerRepository().updateBlocksBroken(gamer);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(final BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            e.setCancelled(true);
            UtilMessage.message(player, "Game", "You are not allowed to place blocks.");
            return;
        }
        final Gamer gamer = getInstance().getGamerUtilities().getGamer(player.getUniqueId());
        if (gamer == null) {
            return;
        }
        gamer.setBlocksPlaced(gamer.getBlocksPlaced() + 1);
        getInstance().getGamerRepository().updateBlocksPlaced(gamer);
    }

    @EventHandler
    public void onInventoryOpen(final InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            final Player player = (Player) e.getPlayer();
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            if (!(getInstance().getClientUtilities().isTrusted(player))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByPlayer(final EntityDamageByEntityEvent e) {
        if (!(getInstance().getRepository().isPvP())) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getDamager() instanceof Player) {
            final Player player = (Player) e.getDamager();
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            if (!(getInstance().getClientUtilities().isTrusted(player))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) {
            return;
        }
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(final EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player) e.getEntity();
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            if (!(getInstance().getClientUtilities().isTrusted(player))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerItemDrop(final PlayerDropItemEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        e.setDeathMessage(null);
        final Player player = e.getEntity().getPlayer();
        if (player != null) {
            final Player killer = e.getEntity().getKiller();
            if (killer != null) {
                getInstance().getGamerUtilities().incDeaths(player.getUniqueId());
                getInstance().getGamerUtilities().incKills(killer.getUniqueId());
                final String item = UtilFormat.cleanString(killer.getInventory().getItemInMainHand().getType().name());
                UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " with " + ChatColor.GREEN + item + ChatColor.GRAY + ".");
                return;
            }
            final EntityDamageEvent cause = player.getLastDamageCause();
            if (cause != null) {
                UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + UtilFormat.cleanString(cause.getCause().name()) + ChatColor.GRAY + ".");
                return;
            }
            UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has died.");
        }
    }

    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent e) {
        if (!(getInstance().getRepository().isWeather())) {
            e.setCancelled(true);
            for (final World world : Bukkit.getWorlds()) {
                if (world != null) {
                    world.setStorm(false);
                    world.setThundering(false);
                }
            }
        }
    }
}