package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TrustCommand extends Command {

    public TrustCommand(final Main instance) {
        super(instance, "trust", new String[]{}, Rank.MOD, false);
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
            if (!(client.hasRank(Rank.OWNER, false))) {
                if (target.getRank().ordinal() >= client.getRank().ordinal()) {
                    UtilMessage.message(player, "Client", "You do not outrank " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                    return;
                }
            }
            if (target.hasRank(Rank.STREAMER, false)) {
                UtilMessage.message(player, "Client", "You cannot change the rank for " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                return;
            }
            if (target.getRank().equals(Rank.MEMBER)) {
                target.setRank(Rank.PLAYER);
            } else {
                target.setRank(Rank.MEMBER);
            }
            UtilMessage.broadcast("Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + (target.getRank().equals(Rank.MEMBER) ? " trusted " : " un-trusted ") + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".", null);
        }
    }

    @Override
    public void help(final Player player) {

    }
}