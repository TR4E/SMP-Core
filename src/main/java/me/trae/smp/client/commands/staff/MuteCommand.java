package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MuteCommand extends Command {

    public MuteCommand(final Main instance) {
        super(instance, "mute", new String[]{}, Rank.MOD, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        if (args.length == 1) {
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            final Client target = getInstance().getClientUtilities().searchClient(player, args[0], true);
            if (target == null) {
                return;
            }
            if (target.getUUID().equals(player.getUniqueId())) {
                UtilMessage.message(player, "Punish", "You cannot mute yourself.");
                return;
            }
            if (target.getRank().getId() >= client.getRank().getId()) {
                UtilMessage.message(player, "Punish", "You do not outrank " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                return;
            }
            target.setMuted(!(target.isMuted()));
            UtilMessage.broadcast("Punish", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + (target.isMuted() ? " muted " : " un-muted ") + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Punish", "Usage: " + ChatColor.AQUA + "/mute <player>");
    }
}