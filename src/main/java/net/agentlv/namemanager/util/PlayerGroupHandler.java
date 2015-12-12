package net.agentlv.namemanager.util;

import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.api.NameManagerAPI;
import net.agentlv.namemanager.api.NameManagerGroupAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author AgentLV
 */
public class PlayerGroupHandler {

    /**
     * Prevent instance creation
     */
    private PlayerGroupHandler() {

    }

    public static void add(Player player) {
        String playerName = player.getName();
        String uuid = player.getUniqueId().toString();
        String prefix = NameManager.getPlayerConfig().getPlayerPrefix(uuid);
        String suffix = NameManager.getPlayerConfig().getPlayerSuffix(uuid);

        // Custom player group
        if (!prefix.isEmpty() || !suffix.isEmpty()) {
            NameManagerAPI.setNametag(player, prefix, suffix);

            return;
        }

        // Custom groups
        for (String group : NameManagerGroupAPI.getGroups().navigableKeySet()) {
            if (player.hasPermission("NameManager.group." + group)) {
                NameManagerGroupAPI.addPlayer(group, playerName);

                return;
            }
        }

        // Default groups
        for (int i = 0; i < ChatColor.values().length; ++i) {
            if (player.hasPermission("NameManager." + ChatColor.values()[i])) {
                NameManager.getMultiScoreboard().setPlayerPrefix(playerName, ChatColor.values()[i].toString());

                return;
            }
        }

        NameManager.getMultiScoreboard().setPlayer(playerName, "", "");
    }

}
