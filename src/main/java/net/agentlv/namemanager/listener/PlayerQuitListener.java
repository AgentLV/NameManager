package net.agentlv.namemanager.listener;

import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.Rainbow;
import net.agentlv.namemanager.api.NameManagerAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author AgentLV
 */
public class PlayerQuitListener implements Listener {

    private FileConfiguration config;

    public PlayerQuitListener(NameManager plugin) {
        config = plugin.getConfig();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (config.getBoolean("Messages")) {

            if (config.getBoolean("CustomNameForMessages")) {
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(player))));
            } else {
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", playerName)));
            }
        }

        Rainbow.disableRainbow(playerName);

        NameManager.getMultiScoreboard().unregisterPlayerTeam(playerName);
    }
}
