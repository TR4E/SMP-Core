package me.trae.smp.client.commands.teleport;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {

    public TeleportCommand(final Main instance) {
        super(instance, "teleport", new String[]{"tp"}, Rank.MOD, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        final Player target = UtilPlayer.searchPlayer(player, args[0], true);
        if (target == null) {
            return;
        }
        if (args.length == 1) {
            player.teleport(target);
            UtilMessage.message(player, "Teleport", "You teleported to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
            return;
        }
        if (args.length == 2) {
            final Player target2 = UtilPlayer.searchPlayer(player, args[1], true);
            if (target2 == null) {
                return;
            }
            target.teleport(target2);
            UtilMessage.message(player, "Teleport", "You teleported " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.YELLOW + target2.getName() + ChatColor.GRAY + ".");
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Teleport", "Usage: " + ChatColor.AQUA + "/tp <player> <target>");
        UtilMessage.message(player, "Teleport", "Usage: " + ChatColor.AQUA + "/tp <player>");
    }
}