package me.trae.smp.framework.update;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class UpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Updater.UpdateType type;

    public UpdateEvent(final Updater.UpdateType type) {
        this.type = type;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public final Updater.UpdateType getType() {
        return type;
    }
}