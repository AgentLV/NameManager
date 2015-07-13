package io.github.AgentLV.NameManager.API;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import io.github.AgentLV.NameManager.Files.FileHandler;
import io.github.AgentLV.NameManager.NameManager;

public class NameManagerGroupAPI {
	
	static NameManager plugin;
	private static Team team = null;
	public static HashMap<String, Integer> groups = new HashMap<String, Integer>();
	
	public NameManagerGroupAPI(NameManager plugin) {
		NameManagerAPI.plugin = plugin;
	}
	
	public static void setGroupNametag(String prefix, String group, String suffix) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.put(group, groups.size());
		} else {
			team = NameManager.board.getTeam(groups.get( group ) + group);
		}

		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		
		FileHandler.writeGroupPrefix(group, prefix);
		FileHandler.writeGroupSuffix(group, suffix);
	}
	
	public static void setGroupNametagPrefix(String group, String prefix) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.put(group, groups.size());
		} else {
			team = NameManager.board.getTeam(groups.get( group ) + group);
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		FileHandler.writeGroupPrefix(group, prefix);
	}
	
	public static void setGroupNametagSuffix(String group, String suffix) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.put(group, groups.size());
		} else {
			team = NameManager.board.getTeam(groups.get( group ) + group);
		}
		
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		FileHandler.writeGroupSuffix(group, suffix);
	}
	
	public static String getGroupNametag(String group) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			team = NameManager.board.getTeam(groups.get( group ) + group);
			return team.getPrefix() + group + team.getSuffix();
		}
		return null;
	}
	
	public static String getGroupNametagPrefix(String group) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			return NameManager.board.getTeam(groups.get( group ) + group).getPrefix();
		}
		return null;
	}
	
	public static String getGroupNametagSuffix(String group) {
		if ( !groups.containsKey(group) && NameManager.board.getTeam( groups.get( ( group ) ) + group) == null ) {
			return NameManager.board.getTeam(groups.get( group ) + group).getSuffix();
		}
		return null;
	}
	
	public static void removeGroup(String group) {
		team = NameManager.board.getTeam(groups.get( group ) + group);
		if(team != null)
			team.unregister();
		
		FileHandler.removeGroup(group);
		groups.remove(group);
	}
	
	
}
