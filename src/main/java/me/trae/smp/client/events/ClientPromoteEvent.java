package me.trae.smp.client.events;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ClientPromoteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Main instance;
    private final UUID uuid;
    private final Rank rank;

    public ClientPromoteEvent(final Main instance, final UUID uuid, final Rank rank) {
        this.instance = instance;
        this.uuid = uuid;
        this.rank = rank;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public final Main getInstance() {
        return instance;
    }

    public final UUID getUUID() {
        return uuid;
    }

    public final Rank getRank() {
        return rank;
    }

    public final Client getClient() {
        return getInstance().getClientUtilities().tryGetOnlineClient(uuid);
    }

    public final Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}