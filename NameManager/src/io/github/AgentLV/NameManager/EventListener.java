package io.github.AgentLV.NameManager;

import io.github.AgentLV.NameManager.API.NameManagerAPI;
import io.github.AgentLV.NameManager.Files.FileManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class EventListener implements Listener {
	
	NameManager plugin;
	int i;
	
	public EventListener(NameManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private String playerGroupChecker(Player p) {
		i = 0;
		for(String s : FileManager.allGroups) {
			if(p.hasPermission("NameManager." + s)) {
				return s;
			}
			++i;
		}
		return null;
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if (plugin.getConfig().getBoolean("HealthBelowName")) 
			p.setScoreboard(NameManager.board);
			
	
		if( NameManager.board.getTeam(p.getName()) != null ) {
			NameManager.board.getTeam(p.getName()).addPlayer(p);
		} else if (playerGroupChecker(p) != null) {
			NameManager.board.getTeam(i + playerGroupChecker(p)).addPlayer(p);
		} else if( p.hasPermission("NameManager.black") ) {
			
		    NameManager.board.getTeam("NM_black").addPlayer(p);
		    
        } else if( p.hasPermission("NameManager.darkblue") ) {
        	
        	NameManager.board.getTeam("NM_darkblue").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.darkgreen") ) {
        	
        	NameManager.board.getTeam("NM_darkgreen").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.darkaqua") ) {
        	
        	NameManager.board.getTeam("NM_darkaqua").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.darkred") ) {
        	
        	NameManager.board.getTeam("NM_darkred").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.darkpurple") ) {
        	
        	NameManager.board.getTeam("NM_darkpurple").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.gold") ) {
        	
        	NameManager.board.getTeam("NM_gold").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.gray") ) {
        	
        	NameManager.board.getTeam("NM_gray").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.darkgray") ) {
        	
        	NameManager.board.getTeam("NM_darkgray").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.blue") ) {
        	
        	NameManager.board.getTeam("NM_blue").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.green") ) {
        	
        	NameManager.board.getTeam("NM_green").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.aqua") ) {
        	
        	NameManager.board.getTeam("NM_aqua").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.red") ) {
        	
        	NameManager.board.getTeam("NM_red").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.lightpurple") ) {
        	
        	NameManager.board.getTeam("NM_lightpurple").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.yellow") ) {
        	
        	NameManager.board.getTeam("NM_yellow").addPlayer(p);
        	
        } else if( p.hasPermission("NameManager.white") ) {
        	
        	NameManager.board.getTeam("NM_white").addPlayer(p);
        	
        } else {
        	
        	NameManager.board.getTeam("ZZZZZZZZZZZZZZZZ").addPlayer(p);
        	
        }
			
		if(plugin.getConfig().getBoolean("Messages")) {
			if(plugin.getConfig().getBoolean("CustomNameForMessages")) {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Join").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Join").replaceAll("%player%", p.getDisplayName())));
			}
			
		}
		
	}
	
	
	@EventHandler
	public void leave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if(plugin.getConfig().getBoolean("Messages")) {
			
			if(plugin.getConfig().getBoolean("CustomNameForMessages")) {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Leave").replaceAll("%player%", p.getDisplayName())));
			}
			
			
		}
		
		if (Commands.map.containsKey(p) || Commands.teams.contains(NameManager.board.getPlayerTeam(p))) {
			
			Rainbow.disableRainbow(p);
			Team team = Commands.map.get(p);
			
			if (team != null) {
				team.addPlayer(p);
			} else {
				NameManager.rainbow.removePlayer(p);
			}
			
			Commands.map.remove(p);
		}
		
		if (NameManager.board.getPlayerTeam(p) != null)
			NameManager.board.getPlayerTeam(p).removePlayer(p);
	}

}