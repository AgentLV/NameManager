package net.agentlv.namemanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.api.NameManagerAPI;
import net.agentlv.namemanager.api.NameManagerGroupAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * @author AgentLV
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Prevent instance creation
public class PlayerGroupHandler {

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
        for (String group : NameManager.getGroupConfig().getGroupConfig().getConfig().getStringList("GroupList")) {
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

    public static Team getTeam(Player player) {
        Team team = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(player.getName());
        String uuid = player.getUniqueId().toString();
        String prefix = NameManager.getPlayerConfig().getPlayerPrefix(uuid);
        String suffix = NameManager.getPlayerConfig().getPlayerSuffix(uuid);

        // Custom player group
        if (!prefix.isEmpty() || !suffix.isEmpty()) {
            team.setPrefix(prefix);
            team.setSuffix(suffix);

            return team;
        }

        // Custom groups
        for (String group : NameManager.getGroupConfig().getGroupConfig().getConfig().getStringList("GroupList")) {
            if (player.hasPermission("NameManager.group." + group)) {
                team.setDisplayName("$" + NameManagerGroupAPI.getGroups().get(group).getId() + group);
                team.setPrefix(NameManagerGroupAPI.getGroupPrefix(group));
                team.setSuffix(NameManagerGroupAPI.getGroupSuffix(group));

                return team;
            }
        }

        // Default groups
        for (int i = 0; i < ChatColor.values().length; ++i) {
            if (player.hasPermission("NameManager." + ChatColor.values()[i])) {
                team.setPrefix(ChatColor.values()[i].toString());

                return team;
            }
        }

        return team;
    }

}
