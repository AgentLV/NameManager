package io.github.AgentLV;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Rainbow {
	
	static NameManager plugin;
	static ArrayList<Player> rainbowPlayers = new ArrayList<Player>();
	static BukkitTask rainbowTask;
	
	
	public Rainbow(NameManager plugin) {
		Rainbow.plugin = plugin;
	}
	
	public static void enableRainbow(final Player p) {
		
		rainbowPlayers.add(p);
		
		if(rainbowPlayers.size() == 1)
			startScheduleTasks();
	}
	
	public static void disableRainbow(Player p) {
		rainbowPlayers.remove(p);
		
		if (rainbowPlayers.size() == 0) 
			plugin.getServer().getScheduler().cancelTask(rainbowTask.getTaskId());
		
	}

	private static void startScheduleTasks() {
		
		final String[] colors = { "black", "darkblue", "darkgreen", "darkaqua", "darkred", "darkpurple", "gold", "gray", "darkgray", "blue", "green", "aqua", "red", "lightpurple", "yellow", "white"};
		
		rainbowTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			Random random = new Random();
			@Override
			public void run() {
					
					for(Player rainbowPlayer : rainbowPlayers) {
						NameManager.rainbow = NameManager.board.getTeam("NM_" + colors[random.nextInt(15)]);
						NameManager.rainbow.addPlayer(rainbowPlayer);
					}
				}
			//Convert to ticks
			},100/20, 100/20);
		
		
	}
	
}
