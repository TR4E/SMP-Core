package me.trae.smp.client.listeners;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.gamer.Gamer;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilItem;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ConnectionListener extends MainListener {

    public ConnectionListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(final PlayerLoginEvent e) {
        if (!(getInstance().hasStarted())) {
            return;
        }
        final Player player = e.getPlayer();
        Client client = getInstance().getClientUtilities().getClient(player.getUniqueId());
        if (client == null) {
            client = new Client(player.getUniqueId());
            client.setName(player.getName());
            client.getIpAddresses().add(e.getAddress().getHostAddress());
            client.setNewClient(true);
            getInstance().getClientRepository().saveClient(client);
            getInstance().getClientUtilities().addClient(client);
            getInstance().getGamerRepository().saveGamer(new Gamer(player.getUniqueId()));
        } else {
            if (!(client.getName().equals(player.getName()))) {
                client.setOldName(client.getName());
                getInstance().getClientRepository().updateOldName(client);
                client.setName(player.getName());
                getInstance().getClientRepository().updateName(client);
            }
            if (!(client.getIpAddresses().contains(e.getAddress().getHostAddress()))) {
                client.getIpAddresses().add(e.getAddress().getHostAddress());
                getInstance().getClientRepository().updateIP(client);
            }
        }
        if (client.hasRank(Rank.OWNER, false)) {
            e.allow();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (client.getFirstJoined() == 0L) {
            client.setFirstJoined(System.currentTimeMillis());
            getInstance().getClientRepository().updateFirstJoined(client);
        }
        UtilMessage.broadcast(ChatColor.GREEN + (client.isNewClient() ? "New> " : "Join> ") + ChatColor.GRAY + player.getName(), null);
        getInstance().getGamerRepository().loadGamer(player.getUniqueId());
        client.setLastJoined(System.currentTimeMillis());
        getInstance().getClientRepository().updateLastJoined(client);
        getInstance().getClientUtilities().addOnlineClient(client);
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            player.setGameMode(GameMode.ADVENTURE);
        } else {
            player.setGameMode(GameMode.SURVIVAL);
        }
        player.setPlayerListName(client.getDisplayName());
        UtilItem.updateItems(player.getInventory());
        getInstance().getTitleManager().sendPlayer(player, (client.isNewClient() ? ChatColor.RED.toString() + ChatColor.BOLD + "Welcome" : ChatColor.RED.toString() + ChatColor.BOLD + "Welcome back"), " ", 2);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinMonitor(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (player.getUniqueId().equals(UUID.fromString("213bae9b-bbe1-4839-a74b-a59da8743062"))) {
            UtilMessage.message(player, "Server", "This Server is currently using your plugin " + ChatColor.GREEN + "SMP-Core" + ChatColor.GRAY + ".");
        }
        if (!(client.isNewClient())) {
            UtilMessage.message(player, "Playtime", "You have played for " + ChatColor.GREEN + client.getPlaytimeString() + ChatColor.GRAY + ".");
        } else {
            client.setNewClient(false);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        e.setQuitMessage(null);
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        for (final Client c : getInstance().getClientUtilities().getOnlineClients().values()) {
            if (c.getTPA() != null && c.getTPA().equals(player.getUniqueId())) {
                c.setTPA(null);
            }
        }
        client.setTPA(null);
        if (UtilLocation.isBadLocation(player.getLocation())) {
            player.teleport(UtilLocation.toCenter(player.getWorld().getSpawnLocation(), UtilLocation.DirectionType.NORTH));
        }
        client.setLastOnline(System.currentTimeMillis());
        getInstance().getClientRepository().updateLastOnline(client);
        client.setPlaytime(client.getPlaytime() + (client.getLastOnline() - client.getLastJoined()));
        getInstance().getClientRepository().updatePlaytime(client);
        final Gamer gamer = getInstance().getGamerUtilities().getGamer(player.getUniqueId());
        if (gamer != null) {
            getInstance().getGamerUtilities().removeGamer(gamer);
        }
        UtilMessage.broadcast(ChatColor.RED + "Quit> " + ChatColor.GRAY + player.getName(), null);
    }
}