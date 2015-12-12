package net.agentlv.namemanager.config;

/**
 * @author AgentLV
 */
public class PlayerConfig {

    private Config playerConfig;

    public PlayerConfig(Config playerConfig) {
        this.playerConfig = playerConfig;
    }

    private void createEntry(String uuid) {
        if (playerConfig.getConfig().getConfigurationSection(uuid) == null) {
            playerConfig.getConfig().set(uuid + ".Prefix", "");
            playerConfig.getConfig().set(uuid + ".Suffix", "");

            playerConfig.saveConfig();
        }
    }

    public void write(String uuid, String prefix, String suffix) {
        createEntry(uuid);

        playerConfig.getConfig().set(uuid + ".Prefix", prefix);
        playerConfig.getConfig().set(uuid + ".Suffix", suffix);

        playerConfig.saveConfig();
    }

    public void writePrefix(String uuid, String prefix) {
        createEntry(uuid);

        playerConfig.getConfig().set(uuid + ".Prefix", prefix);

        playerConfig.saveConfig();
    }

    public void writeSuffix(String uuid, String suffix) {
        createEntry(uuid);

        playerConfig.getConfig().set(uuid + ".Suffix", suffix);

        playerConfig.saveConfig();
    }

    public void removeEntry(String uuid) {
        if (playerConfig.getConfig().getConfigurationSection(uuid) != null) {
            playerConfig.getConfig().set(uuid, null);

            playerConfig.saveConfig();
        }
    }

    public String getPlayerPrefix(String uuid) {
        return playerConfig.getConfig().getString(uuid + ".Prefix", "");
    }

    public String getPlayerSuffix(String uuid) {
        return playerConfig.getConfig().getString(uuid + ".Suffix", "");
    }

    public void reloadConfig() {
        playerConfig.reloadConfig();
    }

}
