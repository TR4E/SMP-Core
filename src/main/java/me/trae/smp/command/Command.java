package me.trae.smp.command;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.module.MainListener;
import org.bukkit.entity.Player;

public abstract class Command extends MainListener {

    private final String name;
    private final String[] aliases;
    private final Rank requiredRank;

    public Command(final Main instance, final String name, final String[] aliases, final Rank requiredRank, final boolean listener) {
        super(instance, listener);
        this.name = name;
        this.aliases = aliases;
        this.requiredRank = requiredRank;
    }

    public final String getName() {
        return name;
    }

    public final String[] getAliases() {
        return aliases;
    }

    public final Rank getRequiredRank() {
        return requiredRank;
    }

    public abstract void execute(final Player player, final String[] args);

    public abstract void help(final Player player);
}