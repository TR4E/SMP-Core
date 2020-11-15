package me.trae.smp.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UtilPlayer {

    public static int getPing(final Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

    public static String getIP(final Player player) {
        return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
    }

    public static Player searchPlayer(final Player player, final String name, final boolean inform) {
        if (Bukkit.getOnlinePlayers().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name))) {
            return Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
        }
        final List<Player> list = Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).map(p -> (Player) p).collect(Collectors.toList());
        if (list.size() == 1) {
            return list.get(0);
        } else if (inform) {
            UtilMessage.message(player, "Player Search", ChatColor.YELLOW.toString() + list.size() + ChatColor.GRAY + " matches found [" + ChatColor.YELLOW + ((list.size() == 0) ? name : list.stream().map(HumanEntity::getName).collect(Collectors.joining(ChatColor.GRAY + ", "))) + "].");
        }
        return null;
    }

    public static void clearInventory(final Player player) {
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }
}