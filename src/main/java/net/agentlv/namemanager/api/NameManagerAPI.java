package net.agentlv.namemanager.api;

import lombok.NonNull;
import net.agentlv.namemanager.NameManager;
import net.agentlv.namemanager.UUIDCallback;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import net.agentlv.namemanager.util.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * @author AgentLV
 */
public class NameManagerAPI {

    /**
     * Prevent instance creation
     */
    private NameManagerAPI() {

    }

    public static void setNametag(OfflinePlayer player, String prefix, String suffix) {
        NameManager.getMultiScoreboard().setPlayer(player.getName(),
                ChatColor.translateAlternateColorCodes('&', prefix), ChatColor.translateAlternateColorCodes('&', suffix));

        NameManager.getPlayerConfig().write(player.getUniqueId().toString(), prefix, suffix);
    }

    public static void setNametagPrefix(OfflinePlayer player, String prefix) {
        NameManager.getMultiScoreboard().setPlayerPrefix(player.getName(), ChatColor.translateAlternateColorCodes('&', prefix));

        NameManager.getPlayerConfig().writePrefix(player.getUniqueId().toString(), prefix);
    }

    public static void setNametagSuffix(OfflinePlayer player, String suffix) {
        NameManager.getMultiScoreboard().setPlayerSuffix(player.getName(), ChatColor.translateAlternateColorCodes('&', suffix));

        NameManager.getPlayerConfig().writeSuffix(player.getUniqueId().toString(), suffix);
    }

    public static String getNametag(@NonNull Player player) {
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        String prefix = "";
        String suffix = "";

        if (team != null) {
            if (team.getPrefix() != null) {
                prefix = team.getPrefix();
            }
            if (team.getSuffix() != null) {
                suffix = team.getSuffix();
            }
        }

        return prefix + player.getName() + suffix;
    }

    public static String getNametagPrefix(@NonNull Player player) {
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        String prefix = "";

        if (team != null && team.getPrefix() != null) {
            prefix = team.getPrefix();
        }

        return prefix;
    }

    public static String getNametagSuffix(@NonNull Player player) {
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        String suffix = "";

        if (team != null && team.getSuffix() != null) {
            suffix = team.getSuffix();
        }

        return suffix;
    }

    public static void clearNametag(@NonNull OfflinePlayer player) {
        NameManager.getMultiScoreboard().setPlayer(player.getName(), "", "");

        NameManager.getPlayerConfig().removeEntry(player.getUniqueId().toString());

        PlayerGroupHandler.add(player.getPlayer());
    }

    public static void getOfflinePlayer(final String playerName, final UUIDCallback callback) {
        Player player = Bukkit.getPlayer(playerName);

        if (player != null)  {
            callback.done(player);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(NameManager.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        final UUID uuid = UUIDFetcher.getUUIDOf(playerName);
                        if (uuid == null)
                            throw new NullPointerException();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(NameManager.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                callback.done(Bukkit.getOfflinePlayer(uuid));
                            }
                        });
                    } catch (Exception e) {
                        callback.fail(e);
                    }
                }
            });
        }
    }

}
