package me.trae.smp.client;

import org.bukkit.ChatColor;

public enum Rank {

    OWNER("Owner", ChatColor.DARK_RED),
    DEVELOPER("Developer", ChatColor.DARK_RED),
    ADMIN("Admin", ChatColor.RED),
    MOD("Mod", ChatColor.AQUA),
    PARTNER("Partner", ChatColor.DARK_PURPLE),
    STREAMER("Streamer", ChatColor.LIGHT_PURPLE),
    MEMBER("Member", ChatColor.GREEN),
    PLAYER("Player", ChatColor.YELLOW);

    private final String name;
    private final ChatColor color;

    Rank(final String name, final ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public static Rank getRank(final int ordinal) {
        for (final Rank rank : Rank.values()) {
            if (rank.ordinal() == ordinal) {
                return rank;
            }
        }
        return null;
    }

    public final String getName() {
        return name;
    }

    public final ChatColor getColor() {
        return color;
    }

    public final String getTag(final boolean bold) {
        return (getColor() + (bold ? ChatColor.BOLD.toString() : "") + getName());
    }
}