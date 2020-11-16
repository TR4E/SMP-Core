package me.trae.smp.framework.recharge;

import me.trae.smp.Main;
import me.trae.smp.framework.update.UpdateEvent;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import me.trae.smp.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.*;

public final class RechargeManager extends MainListener {

    private final Map<UUID, List<Recharge>> cooldown = new HashMap<>();

    public RechargeManager(final Main instance) {
        super(instance, true);
    }

    public final boolean add(final Player player, final String name, final long duration, final boolean inform) {
        if (player == null) {
            return false;
        }
        if (!(cooldown.containsKey(player.getUniqueId()))) {
            cooldown.put(player.getUniqueId(), new ArrayList<>());
            cooldown.get(player.getUniqueId()).add(new Recharge(name, duration, inform));
            return true;
        }
        if (cooldown.get(player.getUniqueId()).stream().noneMatch(r -> r.getName().equals(name))) {
            cooldown.get(player.getUniqueId()).add(new Recharge(name, duration, inform));
            return true;
        }
        if (cooldown.get(player.getUniqueId()).stream().filter(r -> r.getName().equals(name)).anyMatch(r -> !(r.hasExpired() && r.isExpired()))) {
            if (inform) {
                UtilMessage.message(player, "Recharge", "You cannot use " + ChatColor.GREEN + name + ChatColor.GRAY + " for " + ChatColor.GREEN + UtilTime.getTime(cooldown.get(player.getUniqueId()).stream().filter(r -> r.getName().equals(name)).findFirst().get().getRemaining(), UtilTime.TimeUnit.BEST, 1) + ChatColor.GRAY + ".");
            }
            return false;
        }
        return cooldown.get(player.getUniqueId()).stream().filter(r -> r.getName().equals(name)).anyMatch(Recharge::hasExpired);
    }

    public void remove(final Player player, final String name, final boolean inform) {
        if (player == null) {
            return;
        }
        if (cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()).stream().anyMatch(c -> c.getName().equals(name))) {
            cooldown.get(player.getUniqueId()).remove(cooldown.get(player.getUniqueId()).stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null));
            if (inform) {
                UtilMessage.message(player, "Recharge", ChatColor.GREEN + name + ChatColor.GRAY + " has been recharged!");
            }
        }
    }

    public final boolean isCooling(final Player player, final String name, final boolean inform) {
        if (player == null || !(cooldown.containsKey(player.getUniqueId()))) {
            return false;
        }
        if (cooldown.get(player.getUniqueId()).stream().noneMatch(r -> r.getName().equals(name))) {
            return false;
        } else if (inform) {
            UtilMessage.message(player, "Recharge", "You cannot use " + ChatColor.GREEN + name + ChatColor.GRAY + " for " + ChatColor.GREEN + UtilTime.getTime(cooldown.get(player.getUniqueId()).stream().filter(c -> c.getName().equals(name)).findFirst().get().getRemaining(), UtilTime.TimeUnit.BEST, 1) + ChatColor.GRAY + ".");
        }
        return cooldown.get(player.getUniqueId()).stream().filter(r -> r.getName().equals(name)).anyMatch(r -> !(r.hasExpired() && r.isExpired()));
    }

    public final boolean hasCooldowns(final Player player) {
        return cooldown.containsKey(player.getUniqueId());
    }

    public void reset(final Player player) {
        if (hasCooldowns(player)) {
            cooldown.keySet().remove(player.getUniqueId());
        }
    }

    public final List<Recharge> getRecharges(final Player player) {
        return ((cooldown.size() > 0 && cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) != null) ? cooldown.get(player.getUniqueId()) : null);
    }

    public void removeCooldowns(final Player player) {
        if (getRecharges(player) != null) {
            cooldown.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onUpdate(final UpdateEvent e) {
        if (e.getType() == Updater.UpdateType.TICK) {
            for (final Iterator<Map.Entry<UUID, List<Recharge>>> it = cooldown.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry<UUID, List<Recharge>> next = it.next();
                if (next != null) {
                    if (next.getValue().stream().anyMatch(Recharge::hasExpired)) {
                        final Recharge recharge = next.getValue().stream().filter(Recharge::hasExpired).findFirst().get();
                        if (next.getValue().contains(recharge)) {
                            if (recharge.isInform()) {
                                final Player player = Bukkit.getPlayer(next.getKey());
                                if (player != null) {
                                    UtilMessage.message(player, "Recharge", ChatColor.GREEN + recharge.getName() + ChatColor.GRAY + " has been recharged!");
                                }
                            }
                            recharge.setExpired(true);
                            next.getValue().remove(next.getValue().stream().filter(Recharge::hasExpired).findFirst().orElse(null));
                        }
                        if (next.getValue().isEmpty()) {
                            it.remove();
                        }
                    }
                }
            }
        }
    }
}