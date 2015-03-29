package io.github.AgentLV;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
	
	NameManager plugin;
	
	public EventListener(NameManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		
			Player p = e.getPlayer();
		
			if( NameManager.board.getTeam(p.getName()) != null ) {
				NameManager.team = NameManager.board.getTeam(p.getName());
				NameManager.team.addPlayer(p);
			} else if( p.hasPermission("NameManager.black") ) {
			    NameManager.team = NameManager.board.getTeam("NM_black");
		        NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkblue") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkblue");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkgreen") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkgreen");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkaqua") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkaqua");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkred") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkred");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkpurple") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkpurple");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.gold") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_gold");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.gray") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_gray");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.darkgray") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_darkgray");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.blue") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_blue");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.green") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_green");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.aqua") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_aqua");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.red") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_red");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.lightpurple") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_lightpurple");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.yellow") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_yellow");
				NameManager.team.addPlayer(p);
	        } else if( p.hasPermission("NameManager.white") ) {
	        	NameManager.team = NameManager.board.getTeam("NM_white");
				NameManager.team.addPlayer(p);
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
		if(plugin.getConfig().getBoolean("Messages")) {
			Player p = e.getPlayer();
			if(plugin.getConfig().getBoolean("CustomNameForMessages")) {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Leave").replaceAll("%player%", p.getDisplayName())));
			}
			
			
		}
	}

}