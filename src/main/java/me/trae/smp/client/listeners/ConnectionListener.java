package me.trae.smp.client.listeners;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.gamer.Gamer;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
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

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent e) {
        if (!(getInstance().hasStarted())) {
            return;
        }
        final Player player = e.getPlayer();
        Client client = getInstance().getClientUtilities().getClient(player.getUniqueId());
        if (client == null) {
            client = new Client(player.getUniqueId());
            client.setName(player.getName());
            client.getIpAddresses().add(UtilPlayer.getIP(player));
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
            if (!(client.getIpAddresses().contains(UtilPlayer.getIP(player)))) {
                client.getIpAddresses().add(UtilPlayer.getIP(player));
                getInstance().getClientRepository().updateIP(client);
            }
        }
        if (client.hasRank(Rank.OWNER, false)) {
            e.allow();
        }
    }

    @EventHandler
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
        UtilMessage.broadcast(ChatColor.GREEN + (client.isNewClient() ? "New> " : "Join> ") + ChatColor.GRAY + player.getName());
        client.setNewClient(false);
        client.setLastJoined(System.currentTimeMillis());
        getInstance().getClientRepository().updateLastJoined(client);
        getInstance().getClientUtilities().addOnlineClient(client);
        getInstance().getGamerRepository().loadGamer(player.getUniqueId());
        final Gamer gamer = getInstance().getGamerUtilities().getGamer(player.getUniqueId());
        if (gamer != null) {
            gamer.setJoins(gamer.getJoins() + 1);
            getInstance().getGamerRepository().updateJoins(gamer);
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            player.setGameMode(GameMode.ADVENTURE);
        } else {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginAuthorJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (player.getUniqueId().equals(UUID.fromString("213bae9b-bbe1-4839-a74b-a59da8743062"))) {
            UtilMessage.message(player, "Server", "This Server is currently using your plugin " + ChatColor.GREEN + "SMP-Core" + ChatColor.GRAY + ".");
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        e.setQuitMessage(null);
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        getInstance().getClientUtilities().removeOnlineClient(client);
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
        UtilMessage.broadcast(ChatColor.RED + "Quit> " + ChatColor.GRAY + player.getName());
    }
}