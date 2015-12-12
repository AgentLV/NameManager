package de.agentlv.namemanager.files;

import java.util.List;


public class GroupsFileHandler {

	private static List<String> groupList;
	private static FileAccessor groupsFile;
	
	public GroupsFileHandler(FileAccessor groupsFile) {
		GroupsFileHandler.groupsFile = groupsFile;
	}
	
	private static void createGroup(String group) {
		
		if (groupsFile.getConfig().get("Groups." + group) == null) {
			
			groupsFile.getConfig().set("Groups." + group + ".Prefix", "");
			groupsFile.getConfig().set("Groups." + group + ".Suffix", "");
			
			groupsFile.saveConfig();
		}
	}
	
	private static void addToGroupList(String group) {

		groupList = groupsFile.getConfig().getStringList("GroupList");
		
		if (!groupList.contains(group)) {
			groupList.add(group);
			groupsFile.getConfig().set("GroupList", groupList);
		}
		
		groupsFile.saveConfig();
	}
	
	public static void writeGroup(String group, String prefix, String suffix) {
		addToGroupList(group);
		createGroup(group);
		groupsFile.getConfig().set("Groups." + group + ".Prefix", prefix);
		groupsFile.getConfig().set("Groups." + group + ".Suffix", suffix);
		groupsFile.saveConfig();
	}
	
	public static void writeGroupPrefix(String group, String prefix) {
		addToGroupList(group);
		createGroup(group);
		groupsFile.getConfig().set("Groups." + group + ".Prefix", prefix);
		groupsFile.saveConfig();
	}
	
	public static void writeGroupSuffix(String group, String suffix) {
		addToGroupList(group);
		createGroup(group);
		groupsFile.getConfig().set("Groups." + group + ".Suffix", suffix);
		groupsFile.saveConfig();
	}
	
	public static void removeGroup(String group) {
		
		groupList = groupsFile.getConfig().getStringList("GroupList");
		
		if (groupList.contains(group)) {
			groupList.remove(group);
			groupsFile.getConfig().set("GroupList", groupList);
		}
		
		if (groupsFile.getConfig().contains("Groups." + group)) {
			groupsFile.getConfig().set("Groups." + group, null);
		}
		
		groupsFile.saveConfig();
	}
}
