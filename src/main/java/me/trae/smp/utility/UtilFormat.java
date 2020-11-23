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

    public static String getFinalArg(final String[] args, final int start) {
        final StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }
}