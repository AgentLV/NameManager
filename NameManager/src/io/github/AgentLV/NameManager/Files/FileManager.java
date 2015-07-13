package io.github.AgentLV.NameManager.Files;

import io.github.AgentLV.NameManager.API.NameManagerGroupAPI;
import io.github.AgentLV.NameManager.NameManager;

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
        
        if (allGroups.isEmpty())
        	return;
        
        for (String s : allGroups) {
        	
        	NameManagerGroupAPI.groups.put(s, NameManagerGroupAPI.groups.size());
        	
        	if (NameManager.board.getTeam(NameManagerGroupAPI.groups.get(s) + s) != null) {
        		
        		plugin.getLogger().info("Could not initalize group " + s);
        		plugin.getLogger().info("Trying to unregister group " + s + "...");
        		NameManager.board.getTeam(NameManagerGroupAPI.groups.get(s) + s).unregister();
        		plugin.getLogger().info("Succesfully unregistered group " + s + "...");
        		
        	}
        	
        	NameManager.team = NameManager.board.registerNewTeam(NameManagerGroupAPI.groups.get(s) + s);
        	
        	try {
        		String prefix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix"));
        		String suffix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix"));
        		
        		if (prefix.length() > 16)
        			prefix = prefix.substring(0, 16);
        		
        		if (suffix.length() > 16)
        			suffix = suffix.substring(0, 16);
        		
	        	NameManager.team.setPrefix(prefix);
	            NameManager.team.setSuffix(suffix);
	            
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
        	
        	NameManagerGroupAPI.groups.put(s, NameManagerGroupAPI.groups.size());
        	
        	if (NameManager.board.getTeam(NameManagerGroupAPI.groups.get(s) + s) != null) {
        		
        		plugin.getLogger().info("Could not initalize group " + s);
        		plugin.getLogger().info("Trying to unregister group " + s + "...");
        		NameManager.board.getTeam(NameManagerGroupAPI.groups.get(s) + s).unregister();
        		plugin.getLogger().info("Succesfully unregistered group " + s + "...");
        	}
        	
        	NameManager.team = NameManager.board.registerNewTeam(NameManagerGroupAPI.groups.get(s) + s);
        	
        	try {
        		String prefix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix"));
        		String suffix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix"));
        		
        		if (prefix.length() > 16)
        			prefix.substring(0, 16);
        		
        		if (suffix.length() > 16)
        			suffix.substring(0, 16);
        		
	        	NameManager.team.setPrefix(prefix);
	            NameManager.team.setSuffix(suffix);
	            
        	} catch(NullPointerException e) {
        		sender.sendMessage("§cCould not load group '§b" + s + "§c', did you set a prefix and a suffix?");
        	}
    	}
    }
    
    public static void unloadFromFile() {
    	
    	if (allGroups.isEmpty())
        	return;
    	
         for (String s : allGroups) {
        	 try {
        		 NameManager.board.getTeam(NameManagerGroupAPI.groups.get(s) + s).unregister();
        	 } catch(NullPointerException e) {
        		 plugin.getLogger().warning("Could not unregister group '" + s + "', if you are not using this group, you can ignore this. ");
        	 }
         }
         allGroups.clear();
         NameManagerGroupAPI.groups.clear();
    }
}
