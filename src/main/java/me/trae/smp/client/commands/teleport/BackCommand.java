package me.trae.smp.client.commands.teleport;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BackCommand extends Command {

    public BackCommand(final Main instance) {
        super(instance, "back", new String[]{"b"}, Rank.MEMBER, true);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (args == null || args.length == 0) {
            final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (client == null) {
                return;
            }
            if (client.getBackLocation() == null) {
                UtilMessage.message(player, "Back", "You do not have a previous Death Location.");
                return;
            }
            player.teleport(UtilLocation.toCenter(client.getBackLocation(), UtilLocation.DirectionType.SOUTH));
            client.setBackLocation(null);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            getInstance().getTitleManager().sendPlayer(player, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Teleport", ChatColor.WHITE + "You have teleported to previous Death Location.", 2);
            UtilMessage.message(player, "Back", "You have teleported to your previous Death Location.");
        }
    }

    @Override
    public void help(final Player player) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(final PlayerDeathEvent e) {
        final Player player = e.getEntity().getPlayer();
        if (player == null) {
            return;
        }
        if (!(getInstance().getClientUtilities().isTrusted(player))) {
            return;
        }
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        client.setBackLocation(player.getLocation());
        UtilMessage.message(player, "Back", "Type '" + ChatColor.AQUA + "/back" + ChatColor.GRAY + "' to teleport to your previous Death Location.");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        client.setBackLocation(null);
    }
}