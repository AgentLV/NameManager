package io.github.AgentLV.NameManager.Files;

import io.github.AgentLV.NameManager.NameManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
 
public class ConfigAccessor {
 
    private final String fileName;
    private NameManager plugin;
    
    private File configFile;
    private FileConfiguration fileConfiguration;
 
    public ConfigAccessor(NameManager plugin, String fileName) {
        if (plugin == null)
            throw new IllegalArgumentException("plugin cannot be null");
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null)
            throw new IllegalStateException();
        this.configFile = new File(plugin.getDataFolder(), fileName);
    }
 
    public void reloadConfig() {
    	
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

    	
    	// Look for defaults in the jar
    	InputStream defConfigStream = plugin.getResource(fileName);
    	
    	if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			fileConfiguration.setDefaults(defConfig);
			fileConfiguration.options().copyDefaults(true);
      }
    }
 
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
        	this.reloadConfig();
        }
        return fileConfiguration;
    }
 
    public void saveConfig() {
        if (fileConfiguration != null && configFile != null) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().severe("Could not save config to " + configFile + ex);
            }
        }
    }
    
    public void saveDefaultConfig() {
        if (!configFile.exists()) {            
            this.plugin.saveResource(fileName, false);
        }
    }
 
}

