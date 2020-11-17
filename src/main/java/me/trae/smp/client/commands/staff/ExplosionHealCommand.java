package me.trae.smp.client.commands.staff;

import me.trae.smp.Main;
import me.trae.smp.client.Rank;
import me.trae.smp.command.Command;
import me.trae.smp.framework.blockrestore.BlockRestoreData;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ExplosionHealCommand extends Command {

    public ExplosionHealCommand(final Main instance) {
        super(instance, "explosionheal", new String[]{"tntheal", "creeperheal"}, Rank.OWNER, false);
    }

    @Override
    public void execute(final Player player, final String[] args) {
        if (!(getInstance().getRepository().isExplosionHeal())) {
            UtilMessage.message(player, "Game", ChatColor.YELLOW + "Explosion Heal" + ChatColor.GRAY + " is not currently enabled.");
            return;
        }
        if (args == null || args.length == 0) {
            final List<BlockRestoreData> list = getInstance().getBlockRestoreUtilities().getDataList();
            if (list.size() > 0) {
                for (final BlockRestoreData data : list) {
                    if (data.getBlock().getType().equals(Material.AIR)) {
                        data.restore();
                    }
                }
                UtilMessage.message(player, "Explosion Heal", "You regenerated all the Explosion Blocks.");
            } else {
                UtilMessage.message(player, "Explosion Heal", "There is currently no Explosion Blocks to be restored.");
            }
        }
    }

    @Override
    public void help(final Player player) {

    }
}