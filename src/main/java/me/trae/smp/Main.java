package me.trae.smp;

import me.trae.smp.client.ClientManager;
import me.trae.smp.client.ClientRepository;
import me.trae.smp.client.ClientUtilities;
import me.trae.smp.client.commands.*;
import me.trae.smp.client.commands.staff.*;
import me.trae.smp.client.commands.teleport.*;
import me.trae.smp.client.commands.teleport.tpa.TPAAcceptCommand;
import me.trae.smp.client.commands.teleport.tpa.TPACommand;
import me.trae.smp.client.listeners.ConnectionListener;
import me.trae.smp.command.CommandManager;
import me.trae.smp.config.ConfigManager;
import me.trae.smp.config.Repository;
import me.trae.smp.config.commands.ReloadCommand;
import me.trae.smp.events.ServerStartEvent;
import me.trae.smp.events.ServerStopEvent;
import me.trae.smp.framework.TitleManager;
import me.trae.smp.framework.blockrestore.BlockRestoreUtilities;
import me.trae.smp.framework.recharge.RechargeManager;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.gamer.GamerManager;
import me.trae.smp.gamer.GamerRepository;
import me.trae.smp.gamer.GamerUtilities;
import me.trae.smp.world.*;
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
    private BlockRestoreUtilities blockRestoreUtilities;
    private CommandManager commandManager;
    private RechargeManager rechargeManager;
    private TitleManager titleManager;

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
        this.blockRestoreUtilities = new BlockRestoreUtilities(this);
        this.commandManager = new CommandManager(this);
        this.rechargeManager = new RechargeManager(this);
        this.titleManager = new TitleManager(this);
        new Updater(this);
        registerEvents();
        registerCommands();
        Bukkit.getServer().getPluginManager().callEvent(new ServerStartEvent());
    }

    private void registerEvents() {
        new ConnectionListener(this);
        new ClientManager(this);
        new GamerManager(this);
        new ChatListener(this);
        new CombatListener(this);
        new ExplosionListener(this);
        new ServerListener(this);
        new WorldListener(this);
    }

    private void registerCommands() {
        getCommandManager().addCommand(new AltsCommand(this));
        getCommandManager().addCommand(new ClientCommand(this));
        getCommandManager().addCommand(new ExplosionHealCommand(this));
        getCommandManager().addCommand(new MuteCommand(this));
        getCommandManager().addCommand(new ObserverCommand(this));
        getCommandManager().addCommand(new TPAAcceptCommand(this));
        getCommandManager().addCommand(new TPACommand(this));
        getCommandManager().addCommand(new BackCommand(this));
        getCommandManager().addCommand(new SethomeCommand(this));
        getCommandManager().addCommand(new DelhomeCommand(this));
        getCommandManager().addCommand(new HomeCommand(this));
        getCommandManager().addCommand(new DyeCommand(this));
        getCommandManager().addCommand(new HelpCommand(this));
        getCommandManager().addCommand(new ListCommand(this));
        getCommandManager().addCommand(new PingCommand(this));
        getCommandManager().addCommand(new ReloadCommand(this));
        getCommandManager().addCommand(new PlaytimeCommand(this));
        getCommandManager().addCommand(new StatsCommand(this));
        getCommandManager().addCommand(new TeleportCommand(this));
        getCommandManager().addCommand(new TrustCommand(this));
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

    public final BlockRestoreUtilities getBlockRestoreUtilities() {
        return blockRestoreUtilities;
    }

    public final CommandManager getCommandManager() {
        return commandManager;
    }

    public final RechargeManager getRechargeManager() {
        return rechargeManager;
    }

    public final TitleManager getTitleManager() {
        return titleManager;
    }
}