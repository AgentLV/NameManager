package io.github.AgentLV.NameManager.Files;

import java.io.IOException;
import java.util.List;

import io.github.AgentLV.NameManager.NameManager;


public class FileHandler {

	static NameManager plugin;
	
	public FileHandler(NameManager plugin) {
		FileHandler.plugin = plugin;
	}
	
	private static void addToGroupList(String group) {

		List<String> ex = FileManager.groups.getStringList("GroupList");
		
		if (!ex.contains(group)) {
			
			ex.add(group);
			FileManager.groups.set("GroupList", ex);
		}
	}
	
	public static void writeGroupPrefix(String group, String prefix) {

		addToGroupList(group);
		FileManager.groups.set("Groups." + group + ".Prefix", prefix);
		try {
			FileManager.groups.save(FileManager.groupFile);
		} catch (IOException e) {
			plugin.getLogger().warning("Cannot save Groups.yml");
		}

	}
	
	public static void writeGroupSuffix(String group, String suffix) {
<<<<<<< HEAD
		
=======

		addToGroupList(group);
>>>>>>> parent of 37667af... Revert "1.8 release"
		FileManager.groups.set("Groups." + group + ".Suffix", suffix);
		try {
			FileManager.groups.save(FileManager.groupFile);
		} catch (IOException e) {
			plugin.getLogger().warning("Cannot save Groups.yml");
		}
<<<<<<< HEAD
	}
	
	public static void removeGroup(String group) {
		
=======
>>>>>>> parent of 37667af... Revert "1.8 release"
	}
}
