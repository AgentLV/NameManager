package net.agentlv.namemanager.config;

import lombok.Getter;
import net.agentlv.namemanager.Group;
import net.agentlv.namemanager.api.NameManagerGroupAPI;
import net.md_5.bungee.api.ChatColor;

import java.util.Iterator;

/**
 * @author AgentLV
 */
public class GroupConfig {

    @Getter private Config groupConfig;

    public GroupConfig(Config groupConfig) {
        this.groupConfig = groupConfig;
    }

    private void createGroup(String group) {
        if (groupConfig.getConfig().get("Groups." + group) == null) {

            groupConfig.getConfig().set("Groups." + group + ".Prefix", "");
            groupConfig.getConfig().set("Groups." + group + ".Suffix", "");

            groupConfig.saveConfig();
        }
    }

    public void writeGroup(String group, String prefix, String suffix) {
        createGroup(group);

        groupConfig.getConfig().set("Groups." + group + ".Prefix", prefix);
        groupConfig.getConfig().set("Groups." + group + ".Suffix", suffix);

        groupConfig.saveConfig();
    }

    public void writeGroupPrefix(String group, String prefix) {
        createGroup(group);

        groupConfig.getConfig().set("Groups." + group + ".Prefix", prefix);

        groupConfig.saveConfig();
    }

    public void writeGroupSuffix(String group, String suffix) {
        createGroup(group);

        groupConfig.getConfig().set("Groups." + group + ".Suffix", suffix);

        groupConfig.saveConfig();
    }

    public void removeGroup(String group) {
        if (groupConfig.getConfig().contains("Groups." + group)) {
            groupConfig.getConfig().set("Groups." + group, null);
        }

        groupConfig.saveConfig();
    }

    public String getGroupName(String group) {
        return getGroupPrefix(group) + group + getGroupSuffix(group);
    }

    public String getGroupPrefix(String group) {
        return ChatColor.translateAlternateColorCodes('&', groupConfig.getConfig().getString("Groups." + group + ".Prefix", ""));
    }

    public String getGroupSuffix(String group) {
        return ChatColor.translateAlternateColorCodes('&', groupConfig.getConfig().getString("Groups." + group + ".Suffix", ""));
    }

    public void reloadConfig() {
        groupConfig.reloadConfig();
    }

    public void initGroups() {
        Iterator<String> iterator = groupConfig.getConfig().getConfigurationSection("Groups").getValues(false).keySet().iterator();
        for (int i = 0; iterator.hasNext(); ++i) {
            String group = iterator.next();
            NameManagerGroupAPI.getGroups().put(group.substring(0, group.length() >= 16 ? 14 : group.length()), new Group(i));
        }
    }

}
