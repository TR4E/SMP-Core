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
import org.bukkit.entity.Player;

import java.util.List;

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
        UtilMessage.message(player, ChatColor.DARK_GREEN + "IP Aliases: " + ChatColor.WHITE + ips);
        UtilMessage.message(player, ChatColor.DARK_GREEN + "Rank: " + ChatColor.WHITE + UtilFormat.cleanString(target.getRank().name()));
        UtilMessage.message(player, ChatColor.DARK_GREEN + "First Joined: " + ChatColor.WHITE + UtilTime.getTime(System.currentTimeMillis() - target.getFirstJoined(), UtilTime.TimeUnit.BEST, 1));
        if (targetP != null) {
            UtilMessage.message(player, ChatColor.DARK_GREEN + "Last Joined: " + ChatColor.WHITE + UtilTime.getTime(System.currentTimeMillis() - target.getLastJoined(), UtilTime.TimeUnit.BEST, 1));
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
        target.setRank(Rank.getRank(target.getRank().ordinal() + 1));
        getInstance().getClientRepository().updateRank(target);
        Bukkit.getServer().getPluginManager().callEvent(new ClientPromoteEvent(getInstance(), target.getUUID(), target.getRank()));
        UtilMessage.broadcast("Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " promoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.RESET + target.getRank().getTag(true) + ChatColor.GRAY + ".");
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
        Bukkit.getServer().getPluginManager().callEvent(new ClientDemoteEvent(getInstance(), target.getUUID(), target.getRank()));
        target.setRank(Rank.getRank(target.getRank().ordinal() - 1));
        getInstance().getClientRepository().updateRank(target);
        UtilMessage.broadcast("Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " demoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.RESET + target.getRank().getTag(true) + ChatColor.GRAY + ".");
    }
}