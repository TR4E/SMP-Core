package me.trae.smp.framework.blockrestore;

import me.trae.smp.Main;
import me.trae.smp.framework.update.UpdateEvent;
import me.trae.smp.framework.update.Updater;
import me.trae.smp.module.MainListener;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;

import java.util.Iterator;

public final class BlockRestoreManager extends MainListener {

    public BlockRestoreManager(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onUpdate(final UpdateEvent e) {
        if (e.getType() == Updater.UpdateType.TICK) {
            if (getInstance().getBlockRestoreUtilities().getDataList().size() > 0) {
                final Iterator<BlockRestoreData> it = getInstance().getBlockRestoreUtilities().getDataList().iterator();
                while (it.hasNext()) {
                    final BlockRestoreData next = it.next();
                    if (System.currentTimeMillis() >= next.getExpire()) {
                        next.restore();
                        it.remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        if (getInstance().getBlockRestoreUtilities().isRestoredBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFade(final BlockFadeEvent e) {
        if (getInstance().getBlockRestoreUtilities().isRestoredBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockMelt(final BlockFromToEvent e) {
        if (getInstance().getBlockRestoreUtilities().isRestoredBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        if (getInstance().getBlockRestoreUtilities().isRestoredBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonExtend(final BlockPistonExtendEvent e) {
        Block block = e.getBlock();
        for (int i = 0; i < 13; i++) {
            block = block.getRelative(e.getDirection());
            if (block.getType() != Material.AIR) {
                if (getInstance().getBlockRestoreUtilities().isRestoredBlock(block)) {
                    e.setCancelled(true);
                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType().ordinal());
                }
            }
        }
    }

    @EventHandler
    public void onPistonRetract(final BlockPistonRetractEvent e) {
        final Block block = e.getBlock().getRelative(e.getDirection());
        if (block.getType() != Material.AIR) {
            if (getInstance().getBlockRestoreUtilities().isRestoredBlock(block)) {
                e.setCancelled(true);
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType().ordinal());
            }
        }
    }
}