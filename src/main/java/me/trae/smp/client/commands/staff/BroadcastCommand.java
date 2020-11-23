package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastCommand extends Command {

    public BroadcastCommand(final Main instance) {
        super(instance, "broadcast", new String[]{"bc"}, Rank.DEVELOPER, true);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        UtilMessage.broadcast(ChatColor.translateAlternateColorCodes('&', UtilFormat.getFinalArg(args, 0)), null);
    }

    @Override
    public void help(final Player player) {
        UtilMessage.message(player, "Broadcast", "Usage: " + ChatColor.AQUA + "/broadcast <message>");
    }
}