package de.agentlv.namemanager.utils;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class RainbowPlayer {
	
	Team team;
	String prefix;
	ChatColor color;
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
}
