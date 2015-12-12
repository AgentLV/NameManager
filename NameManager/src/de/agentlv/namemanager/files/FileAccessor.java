package de.agentlv.namemanager.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.agentlv.namemanager.NameManager;
 
public class FileAccessor {
 
    private final String fileName;
    private NameManager plugin;
    
    private File file;
    private FileConfiguration fileConfiguration;
 
    public FileAccessor(NameManager plugin, String fileName) {
        if (plugin == null)
            throw new IllegalArgumentException("plugin cannot be null");
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null)
            throw new IllegalStateException();
        this.file = new File(plugin.getDataFolder(), fileName);
    }
 
    public void reloadConfig() {        
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
 
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
    }
 
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
        	this.reloadConfig();
        }
        return fileConfiguration;
    }
 
    public void saveConfig() {
        if (fileConfiguration != null && file != null) {
            try {
                getConfig().save(file);
            } catch (IOException ex) {
                plugin.getLogger().severe("Could not save config to " + file + ex);
            }
        }
    }
    
    public void saveDefaultConfig() {
        if (!file.exists()) {            
            this.plugin.saveResource(fileName, false);
        }
    }
 
}

