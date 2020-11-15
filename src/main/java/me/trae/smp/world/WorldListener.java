package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.gamer.Gamer;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldListener extends MainListener {

    public WorldListener(final Main instance) {
        super(instance, true);
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
    public void onPlayerDamage(final EntityDamageEvent e) {
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
    public void onPlayerDamageByPlayer(final EntityDamageByEntityEvent e) {
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
}