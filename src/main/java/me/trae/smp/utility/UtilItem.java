package me.trae.smp.utility;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
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
            meta.setDisplayName(ChatColor.YELLOW + getItemNameWithoutColor(item));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static String getItemNameWithoutColor(final ItemStack item) {
        String name = UtilFormat.cleanString(item.getType().name());
        for (final DyeColor dye : DyeColor.values()) {
            final String dyename = UtilFormat.cleanString(dye.name());
            if (name.contains(dyename)) {
                name = name.replaceAll(dyename + " ", "");
            }
        }
        return name;
    }
}