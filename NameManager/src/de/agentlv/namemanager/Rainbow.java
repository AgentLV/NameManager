package de.agentlv.namemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.utils.RainbowPlayer;

public class Rainbow {
	
	private static NameManager plugin;
	private static Map<String, RainbowPlayer> rainbowPlayers = new HashMap<String, RainbowPlayer>();
	public static Map<Team, Set<String>> rainbowTeams = new HashMap<Team, Set<String>>();
	private static BukkitTask rainbowTask;
	
	public Rainbow(NameManager plugin) {
		Rainbow.plugin = plugin;
	}
	
	public static boolean enableRainbow(String playerName) {
		
		if (rainbowPlayers.containsKey(playerName))
			return true;
		
		//Does a team with the players name already exist?
		Team newTeam = NameManager.board.getTeam(playerName);
		Team oldTeam = NameManager.board.getEntryTeam(playerName);
		
		if (newTeam == null) 
			newTeam = NameManager.board.registerNewTeam(playerName);
		
		RainbowPlayer rainbowPlayer = new RainbowPlayer();
		rainbowPlayer.setColor(ChatColor.AQUA);
		rainbowPlayer.setTeam(NameManager.board.getEntryTeam(playerName));
			
		if (oldTeam != null) {
			rainbowPlayer.setPrefix(oldTeam.getPrefix());
			
			String prefix = oldTeam.getPrefix();
			
			prefix = prefix.substring(0, (prefix.length() > 14) ? 14 : prefix.length()) + ChatColor.AQUA.toString();
			
			newTeam.setPrefix(prefix);
			newTeam.setSuffix(oldTeam.getSuffix() != null ? oldTeam.getSuffix() : "");
		}
		
		newTeam.addEntry(playerName);
		
		rainbowPlayers.put(playerName, rainbowPlayer);
		
		if(rainbowTask == null || !plugin.getServer().getScheduler().isCurrentlyRunning(rainbowTask.getTaskId()))
			startRainbowTask();
		
		return false;
	}
	
	public static void disableRainbow(String playerName) {
		
		if (playerName != null && rainbowPlayers.containsKey(playerName)) {
			
			Team team = rainbowPlayers.get(playerName).getTeam();
			
			if (team == null) {
				NameManager.board.getTeam(playerName).unregister();
			} else if (team.getName().equals(playerName)) {
				NameManager.board.getEntryTeam(playerName).setPrefix(rainbowPlayers.get(playerName).getPrefix());
			} else {
				team.addEntry(playerName);
				NameManager.board.getTeam(playerName).unregister();
			}
		
			rainbowPlayers.remove(playerName);
			
			if (rainbowPlayers.size() == 0) 
				plugin.getServer().getScheduler().cancelTask(rainbowTask.getTaskId());
		
		}
		
	}

	private static void startRainbowTask() {
		
		rainbowTask = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			
			Random random = new Random();
			@Override
			public void run() {
					
					for(Entry<String, RainbowPlayer> entry : rainbowPlayers.entrySet()) {
						String playerName = entry.getKey();
						RainbowPlayer rp = entry.getValue();
						Team team = NameManager.board.getEntryTeam(playerName);
						String prefix = team.getPrefix();
						prefix = prefix.substring(0, prefix.length() - 2);
						
						ChatColor color = ChatColor.valueOf(NameManager.colors[random.nextInt(NameManager.colors.length - 1)]);
						prefix += color;
						team.setPrefix(prefix);
						rp.setColor(color);
					}
				}
			
			},10, 10);
		
	}
	
}
