package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.gamer.Gamer;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StatsCommand extends Command {

    public StatsCommand(final Main instance) {
        super(instance, "statistics", new String[]{"stats", "info"}, Rank.MEMBER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            statistics(player, player);
            return;
        }
        if (args.length == 1) {
            final Player target = UtilPlayer.searchPlayer(player, args[0], true);
            if (target == null) {
                return;
            }
            statistics(player, target);
        }
    }

    @Override
    public void help(final Player player) {

    }

    private void statistics(final Player player, final Player target) {
        final Gamer gamer = getInstance().getGamerUtilities().getGamer(target.getUniqueId());
        if (gamer != null) {
            UtilMessage.message(player, "Gamer", (target.equals(player) ? "Your Statistics:" : "Statistics of " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ":"));
            UtilMessage.message(player, ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + gamer.getBlocksBroken());
            UtilMessage.message(player, ChatColor.GREEN + "Blocks Placed: " + ChatColor.WHITE + gamer.getBlocksPlaced());
            UtilMessage.message(player, ChatColor.GREEN + "Kills: " + ChatColor.WHITE + gamer.getKills());
            UtilMessage.message(player, ChatColor.GREEN + "Deaths: " + ChatColor.WHITE + gamer.getDeaths());
            UtilMessage.message(player, ChatColor.GREEN + "Joins: " + ChatColor.WHITE + gamer.getJoins());
        }
    }
}