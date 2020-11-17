package me.trae.smp.framework.blockrestore;

import me.trae.smp.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public final class BlockRestoreData {

    private final Main instance;
    private final Sound sound;
    private Block block;
    private final Material oldMaterial;
    private Material material;
    private BlockData data;
    private long expire;

    public BlockRestoreData(final Main instance, final Block block, final Material material, final long expire, final Sound sound) {
        this.instance = instance;
        this.block = block;
        this.material = material;
        this.data = block.getBlockData();
        this.oldMaterial = block.getType();
        this.expire = System.currentTimeMillis() + expire;
        this.sound = sound;
        if (!(instance.getBlockRestoreUtilities().isRestoredBlock(block))) {
            instance.getBlockRestoreUtilities().getDataList().add(this);
            setRestoreData();
        }
    }

    public final Block getBlock() {
        return block;
    }

    public void setBlock(final Block block) {
        this.block = block;
    }

    public final Material getMaterial() {
        return material;
    }

    public void setMaterial(final Material material) {
        this.material = material;
    }

    public final Material getOldMaterial() {
        return oldMaterial;
    }

    public final BlockData getData() {
        return data;
    }

    public void setData(final BlockData data) {
        this.data = data;
    }

    public final long getExpire() {
        return expire;
    }

    public void setExpire(final long expire) {
        this.expire = expire;
    }

    public final Sound getSound() {
        return sound;
    }

    public void setRestoreData() {
        getBlock().setType(getMaterial());
        getBlock().setBlockData(getData());
    }

    public void restore() {
        setRestoreData();
        getBlock().getState().update();
        setExpire(0L);
        if (getSound() != null) {
            getBlock().getWorld().playSound(getBlock().getLocation(), getSound(), 1.0F, 1.0F);
        }
    }

    public void update(final Block block, final Material material, final long expire) {
        setBlock(block);
        setMaterial(material);
        setData(block.getBlockData());
        setExpire(System.currentTimeMillis() + expire);
    }
}