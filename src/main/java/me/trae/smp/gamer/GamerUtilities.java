package me.trae.smp.gamer;

import me.trae.smp.Main;
import me.trae.smp.module.MainListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GamerUtilities extends MainListener {

    private final Map<UUID, Gamer> gamers = new HashMap<>();

    public GamerUtilities(final Main instance) {
        super(instance, true);
    }

    public void addGamer(final Gamer gamer) {
        getGamers().put(gamer.getUUID(), gamer);
    }

    public void removeGamer(final Gamer gamer) {
        getGamers().remove(gamer.getUUID());
    }

    public final Gamer getGamer(final UUID uuid) {
        return getGamers().get(uuid);
    }

    public final Map<UUID, Gamer> getGamers() {
        return this.gamers;
    }
}