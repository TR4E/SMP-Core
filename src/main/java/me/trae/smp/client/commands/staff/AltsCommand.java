package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class AltsCommand extends Command {

    public AltsCommand(final Main instance) {
        super(instance, "alts", new String[]{"checkalts", "searchalts"}, Rank.ADMIN, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        if (args.length == 1) {
            final Client target = getInstance().getClientUtilities().searchClient(player, args[0], true);
            if (target == null) {
                return;
            }
            final Set<Client> alts = getInstance().getClientUtilities().getAltsOfClient(target.getUUID());
            if (alts.size() > 0) {
                UtilMessage.message(player, "Alts", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " has " + ChatColor.YELLOW + alts.size() + ChatColor.GRAY + " alternative accounts on this server.");
                UtilMessage.message(player, "Alts", "[" + ChatColor.YELLOW + alts.stream().map(Client::getName).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "].");
            } else {
                UtilMessage.message(player, "Alts", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " has no alternative accounts on this server.");
            }
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Alts", "Usage: " + ChatColor.AQUA + "/alts <client>");
    }
}