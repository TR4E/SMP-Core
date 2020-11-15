package me.trae.smp;

import me.trae.smp.client.ClientManager;
import me.trae.smp.client.ClientRepository;
import me.trae.smp.client.ClientUtilities;
import me.trae.smp.client.commands.PingCommand;
import me.trae.smp.client.commands.PlaytimeCommand;
import me.trae.smp.client.commands.staff.ClientCommand;
import me.trae.smp.client.listeners.ConnectionListener;
import me.trae.smp.command.CommandManager;
import me.trae.smp.config.ConfigManager;
import me.trae.smp.config.Repository;
import me.trae.smp.config.commands.ReloadCommand;
import me.trae.smp.events.ServerStartEvent;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.gamer.GamerRepository;
import me.trae.smp.gamer.GamerUtilities;
import me.trae.smp.world.PreConnectionListener;
import me.trae.smp.world.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private boolean started, stoppedByForce;

    private ConfigManager configManager;
    private Repository repository;
    private ClientRepository clientRepository;
    private GamerRepository gamerRepository;
    private ClientUtilities clientUtilities;
    private GamerUtilities gamerUtilities;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        initialize();
    }

    @Override
    public void onDisable() {
        if (!(isStoppedByForce())) {
            Bukkit.getServer().getPluginManager().callEvent(new ServerStopEvent());
        }
    }

    private void initialize() {
        this.started = false;
        Bukkit.getServer().getPluginManager().registerEvents(new PreConnectionListener(this), this);
        this.stoppedByForce = false;
        this.configManager = new ConfigManager(this);
        this.repository = new Repository(this);
        this.clientRepository = new ClientRepository(this);
        this.gamerRepository = new GamerRepository(this);
        this.clientUtilities = new ClientUtilities(this);
        this.gamerUtilities = new GamerUtilities(this);
        this.commandManager = new CommandManager(this);
        registerEvents();
        registerCommands();
        Bukkit.getServer().getPluginManager().callEvent(new ServerStartEvent());
    }

    private void registerEvents() {
        new ServerListener(this);
        new ConnectionListener(this);
        new ClientManager(this);
    }

    private void registerCommands() {
        getCommandManager().addCommand(new ClientCommand(this));
        getCommandManager().addCommand(new PingCommand(this));
        getCommandManager().addCommand(new ReloadCommand(this));
        getCommandManager().addCommand(new PlaytimeCommand(this));
    }

    public final boolean hasStarted() {
        return started;
    }

    public void setStarted(final boolean started) {
        this.started = started;
    }

    public final boolean isStoppedByForce() {
        return stoppedByForce;
    }

    public void setStoppedByForce(final boolean stoppedByForce) {
        this.stoppedByForce = stoppedByForce;
    }

    public final ConfigManager getConfigManager() {
        return configManager;
    }

    public final Repository getRepository() {
        return repository;
    }

    public final ClientRepository getClientRepository() {
        return clientRepository;
    }

    public final GamerRepository getGamerRepository() {
        return gamerRepository;
    }

    public final ClientUtilities getClientUtilities() {
        return clientUtilities;
    }

    public final GamerUtilities getGamerUtilities() {
        return gamerUtilities;
    }

    public final CommandManager getCommandManager() {
        return commandManager;
    }
}