package me.trae.smp.client.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DyeCommand extends Command {

    public DyeCommand(final Main instance) {
        super(instance, "dye", new String[]{"color", "colour"}, Rank.MEMBER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            UtilMessage.message(player, "Dye", "You cannot dye this Item.");
            return;
        }
        if (!(isCorrectItem(player, item.getType()))) {
            return;
        }
        if (args[0].equalsIgnoreCase("Reset") || args[0].equalsIgnoreCase("Default")) {
            addColorToItem(player, item, null);
        } else {
            final DyeColor dye = searchDye(player, args[0], true);
            if (dye != null) {
                addColorToItem(player, item, dye);
            }
        }
    }

    @Override
    public void help(final Player player) {
        final List<DyeColor> color = Arrays.asList(DyeColor.values());
        final String colors = color.stream().map(c -> UtilFormat.cleanString(c.name())).collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.YELLOW + ""));
        UtilMessage.message(player, "Dye", "You did not input a dye color!");
        UtilMessage.message(player, "Dye", "Available Colors: " + "[" + ChatColor.YELLOW + colors + ChatColor.GRAY + "].");
    }

    private DyeColor searchDye(final Player player, final String color, final boolean inform) {
        if (Arrays.stream(DyeColor.values()).anyMatch(dyeColor -> dyeColor.name().toLowerCase().equals(color.toLowerCase()))) {
            return Arrays.stream(DyeColor.values()).filter(dyeColor -> dyeColor.name().replaceAll("_", "").toLowerCase().equals(color.toLowerCase())).findFirst().get();
        }
        final List<String> colors = Arrays.stream(DyeColor.values()).filter(dyeColor -> dyeColor.name().toLowerCase().replaceAll("_", "").contains(color.toLowerCase())).map(Enum::name).collect(Collectors.toList());
        if (colors.size() == 1) {
            return DyeColor.valueOf(colors.get(0));
        } else if (!(colors.isEmpty())) {
            if (inform) {
                UtilMessage.message(player, "Dye Search", ChatColor.YELLOW.toString() + colors.size() + ChatColor.GRAY + " Matches found [" + ChatColor.WHITE + "Reset" + ChatColor.GRAY + ", " + ChatColor.YELLOW + colors.stream().map(dyeColor -> ChatColor.YELLOW + UtilFormat.cleanString(dyeColor).replaceAll(" ", "")).collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.YELLOW)) + ChatColor.GRAY + "].");
            }
            return null;
        }
        if (inform) {
            UtilMessage.message(player, "Dye Search", ChatColor.YELLOW + "0" + ChatColor.GRAY + " Matches found. [" + ChatColor.YELLOW + color + ChatColor.GRAY + "].");
        }
        return null;
    }

    private boolean isCorrectItem(final Player player, final Material m) {
        if (m.name().endsWith("WOOL") || m.name().contains("CARPET") || m.name().contains("CONCRETE") || m.name().contains("TERRACOTTA") || m.name().startsWith("LEATHER_") || (m.name().contains("GLASS") && !(m.name().contains("BOTTLE"))) || m.name().endsWith("_PANE")) {
            return true;
        }
        UtilMessage.message(player, "Dye", "You cannot dye this Item.");
        UtilMessage.message(player, "Dye", "Available Items: " + "[" + ChatColor.WHITE + "Wool" + ChatColor.GRAY + ", " + ChatColor.WHITE + "Glass" + ChatColor.GRAY + ", " + ChatColor.WHITE + "Concrete" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "Terracotta" + ChatColor.GRAY + ", " + ChatColor.WHITE + "Leather Armour" + ChatColor.GRAY + "] ");
        return false;
    }

    private void addColorToItem(final Player player, final ItemStack item, final DyeColor dye) {
        if (item.getType().name().contains("WOOL")) {
            item.setType(Material.valueOf(dye.name() + "_WOOL"));
        } else if (item.getType().name().contains("CARPET")) {
            item.setType(Material.valueOf(dye.name() + "_CARPET"));
        } else if (item.getType().name().contains("CONCRETE_POWDER")) {
            item.setType(Material.valueOf(dye.name() + "_CONCRETE_POWDER"));
        } else if (item.getType().name().contains("CONCRETE")) {
            item.setType(Material.valueOf(dye.name() + "_CONCRETE"));
        } else if (item.getType().name().contains("TERRACOTTA")) {
            item.setType(Material.valueOf(dye.name() + "_TERRACOTTA"));
        } else if (item.getType().name().startsWith("LEATHER_")) {
            if (dye == null) {
                player.getInventory().setItemInMainHand(new ItemStack(item.getType(), item.getAmount()));
            } else {
                final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                if (meta != null) {
                    meta.setColor(dye.getColor());
                    item.setItemMeta(meta);
                }
            }
        } else if (item.getType().name().contains("STAINED_GLASS")) {
            if (dye == null) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS, item.getAmount()));
            } else {
                item.setType(Material.valueOf(dye.name() + "_STAINED_GLASS"));
            }
        } else if (item.getType().name().contains("STAINED_GLASS_PANE")) {
            if (dye == null) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_PANE, item.getAmount()));
            } else {
                item.setType(Material.valueOf(dye.name() + "_STAINED_GLASS_PANE"));
            }
        }
    }
}