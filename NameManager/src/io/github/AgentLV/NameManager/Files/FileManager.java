package io.github.AgentLV.NameManager.Files;

import io.github.AgentLV.NameManager.NameManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.Team;

public class FileManager {
	
	static NameManager plugin;
	private static String configVersion = "1.1";
	
	public FileManager(NameManager plugin) {
		FileManager.plugin = plugin;
	}

	//config.yml
	
	public static FileConfiguration getFileConfiguration(String fileName) {
		
		File file = new File("plugins/NameManager/" + fileName + ".yml");
        FileConfiguration fileConfiguration = new YamlConfiguration();
    	
        try {
            fileConfiguration.load(file);
            
            if (fileName == "config") {
	            String version = fileConfiguration.getString("version");
	
	            if (version != null && version.equals(configVersion)) {
	                return fileConfiguration;
	            }
	
	            if (version == null) {
	                version = "backup";
	            }
	
	            if (file.renameTo(new File(plugin.getDataFolder(), "old-" + fileName + "-" + version + ".yml"))) {
	            	plugin.getLogger().info("Found outdated config, creating backup...");
	                plugin.getLogger().info("Created a backup for: " + fileName + ".yml");
	            }
            } else {
            	return fileConfiguration;
            }
        } catch (IOException|InvalidConfigurationException e) {
            plugin.getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
        }

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                InputStream in = plugin.getResource(fileName + ".yml");
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                out.close();
                in.close();
            }
            fileConfiguration.load(file);
        } catch(IOException|InvalidConfigurationException ex) {
            plugin.getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
            plugin.getLogger().severe("Disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            ex.printStackTrace();
        }
        
        return fileConfiguration;
    }
	
	//Groups.yml
	
    public static List<String> allGroups = new ArrayList<>();
    public static File groupFile = new File("plugins/NameManager/Groups.yml");
    public static FileConfiguration groups;
    
    public static void loadFromFile() {
    	
    	groups = getFileConfiguration("Groups");
        allGroups.clear();
        allGroups = groups.getStringList("GroupList");

        for (String s : allGroups) {
        	if (NameManager.board.getTeam(s) == null) {
	        	NameManager.team = NameManager.board.registerNewTeam(s);
	        	NameManager.team.setPrefix(ChatColor.translateAlternateColorCodes('&', groups.getString("Groups." + s + ".Prefix")));
	            NameManager.team.setSuffix(ChatColor.translateAlternateColorCodes('&', groups.getString("Groups." + s + ".Suffix")));
        	} else {
        		plugin.getLogger().warning("§cCould not initalize group " + s);
        	}
        }
    }
    
    public static void unloadFromFile() {

        for (Team team : NameManager.board.getTeams()) {
        	if (!team.getName().startsWith("NM_")) {
        		team.unregister();
        	}
        }
    }
    
    public static void initGroupsFile() {
    	try {
            if (!FileManager.groupFile.exists()) {
            	FileManager.groupFile.getParentFile().mkdirs();
                InputStream in = plugin.getResource("Groups.yml");
                OutputStream out = new FileOutputStream(FileManager.groupFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                out.close();
                in.close();
            }
            FileManager.groups.load(FileManager.groupFile);
        } catch(IOException|InvalidConfigurationException ex) {
            plugin.getLogger().warning("§cUnable to load Groups.yml");
        }
   	
	   	try {
	   		FileManager.groups.save(FileManager.groupFile);
	   	} catch (IOException e) {
	   		plugin.getLogger().warning("§cUnable to load Groups.yml");
	   	}
    }
	
	
}
