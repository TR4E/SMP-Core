package me.trae.smp.client;

import me.trae.smp.Main;
import me.trae.smp.config.ConfigManager;
import me.trae.smp.config.IRepository;
import me.trae.smp.utility.UtilLocation;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

public class ClientRepository extends IRepository {

    public ClientRepository(final Main instance) {
        super(instance, ConfigManager.ConfigType.CLIENTS_DATA);
    }

    public void saveClient(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Name.Current", client.getName());
        config.getConfig().set(client.getUUID().toString() + ".Name.Previous", client.getOldName());
        config.getConfig().set(client.getUUID().toString() + ".IP", client.getIpAddresses());
        config.getConfig().set(client.getUUID().toString() + ".Rank", client.getRank().name());
        config.getConfig().set(client.getUUID().toString() + ".First-Joined", client.getFirstJoined());
        config.getConfig().set(client.getUUID().toString() + ".Last-Joined", client.getLastJoined());
        config.getConfig().set(client.getUUID().toString() + ".Last-Online", client.getLastOnline());
        config.getConfig().set(client.getUUID().toString() + ".Playtime", client.getPlaytime());
        config.getConfig().set(client.getUUID().toString() + ".Muted", (client.isMuted() ? 1 : 0));
        config.getConfig().set(client.getUUID().toString() + ".Home", (client.getHomeLocation() != null ? UtilLocation.locationToFile(client.getHomeLocation()) : "None"));
        config.saveFile();
    }

    public void updateName(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Name.Current", client.getName());
        config.saveFile();
    }

    public void updateOldName(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Name.Previous", client.getOldName());
        config.saveFile();
    }

    public void updateIP(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".IP", client.getIpAddresses());
        config.saveFile();
    }

    public void updateRank(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Rank", client.getRank().name());
        config.saveFile();
    }

    public void updateFirstJoined(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".First-Joined", client.getFirstJoined());
        config.saveFile();
    }

    public void updateLastJoined(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Last-Joined", client.getLastJoined());
        config.saveFile();
    }

    public void updateLastOnline(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Last-Online", client.getLastOnline());
        config.saveFile();
    }

    public void updatePlaytime(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Playtime", client.getPlaytime());
        config.saveFile();
    }

    public void updateMuted(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Muted", (client.isMuted() ? 1 : 0));
        config.saveFile();
    }

    public void updateHome(final Client client) {
        config.loadFile();
        config.getConfig().set(client.getUUID().toString() + ".Home", (client.getHomeLocation() != null ? UtilLocation.locationToFile(client.getHomeLocation()) : "None"));
        config.saveFile();
    }

    @Override
    public synchronized void load() {
        this.config.loadFile();
        this.config.saveFile();
        final YamlConfiguration yml = this.config.getConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final String str : ClientRepository.this.config.getConfig().getKeys(false)) {
                    final Client client = new Client(UUID.fromString(str));
                    client.setName(yml.getString(str + ".Name.Current"));
                    client.setOldName(yml.getString(str + ".Name.Previous"));
                    client.getIpAddresses().addAll(yml.getStringList(str + ".IP"));
                    client.setRank(Rank.valueOf(yml.getString(str + ".Rank")));
                    client.setFirstJoined(yml.getLong(str + ".First-Joined"));
                    client.setLastJoined(yml.getLong(str + ".Last-Joined"));
                    client.setLastOnline(yml.getLong(str + ".Last-Online"));
                    client.setPlaytime(yml.getLong(str + ".Playtime"));
                    client.setMuted(yml.getInt(str + ".Muted") == 1);
                    client.setHomeLocation((!(Objects.equals(yml.getString(str + ".Home"), "None")) ? UtilLocation.fileToLocation(yml.getString(str + ".Home")) : null));
                    getInstance().getClientUtilities().addClient(client);
                }
                UtilMessage.log("Database", "Loaded " + ChatColor.YELLOW + getInstance().getClientUtilities().getClients().size() + ChatColor.GRAY + " Clients.");
            }
        }.runTaskAsynchronously(getInstance());
    }

    @Override
    public void reload() {
        getInstance().getClientUtilities().getClients().clear();
        getInstance().getClientUtilities().getOnlineClients().clear();
        load();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    final Client client = getInstance().getClientUtilities().getClient(player.getUniqueId());
                    if (client != null) {
                        getInstance().getClientUtilities().addOnlineClient(client);
                    }
                }
            }
        }.runTaskLaterAsynchronously(getInstance(), 10L);
    }
}