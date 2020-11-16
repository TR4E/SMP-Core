package me.trae.smp.utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UtilItem {

    public static ItemStack updateNames(final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        final List<String> lore = new ArrayList<>();
        if (item.getType().equals(Material.TNT)) {
            meta.setDisplayName(ChatColor.RED + "TNT");
        } else {
            meta.setDisplayName(ChatColor.YELLOW + UtilFormat.cleanString(item.getType().name()));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}