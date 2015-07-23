package de.agentlv.namemanager.files;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.NameManager;
import de.agentlv.namemanager.api.NameManagerGroupAPI;

public class FileManager {
	
	private static List<String> allGroups = new ArrayList<String>();
	private static NameManager plugin;
	private static FileAccessor groupsFile;
    private static FileConfiguration configGroups;
	
	public FileManager(NameManager plugin) {
		FileManager.plugin = plugin;
		groupsFile = NameManager.groupsFile;
	}
	
    public static void loadFromFile() {
    	
    	groupsFile.reloadConfig();
    	
    	configGroups = groupsFile.getConfig();
        allGroups = groupsFile.getConfig().getStringList("GroupList");
        
        if (allGroups.isEmpty())
        	return;
        
        for (String s : allGroups) {
        	
        	NameManagerGroupAPI.groups.add(s);
        	String teamName = NameManagerGroupAPI.groups.indexOf(s) + s;
        	
        	if (NameManager.board.getTeam(teamName) != null)
        		NameManager.board.getTeam(teamName).unregister();
        	
        	Team team = NameManager.board.registerNewTeam(teamName);
        	
        	try {
        		String prefix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix"));
        		String suffix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix"));
        		
        		if (prefix.length() > 16)
        			prefix = prefix.substring(0, 16);
        		
        		if (suffix.length() > 16)
        			suffix = suffix.substring(0, 16);
        		
	        	team.setPrefix(prefix);
	            team.setSuffix(suffix);
	            
        	} catch(NullPointerException e) {
        		plugin.getLogger().warning("Could not load group '" + s + "', did you set a prefix and a suffix?");
        	}
    	}
        
    }
    
    public static void loadFromFile(CommandSender sender) {
    	
    	groupsFile.reloadConfig();
    	
    	configGroups = groupsFile.getConfig();
        allGroups = groupsFile.getConfig().getStringList("GroupList");
        
        if ( allGroups.isEmpty() )
        	return;
        
        for (String s : allGroups) {
        	
        	NameManagerGroupAPI.groups.add(s);
        	String teamName = NameManagerGroupAPI.groups.indexOf(s) + s;
        	
        	if (NameManager.board.getTeam(teamName) != null)
        		NameManager.board.getTeam(teamName).unregister();
        	
        	Team team = NameManager.board.registerNewTeam(teamName);
        	
        	try {
        		String prefix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Prefix"));
        		String suffix = ChatColor.translateAlternateColorCodes('&', configGroups.getString("Groups." + s + ".Suffix"));
        		
        		if (prefix.length() > 16)
        			prefix.substring(0, 16);
        		
        		if (suffix.length() > 16)
        			suffix.substring(0, 16);
        		
	        	team.setPrefix(prefix);
	            team.setSuffix(suffix);
	            
        	} catch(NullPointerException e) {
        		sender.sendMessage("§cCould not load group '§b" + s + "§c', did you set a prefix and a suffix?");
        	}
    	}
    }
    
    public static void unloadFromFile() {
    	
    	if (allGroups.isEmpty())
        	return;
    	
         for (String s : allGroups) {
        	 
        	 Team team = NameManager.board.getTeam(NameManagerGroupAPI.groups.indexOf(s) + s);
        	 
        	 if (team != null)
        		 team.unregister();
        	 
         }
         allGroups.clear();
         NameManagerGroupAPI.groups.clear();
    }
}
