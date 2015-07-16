package de.agentlv.namemanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.api.NameManagerAPI;
import de.agentlv.namemanager.files.ConfigAccessor;
import de.agentlv.namemanager.files.FileManager;

public class PlayerJoinListener implements Listener {
	
	NameManager plugin;
	int i;
	ConfigAccessor cConfig;
	boolean useVault = false;
	
	public PlayerJoinListener(NameManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		cConfig = NameManager.cConfig;
		useVault = NameManager.useVault;
	}
	
	private String playerGroupChecker(Player p) {
		i = 0;
		for(String s : FileManager.allGroups) {
			if(p.hasPermission("NameManager.group." + s)) {
				return s;
			}
			++i;
		}
		return null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		final Player p = e.getPlayer();
		
		if (cConfig.getConfig().getBoolean("HealthBelowName")) 
			p.setScoreboard(NameManager.board);
			
		if(NameManager.board.getTeam(p.getName()) != null) {
			
			NameManager.board.getTeam(p.getName()).addEntry(p.getName());;
			
		} else if (playerGroupChecker(p) != null) {
			
			NameManager.board.getTeam(i + playerGroupChecker(p)).addEntry(p.getName());
			
		} else if(p.hasPermission("NameManager.black")) {
			
		    NameManager.board.getTeam("NM_black").addEntry(p.getName());
		    
        } else if(p.hasPermission("NameManager.darkblue")) {
        	
        	NameManager.board.getTeam("NM_darkblue").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.darkgreen")) {
        	
        	NameManager.board.getTeam("NM_darkgreen").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.darkaqua")) {
        	
        	NameManager.board.getTeam("NM_darkaqua").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.darkred")) {
        	
        	NameManager.board.getTeam("NM_darkred").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.darkpurple")) {
        	
        	NameManager.board.getTeam("NM_darkpurple").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.gold")) {
        	
        	NameManager.board.getTeam("NM_gold").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.gray")) {
        	
        	NameManager.board.getTeam("NM_gray").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.darkgray")) {
        	
        	NameManager.board.getTeam("NM_darkgray").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.blue")) {
        	
        	NameManager.board.getTeam("NM_blue").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.green")) {
        	
        	NameManager.board.getTeam("NM_green").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.aqua")) {
        	
        	NameManager.board.getTeam("NM_aqua").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.red")) {
        	
        	NameManager.board.getTeam("NM_red").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.lightpurple")) {
        	
        	NameManager.board.getTeam("NM_lightpurple").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.yellow")) {
        	
        	NameManager.board.getTeam("NM_yellow").addEntry(p.getName());
        	
        } else if(p.hasPermission("NameManager.white")) {
        	
        	NameManager.board.getTeam("NM_white").addEntry(p.getName());
        	
        } else {
        	
        	NameManager.board.getTeam("ZZZZZZZZZZZZZZZZ").addEntry(p.getName());
        	
        }
			
		if(cConfig.getConfig().getBoolean("Messages")) {
			if(cConfig.getConfig().getBoolean("CustomNameForMessages")) {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Join").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Join").replaceAll("%player%", p.getName())));
			}
			
		}
		
		if (cConfig.getConfig().getBoolean("Bungee")) {
			
			plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
				
				@Override
				public void run() {
					
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF(p.getName());
					out.writeUTF("TablistName");
					out.writeUTF(NameManagerAPI.getNametag(p));

					p.sendPluginMessage(plugin, "NameManager", out.toByteArray());
				}
			}, 1L);
			
		}
		
		if (useVault) {
			NameManager.chat.setPlayerPrefix(p, NameManagerAPI.getNametagPrefix(p));
			NameManager.chat.setPlayerSuffix(p, NameManagerAPI.getNametagSuffix(p));
		}
	}
}
