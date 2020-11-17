package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.client.events.ClientDemoteEvent;
import me.trae.smp.client.events.ClientPromoteEvent;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import me.trae.smp.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ClientCommand extends Command {

    public ClientCommand(final Main instance) {
        super(instance, "client", new String[]{}, Rank.MOD, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        if (args[0].equalsIgnoreCase("search")) {
            searchCommand(player, args);
        } else if (args[0].equalsIgnoreCase("promote")) {
            promoteCommand(player, args);
        } else if (args[0].equalsIgnoreCase("demote")) {
            demoteCommand(player, args);
        } else if (args[0].equalsIgnoreCase("list")) {
            listCommand(player, args);
        } else {
            help(player);
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Client", "Commands List:");
        UtilMessage.message(player, ChatColor.AQUA + "/client search <client>" + ChatColor.GRAY + " - " + "Search a Client.");
        UtilMessage.message(player, ChatColor.AQUA + "/client promote <client>" + ChatColor.GRAY + " - " + "Promote a Client.");
        UtilMessage.message(player, ChatColor.AQUA + "/client demote <client>" + ChatColor.GRAY + " - " + "Demote a Client.");
    }

    private void searchCommand(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(client.hasRank(Rank.MOD, true))) {
            return;
        }
        if (args.length == 1) {
            UtilMessage.message(player, "Client", "Usage: " + ChatColor.AQUA + "/client search <client>");
            return;
        }
        final Client target = getInstance().getClientUtilities().searchClient(player, args[1], true);
        if (target == null) {
            return;
        }
        final Player targetP = Bukkit.getPlayer(target.getUUID());
        UtilMessage.message(player, "Client Search", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " Information:");
        UtilMessage.message(player, ChatColor.DARK_GREEN + "Profile: " + ChatColor.WHITE + "https://mine.ly/" + target.getName());
        UtilMessage.message(player, ChatColor.DARK_GREEN + "UUID: " + ChatColor.WHITE + target.getUUID().toString());

        final List<String> ips = target.getIpAddresses();
        if (targetP != null) {
            UtilMessage.message(player, ChatColor.DARK_GREEN + "IP Address: " + ChatColor.WHITE + UtilPlayer.getIP(targetP));
            ips.remove(UtilPlayer.getIP(targetP));
        }
        if (ips.size() > 0) {
            UtilMessage.message(player, ChatColor.DARK_GREEN + "IP Aliases: " + ChatColor.WHITE + ips);
        }
        UtilMessage.message(player, ChatColor.DARK_GREEN + "Rank: " + ChatColor.WHITE + UtilFormat.cleanString(target.getRank().name()));
        UtilMessage.message(player, ChatColor.DARK_GREEN + "First Joined: " + ChatColor.WHITE + UtilTime.getTime(System.currentTimeMillis() - target.getFirstJoined(), UtilTime.TimeUnit.BEST, 1) + " ago");
        if (targetP != null) {
            UtilMessage.message(player, ChatColor.DARK_GREEN + "Last Joined: " + ChatColor.WHITE + UtilTime.getTime(System.currentTimeMillis() - target.getLastJoined(), UtilTime.TimeUnit.BEST, 1) + " ago");
        }
        if (targetP == null) {
            UtilMessage.message(player, ChatColor.DARK_GREEN + "Last Online: " + ChatColor.WHITE + UtilTime.getTime(System.currentTimeMillis() - target.getLastOnline(), UtilTime.TimeUnit.BEST, 1));
        }
        UtilMessage.message(player, ChatColor.DARK_GREEN + "Playtime: " + ChatColor.WHITE + target.getPlaytimeString());
    }

    private void promoteCommand(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(client.hasRank(Rank.OWNER, true))) {
            return;
        }
        if (args.length == 1) {
            UtilMessage.message(player, "Client", "Usage: " + ChatColor.AQUA + "/client promote <client>");
            return;
        }
        final Client target = getInstance().getClientUtilities().searchClient(player, args[1], true);
        if (target == null) {
            return;
        }
        if (target.getRank().equals(Rank.OWNER)) {
            UtilMessage.message(player, "Client", "You cannot promote " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " any further.");
            return;
        }
        target.setRank(Rank.getRank(target.getRank().getId() + 1));
        getInstance().getClientRepository().updateRank(target);
        Bukkit.getServer().getPluginManager().callEvent(new ClientPromoteEvent(getInstance(), target.getUUID(), target.getRank()));
        final Player targetP = Bukkit.getPlayer(target.getUUID());
        if (targetP != null) {
            targetP.playSound(targetP.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
        }
        UtilMessage.broadcast("Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " promoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.RESET + target.getRank().getTag(true) + ChatColor.GRAY + ".", null);
    }

    private void demoteCommand(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(client.hasRank(Rank.OWNER, true))) {
            return;
        }
        if (args.length == 1) {
            UtilMessage.message(player, "Client", "Usage: " + ChatColor.AQUA + "/client demote <client>");
            return;
        }
        final Client target = getInstance().getClientUtilities().searchClient(player, args[1], true);
        if (target == null) {
            return;
        }
        if (target.getRank().equals(Rank.PLAYER)) {
            UtilMessage.message(player, "Client", "You cannot demote " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " any further.");
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClientDemoteEvent(getInstance(), target.getUUID(), target.getRank()));
        target.setRank(Rank.getRank(target.getRank().getId() - 1));
        getInstance().getClientRepository().updateRank(target);
        final Player targetP = Bukkit.getPlayer(target.getUUID());
        if (targetP != null) {
            targetP.playSound(targetP.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
        }
        UtilMessage.broadcast("Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " demoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.RESET + target.getRank().getTag(true) + ChatColor.GRAY + ".", null);
    }

    private void listCommand(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(client.hasRank(Rank.OWNER, true))) {
            return;
        }
        if (args.length == 1) {
            UtilMessage.message(player, "Clients", "Showing a List of " + ChatColor.YELLOW + getInstance().getClientUtilities().getClients().size() + ChatColor.GRAY + " Clients:");
            UtilMessage.message(player, "Clients", "[" + ChatColor.YELLOW + getInstance().getClientUtilities().getClients().values().stream().map(c -> c.getRank().getColor() + c.getName()).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "].");
        }
    }
}