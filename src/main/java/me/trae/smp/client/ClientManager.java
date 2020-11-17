package me.trae.smp.client;

import me.trae.smp.Main;
import me.trae.smp.client.events.ClientDemoteEvent;
import me.trae.smp.client.events.ClientPromoteEvent;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientManager extends MainListener {

    public ClientManager(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onClientPromote(final ClientPromoteEvent e) {
        final Client client = e.getClient();
        if (client == null) {
            return;
        }
        final Player player = e.getPlayer();
        switch (e.getRank()) {
            case MEMBER:
                if (player != null) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
                break;
        }
    }

    @EventHandler
    public void onClientDemote(final ClientDemoteEvent e) {
        final Client client = e.getClient();
        if (client == null) {
            return;
        }
        final Player player = e.getPlayer();
        switch (e.getRank()) {
            case MEMBER:
                if (player != null) {
                    player.setGameMode(GameMode.ADVENTURE);
                    final List<ItemStack> items = new ArrayList<>();
                    items.addAll(Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList()));
                    items.addAll(Arrays.stream(player.getInventory().getArmorContents()).filter(Objects::nonNull).collect(Collectors.toList()));
                    UtilPlayer.clearInventory(player);
                    if (items.size() > 0) {
                        for (final ItemStack item : items) {
                            player.getWorld().dropItem(player.getLocation(), item);
                        }
                    }
                }
                break;
        }
    }
}