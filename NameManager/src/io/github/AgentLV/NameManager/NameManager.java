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
			objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("HealthFormat")));
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
		team = board.getTeam("NM_black");
		team.setPrefix("§0");
		
		board.registerNewTeam("NM_darkblue");
		team = board.getTeam("NM_darkblue");
		team.setPrefix("§1");
		
		board.registerNewTeam("NM_darkgreen");
		team = board.getTeam("NM_darkgreen");
		team.setPrefix("§2");
		
		board.registerNewTeam("NM_darkaqua");
		team = board.getTeam("NM_darkaqua");
		team.setPrefix("§3");
		
		board.registerNewTeam("NM_darkred");
		team = board.getTeam("NM_darkred");
		team.setPrefix("§4");
		
		board.registerNewTeam("NM_darkpurple");
		team = board.getTeam("NM_darkpurple");
		team.setPrefix("§5");
		
		board.registerNewTeam("NM_gold");
		team = board.getTeam("NM_gold");
		team.setPrefix("§6");
		
		board.registerNewTeam("NM_gray");
		team = board.getTeam("NM_gray");
		team.setPrefix("§7");
		
		board.registerNewTeam("NM_darkgray");
		team = board.getTeam("NM_darkgray");
		team.setPrefix("§8");
		
		board.registerNewTeam("NM_blue");
		team = board.getTeam("NM_blue");
		team.setPrefix("§9");
		
		board.registerNewTeam("NM_green");
		team = board.getTeam("NM_green");
		team.setPrefix("§a");
		
		board.registerNewTeam("NM_aqua");
		team = board.getTeam("NM_aqua");
		team.setPrefix("§b");
		
		board.registerNewTeam("NM_red");
		team = board.getTeam("NM_red");
		team.setPrefix("§c");
		
		board.registerNewTeam("NM_lightpurple");
		team = board.getTeam("NM_lightpurple");
		team.setPrefix("§d");
		
		board.registerNewTeam("NM_yellow");
		team = board.getTeam("NM_yellow");
		team.setPrefix("§e");
		
		board.registerNewTeam("NM_white");
		team = board.getTeam("NM_white");
		team.setPrefix("§f");
	}
	
	private void unregisterTeams() {

		
		team = board.getTeam("NM_black");
		team.unregister();
		
		team = board.getTeam("NM_darkblue");
		team.unregister();
		
		team = board.getTeam("NM_darkgreen");
		team.unregister();
		
		team = board.getTeam("NM_darkaqua");
		team.unregister();
		
		team = board.getTeam("NM_darkred");
		team.unregister();
		
		team = board.getTeam("NM_darkpurple");
		team.unregister();
		
		team = board.getTeam("NM_gold");
		team.unregister();
		
		team = board.getTeam("NM_gray");
		team.unregister();
		
		team = board.getTeam("NM_darkgray");
		team.unregister();
		
		team = board.getTeam("NM_blue");
		team.unregister();
		
		team = board.getTeam("NM_green");
		team.unregister();
		
		team = board.getTeam("NM_aqua");
		team.unregister();
		
		team = board.getTeam("NM_red");
		team.unregister();
		
		team = board.getTeam("NM_lightpurple");
		team.unregister();
		
		team = board.getTeam("NM_yellow");
		team.unregister();
		
		team = board.getTeam("NM_white");
		team.unregister();
	}

}
