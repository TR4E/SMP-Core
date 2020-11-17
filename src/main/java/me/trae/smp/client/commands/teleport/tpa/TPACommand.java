package me.trae.smp.client.commands.teleport.tpa;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TPACommand extends Command {

    public TPACommand(final Main instance) {
        super(instance, "tpa", new String[]{}, Rank.MEMBER, false);
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
            final Client targetC = getInstance().getClientUtilities().getOnlineClient(target.getUniqueId());
            if (targetC == null) {
                return;
            }
            if (target.equals(player)) {
                UtilMessage.message(player, "TPA", "You cannot send a teleport request to yourself.");
                return;
            }
            targetC.setTPA(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (targetC.getTPA() != null && targetC.getTPA().equals(player.getUniqueId())) {
                        targetC.setTPA(null);
                        UtilMessage.message(player, "TPA", "Your Teleport request to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " has expired.");
                        UtilMessage.message(target, "TPA", "The Teleport request from " + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has expired.");
                    }
                }
            }.runTaskLater(getInstance(), 6000L);
            UtilMessage.message(player, "TPA", "You have sent a teleport request to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
            UtilMessage.message(target, "TPA", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has requested to teleport to You.");
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "TPA", "Usage: " + ChatColor.AQUA + "/tpa <player>");
    }
}