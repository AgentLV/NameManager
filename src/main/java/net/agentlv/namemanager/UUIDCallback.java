package net.agentlv.namemanager;

import org.bukkit.OfflinePlayer;

/**
 * @author AgentLV
 */
public interface UUIDCallback {
    void done(OfflinePlayer offlinePlayer);

    void fail(Exception e);
}
