package io.github.AgentLV.NameManager;

import io.github.AgentLV.NameManager.API.NameManagerAPI;
import io.github.AgentLV.NameManager.Files.ConfigAccessor;
import io.github.AgentLV.NameManager.Files.FileManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EventListener implements Listener {
	
	NameManager plugin;
	int i;
	ConfigAccessor cConfig;
	
	public EventListener(NameManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		cConfig = NameManager.cConfig;
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
	public void join(PlayerJoinEvent e) {

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
	}
	
	
	@EventHandler
	public void leave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if(cConfig.getConfig().getBoolean("Messages")) {
			
			if(cConfig.getConfig().getBoolean("CustomNameForMessages")) {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", p.getName())));
			}
		}
		
		if (Commands.map.containsKey(p) || Commands.teams.contains(NameManager.board.getEntryTeam(p.getName()))) {
			
			Rainbow.disableRainbow(p);
			Team team = Commands.map.get(p);
			
			if (team != null) {
				team.addEntry(p.getName());
			} else {
				NameManager.rainbow.removeEntry(p.getName());
			}
			
			Commands.map.remove(p);
		}
		
		if (NameManager.board.getEntryTeam(p.getName()) != null)
			NameManager.board.getEntryTeam(p.getName()).removeEntry(p.getName());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		
		if(cConfig.getConfig().getBoolean("Messages")) {
			
			if(cConfig.getConfig().getBoolean("CustomNameForMessages")) {
				e.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", p.getName())));
			}
			
		}
		
		if (Commands.map.containsKey(p) || Commands.teams.contains(NameManager.board.getEntryTeam(p.getName()))) {
			
			Rainbow.disableRainbow(p);
			Team team = Commands.map.get(p);
			
			if (team != null) {
				team.removeEntry(p.getName());
			} else {
				NameManager.rainbow.removeEntry(p.getName());
			}
			
			Commands.map.remove(p);
		}
		
		if (NameManager.board.getEntryTeam(p.getName()) != null)
			NameManager.board.getEntryTeam(p.getName()).removeEntry(p.getName());
	}

}
