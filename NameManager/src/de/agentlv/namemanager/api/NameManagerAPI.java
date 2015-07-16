package de.agentlv.namemanager.api;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.utils.UUIDFetcher;

public class NameManagerAPI {
	
	static NameManager plugin;
	private static Team team;
	static HashMap<String, OfflinePlayer> ofs = new HashMap<String, OfflinePlayer>();
	
	public NameManagerAPI(NameManager plugin) {
		NameManagerAPI.plugin = plugin;
	}
	
	public static void setNametag(String prefix, OfflinePlayer p, String suffix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			team = NameManager.board.registerNewTeam(p.getName());
		} else {
			team = NameManager.board.getTeam(p.getName());
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		team.addEntry(p.getName());
	}

	public static void setNametagPrefix(OfflinePlayer p, String prefix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			team = NameManager.board.registerNewTeam(p.getName());
		} else {
			team = NameManager.board.getTeam(p.getName());
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.addEntry(p.getName());
	}
	
	public static void setNametagSuffix(OfflinePlayer p, String suffix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			team = NameManager.board.registerNewTeam(p.getName());
		} else {
			team = NameManager.board.getTeam(p.getName());
		}
		
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		team.addEntry(p.getName());
	}
	
	public static void setNametagColor(OfflinePlayer p, String color) {
		team = NameManager.board.getTeam("NM_" + color);
		team.addEntry(p.getName());
	}
	
	public static String getNametag(OfflinePlayer p) {
		String prefix = "";
		String suffix = "";
		team = NameManager.board.getEntryTeam(p.getName());
		
		if ( team != null ) {
			if (team.getPrefix() != null) {
				prefix = team.getPrefix();
			}
			if (team.getSuffix() != null) {
				suffix = team.getSuffix();
			}
		}
		return prefix + p.getName() + suffix;
	}
	
	public static String getNametagPrefix(OfflinePlayer p) {
		String prefix = "";
		team = NameManager.board.getEntryTeam(p.getName());
		
		if ( NameManager.board.getEntryTeam(p.getName()) != null ) {
			
			if ( team.getPrefix() != null ) {
				prefix = team.getPrefix();
			}
		}
		
		return prefix;
	}
	
	public static String getNametagSuffix(OfflinePlayer p) {
		String suffix = "";
		team = NameManager.board.getEntryTeam(p.getName());
		
		if ( NameManager.board.getEntryTeam(p.getName()) != null ) {
			
			if ( team.getSuffix() != null ) {
				suffix = team.getSuffix();
			}
		}
		
		return suffix;
	}
	
	//Only works for custom prefixes and suffixes
	public static void clearNametag(OfflinePlayer p) {
		team = NameManager.board.getTeam(p.getName());
		if(team != null)
			team.unregister();
	}
	
	public static OfflinePlayer playerToOfflinePlayer(String playerName) {
		
		Player p = plugin.getServer().getPlayer(playerName);
		OfflinePlayer offlinePlayer = null;
		
		
		if(p != null && p.isOnline())  {
			offlinePlayer = p;
		} else if ( ofs.containsKey( playerName ) ) {
			offlinePlayer = ofs.get( playerName );
		} else {
			try {
				offlinePlayer = plugin.getServer().getOfflinePlayer(UUIDFetcher.getUUIDOf(playerName));
				ofs.put(playerName, offlinePlayer);
			} catch (Exception e) {	}
			
		}
		
		return offlinePlayer;
	}

}
