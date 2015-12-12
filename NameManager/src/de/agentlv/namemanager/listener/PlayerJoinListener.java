package de.agentlv.namemanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.api.NameManagerAPI;
import de.agentlv.namemanager.utils.PlayerGroupHandler;

public class PlayerJoinListener implements Listener {
	
	private NameManager plugin;
	private FileConfiguration config;
	
	public PlayerJoinListener(NameManager plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		final Player p = e.getPlayer();	
		final String playerName = p.getName();
		
		if (config.getBoolean("HealthBelowName")) 
			p.setScoreboard(NameManager.board);
		
		PlayerGroupHandler.add(p);
			
		if (config.getBoolean("Messages")) {
			
			if (config.getBoolean("CustomNameForMessages")) {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Join").replaceAll("%player%", NameManagerAPI.getNametag(playerName))));
			} else {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Join").replaceAll("%player%", playerName)));
			}
			
		}
		
		if (config.getBoolean("Bungee")) {
			
			plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF(p.getName());
					out.writeUTF("TablistName");
					out.writeUTF(NameManagerAPI.getNametag(playerName));

					p.sendPluginMessage(plugin, "NameManager", out.toByteArray());
				}
			}, 1L);
			
		}
		
		if (NameManager.useVault) {
			NameManager.chat.setPlayerPrefix(p, NameManagerAPI.getNametagPrefix(playerName));
			NameManager.chat.setPlayerSuffix(p, NameManagerAPI.getNametagSuffix(playerName));
		}
	}
}
