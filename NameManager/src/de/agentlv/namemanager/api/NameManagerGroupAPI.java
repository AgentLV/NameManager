package de.agentlv.namemanager.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.files.FileManager;
import de.agentlv.namemanager.files.GroupsFileHandler;
import de.agentlv.namemanager.utils.PlayerGroupHandler;

public class NameManagerGroupAPI {
	
	public static List<String> groups = new ArrayList<String>();
	private static NameManager plugin;
	
	public NameManagerGroupAPI(NameManager plugin) {
		NameManagerGroupAPI.plugin = plugin;
	}
	
	public static void setGroupNametag(String prefix, String group, String suffix) {
		Team team;
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.add(group);
		} else {
			team = NameManager.board.getTeam(groups.indexOf(group) + group);
		}

		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		
		GroupsFileHandler.writeGroup(group, prefix, suffix);
	}
	
	public static void setGroupNametagPrefix(String group, String prefix) {
		Team team;
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.add(group);
		} else {
			team = NameManager.board.getTeam(groups.indexOf(group) + group);
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		GroupsFileHandler.writeGroupPrefix(group, prefix);
	}
	
	public static void setGroupNametagSuffix(String group, String suffix) {
		Team team;
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			team = NameManager.board.registerNewTeam(groups.size() + group);
			groups.add(group);
		} else {
			team = NameManager.board.getTeam(groups.indexOf(group) + group);
		}
		
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		GroupsFileHandler.writeGroupSuffix(group, suffix);
	}
	
	public static String getGroupNametag(String group) {
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			Team team = NameManager.board.getTeam(groups.indexOf(group) + group);
			return team.getPrefix() + group + team.getSuffix();
		}
		
		return null;
	}
	
	public static String getGroupNametagPrefix(String group) {
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			return NameManager.board.getTeam(groups.indexOf(group) + group).getPrefix();
		}
		
		return null;
	}
	
	public static String getGroupNametagSuffix(String group) {
		
		if (!groups.contains(group) && NameManager.board.getTeam(groups.indexOf((group)) + group) == null) {
			return NameManager.board.getTeam(groups.indexOf(group) + group).getSuffix();
		}
		
		return null;
	}
	
	public static void removeGroup(String group) {
		Team team = NameManager.board.getTeam(groups.indexOf(group) + group);
		Set<String> players = team.getEntries();
		
		if(team != null)
			team.unregister();
		
		GroupsFileHandler.removeGroup(group);
		FileManager.unloadFromFile();
		FileManager.loadFromFile();
		
		for (String player : players)
			PlayerGroupHandler.add(plugin.getServer().getPlayer(player));
	}
	
}
