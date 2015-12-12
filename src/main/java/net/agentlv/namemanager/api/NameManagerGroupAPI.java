package net.agentlv.namemanager.api;

import lombok.Getter;
import net.agentlv.namemanager.Group;
import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.TreeMap;

/**
 * @author AgentLV
 */
public class NameManagerGroupAPI {

    @Getter private static TreeMap<String, Group> groups = new TreeMap<>();

    /**
     * Prevent instance creation
     */
    private NameManagerGroupAPI() {

    }

    public static void addPlayer(String group, String playerName) {
        if (groups.containsKey(group)) {
            groups.get(group).getPlayers().add(playerName);
            NameManager.getMultiScoreboard().setPlayerGroup(playerName, group);
        }

    }

    public static void removePlayer(String group, String playerName) {
        if (groups.containsKey(group)) {
            groups.get(group).getPlayers().remove(playerName);
            PlayerGroupHandler.add(Bukkit.getPlayer(playerName));
        }
    }

    private static void addGroup(String group) {
        if (!groups.containsKey(group)) {
            NameManager.getGroupConfig().writeGroup(group, "", "");
            groups.put(group, new Group());
        }
    }

    public static void setGroupNametag(String group, String prefix, String suffix) {
        addGroup(group);

        NameManager.getGroupConfig().writeGroup(group, prefix, suffix);

        for (String name : groups.get(group).getPlayers()) {
            NameManager.getMultiScoreboard().setPlayer(name, prefix, suffix);
        }
    }

    public static void setGroupPrefix(String group, String prefix) {
        addGroup(group);

        NameManager.getGroupConfig().writeGroupPrefix(group, prefix);

        for (String name : groups.get(group).getPlayers()) {
            NameManager.getMultiScoreboard().setPlayerPrefix(name, prefix);
        }
    }

    public static void setGroupSuffix(String group, String suffix) {
        addGroup(group);

        NameManager.getGroupConfig().writeGroupSuffix(group, suffix);

        for (String name : groups.get(group).getPlayers()) {
            NameManager.getMultiScoreboard().setPlayerSuffix(name, suffix);
        }
    }

    public static void removeGroup(String group) {
        if (groups.containsKey(group)) {
            Set<String> players = groups.remove(group).getPlayers();

            for (String name : players) {
                PlayerGroupHandler.add(Bukkit.getPlayer(name));
            }

            NameManager.getGroupConfig().removeGroup(group);
        }
    }

    public static String getGroupNametag(String group) {
        return NameManager.getGroupConfig().getGroupName(group);
    }

    public static String getGroupPrefix(String group) {
        return NameManager.getGroupConfig().getGroupPrefix(group);
    }

    public static String getGroupSuffix(String group) {
        return NameManager.getGroupConfig().getGroupSuffix(group);
    }

}
