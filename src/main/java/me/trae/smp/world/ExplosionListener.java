package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.framework.blockrestore.BlockRestoreData;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMath;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener extends MainListener {

    public ExplosionListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onExplode(final EntityExplodeEvent e) {
        if (!(getInstance().getRepository().isExplosionHeal())) {
            return;
        }
        for (final Block block : e.blockList()) {
            if (block.getType().name().contains("CHEST") || block.getType().name().contains("FURNACE") || block.getType().name().contains("STONECUTTER") || block.getType().name().contains("SHULKER")) {
                continue;
            }
            new BlockRestoreData(getInstance(), block, Material.AIR, UtilMath.randomInt(10000, 20000), Sound.BLOCK_STONE_PLACE);
        }
    }
}