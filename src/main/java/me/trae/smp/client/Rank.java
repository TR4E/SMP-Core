package me.trae.smp.client;

import org.bukkit.ChatColor;

public enum Rank {

    OWNER("Owner", ChatColor.DARK_RED, 7),
    DEVELOPER("Developer", ChatColor.DARK_RED, 6),
    ADMIN("Admin", ChatColor.RED, 5),
    MOD("Mod", ChatColor.AQUA, 4),
    PARTNER("Partner", ChatColor.DARK_PURPLE, 3),
    STREAMER("Streamer", ChatColor.LIGHT_PURPLE, 2),
    MEMBER("Member", ChatColor.GREEN, 1),
    PLAYER("Player", ChatColor.YELLOW, 0);

    private final String name;
    private final ChatColor color;
    private final int id;

    Rank(final String name, final ChatColor color, final int id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public static Rank getRank(final int id) {
        for (final Rank rank : Rank.values()) {
            if (rank.getId() == id) {
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

    public final int getId() {
        return id;
    }

    public final String getTag(final boolean bold) {
        return (getColor() + (bold ? ChatColor.BOLD.toString() : "") + getName());
    }
}