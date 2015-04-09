package io.github.AgentLV.NameManager.Files;

import java.io.IOException;
import java.util.List;

import io.github.AgentLV.NameManager.NameManager;


public class FileHandler {

	private static NameManager plugin;
	private static List<String> ex;
	
	public FileHandler(NameManager plugin) {
		FileHandler.plugin = plugin;
	}
	
	private static void saveConfig() {
		try {
			FileManager.groups.save(FileManager.groupFile);
		} catch (IOException e) {
			plugin.getLogger().warning("Cannot save Groups.yml");
		}
	}
	
	private static void createGroup(String group) {
		
		if (FileManager.groups.get("Groups." + group) == null) {
			FileManager.groups.set("Groups." + group + ".Prefix", "");
			FileManager.groups.set("Groups." + group + ".Suffix", "");
		}
	}
	
	private static void addToGroupList(String group) {

		ex = FileManager.groups.getStringList("GroupList");
		
		if (!ex.contains(group)) {
			ex.add(group);
			FileManager.groups.set("GroupList", ex);
		}
	}
	
	public static void writeGroupPrefix(String group, String prefix) {

		addToGroupList(group);
		createGroup(group);
		FileManager.groups.set("Groups." + group + ".Prefix", prefix);
		saveConfig();
	}
	
	public static void writeGroupSuffix(String group, String suffix) {
		
		addToGroupList(group);
		createGroup(group);
		FileManager.groups.set("Groups." + group + ".Suffix", suffix);
		saveConfig();
	}
	
	public static void removeGroup(String group) {
		
		ex = FileManager.groups.getStringList("GroupList");
		
		if (ex.contains(group)) {
			ex.remove(group);
			FileManager.groups.set("GroupList", ex);
		}
		
		if (FileManager.groups.contains("Groups." + group)) {
			FileManager.groups.set("Groups." + group, null);
		}
		
		saveConfig();
	}
}
