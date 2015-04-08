package io.github.AgentLV.NameManager.API;

import org.bukkit.ChatColor;

import io.github.AgentLV.NameManager.NameManager;
import io.github.AgentLV.NameManager.Files.FileHandler;

public class GroupAPI {

static NameManager plugin;
	
	public GroupAPI(NameManager plugin) {
		API.plugin = plugin;
	}
	
	public static void setGroupNametag(String prefix, String group, String suffix) {
		if ( NameManager.board.getTeam(group) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(group);
		} else {
			NameManager.team = NameManager.board.getTeam(group);
		}
		
		NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
	}
	
	public static void setGroupNametagPrefix(String group, String prefix) {
		if ( NameManager.board.getTeam(group) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(group);
		} else {
			NameManager.team = NameManager.board.getTeam(group);
		}
		
		NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		FileHandler.writeGroupPrefix(group, prefix);
	}
	
	public static void setGroupNametagSuffix(String group, String suffix) {
		if ( NameManager.board.getTeam(group) == null ) {
			NameManager.team = NameManager.board.registerNewTeam(group);
		} else {
			NameManager.team = NameManager.board.getTeam(group);
		}
		
		NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		FileHandler.writeGroupSuffix(group, suffix);
	}
	
	public static String getGroupNametag(String group) {
		if ( NameManager.board.getTeam(group) != null ) {
			NameManager.team = NameManager.board.getTeam(group);
			return NameManager.team.getPrefix() + "Example" + NameManager.team.getSuffix();
		}
		return null;
	
	}
	
	public static String getGroupNametagPrefix(String group) {
		if ( NameManager.board.getTeam(group) != null ) {
			return NameManager.board.getTeam(group).getPrefix();
		}
		return null;
	}
	
	public static String getGroupNametagSuffix(String group) {
		if ( NameManager.board.getTeam(group) != null ) {
			return NameManager.board.getTeam(group).getSuffix();
		}
		return null;
	}
	
	public static void removeGroup(String group) {
		NameManager.team = NameManager.board.getTeam(group);
		if(NameManager.team != null)
			NameManager.team.unregister();
	}
	
}
