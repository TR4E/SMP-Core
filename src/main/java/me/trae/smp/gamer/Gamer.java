package me.trae.smp.gamer;

import java.util.UUID;

public class Gamer {

    private final UUID uuid;

    private int blocksBroken, blocksPlaced, kills, deaths, joins;

    public Gamer(final UUID uuid) {
        this.uuid = uuid;
        this.blocksBroken = 0;
        this.blocksPlaced = 0;
        this.kills = 0;
        this.deaths = 0;
        this.joins = 0;
    }

    public final UUID getUUID() {
        return uuid;
    }

    public final int getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(final int blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public final int getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setBlocksPlaced(final int blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    public final int getKills() {
        return kills;
    }

    public void setKills(final int kills) {
        this.kills = kills;
    }

    public final int getDeaths() {
        return deaths;
    }

    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }

    public final int getJoins() {
        return joins;
    }

    public void setJoins(final int joins) {
        this.joins = joins;
    }
}