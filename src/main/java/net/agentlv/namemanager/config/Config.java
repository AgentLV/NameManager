package net.agentlv.namemanager.config;

import net.agentlv.namemanager.NameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author AgentLV
 */
public class Config {

    private final String fileName;
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(String fileName) {
        this.fileName = fileName;
        File dataFolder = NameManager.getInstance().getDataFolder();
        if (dataFolder == null)
            throw new IllegalStateException();

        this.file = new File(NameManager.getInstance().getDataFolder(), fileName);
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        // Look for defaults in the jar
        InputStream defConfigStream = NameManager.getInstance().getResource(fileName);
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
                NameManager.getInstance().getLogger().severe("Could not save config to " + file + ex);
            }
        }
    }

    public void saveDefaultConfig() {
        if (!file.exists()) {
            NameManager.getInstance().saveResource(fileName, false);
        }
    }

}
