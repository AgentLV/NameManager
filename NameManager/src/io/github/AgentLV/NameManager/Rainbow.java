package io.github.AgentLV.NameManager;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitTask;

public class Rainbow {
	
	static NameManager plugin;
	static ArrayList<OfflinePlayer> rainbowPlayers = new ArrayList<OfflinePlayer>();
	static BukkitTask rainbowTask;
	
	
	public Rainbow(NameManager plugin) {
		Rainbow.plugin = plugin;
	}
	
	public static void enableRainbow(final OfflinePlayer of) {
		
		rainbowPlayers.add(of);
		
		if(rainbowTask == null || !plugin.getServer().getScheduler().isCurrentlyRunning(rainbowTask.getTaskId()))
			startScheduleTasks();
	}
	
	public static void disableRainbow(OfflinePlayer of) {
		rainbowPlayers.remove(of);
		
		if (rainbowPlayers.size() == 0) 
			plugin.getServer().getScheduler().cancelTask(rainbowTask.getTaskId());
		
	}

	private static void startScheduleTasks() {
		
		final String[] colors = { "black", "darkblue", "darkgreen", "darkaqua", "darkred", "darkpurple", "gold", "gray", "darkgray", "blue", "green", "aqua", "red", "lightpurple", "yellow", "white"};
		
		rainbowTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			Random random = new Random();
			@Override
			public void run() {
					
					for(OfflinePlayer rainbowPlayer : rainbowPlayers) {
						NameManager.rainbow = NameManager.board.getTeam("NM_" + colors[random.nextInt(15)]);
						NameManager.rainbow.addEntry(rainbowPlayer.getName());
					}
				}
			//Convert to ticks
			},100/20, 100/20);
		
		
	}
	
}
