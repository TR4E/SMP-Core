package me.trae.smp.client.commands.teleport;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.entity.Player;

public class SethomeCommand extends Command {

    public SethomeCommand(final Main instance) {
        super(instance, "sethome", new String[]{"sh"}, Rank.MEMBER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            client.setHomeLocation(player.getLocation());
            getInstance().getClientRepository().updateHome(client);
            UtilMessage.message(player, "Home", "You have set your Home.");
        }
    }

    @Override
    public void help(final Player player) {

    }
}