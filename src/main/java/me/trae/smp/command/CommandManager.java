package me.trae.smp.command;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager extends MainListener {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandManager(final Main instance) {
        super(instance, true);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addCommand(final Command command) {
        getCommands().put(command.getName(), command);
    }

    public void removeCommand(final Command command) {
        getCommands().remove(command.getName());
    }

    public final Command getCommand(final String string) {
        for (final Command command : getCommands().values()) {
            if (command.getName().equalsIgnoreCase(string) || Arrays.asList(command.getAliases()).contains(string.toLowerCase())) {
                return command;
            }
        }
        return null;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(final PlayerCommandPreprocessEvent e) {
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(client.hasRank(Rank.OWNER, false))) {
            if (e.getMessage().startsWith("/?")) {
                e.setMessage("/help");
            }
        }
        if (e.getMessage().equalsIgnoreCase("/restart") || e.getMessage().equalsIgnoreCase("/stop")) {
            e.setCancelled(true);
            if (client.hasRank(Rank.OWNER, true)) {
                getInstance().setStoppedByForce(true);
                Bukkit.getServer().getPluginManager().callEvent(new ServerStopEvent());
            }
            return;
        }
        String cmd = e.getMessage().substring(1);
        String[] args = null;
        if (cmd.contains(" ")) {
            cmd = cmd.split(" ")[0];
            args = e.getMessage().substring(e.getMessage().indexOf(' ') + 1).split(" ");
        }
        if (cmd == null) {
            return;
        }
        final Command command = getCommand(cmd.toLowerCase());
        if (command != null) {
            e.setCancelled(true);
            if (client.hasRank(command.getRequiredRank(), true)) {
                command.execute(player, args);
            }
        } else {
            if (client.hasRank(Rank.OWNER, false)) {
                return;
            }
            e.setCancelled(true);
            UtilMessage.message(player, ChatColor.WHITE + "Unknown command. Type \"/help\" for help.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerCommand(final ServerCommandEvent e) {
        if (e.getCommand().equalsIgnoreCase("restart") || e.getCommand().equalsIgnoreCase("stop")) {
            e.setCancelled(true);
            getInstance().setStoppedByForce(true);
            Bukkit.getServer().getPluginManager().callEvent(new ServerStopEvent());
        }
    }
}