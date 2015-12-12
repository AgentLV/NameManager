package de.agentlv.namemanager.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.api.NameManagerAPI;
import de.agentlv.namemanager.api.NameManagerGroupAPI;

public class PlayerGroupHandler {
	
	public static List<String> createdPlayerTeams = new ArrayList<String>();

	public static void add(Player player) {
		
		String playerName = player.getName();
		String uuid = player.getUniqueId().toString();
		Team team = NameManager.board.getTeam(playerName);
		
		//Custom player group
		if (team != null) {
			createdPlayerTeams.add(playerName);
			team.addEntry(playerName);
			return;
		}
		
		if (NameManager.playerFile.getConfig().contains(uuid)) {
			String prefix = NameManager.playerFile.getConfig().getString(uuid + ".Prefix", "");
			String suffix = NameManager.playerFile.getConfig().getString(uuid + ".Suffix", "");
			NameManagerAPI.setNametag(player, prefix, suffix);
			
			createdPlayerTeams.add(playerName);
			return;
		}
		
		//Custom groups
		for (String teamName : NameManagerGroupAPI.groups) {
			
			if (player.hasPermission("NameManager.group." + teamName)) {
				NameManager.board.getTeam(NameManagerGroupAPI.groups.indexOf(teamName) + teamName).addEntry(playerName);
				return;
			}
			
		}
		
		//Default groups
		for (int i = 0; i < NameManager.colors.length; ++i) {
			
			if (player.hasPermission("NameManager." + NameManager.colors[i])) {
				NameManager.board.getTeam("NM_" + NameManager.colors[i]).addEntry(playerName);
				return;
			}
			
		}
		
		if (NameManager.board.getEntryTeam(playerName) == null)
			NameManager.board.getTeam("ZZZZZZZZZZZZZZZZ").addEntry(playerName);
	}
	
	public static void remove(String playerName) {
		Team team = NameManager.board.getEntryTeam(playerName);
		
		if (team != null)
			team.removeEntry(playerName);
			
	}
	
}
