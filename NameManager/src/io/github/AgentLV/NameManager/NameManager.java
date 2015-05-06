package io.github.AgentLV.NameManager;

import java.io.File;
import java.io.IOException;

import io.github.AgentLV.NameManager.API.NameManagerAPI;
import io.github.AgentLV.NameManager.API.NameManagerGroupAPI;
import io.github.AgentLV.NameManager.Files.FileHandler;
import io.github.AgentLV.NameManager.Files.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameManager extends JavaPlugin {
	
	public static Scoreboard board;
	public static Team team;
	public static Team rainbow;
	static Objective objective;
	
	@Override
	public void onEnable() {

		board = Bukkit.getScoreboardManager().getMainScoreboard();
		team = null;
		rainbow = null;
		
		new EventListener(this);
		new NameManagerAPI(this);
		new NameManagerGroupAPI(this);
		new Rainbow(this);
		new FileManager(this);
		new FileHandler(this);
		
		FileManager.getFileConfiguration("config");
		FileManager.loadFromFile();
		FileManager.initGroupsFile();
		initTeams();
		
		getCommand("namemanager").setExecutor(new Commands(this));
		
		if (getConfig().getBoolean("HealthBelowName") && board.getObjective("showhealth") == null) {
				objective = board.registerNewObjective("showhealth", "health");
				objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
				objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("HealthIcon")));
		}
		
	}
	
	@Override
	public void onDisable() {
		unregisterTeams();
		FileManager.unloadFromFile();
		
		if (board.getObjective("showhealth") != null) {
			objective = board.getObjective("showhealth");
			objective.unregister();
		}
		
		File file = new File(getDataFolder(), "Groups.yml");
		try {
			FileManager.getFileConfiguration("Groups").save(file);
		} catch (IOException e) {
			getLogger().warning("§cGroups.yml could not be saved!");
		}
	}

	private void initTeams() {
		
		board.registerNewTeam("NM_black");
		board.getTeam("NM_black").setPrefix("§0");
		
		board.registerNewTeam("NM_darkblue");
		board.getTeam("NM_darkblue").setPrefix("§1");
		
		board.registerNewTeam("NM_darkgreen");
		board.getTeam("NM_darkgreen").setPrefix("§2");
		
		board.registerNewTeam("NM_darkaqua");
		board.getTeam("NM_darkaqua").setPrefix("§3");
		
		board.registerNewTeam("NM_darkred");
		board.getTeam("NM_darkred").setPrefix("§4");
		
		board.registerNewTeam("NM_darkpurple");
		board.getTeam("NM_darkpurple").setPrefix("§5");
		
		board.registerNewTeam("NM_gold");
		board.getTeam("NM_gold").setPrefix("§6");
		
		board.registerNewTeam("NM_gray");
		board.getTeam("NM_gray").setPrefix("§7");
		
		board.registerNewTeam("NM_darkgray");
		board.getTeam("NM_darkgray").setPrefix("§8");
		
		board.registerNewTeam("NM_blue");
		board.getTeam("NM_blue").setPrefix("§9");
		
		board.registerNewTeam("NM_green");
		board.getTeam("NM_green").setPrefix("§a");
	
		board.registerNewTeam("NM_aqua");
		board.getTeam("NM_aqua").setPrefix("§b");
		
		board.registerNewTeam("NM_red");
		board.getTeam("NM_red").setPrefix("§c");
		
		board.registerNewTeam("NM_lightpurple");
		board.getTeam("NM_lightpurple").setPrefix("§d");
		
		board.registerNewTeam("NM_yellow");
		board.getTeam("NM_yellow").setPrefix("§e");
		
		board.registerNewTeam("NM_white");
		board.getTeam("NM_white").setPrefix("§f");
		
		board.registerNewTeam("ZZZZZZZZZZZZZZZZ");
	}
	
	private void unregisterTeams() {
		
		board.getTeam("NM_black").unregister();
		
		board.getTeam("NM_darkblue").unregister();
		
		board.getTeam("NM_darkgreen").unregister();
		
		board.getTeam("NM_darkaqua").unregister();
		
		board.getTeam("NM_darkred").unregister();
		
		board.getTeam("NM_darkpurple").unregister();
		
		board.getTeam("NM_gold").unregister();
		
		board.getTeam("NM_gray").unregister();
		
		board.getTeam("NM_darkgray").unregister();
		
		board.getTeam("NM_blue").unregister();
		
		board.getTeam("NM_green").unregister();
		
		board.getTeam("NM_aqua").unregister();
		
		board.getTeam("NM_red").unregister();
		
		board.getTeam("NM_lightpurple").unregister();
		
		board.getTeam("NM_yellow").unregister();
		
		board.getTeam("NM_white").unregister();
		
		board.getTeam("ZZZZZZZZZZZZZZZZ").unregister();
	}

}
