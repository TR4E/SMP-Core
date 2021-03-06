package me.trae.smp.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class UtilMessage {

    public static void message(final Player player, final String prefix, final String message) {
        player.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static void message(final Player player, final String message) {
        player.sendMessage(message);
    }

    public static void broadcast(final String prefix, final String message, final UUID[] ignore) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (ignore != null && Arrays.asList(ignore).contains(player.getUniqueId())) {
                continue;
            }
            message(player, prefix, message);
        }
    }

    public static void broadcast(final String message, final UUID[] ignore) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (ignore != null && Arrays.asList(ignore).contains(player.getUniqueId())) {
                continue;
            }
            message(player, message);
        }
    }

    public static void log(final String prefix, final String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static void log(final String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
}