package net.agentlv.namemanager.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.api.NameManagerAPI;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author AgentLV
 */
public class PlayerJoinListener implements Listener {

    private NameManager plugin;
    private FileConfiguration config;

    public PlayerJoinListener(NameManager plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String playerName = player.getName();

        if (config.getBoolean("HealthBelowName"))
            NameManager.getMultiScoreboard().registerHealthObjective(player);

        NameManager.getMultiScoreboard().init(player);
        PlayerGroupHandler.add(player);

        if (config.getBoolean("Messages")) {

            if (config.getBoolean("CustomNameForMessages")) {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Join").replaceAll("%player%", NameManagerAPI.getNametag(player))));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Join").replaceAll("%player%", playerName)));
            }

        }

        if (config.getBoolean("Bungee")) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

                @Override
                public void run() {
                    if (player.isOnline()) {
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF(player.getName());
                        out.writeUTF("TablistName");
                        out.writeUTF(NameManagerAPI.getNametag(player));

                        player.sendPluginMessage(plugin, "NameManager", out.toByteArray());
                    }
                }
            }, 1L);

        }

        if (NameManager.useVault()) {
            NameManager.getChat().setPlayerPrefix(player, NameManagerAPI.getNametagPrefix(player));
            NameManager.getChat().setPlayerSuffix(player, NameManagerAPI.getNametagSuffix(player));
        }
    }

}
