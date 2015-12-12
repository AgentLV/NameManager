package de.agentlv.namemanager.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.files.PlayersFileHandler;
import de.agentlv.namemanager.utils.PlayerGroupHandler;
import de.agentlv.namemanager.utils.UUIDFetcher;

public class NameManagerAPI {
	
	private static NameManager plugin;
	private static Map<String, OfflinePlayer> cachedPlayers = new HashMap<String, OfflinePlayer>();
	
	public NameManagerAPI(NameManager plugin) {
		NameManagerAPI.plugin = plugin;
	}
	
	public static void setNametag(OfflinePlayer player, String prefix, String suffix) {
		Team team;
		String playerName = player.getName();
		
		if (NameManager.board.getTeam(playerName) == null) {
			team = NameManager.board.registerNewTeam(playerName);
		} else {
			team = NameManager.board.getTeam(playerName);
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		team.addEntry(playerName);
		
		PlayersFileHandler.write(player.getUniqueId().toString(), prefix, suffix);
		
	}

	public static void setNametagPrefix(OfflinePlayer player, String prefix) {
		Team team;
		String playerName = player.getName();
		
		if (NameManager.board.getTeam(playerName) == null) {
			team = NameManager.board.registerNewTeam(playerName);
		} else {
			team = NameManager.board.getTeam(playerName);
		}
		
		team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		team.addEntry(playerName);
		
		PlayersFileHandler.writePrefix(player.getUniqueId().toString(), prefix);
	}
	
	public static void setNametagSuffix(OfflinePlayer player, String suffix) {
		Team team;
		String playerName = player.getName();
		
		if (NameManager.board.getTeam(playerName) == null) {
			team = NameManager.board.registerNewTeam(playerName);
		} else {
			team = NameManager.board.getTeam(playerName);
		}
		
		team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
		team.addEntry(playerName);
		
		PlayersFileHandler.writeSuffix(player.getUniqueId().toString(), suffix);
	}
	
	public static void setNametagColor(String playerName, String color) {
		Team team = NameManager.board.getTeam("NM_" + color);
		
		if (team == null) 
			throw new IllegalArgumentException("Invalid Color!");
			
		team.addEntry(playerName);
	}
	
	public static String getNametag(String playerName) {
		Team team = NameManager.board.getEntryTeam(playerName);
		String prefix = "";
		String suffix = "";
		
		if (team != null) {
			if (team.getPrefix() != null) {
				prefix = team.getPrefix();
			}
			if (team.getSuffix() != null) {
				suffix = team.getSuffix();
			}
		}
		return prefix + playerName + suffix;
	}
	
	public static String getNametagPrefix(String playerName) {
		Team team = NameManager.board.getEntryTeam(playerName);
		String prefix = "";
		
		if (NameManager.board.getEntryTeam(playerName) != null) {
			
			if (team.getPrefix() != null) {
				prefix = team.getPrefix();
			}
		}
		
		return prefix;
	}
	
	public static String getNametagSuffix(String playerName) {
		Team team = NameManager.board.getEntryTeam(playerName);
		String suffix = "";
		
		if (NameManager.board.getEntryTeam(playerName) != null) {
			
			if (team.getSuffix() != null) {
				suffix = team.getSuffix();
			}
		}
		
		return suffix;
	}
	
	public static void clearNametag(OfflinePlayer player) {
		Team team = NameManager.board.getTeam(player.getName());
		
		if (team != null)
			team.unregister();
		
		PlayersFileHandler.removeEntry(player.getUniqueId().toString());
		
		PlayerGroupHandler.add(player.getPlayer());
		
	}
	
	public static OfflinePlayer playerToOfflinePlayer(String playerName) {
		
		Player p = plugin.getServer().getPlayer(playerName);
		OfflinePlayer offlinePlayer = null;
		
		
		if (p != null && p.isOnline())  {
			offlinePlayer = p;
		} else if (cachedPlayers.containsKey(playerName)) {
			offlinePlayer = cachedPlayers.get(playerName);
		} else {
			try {
				offlinePlayer = plugin.getServer().getOfflinePlayer(UUIDFetcher.getUUIDOf(playerName));
				cachedPlayers.put(playerName, offlinePlayer);
			} catch (Exception e) {	}
			
		}
		
		return offlinePlayer;
	}

}
