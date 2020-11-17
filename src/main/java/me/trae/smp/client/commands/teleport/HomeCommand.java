package me.trae.smp.client.commands.teleport;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class HomeCommand extends Command {

    public HomeCommand(final Main instance) {
        super(instance, "home", new String[]{"h"}, Rank.MEMBER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (args == null || args.length == 0) {
            if (client.getHomeLocation() == null) {
                UtilMessage.message(player, "Home", "You do not have a Home set.");
                return;
            }
            player.teleport(UtilLocation.toCenter(client.getHomeLocation(), UtilLocation.DirectionType.SOUTH));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            getInstance().getTitleManager().sendPlayer(player, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Teleport", ChatColor.WHITE + "You have teleported to your Home.", 2);
            UtilMessage.message(player, "Home", "You have teleported to your Home.");
            return;
        }
        if (!(client.hasRank(Rank.ADMIN, true))) {
            return;
        }
        if (args.length == 1) {
            final Client target = getInstance().getClientUtilities().searchClient(player, args[0], true);
            if (target == null) {
                return;
            }
            if (target.getHomeLocation() == null) {
                UtilMessage.message(player, "Home", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " does not have a Home set.");
                return;
            }
            player.teleport(UtilLocation.toCenter(target.getHomeLocation(), null));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            getInstance().getTitleManager().sendPlayer(player, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Home", ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + "'s Home.", 2);
            UtilMessage.message(player, "Home", "You teleported to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + "'s Home.");
        }
    }

    @Override
    public void help(final Player player) {

    }
}