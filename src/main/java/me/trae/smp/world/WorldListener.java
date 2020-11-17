package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.framework.update.UpdateEvent;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilItem;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.*;
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
        }
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
        }
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
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if (!(getInstance().getRepository().isPvP())) {
                e.setCancelled(true);
                return;
            }
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
                return;
            }
            e.getItem().setItemStack(UtilItem.updateNames(e.getItem().getItemStack()));
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDeath(final PlayerDeathEvent e) {
        e.setDeathMessage(null);
        final Player player = e.getEntity().getPlayer();
        if (player == null) {
            return;
        }
        final EntityDamageEvent cause = player.getLastDamageCause();
        if (player.getLastDamageCause() == null || cause == null) {
            return;
        }
        if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return;
        }
        if (player.getKiller() != null) {
            if (player == player.getKiller()) {
                UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has died.", null);
                return;
            }
        }
        String name = "";
        if (player.getLastDamageCause().getEntityType() != EntityType.PLAYER) {
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                final EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent) player.getLastDamageCause();
                name = ChatColor.GRAY + "a " + ChatColor.YELLOW + UtilFormat.cleanString(entity.getDamager().getName());
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                final EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent) player.getLastDamageCause();
                if (entity.getDamager() instanceof Arrow) {
                    final Arrow a = (Arrow) entity.getDamager();
                    if (a.getShooter() instanceof LivingEntity) {
                        name = ChatColor.GRAY + (a.getShooter() instanceof Player ? "" : "a ") + ChatColor.YELLOW + ((LivingEntity) a.getShooter()).getName();
                    }
                }
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                final EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent) player.getLastDamageCause();
                name = ChatColor.GRAY + ((entity.getDamager() instanceof Creeper || entity.getDamager() instanceof Wither) ? "a " : "") + ChatColor.YELLOW + UtilFormat.cleanString(entity.getDamager().getName());
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                name = ChatColor.YELLOW + "Fire";
            } else {
                name = ChatColor.YELLOW + UtilFormat.cleanString(player.getLastDamageCause().getCause().name());
            }
            UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + name + ChatColor.GRAY + ".", null);
            return;
        }
        if (player.getKiller() != null) {
            final Player killer = player.getKiller();
            if (killer != player) {
                UtilMessage.broadcast("Death", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " with " + ChatColor.GREEN + UtilFormat.cleanString(killer.getInventory().getItemInMainHand().getType().name()) + ChatColor.GRAY + ".", null);
            }
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