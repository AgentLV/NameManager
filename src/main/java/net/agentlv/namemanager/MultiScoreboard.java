package net.agentlv.namemanager;

import net.agentlv.namemanager.api.NameManagerGroupAPI;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author AgentLV
 */
public class MultiScoreboard {

    public void removeGroups() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            Team team;

            for (String group : NameManagerGroupAPI.getGroups().keySet()) {
                if ((team = board.getTeam("$" + NameManagerGroupAPI.getGroups().get(group).getId() + group)) != null) {
                    team.unregister();
                }
            }
        }

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team;
        for (String group : NameManagerGroupAPI.getGroups().keySet()) {
            if ((team = board.getTeam("$" + NameManagerGroupAPI.getGroups().get(group).getId() + group)) != null) {
                team.unregister();
            }
        }
    }

    public void setGroup(String group, String prefix, String suffix) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            Team team = getGroupTeam(board, group);

            team.setPrefix(prefix);
            team.setSuffix(suffix);

            for (String playerName : NameManagerGroupAPI.getGroups().get(group).getPlayers()) {
                team.addEntry(playerName);
            }
        }
    }

    public void init(Player player) {
        Scoreboard board = player.getScoreboard();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (player != p) {
                Team original = p.getScoreboard().getEntryTeam(p.getName());

                if (original == null)
                    original = PlayerGroupHandler.getTeam(p);

                Team copy = getTeam(board, original.getName());

                copy.setPrefix(original.getPrefix());
                copy.setSuffix(original.getSuffix());

                for (String entry : original.getEntries()) {
                    copy.addEntry(entry);
                }
            }
        }
    }

    public void setPlayerGroup(String playerName, String group) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            Team team = getGroupTeam(board, group);

            team.addEntry(playerName);
        }
    }

    public void setPlayerPrefix(String playerName, String prefix) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            Team team = getTeam(board, playerName);
            team.addEntry(playerName);
            team.setPrefix(prefix);
        }
    }

    public void setPlayerSuffix(String playerName, String suffix) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            Team team = getTeam(board, playerName);
            team.addEntry(playerName);
            team.setSuffix(suffix);
        }
    }

    public void setPlayer(String playerName, String prefix, String suffix) {
        setPlayerPrefix(playerName, prefix);
        setPlayerSuffix(playerName, suffix);
    }

    public void unregisterPlayerTeam(String playerName) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            Team team;

            if ((team = board.getTeam(playerName)) != null) {
                team.unregister();
            } else if (((team = board.getEntryTeam(playerName)) != null)) {
                team.removeEntry(playerName);

                if (team.getEntries().size() <= 0) {
                    team.unregister();
                }
            }
        }
    }

    public void registerHealthObjective() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            registerHealthObjective(player);
        }
    }

    public void registerHealthObjective(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("showhealth");

        if (objective == null) {
            objective = board.registerNewObjective("showhealth", "health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', NameManager.getInstance().getConfig().getString("HealthFormat")));
        }
    }

    public void unregisterHealthObjective() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            unregisterHealthObjective(player);
        }
    }

    public void unregisterHealthObjective(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("showhealth");

        if (objective != null)
            objective.unregister();
    }

    private Team getGroupTeam(Scoreboard scoreboard, String group) {
        Team team = scoreboard.getTeam("$" + NameManagerGroupAPI.getGroups().get(group).getId() + group);

        if (team == null) {
            team = scoreboard.registerNewTeam("$" + NameManagerGroupAPI.getGroups().get(group).getId() + group);
            team.setPrefix(NameManagerGroupAPI.getGroupPrefix(group));
            team.setSuffix(NameManagerGroupAPI.getGroupSuffix(group));
        }

        return team;
    }

    private Team getTeam(Scoreboard scoreboard, String name) {
        return scoreboard.getTeam(name) == null ? scoreboard.registerNewTeam(name) : scoreboard.getTeam(name);
    }

}
