package me.trae.smp.client;

import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private final UUID uuid;
    private final List<String> ipAddresses;
    private String name, oldName;
    private Rank rank;
    private long firstJoined, lastJoined, lastOnline, playtime;
    private boolean newClient, muted;

    public Client(final UUID uuid) {
        this.uuid = uuid;
        this.ipAddresses = new ArrayList<>();
        this.rank = Rank.PLAYER;
        this.firstJoined = 0L;
        this.muted = false;
    }

    public final UUID getUUID() {
        return uuid;
    }

    public final String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public final String getOldName() {
        return oldName;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    public final List<String> getIpAddresses() {
        return ipAddresses;
    }

    public final Rank getRank() {
        return rank;
    }

    public void setRank(final Rank rank) {
        this.rank = rank;
    }

    public final boolean hasRank(final Rank rank, final boolean inform) {
        final Player player = Bukkit.getPlayer(getUUID());
        if (player != null && player.isOp()) {
            return true;
        }
        if (!(getRank().ordinal() >= rank.ordinal())) {
            if (inform) {
                if (player != null) {
                    UtilMessage.message(player, "Permissions", "This requires the Permission Rank [" + rank.getTag(false) + ChatColor.GRAY + "].");
                }
            }
            return false;
        }
        return true;
    }

    public final long getFirstJoined() {
        return firstJoined;
    }

    public void setFirstJoined(final long firstJoined) {
        this.firstJoined = firstJoined;
    }

    public final long getLastJoined() {
        return lastJoined;
    }

    public void setLastJoined(final long lastJoined) {
        this.lastJoined = lastJoined;
    }

    public final long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(final long lastOnline) {
        this.lastOnline = lastOnline;
    }

    public final long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(final long playtime) {
        this.playtime = playtime;
    }

    public final String getPlaytimeString() {
        if (getPlaytime() > 0) {
            return UtilTime.getTime(getPlaytime() + ((Bukkit.getPlayer(getUUID()) != null ? System.currentTimeMillis() : getLastOnline()) - getLastJoined()), UtilTime.TimeUnit.BEST, 1);
        }
        return "Never Played";
    }

    public final boolean isNewClient() {
        return newClient;
    }

    public void setNewClient(final boolean newClient) {
        this.newClient = newClient;
    }

    public final boolean isMuted() {
        return muted;
    }

    public void setMuted(final boolean muted) {
        this.muted = muted;
    }
}