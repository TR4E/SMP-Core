package me.trae.smp.config.commands;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.entity.Player;

public class ReloadCommand extends Command {

    public ReloadCommand(final Main instance) {
        super(instance, "smpreload", new String[]{}, Rank.OWNER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        getInstance().getRepository().reload();
        getInstance().getClientRepository().reload();
        UtilMessage.message(player, "Config", "Reloaded Configurations.");
    }

    @Override
    public void help(final Player player) {

    }
}