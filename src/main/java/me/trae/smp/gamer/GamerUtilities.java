package me.trae.smp.gamer;

import me.trae.smp.Main;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;

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

    public void incBlocksBroken(final UUID uuid) {
        final Gamer gamer = getGamer(uuid);
        if (gamer != null) {
            gamer.setBlocksBroken(gamer.getBlocksBroken() + 1);
            getInstance().getGamerRepository().updateBlocksBroken(gamer);
        }
    }

    public void incBlocksPlaced(final UUID uuid) {
        final Gamer gamer = getGamer(uuid);
        if (gamer != null) {
            gamer.setBlocksPlaced(gamer.getBlocksPlaced() + 1);
            getInstance().getGamerRepository().updateBlocksPlaced(gamer);
        }
    }

    public void incKills(final UUID uuid) {
        final Gamer gamer = getGamer(uuid);
        if (gamer != null) {
            gamer.setKills(gamer.getKills() + 1);
            getInstance().getGamerRepository().updateKills(gamer);
        }
    }

    public void incDeaths(final UUID uuid) {
        final Gamer gamer = getGamer(uuid);
        if (gamer != null) {
            gamer.setKills(gamer.getDeaths() + 1);
            getInstance().getGamerRepository().updateDeaths(gamer);
        }
    }

    public void incJoins(final UUID uuid) {
        final Gamer gamer = getGamer(uuid);
        if (gamer != null) {
            gamer.setJoins(gamer.getJoins() + 1);
            getInstance().getGamerRepository().updateJoins(gamer);
            UtilMessage.log("Gamer", "Added a Join to " + ChatColor.YELLOW + uuid.toString() + ChatColor.GRAY + " [" + ChatColor.GREEN + gamer.getJoins() + ChatColor.GRAY + "].");
        }
    }
}