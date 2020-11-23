package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.utility.UtilFormat;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ObserverCommand extends Command {

    public ObserverCommand(final Main instance) {
        super(instance, "observer", new String[]{"obs"}, Rank.MOD, true);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (args == null || args.length == 0) {
            if (client.isObserving()) {
                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(client.getObserverLocation());
                client.setObserverLocation(null);
            } else {
                client.setObserverLocation(player.getLocation());
                player.setGameMode(GameMode.SPECTATOR);
            }
            UtilMessage.message(player, "Observer", "Observer Mode: " + UtilFormat.getStatus(client.isObserving(), true));
            getInstance().getClientUtilities().messageStaff("Observer", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is " + (client.isObserving() ? "now" : "no longer") + " Observing.", Rank.ADMIN, new UUID[]{player.getUniqueId()});
            return;
        }
        if (args.length == 1) {
            final Player target = UtilPlayer.searchPlayer(player, args[0], true);
            if (target == null) {
                return;
            }
            final Client targetC = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
            if (targetC == null) {
                return;
            }
            if (targetC.isObserving()) {
                target.setGameMode(GameMode.SURVIVAL);
                target.teleport(targetC.getObserverLocation());
                targetC.setObserverLocation(null);
            } else {
                targetC.setObserverLocation(target.getLocation());
                target.setGameMode(GameMode.SPECTATOR);
            }
            UtilMessage.message(target, "Observer", "Observer Mode: " + UtilFormat.getStatus(targetC.isObserving(), true));
            getInstance().getClientUtilities().messageStaff("Observer", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " is " + (targetC.isObserving() ? "now" : "no longer") + " Observing.", Rank.ADMIN, new UUID[]{target.getUniqueId()});
        }
    }

    @Override
    public void help(final Player player) {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client != null && client.isObserving()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(client.getObserverLocation());
            client.setObserverLocation(null);
        }
    }
}