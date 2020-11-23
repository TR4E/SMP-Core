package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class ListCommand extends Command {

    public ListCommand(final Main instance) {
        super(instance, "list", new String[]{"players", "online"}, Rank.PLAYER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        UtilMessage.message(player, "List", "There is currently " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().stream().filter(player::canSee).count() + ChatColor.GRAY + " players online.");
        UtilMessage.message(player, "List", "Players: [" + getInstance().getClientUtilities().getOnlineClients().values().stream().sorted((o1, o2) -> o2.getRank().compareTo(o1.getRank())).map(c -> c.getRank().getColor() + c.getName()).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "].");
    }

    @Override
    public void help(final Player player) {

    }
}