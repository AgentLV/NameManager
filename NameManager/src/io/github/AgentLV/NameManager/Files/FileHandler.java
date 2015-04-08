package io.github.AgentLV.NameManager.Files;

import java.io.IOException;

import io.github.AgentLV.NameManager.NameManager;


public class FileHandler {

	static NameManager plugin;
	
	public FileHandler(NameManager plugin) {
		FileHandler.plugin = plugin;
	}
	
	public static void writeGroupPrefix(String group, String prefix) {

		FileManager.groups.set("Groups." + group + ".Prefix", prefix);
		try {
			FileManager.groups.save(FileManager.groupFile);
		} catch (IOException e) {
			plugin.getLogger().warning("Cannot save Groups.yml");
		}

	}
	
	public static void writeGroupSuffix(String group, String suffix) {

	}
}
