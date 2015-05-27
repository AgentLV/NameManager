package io.github.AgentLV.NameManager.Files;

import io.github.AgentLV.NameManager.NameManager;
import io.github.AgentLV.NameManager.API.NameManagerGroupAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {
	
	static NameManager plugin;
	static ConfigAccessor cGroups;
	
	public FileManager(NameManager plugin) {
		FileManager.plugin = plugin;
		cGroups = NameManager.cGroups;
	}
	
	//Groups.yml
	
    public static List<String> allGroups = new ArrayList<>();
    public static FileConfiguration configGroups;
    
    public static void loadFromFile() {
    	
    	cGroups.reloadConfig();
    	configGroups = cGroups.getConfig();
        allGroups.clear();
        allGroups = cGroups.getConfig().getStringList("GroupList");
        
        if ( allGroups.isEmpty() )
        	return;
        
        for (String s : allGroups) {
        	
        	if (NameManager.board.getTeam(s) != null) {
        		
        		plugin.getLogger().info("§cCould not initalize group " + s);
        		plugin.getLogger().info("§cTrying to unregister group " + s + "...");
        		NameManager.board.getTeam(s).unregister();
        		
        	}
        	
    		NameManagerGroupAPI.groups.put(s, NameManagerGroupAPI.groups.size());
        	NameManager.team = NameManager.board.registerNewTeam(NameManagerGroupAPI.groups.get( s ) + s);
        	
        	try {
	        	NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix")));
	            NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix")));
	            
        	} catch(NullPointerException e) {
        		plugin.getLogger().warning("Could not load group '" + s + "', did you set a prefix and a suffix?");
        	}
    	}
    }
    
    public static void loadFromFile(CommandSender sender) {
    	
    	cGroups.reloadConfig();
    	configGroups = cGroups.getConfig();
        allGroups.clear();
        allGroups = cGroups.getConfig().getStringList("GroupList");
        
        if ( allGroups.isEmpty() )
        	return;
        
        for (String s : allGroups) {
        	
        	if (NameManager.board.getTeam(s) != null) {
        		
        		plugin.getLogger().info("§cCould not initalize group " + s);
        		plugin.getLogger().info("§cTrying to unregister group " + s + "...");
        		NameManager.board.getTeam(s).unregister();
        		
        	}
        	
    		NameManagerGroupAPI.groups.put(s, NameManagerGroupAPI.groups.size());
        	NameManager.team = NameManager.board.registerNewTeam(NameManagerGroupAPI.groups.get( s ) + s);
        	
        	try {
	        	NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix")));
	            NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix")));
	            
        	} catch(NullPointerException e) {
        		sender.sendMessage("§cCould not load group '" + s + "', did you set a prefix and a suffix?");
        	}
    	}
    }
    
    public static void unloadFromFile() {
        
    	cGroups.reloadConfig();
    	allGroups.clear();
        allGroups = configGroups.getStringList("GroupList");
    	
    	if ( allGroups.isEmpty() )
        	return;
    	
         for (String s : allGroups) {
        	 try {
        		 NameManager.board.getTeam(NameManagerGroupAPI.groups.get( s ) + s).unregister();
        	 } catch(NullPointerException e) {
        		 plugin.getLogger().warning("§cCould not unregister group '" + s + "', if you are not using this group, you can ignore this. ");
        	 }
         }
         NameManagerGroupAPI.groups.clear();
    }
}
