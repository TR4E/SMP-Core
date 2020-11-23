package me.trae.smp.client;

import org.bukkit.ChatColor;

public enum Rank {

    PLAYER("Player", ChatColor.YELLOW),
    MEMBER("Member", ChatColor.GREEN),
    STREAMER("Streamer", ChatColor.LIGHT_PURPLE),
    PARTNER("Partner", ChatColor.DARK_PURPLE),
    MOD("Mod", ChatColor.AQUA),
    ADMIN("Admin", ChatColor.RED),
    DEVELOPER("Developer", ChatColor.DARK_RED),
    OWNER("Owner", ChatColor.DARK_RED);

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