package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlaytimeCommand extends Command {

    public PlaytimeCommand(final Main instance) {
        super(instance, "playtime", new String[]{}, Rank.MEMBER, false);
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
            if (target.getUUID().equals(player.getUniqueId())) {
                help(player);
                return;
            }
            UtilMessage.message(player, "Playtime", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " has played for " + ChatColor.GREEN + target.getPlaytimeString() + ChatColor.GRAY + ".");
        }
    }

    @Override
    public void help(final Player player) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client != null) {
            UtilMessage.message(player, "Playtime", "You have played for " + ChatColor.GREEN + client.getPlaytimeString() + ChatColor.GRAY + ".");
        }
    }
}