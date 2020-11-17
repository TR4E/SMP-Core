package me.trae.smp.world;

import me.trae.smp.Main;
import me.trae.smp.client.Client;
import me.trae.smp.client.Rank;
import me.trae.smp.module.MainListener;
import me.trae.smp.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends MainListener {

    public ChatListener(final Main instance) {
        super(instance, true);
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        final Player player = e.getPlayer();
        final Client client = getInstance().getClientUtilities().getOnlineClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        if (!(player.isOp()) && client.isMuted()) {
            UtilMessage.message(player, "Punish", "You are currently muted!");
            return;
        }
        if (!(client.hasRank(Rank.MOD, false)) && !(getInstance().getRechargeManager().add(player, "Chat", (client.hasRank(Rank.MEMBER, false) ? 1000L : 2000L), false))) {
            UtilMessage.message(player, "Chat", "Please wait " + ChatColor.GREEN + getInstance().getRechargeManager().getCooldownLeft(player, "Chat") + ChatColor.GRAY + " before sending a new message.");
            return;
        }
        final String message = e.getMessage();
        final String rank = ((client.getRank() != Rank.PLAYER) ? client.getRank().getTag(true) + " " : "");
        UtilMessage.broadcast(rank + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + ": " + message, null);
    }
}