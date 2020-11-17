package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CombatListener extends MainListener {

    public CombatListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onPlayerDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player player = (Player) e.getDamager();
            if (e.getEntity() instanceof Player) {
                if (!(getInstance().getRepository().isPvP())) {
                    e.setCancelled(true);
                    return;
                }
                final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
                if (client == null) {
                    return;
                }
                if (!(getInstance().getClientUtilities().isTrusted(player))) {
                    e.setCancelled(true);
                    return;
                }
            }
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.AIR)) {
                return;
            }
            final ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
            setAttackSpeed(player, 16.0D);
            UtilMessage.message(player, "Damage", "You dealt " + ChatColor.GREEN + e.getDamage() + ChatColor.GRAY + " damage.");
        }
    }

    private void setAttackSpeed(final Player player, final double attackSpeed) {
        final AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            final double baseValue = attribute.getBaseValue();
            if (baseValue != attackSpeed) {
                attribute.setBaseValue(attackSpeed);
                player.saveData();
            }
        }
    }
}