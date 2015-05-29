package io.github.AgentLV.NameManager.Files;

import java.util.List;


public class FileHandler {

	public static List<String> ex;
	private static ConfigAccessor cGroups;
	
	public FileHandler(ConfigAccessor cGroups) {
		FileHandler.cGroups = cGroups;
	}
	
	private static void createGroup(String group) {
		
		if ( cGroups.getConfig().get( "Groups." + group ) == null ) {
			
			cGroups.getConfig().set("Groups." + group + ".Prefix", "");
			cGroups.getConfig().set("Groups." + group + ".Suffix", "");
			
			cGroups.saveConfig();
		}
	}
	
	private static void addToGroupList(String group) {

		ex = cGroups.getConfig().getStringList("GroupList");
		
		if (!ex.contains(group)) {
			ex.add(group);
			cGroups.getConfig().set("GroupList", ex);
		}
		
		cGroups.saveConfig();
		ex = cGroups.getConfig().getStringList("GroupList");
	}
	
	public static void writeGroupPrefix(String group, String prefix) {
		addToGroupList(group);
		createGroup(group);
		cGroups.getConfig().set("Groups." + group + ".Prefix", prefix);
		cGroups.saveConfig();
	}
	
	public static void writeGroupSuffix(String group, String suffix) {
		addToGroupList(group);
		createGroup(group);
		cGroups.getConfig().set("Groups." + group + ".Suffix", suffix);
		cGroups.saveConfig();
	}
	
	public static void removeGroup(String group) {
		
		ex = cGroups.getConfig().getStringList("GroupList");
		
		if (ex.contains(group)) {
			ex.remove(group);
			cGroups.getConfig().set("GroupList", ex);
		}
		
		if (cGroups.getConfig().contains("Groups." + group)) {
			cGroups.getConfig().set("Groups." + group, null);
		}
		
		cGroups.saveConfig();
		
		ex = cGroups.getConfig().getStringList("GroupList");
	}
}
