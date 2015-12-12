package net.agentlv.namemanager.listener;

import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.api.NameManagerAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

/**
 * @author AgentLV
 */
public class PlayerKickListener implements Listener {

    private FileConfiguration config;

    public PlayerKickListener(NameManager plugin) {
        config = plugin.getConfig();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (config.getBoolean("Messages")) {

            if (config.getBoolean("CustomNameForMessages")) {
                event.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(player))));
            } else {
                event.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", playerName)));
            }

        }

    }

}
