package de.agentlv.namemanager.files;

public class PlayersFileHandler {

	private static FileAccessor playerFile;
	
	public PlayersFileHandler(FileAccessor playerFile) {
		PlayersFileHandler.playerFile = playerFile;
	}
	
	private static void createEntry(String uuid) {
		
		if (playerFile.getConfig().getConfigurationSection(uuid) == null) {
		
			playerFile.getConfig().set(uuid + ".Prefix", "");
			playerFile.getConfig().set(uuid + ".Suffix", "");
			
			playerFile.saveConfig();
		}
	}
	
	public static void write(String uuid, String prefix, String suffix) {
		createEntry(uuid);
		playerFile.getConfig().set(uuid + ".Prefix", prefix);
		playerFile.getConfig().set(uuid + ".Suffix", suffix);
		
		playerFile.saveConfig();
	}
	
	public static void writePrefix(String uuid, String prefix) {
		createEntry(uuid);
		playerFile.getConfig().set(uuid + ".Prefix", prefix);
		
		playerFile.saveConfig();
	}
	
	public static void writeSuffix(String uuid, String suffix) {
		createEntry(uuid);
		playerFile.getConfig().set(uuid + ".Suffix", suffix);
		
		playerFile.saveConfig();
	}
	
	public static void removeEntry(String uuid) {
		
		if (playerFile.getConfig().getConfigurationSection(uuid) != null) {
			playerFile.getConfig().set(uuid, null);
			playerFile.saveConfig();
		}
	}
	
}
