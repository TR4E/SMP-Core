package me.trae.smp.client.commands.teleport.tpa;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TPAAcceptCommand extends Command {

    public TPAAcceptCommand(final Main instance) {
        super(instance, "tpaccept", new String[]{}, Rank.MEMBER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            final Client target = getInstance().getClientUtilities().getOnlineClient(client.getTPA());
            if (target == null) {
                UtilMessage.message(player, "TPA", "You have not yet received a teleport request.");
                return;
            }
            final Player targetP = Bukkit.getPlayer(target.getUUID());
            if (targetP == null) {
                return;
            }
            targetP.teleport(UtilLocation.toCenter(player.getLocation(), null));
            targetP.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            getInstance().getTitleManager().sendPlayer(targetP, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Teleport", ChatColor.WHITE + "You have teleported to " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + ".", 2);
            client.setTPA(null);
            UtilMessage.message(player, "TPA", "You have accepted the teleport request from " + ChatColor.YELLOW + targetP.getName() + ChatColor.GRAY + ".");
            UtilMessage.message(targetP, "TPA", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has accepted your teleport request.");
        }
    }

    @Override
    public void help(final Player player) {

    }
}