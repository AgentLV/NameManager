package de.agentlv.namemanager;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.api.NameManagerAPI;
import de.agentlv.namemanager.api.NameManagerGroupAPI;
import de.agentlv.namemanager.files.FileAccessor;
import de.agentlv.namemanager.files.GroupsFileHandler;
import de.agentlv.namemanager.files.FileManager;
import de.agentlv.namemanager.files.PlayersFileHandler;
import de.agentlv.namemanager.listener.PlayerJoinListener;
import de.agentlv.namemanager.listener.PlayerKickListener;
import de.agentlv.namemanager.utils.PlayerGroupHandler;

public class NameManager extends JavaPlugin {
	
	public static Scoreboard board;
	private static Objective objective;
	public static FileAccessor groupsFile;
	public static FileAccessor playerFile;
	public static Chat chat = null;
	public static boolean useVault = false;
	
	public static String[] colors = { "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD",
		"GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"};
	
	
	@Override
	public void onEnable() {
		
		board = Bukkit.getScoreboardManager().getMainScoreboard();
		
		initConfigs();
		setupVaultChat();
		
		new PlayerJoinListener(this);
		new PlayerKickListener(this);
		new PlayerKickListener(this);
		new NameManagerAPI(this);
		new NameManagerGroupAPI(this);
		new Rainbow(this);
		new FileManager(this);
		new GroupsFileHandler(groupsFile);
		new PlayersFileHandler(playerFile);
		
		FileManager.loadFromFile();
		initTeams();
		
		getCommand("namemanager").setExecutor(new Commands(this));
		
		activateHealth();
		registerOutgoingPluginChannel();
		
		//Support for /reload
		for (Player p : Bukkit.getOnlinePlayers())
			PlayerGroupHandler.add(p);
		
	}
	
	@Override
	public void onDisable() {
		
		unregisterTeams();
		FileManager.unloadFromFile();
		
		if (objective != null) 
			objective.unregister();
		
	}
	
	private void setupVaultChat() {
		
		if (this.getConfig().getBoolean("Vault")) {
		
			if (getServer().getPluginManager().getPlugin("Vault") != null) {
				
				RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		        if (chatProvider != null) {
		        	
		            chat = chatProvider.getProvider();
		            useVault = true;
		            getLogger().info("Hooked into Vault");
		            
		            return;
		        }

			}
	        getLogger().warning("Could not hook into Vault, are you sure Vault is installed?");
		}
    }
	
	private void activateHealth() {
		
		if (getConfig().getBoolean("HealthBelowName")) {
			
			objective = board.getObjective("showhealth");
			
			if (objective != null)
				objective.unregister();
			
			objective = board.registerNewObjective("showhealth", "health");
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("HealthFormat")));
		}
	
	}
	
	private void registerOutgoingPluginChannel() {
		
		if (this.getConfig().getBoolean("Bungee")) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "NameManager");
			getLogger().info("Bungeecord mode activated!");
		}
	
	}
	
	private void initConfigs() {
		this.saveDefaultConfig();
		
		groupsFile = new FileAccessor(this, "groups.yml");
		groupsFile.saveDefaultConfig();
		
		playerFile = new FileAccessor(this, "players.yml");
		playerFile.saveDefaultConfig();
	}

	public static void initTeams() {
		Team team;
		
		for (String color : colors) {
			
			team = board.getTeam("NM_" + color);
			
			if (team != null)
				team.unregister();
			
			team = board.registerNewTeam("NM_" + color);
			team.setPrefix(ChatColor.valueOf(color).toString());
			
		}
		
		//Default team
		team = board.getTeam("ZZZZZZZZZZZZZZZZ");
		
		if (team != null)
			team.unregister();
		
		board.registerNewTeam("ZZZZZZZZZZZZZZZZ");
	}
	
	public static void unregisterTeams() {
		Team team;
		
		for (String color : colors) {
			
			team = board.getTeam("NM_" + color);
			
			if (team != null)
				team.unregister();
		}
		
		for (String s : PlayerGroupHandler.createdPlayerTeams) {
			
			team = board.getTeam(s);
			
			if (team != null)
				team.unregister();
		}
		PlayerGroupHandler.createdPlayerTeams.clear();
		
		team = board.getTeam("ZZZZZZZZZZZZZZZZ");
		
		if (team != null)
			team.unregister();
	}

}
