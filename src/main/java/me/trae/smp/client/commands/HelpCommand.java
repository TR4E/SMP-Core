package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends Command {

    public HelpCommand(final Main instance) {
        super(instance, "help", new String[]{}, Rank.PLAYER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            if (!(getInstance().getClientUtilities().isTrusted(player))) {
                UtilMessage.message(player, "Help", "You are not allowed to view the list of commands!");
                return;
            }
            help(player);
        }
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Help", "Commands List:");
        sendMessage(player, "/smpreload", "Reload Configurations.", Rank.OWNER);
        sendMessage(player, "/client", "Client Management.", Rank.MOD);
        sendMessage(player, "/mute <player>", "Mute a Player.", Rank.MEMBER);
        sendMessage(player, "/playtime <player>", "Show Playtime.", Rank.MEMBER);
        sendMessage(player, "/ping <player>", "Show Ping.", Rank.MEMBER);
        sendMessage(player, "/stats <player>", "Show Statistics.", Rank.MEMBER);
        sendMessage(player, "/list", "Show Online Players.", Rank.MEMBER);
        sendMessage(player, "/sethome", "Create a Home.", Rank.MEMBER);
        sendMessage(player, "/delhome", "Delete a Home.", Rank.MEMBER);
        sendMessage(player, "/home", "Teleport to Home.", Rank.MEMBER);
    }

    private void sendMessage(final Player player, final String cmd, final String description, final Rank requiredRank) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client != null && client.hasRank(requiredRank, false)) {
            UtilMessage.message(player, requiredRank.getColor() + cmd + ChatColor.GRAY + " - " + description);
        }
    }
}