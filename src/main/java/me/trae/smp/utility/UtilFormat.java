package me.trae.smp.utility;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

public class UtilFormat {

    public static String cleanString(final String string) {
        return WordUtils.capitalizeFully(string.replaceAll("_", " ")).replaceAll("_", " ");
    }

    public static String getStatus(final boolean b, final boolean color) {
        return (b ? (color ? ChatColor.GREEN : "") + "Enabled" : (color ? ChatColor.RED : "") + "Disabled");
    }
}