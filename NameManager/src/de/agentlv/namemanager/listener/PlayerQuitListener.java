package de.agentlv.namemanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.Rainbow;
import de.agentlv.namemanager.api.NameManagerAPI;

public class PlayerQuitListener implements Listener {

	private FileConfiguration config;
	
	public PlayerQuitListener(NameManager plugin) {
		config = plugin.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		String playerName = e.getPlayer().getName();
		
		if (config.getBoolean("Messages")) {
			
			if (config.getBoolean("CustomNameForMessages")) {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(playerName))));
			} else {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Leave").replaceAll("%player%", playerName)));
			}
		}
		
		Rainbow.disableRainbow(playerName);
		
		if (NameManager.board.getEntryTeam(playerName) != null)
			NameManager.board.getEntryTeam(playerName).removeEntry(playerName);
	}
	
}
