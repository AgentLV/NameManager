package net.agentlv.namemanager;

import net.agentlv.namemanager.util.PlayerGroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author AgentLV
 */
public class Rainbow {

    private static Set<String> rainbowPlayers = new HashSet<>();
    private static BukkitTask rainbowTask;

    public static boolean enableRainbow(Player player) {
        if (rainbowEnabled(player.getName()))
            return true;

        String suffix = "";
        String prefix = "";
        String newPrefix;

        Scoreboard scoreboard = player.getScoreboard();
        Team team;
        if ((team = scoreboard.getEntryTeam(player.getName())) != null) {
            prefix = team.getPrefix();
            suffix = team.getSuffix();
        }

        newPrefix = prefix.substring(0, (prefix.length() > 14) ? 14 : prefix.length()) + ChatColor.AQUA.toString();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            Team newTeam = board.getTeam(player.getName());

            if (newTeam == null)
                newTeam = board.registerNewTeam(player.getName());

            newTeam.setPrefix(newPrefix);
            newTeam.setSuffix(suffix);
            newTeam.addEntry(player.getName());
        }

        rainbowPlayers.add(player.getName());

        if(rainbowTask == null || !Bukkit.getScheduler().isCurrentlyRunning(rainbowTask.getTaskId()))
            startRainbowTask();

        return false;
    }

    public static void disableRainbow(String playerName) {
        if (rainbowPlayers.contains(playerName)) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                Scoreboard board = player.getScoreboard();
                Team team = board.getTeam(playerName);

                if (team != null)
                    team.unregister();
            }

            PlayerGroupHandler.add(Bukkit.getPlayer(playerName));

            rainbowPlayers.remove(playerName);

            // Stop task
            if (rainbowPlayers.size() == 0)
                Bukkit.getScheduler().cancelTask(rainbowTask.getTaskId());
        }
    }

    private static void startRainbowTask() {
        rainbowTask = Bukkit.getScheduler().runTaskTimer(NameManager.getInstance(), new Runnable() {

            Random random = new Random();
            @Override
            public void run() {
                for (String playerName : rainbowPlayers) {

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Scoreboard board = player.getScoreboard();
                        Team team = board.getTeam(playerName);

                        String prefix = team.getPrefix().substring(0, team.getPrefix().length() - 2);
                        prefix += ChatColor.values()[random.nextInt(ChatColor.values().length)];
                        team.setPrefix(prefix);
                    }

                }
            }

        },10, 10);

    }

    public static boolean rainbowEnabled(String playerName) {
        return rainbowPlayers.contains(playerName);
    }

}
