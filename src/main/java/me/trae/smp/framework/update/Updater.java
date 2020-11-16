package me.trae.smp.framework.update;

import me.trae.smp.Main;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Updater extends BukkitRunnable {

    private final Map<UpdateType, Long> map = new HashMap<>();

    public Updater(final Main instance) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Updater.this.run();
                } catch (final Exception e) {
                    UtilMessage.log("\n" + ChatColor.RED + "Error> " + ChatColor.YELLOW + "Updater" + ChatColor.GRAY + " threw an Error!" + "\n");
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(instance, 0L, 1L);
    }

    @Override
    public void run() {
        for (final UpdateType type : UpdateType.values()) {
            if (!(map.containsKey(type))) {
                map.put(type, System.currentTimeMillis());
            }
            if ((map.get(type) + type.time) < System.currentTimeMillis()) {
                Bukkit.getServer().getPluginManager().callEvent(new UpdateEvent(type));
                map.put(type, System.currentTimeMillis());
            }
        }
    }

    public enum UpdateType {
        TICK(0L),
        TICK_50(50L),
        FASTEST(125L),
        FASTER(250L),
        FAST(500L),
        SEC_01(1000L),
        SEC_02(2000L),
        SEC_05(5000L),
        SEC_10(10000L),
        SEC_30(30000L),
        SEC_45(45000L),
        MIN_01(60000L),
        MIN_02(120000L),
        MIN_05(300000L),
        MIN_10(600000L),
        MIN_15(960000L),
        MIN_30(1920000L),
        MIN_60(3840000L),
        MIN_120(7680000L);
        private final long time;

        UpdateType(final long time) {
            this.time = time;
        }

        public final long getTime() {
            return time;
        }
    }
}