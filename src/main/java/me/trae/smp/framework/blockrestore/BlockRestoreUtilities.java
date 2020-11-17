package me.trae.smp.framework.blockrestore;

import me.trae.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public final class BlockRestoreUtilities {

    private final List<BlockRestoreData> data = new ArrayList<>();

    public BlockRestoreUtilities(final Main instance) {
        Bukkit.getServer().getPluginManager().registerEvents(new BlockRestoreManager(instance), instance);
    }

    public final BlockRestoreData getRestoreBlock(final Block block) {
        for (final BlockRestoreData brd : data) {
            if (brd.getBlock().equals(block)) {
                return brd;
            }
        }
        return null;
    }

    public final boolean isRestoredBlock(final Block block) {
        return getRestoreBlock(block) != null;
    }

    public final List<BlockRestoreData> getDataList() {
        return data;
    }
}