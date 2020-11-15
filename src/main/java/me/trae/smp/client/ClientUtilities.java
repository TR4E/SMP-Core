package me.trae.smp.client;

import me.trae.smp.Main;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientUtilities {

    private final Main instance;
    private final Map<UUID, Client> clients = new HashMap<>();
    private final Map<UUID, Client> onlineclients = new HashMap<>();

    public ClientUtilities(final Main instance) {
        this.instance = instance;
    }

    public void addClient(final Client client) {
        getClients().put(client.getUUID(), client);
    }

    public void removeClient(final Client client) {
        getClients().remove(client.getUUID());
    }

    public final Client getClient(final UUID uuid) {
        return getClients().get(uuid);
    }

    public final Map<UUID, Client> getClients() {
        return clients;
    }

    public void addOnlineClient(final Client client) {
        getOnlineClients().put(client.getUUID(), client);
    }

    public void removeOnlineClient(final Client client) {
        getOnlineClients().remove(client.getUUID());
    }

    public final Client getOnlineClient(final UUID uuid) {
        return getOnlineClients().get(uuid);
    }

    public final Client tryGetOnlineClient(final UUID uuid) {
        return (Bukkit.getPlayer(uuid) != null ? getOnlineClient(uuid) : getClient(uuid));
    }

    public final Map<UUID, Client> getOnlineClients() {
        return onlineclients;
    }

    public final Client searchClient(final Player player, final String name, final boolean inform) {
        if (getClients().values().stream().anyMatch(c -> c.getName().equalsIgnoreCase(name))) {
            return getClients().values().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().get();
        }
        final List<Client> list = this.clients.values().parallelStream().filter(c -> c.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        if (list.size() == 1) {
            return list.get(0);
        } else if (inform) {
            UtilMessage.message(player, "Client Search", ChatColor.YELLOW.toString() + list.size() + ChatColor.GRAY + " matches found [" + ChatColor.YELLOW + ((list.size() == 0) ? name : list.stream().map(c -> ChatColor.YELLOW + c.getName()).collect(Collectors.joining(ChatColor.GRAY + ", "))) + ChatColor.GRAY + "].");
        }
        return null;
    }

    public void messageStaff(final String prefix, final String message, final Rank minimumRank, final UUID[] ignore) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Client client = getOnlineClient(player.getUniqueId());
            if (client != null && client.hasRank(minimumRank, false)) {
                UtilMessage.message(player, prefix, message);
            }
        }
    }

    public void messageStaff(final String message, final Rank minimumRank, final UUID[] ignore) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Client client = getOnlineClient(player.getUniqueId());
            if (client != null && client.hasRank(minimumRank, false)) {
                UtilMessage.message(player, message);
            }
        }
    }

    public final boolean isTrusted(final Player player) {
        if (player.isOp()) {
            return true;
        }
        final Client client = getOnlineClient(player.getUniqueId());
        return !(client.getRank().equals(Rank.PLAYER));
    }
}