package me.trae.smp.gamer;

import me.trae.smp.Main;
import me.trae.smp.config.ConfigManager;
import me.trae.smp.config.IRepository;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GamerRepository extends IRepository {

    public GamerRepository(final Main instance) {
        super(instance, ConfigManager.ConfigType.GAMERS_DATA);
    }

    public void saveGamer(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Blocks-Broken", gamer.getBlocksBroken());
        config.getConfig().set(gamer.getUUID().toString() + ".Blocks-Placed", gamer.getBlocksBroken());
        config.getConfig().set(gamer.getUUID().toString() + ".Kills", gamer.getKills());
        config.getConfig().set(gamer.getUUID().toString() + ".Deaths", gamer.getDeaths());
        config.getConfig().set(gamer.getUUID().toString() + ".Joins", gamer.getJoins());
        config.saveFile();
    }

    public void updateBlocksBroken(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Blocks-Broken", gamer.getBlocksBroken());
        config.saveFile();
    }

    public void updateBlocksPlaced(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Blocks-Placed", gamer.getBlocksPlaced());
        config.saveFile();
    }

    public void updateKills(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Kills", gamer.getKills());
        config.saveFile();
    }

    public void updateDeaths(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Deaths", gamer.getDeaths());
        config.saveFile();
    }

    public void updateJoins(final Gamer gamer) {
        config.loadFile();
        config.getConfig().set(gamer.getUUID().toString() + ".Joins", gamer.getJoins());
        config.saveFile();
    }

    public void loadGamer(final UUID uuid) {
        this.config.loadFile();
        this.config.saveFile();
        final YamlConfiguration yml = this.config.getConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                final String str = uuid.toString();
                final Gamer gamer = new Gamer(uuid);
                gamer.setBlocksBroken(yml.getInt(str + ".Blocks-Broken"));
                gamer.setBlocksPlaced(yml.getInt(str + ".Blocks-Placed"));
                gamer.setKills(yml.getInt(str + ".Kills"));
                gamer.setDeaths(yml.getInt(str + ".Deaths"));
                gamer.setJoins(yml.getInt(str + ".Joins"));
                getInstance().getGamerUtilities().addGamer(gamer);
            }
        }.runTaskAsynchronously(getInstance());
    }

    @Override
    public void load() {
    }

    @Override
    public void reload() {
        getInstance().getGamerUtilities().getGamers().clear();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            loadGamer(player.getUniqueId());
        }
    }
}