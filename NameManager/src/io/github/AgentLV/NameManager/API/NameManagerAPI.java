package io.github.AgentLV.NameManager.API;

import io.github.AgentLV.NameManager.NameManager;
import io.github.AgentLV.NameManager.UUIDFetcher;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class NameManagerAPI {
	
	static NameManager plugin;
	
	public NameManagerAPI(NameManager plugin) {
		NameManagerAPI.plugin = plugin;
	}
	
	public static void setNametag(String prefix, OfflinePlayer p, String suffix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(p.getName());
		} else {
			NameManager.team = NameManager.board.getTeam(p.getName());
		}
		
		NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		NameManager.team.addPlayer(p);
	}

	public static void setNametagPrefix(OfflinePlayer p, String prefix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(p.getName());
		} else {
			NameManager.team = NameManager.board.getTeam(p.getName());
		}
		
		NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		NameManager.team.addPlayer(p);
	}
	
	public static void setNametagSuffix(OfflinePlayer p, String suffix) {
		if ( NameManager.board.getTeam(p.getName()) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(p.getName());
		} else {
			NameManager.team = NameManager.board.getTeam(p.getName());
		}
		
		NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		NameManager.team.addPlayer(p);
	}
	
	public static void setNametagColor(OfflinePlayer p, String color) {
		NameManager.team = NameManager.board.getTeam("NM_" + color);
		NameManager.team.addPlayer(p);
	}
	
	public static String getNametag(OfflinePlayer p) {
		String prefix = "";
		String suffix = "";
		NameManager.team = NameManager.board.getPlayerTeam(p);
		
		if (NameManager.team.getPrefix() != null) {
			prefix = NameManager.team.getPrefix();
		}
		if (NameManager.team.getSuffix() != null) {
			suffix = NameManager.team.getSuffix();
		}
		return prefix + p.getName() + suffix;
	}
	
	public static String getNametagPrefix(OfflinePlayer p) {
			NameManager.team = NameManager.board.getPlayerTeam(p);
			return NameManager.team.getPrefix();

	}
	
	public static String getNametagSuffix(OfflinePlayer p) {
			NameManager.team = NameManager.board.getPlayerTeam(p);
			return NameManager.team.getSuffix();

	}
	
	//Only works for custom prefixes and suffixes
	public static void clearNametag(OfflinePlayer p) {
		NameManager.team = NameManager.board.getTeam(p.getName());
		if(NameManager.team != null)
			NameManager.team.unregister();
	}
	
	public static UUID getUUID(OfflinePlayer p) throws Exception {
		return UUIDFetcher.getUUIDOf(p.getName());
	}
	
	public static OfflinePlayer playerToOfflinePlayer(String playerName) {
		
		Player p = plugin.getServer().getPlayer(playerName);
		OfflinePlayer offlinePlayer = null;
		
		
		if(p != null && p.isOnline())  {
			offlinePlayer = p;
		} else {
			
			try {
				offlinePlayer = plugin.getServer().getOfflinePlayer(UUIDFetcher.getUUIDOf(playerName));
				
			} catch (Exception e) {	}
			
		}
		
		return offlinePlayer;
	}

}
