package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PingCommand extends Command {

    public PingCommand(final Main instance) {
        super(instance, "ping", new String[]{}, Rank.PLAYER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        if (args.length == 1) {
            final Player target = UtilPlayer.searchPlayer(player, args[0], true);
            if (target == null) {
                return;
            }
            if (target.equals(player)) {
                help(player);
                return;
            }
            UtilMessage.message(player, "Ping", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + "'s ping is: " + ChatColor.GREEN + UtilPlayer.getPing(target) + "ms" + ChatColor.GRAY + ".");
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Ping", "You ping is: " + ChatColor.GREEN + UtilPlayer.getPing(player) + "ms" + ChatColor.GRAY + ".");
    }
}