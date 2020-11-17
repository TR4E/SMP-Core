package me.trae.smp.utility;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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

    public static void updateItems(final Inventory inv) {
        if (inv.getContents().length > 0) {
            for (final ItemStack item : inv.getContents()) {
                if (item != null) {
                    item.setItemMeta(UtilItem.updateNames(item).getItemMeta());
                }
            }
        }
        if (inv.getType().equals(InventoryType.PLAYER)) {
            final PlayerInventory pInv = (PlayerInventory) inv;
            if (pInv.getArmorContents().length > 0) {
                pInv.setHelmet((pInv.getHelmet() != null ? UtilItem.updateNames(pInv.getHelmet()) : null));
                pInv.setChestplate((pInv.getChestplate() != null ? UtilItem.updateNames(pInv.getChestplate()) : null));
                pInv.setLeggings((pInv.getLeggings() != null ? UtilItem.updateNames(pInv.getLeggings()) : null));
                pInv.setBoots((pInv.getBoots() != null ? UtilItem.updateNames(pInv.getBoots()) : null));
            }
        }
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