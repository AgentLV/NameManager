package net.agentlv.namemanager;

import lombok.Getter;
import net.agentlv.namemanager.config.Config;
import net.agentlv.namemanager.config.GroupConfig;
import net.agentlv.namemanager.config.PlayerConfig;
import net.agentlv.namemanager.listener.PlayerJoinListener;
import net.agentlv.namemanager.listener.PlayerKickListener;
import net.agentlv.namemanager.listener.PlayerQuitListener;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author AgentLV
 */
public class NameManager extends JavaPlugin {

    @Getter private static NameManager instance;
    @Getter private static MultiScoreboard multiScoreboard;
    @Getter private static GroupConfig groupConfig;
    @Getter private static PlayerConfig playerConfig;
    @Getter private static Chat chat = null;
    private static boolean useVault = false;

    public static boolean useVault() {
        return useVault;
    }

    @Override
    public void onEnable() {
        instance = this;

        initConfigs();
        setupVaultChat();

        multiScoreboard = new MultiScoreboard();

        // Register Listener
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new PlayerKickListener(this);

        getCommand("namemanager").setExecutor(new Commands(this));

        activateHealth();
        registerOutgoingPluginChannel();

        // Support for /reload
        for (Player p : Bukkit.getOnlinePlayers())
            PlayerGroupHandler.add(p);

    }

    @Override
    public void onDisable() {
        multiScoreboard.removeGroups();
        multiScoreboard.unregisterHealthObjective();
    }

    private void setupVaultChat() {

        if (this.getConfig().getBoolean("Vault")) {

            if (getServer().getPluginManager().getPlugin("Vault") != null) {

                RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
                if (chatProvider != null) {

                    chat = chatProvider.getProvider();
                    useVault = true;
                    getLogger().info("Hooked into Vault");

                    return;
                }

            }
            getLogger().warning("Could not hook into Vault, are you sure Vault is installed?");
        }
    }

    private void activateHealth() {
        if (getConfig().getBoolean("HealthBelowName")) {
            multiScoreboard.registerHealthObjective();
        }
    }

    private void registerOutgoingPluginChannel() {
        if (this.getConfig().getBoolean("Bungee")) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "NameManager");
            getLogger().info("Bungeecord mode activated!");
        }
    }

    private void initConfigs() {
        this.saveDefaultConfig();
        Config config;

        config = new Config("groups.yml");
        config.saveDefaultConfig();
        groupConfig = new GroupConfig(config);

        groupConfig.initGroups();

        config = new Config("players.yml");
        config.saveDefaultConfig();
        playerConfig = new PlayerConfig(config);
    }

}
