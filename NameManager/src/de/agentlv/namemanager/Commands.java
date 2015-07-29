package de.agentlv.namemanager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.agentlv.namemanager.api.NameManagerAPI;
import de.agentlv.namemanager.api.NameManagerGroupAPI;
import de.agentlv.namemanager.files.FileManager;
import de.agentlv.namemanager.utils.PlayerGroupHandler;

public class Commands implements CommandExecutor {

	private NameManager plugin;
	
	public Commands(NameManager plugin) {
		this.plugin = plugin;
	}
	
	private void pluginDescription(CommandSender sender) {
		String mainAuthor = plugin.getDescription().getAuthors().get(0);
		String pluginName = plugin.getDescription().getName();
		String pluginVersion = plugin.getDescription().getVersion();
		
		sender.sendMessage("§3" + pluginName + "§7 v" + pluginVersion + " by §3" + mainAuthor);
		sender.sendMessage("§7Commands: /nm help");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String invalidPermission = "§cYou don't have permission.";
		
		//Sender -> Player
		Player p = null;
		if (sender instanceof Player)
			p = (Player) sender;
			
		OfflinePlayer offlinePlayer = null;
	
		if (cmd.getName().equalsIgnoreCase("namemanager")) {
			
			if (args.length == 0) {
				pluginDescription(sender);
				
			} else if (args[0].equalsIgnoreCase("help")) {
				
				if (sender.hasPermission("namemanager.help")) {
					
					sender.sendMessage("");
					sender.sendMessage("§3---- §b§lNameManager commands §r§3----");
					sender.sendMessage("");
					sender.sendMessage("§3/nm prefix <player> <prefix>  §7§  §bSets a prefix for a player");
					sender.sendMessage("§3/nm suffix <player> <suffix>  §7§  §bSets a suffix for a player");
					sender.sendMessage("§3/nm clear [player]  §7§  §bResets a name");
					sender.sendMessage("§3/nm rainbow [player]  §7§  §bRainbow name §c(could cause lag)");
					sender.sendMessage("§3/nm uuid [player]  §7§  §bShows the UUID of a player");
					sender.sendMessage("§3/nm reload  §7§  §bReloads NameManager");
					sender.sendMessage("§3/nm group  §7§  §bDisplay group commands");
					sender.sendMessage("");
					sender.sendMessage("§3All names are §ncase sensitive§r§3!");
					sender.sendMessage("");
					
				} else {
					sender.sendMessage(invalidPermission);
				} 
				
			} else if (args[0].equalsIgnoreCase("prefix")) {
				
				if (sender.hasPermission("namemanager.prefix")) {
				
					if (args.length >= 3) {
						
						offlinePlayer = NameManagerAPI.playerToOfflinePlayer(args[1]);
						
						String prefix = args[2];
						for(int i = 3; i < args.length; ++i) {
						     prefix += " " + args[i];
						}
	
						if (prefix.length() > 16) {
							sender.sendMessage("§3The prefix can only contain 16 Characters.");
						} else {
						
							if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
								
								NameManagerAPI.setNametagPrefix(offlinePlayer, prefix);
								sender.sendMessage("§3Prefix '§c" + prefix + "§3' set for §c" + args[1]);
								
							} else {
								sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
							}
						
						} 
					
					} else {
						sender.sendMessage("§cUsage: /nm prefix <player> <prefix>");
					}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			} else if (args[0].equalsIgnoreCase("suffix")) {
				
				if (sender.hasPermission("namemanager.suffix")) {
				
					if (args.length >= 3) {
						
						offlinePlayer = NameManagerAPI.playerToOfflinePlayer(args[1]);
						
						String suffix = args[2];
						for(int i = 3; i < args.length; ++i) {
						     suffix += " " + args[i];
						}
						
						if (suffix.length() > 16) {
							sender.sendMessage("§3The suffix can only contain 16 Characters.");
						} else {
							
							if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
								NameManagerAPI.setNametagSuffix(offlinePlayer, suffix);
								sender.sendMessage("§3Suffix '§c" + suffix + "§3' set for §c" + args[1]);
								
							} else {
								sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
							}
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm suffix <player> <suffix>");
					}
				} else {
					sender.sendMessage(invalidPermission);
				}
					
			} else if (args[0].equalsIgnoreCase("clear")) {
				
				if (sender.hasPermission("namemanager.clear")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							NameManagerAPI.clearNametag(NameManagerAPI.playerToOfflinePlayer(p.getName()));
							sender.sendMessage("§3Your name was cleared.");
							
						} else {
							sender.sendMessage("§3A player is required.");
						}
					} else if (args.length == 2) {
						
						offlinePlayer = NameManagerAPI.playerToOfflinePlayer(args[1]);
						
						if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
							NameManagerAPI.clearNametag(offlinePlayer);
							sender.sendMessage("§3Name cleared for §c" + args[1]);
							
						} else {
							sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm clear <player>");
					}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			} else if (args[0].equalsIgnoreCase("uuid")) {
				
				if (sender.hasPermission("namemanager.uuid")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							sender.sendMessage("§3Your UUID: §c" + p.getUniqueId());
							
						} else {
							sender.sendMessage("§cA player is required.");
						}
						
					} else if (args.length == 2) {
						
						offlinePlayer = NameManagerAPI.playerToOfflinePlayer(args[1]);
						
						if (offlinePlayer != null) {
							sender.sendMessage("§3UUID of §c" + args[1] + "§3: §c" + offlinePlayer.getUniqueId().toString());
						} else {
							sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm uuid <player>");
					}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			} else if (args[0].equalsIgnoreCase("rainbow")) {
				
				if (sender.hasPermission("namemanager.rainbow")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							if (!Rainbow.enableRainbow(p.getName())) {
								
								sender.sendMessage("§3Rainbow activated");
								
							} else {
								
								Rainbow.disableRainbow(p.getName());
								sender.sendMessage("§3Rainbow deactivated");
							}
							
						} else {
							sender.sendMessage("§cA player is required.");
						}
						
						} else if (args.length == 2) {
							
							Player targetPlayer = plugin.getServer().getPlayer(args[1]);
							
							if (targetPlayer != null && targetPlayer.isOnline()) {
								
								if (!Rainbow.enableRainbow(targetPlayer.getName())) {
									
									sender.sendMessage("§3Rainbow activated for §c" + targetPlayer.getName());
								
								} else {
									
									Rainbow.disableRainbow(targetPlayer.getName());
									sender.sendMessage("§3Rainbow deactivated for §c" + targetPlayer.getName());
								}
								
							} else {
								sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
							}
						} else {
							sender.sendMessage("§c§cUsage: /nm rainbow <player>");
						}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			}  else if (args[0].equalsIgnoreCase("reload")) {
				
				if (sender.hasPermission("namemanager.reload")) {
					
					for (Player reloadPlayer : Bukkit.getOnlinePlayers())
						PlayerGroupHandler.remove(reloadPlayer.getName());
					
					FileManager.unloadFromFile();
					FileManager.loadFromFile(sender);
					NameManager.unregisterTeams();
					NameManager.initTeams();
					
					plugin.reloadConfig();
					NameManager.playerFile.reloadConfig();
					
					for (Player reloadPlayer : Bukkit.getOnlinePlayers()) 
						PlayerGroupHandler.add(reloadPlayer);
					
					sender.sendMessage("§3Reloaded NameManager!");
				} else {
					sender.sendMessage(invalidPermission);
				}
			
			}
			
			
			//Group commands	
				
			else if (args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("groups")) {
				
				if (args.length == 1) {
					
					if(sender.hasPermission("namemanager.group.help")) {
						sender.sendMessage("");
						sender.sendMessage("§3---- §b§lNameManager group commands §r§3----");
						sender.sendMessage("");
						sender.sendMessage("§3/nm group prefix <group> <prefix>  §7§  §bSets a prefix for a group");
						sender.sendMessage("§3/nm group suffix <group> <suffix>  §7§  §bSets a suffix for a group");
						sender.sendMessage("§3/nm group remove <group>  §7§  §bRemoves a group");
						sender.sendMessage("§3/nm group list  §7§  §bDisplays all valid groups");
						sender.sendMessage("§3/nm group rainbow <group>  §7§  §bToggles rainbow name for all group members §c(could cause lag)");
						sender.sendMessage("33/nm group add <player> <group>  §7§  §bTemporarily add a player to a group");
						sender.sendMessage("");
					} else {
						sender.sendMessage(invalidPermission);
					}
					
					//Command /nm group prefix
				} else if(args[1].equalsIgnoreCase("prefix")) {
					
					if (sender.hasPermission("namemanager.group.prefix")) {
						
						if (args.length >= 4) {
							
							String prefix = args[3];
							for(int i = 4; i < args.length; ++i) {
							     prefix += " " + args[i];
							}
		
							if (prefix.length() > 16) {
								sender.sendMessage("§3The prefix can only contain 16 Characters.");
							} else {
								NameManagerGroupAPI.setGroupNametagPrefix(args[2], prefix);
								sender.sendMessage("§3Set prefix '§c" + prefix + "§3' for group §c" + args[2]);
							}
						} else {
							sender.sendMessage("§cUsage: /nm group prefix <group> <prefix>");
						}
						
					} else {
						sender.sendMessage(invalidPermission);
					}
					
					//Command /nm group suffix
				} else if(args[1].equalsIgnoreCase("suffix")) {
					
					if (sender.hasPermission("namemanager.group.suffix")) {
						
						if (args.length >= 4) {
							
							String suffix = args[3];
							for(int i = 4; i < args.length; ++i) {
								suffix += " " + args[i];
							}
		
							if (suffix.length() > 16) {
								sender.sendMessage("§3The suffix can only contain 16 Characters.");
							} else {
								NameManagerGroupAPI.setGroupNametagSuffix(args[2], suffix);
								sender.sendMessage("§3Set suffix '§c" + suffix + "§3' for group §c" + args[2]);
							}
						} else {
							sender.sendMessage("§cUsage: /nm group suffix <group> <prefix>");
						}
						
					} else {
						sender.sendMessage(invalidPermission);
					}
				
				//nm group reload
			} else if (args[1].equalsIgnoreCase("remove")) {
				
				if (sender.hasPermission("namemanager.group.remove")) {
					NameManagerGroupAPI.removeGroup(args[2]);
					sender.sendMessage("§3Succesfully removed group '§c" + args[2] + "§3'");
				} else {
					sender.sendMessage(invalidPermission);
				}
			
			//nm group list
			} else if (args[1].equalsIgnoreCase("list")) {
				
				if (sender.hasPermission("namemanager.group.list")) {
					for (String s : NameManagerGroupAPI.groups) {
			        	sender.sendMessage("§3" + s);
			        }
				} else {
					sender.sendMessage(invalidPermission);
				}
			
			//nm group rainbow
			} else if (args[1].equalsIgnoreCase("rainbow")) {
				
				if (sender.hasPermission("namemanager.group.rainbow")) {
				
					if (args.length == 3) {
						String teamName = args[2];
						
						if (NameManagerGroupAPI.groups.contains(teamName)) {
							
							Team team = NameManager.board.getTeam(NameManagerGroupAPI.groups.indexOf(teamName) + teamName);
							
							if (!Rainbow.rainbowTeams.containsKey(team)) {
								
								Rainbow.rainbowTeams.put(team, team.getEntries());
								
								for (String playerName : team.getEntries())
									Rainbow.enableRainbow(playerName);
								
								sender.sendMessage("§3Rainbow activated for group §c" + teamName);
								
							} else {
								
									for (String playerName : Rainbow.rainbowTeams.get(team)) {
										Rainbow.disableRainbow(playerName); 
									}
									
									Rainbow.rainbowTeams.remove(team);
									sender.sendMessage("§3Rainbow deactivated for group §c" + teamName);
								
							}
							
						} else {
							sender.sendMessage("§3Group '§c" + teamName + "§3' is not a valid group.");
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm group rainbow <group>");
					}
				
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			} else if (cmd.getName().equals("add")) {
				
				if (sender.hasPermission("namemanager.group.add")) {
				
					if (args.length == 4) {
						
						Player targetPlayer = Bukkit.getPlayer(args[3]);
						
						if (targetPlayer != null && targetPlayer.isOnline()) {
							
							String team = args[4];
							
							if (NameManagerGroupAPI.groups.contains(team)) {
								team = NameManagerGroupAPI.groups.indexOf(team) + team;
							}
							
							if (NameManager.board.getTeam(args[4]) != null) {
								
								NameManager.board.getTeam(args[4]).addEntry(targetPlayer.getName());
								
								sender.sendMessage("§3Temporarily added §c" + targetPlayer.getName() + " §3to Group §c" + team);
								
							} else {
								sender.sendMessage("§cGroup §3" + team + " §cnot found.");
							}
							
						} else {
							sender.sendMessage("§cPlayer §3" + targetPlayer.getName() + " §cnot found.");
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm group add <player> <group>");
					}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			} else {
				pluginDescription(sender);
			}

		} else {
			pluginDescription(sender);
		}
			
	}
		
		return true;
	}

}
