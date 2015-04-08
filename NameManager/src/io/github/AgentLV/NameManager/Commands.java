package io.github.AgentLV.NameManager;

import io.github.AgentLV.NameManager.API.API;
import io.github.AgentLV.NameManager.API.GroupAPI;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class Commands implements CommandExecutor {

	static NameManager plugin;
	Map<Player, Team> map = new HashMap<Player, Team>();
	
	public Commands(NameManager plugin) {
		Commands.plugin = plugin;
	}
	
	public static void pluginDescription(CommandSender sender) {
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
		Player p = (Player) sender;
			
		OfflinePlayer offlinePlayer = null;
		
		//Command /namemanager
		if (cmd.getName().equalsIgnoreCase("namemanager")) {
			
			if (args.length == 0) {
				pluginDescription(sender);
				
			}
			
			//Command /nm help
			else if (args[0].equalsIgnoreCase("help")) {
				if(sender.hasPermission("namemanager.help")) {
					sender.sendMessage("");
					sender.sendMessage("§3---- §b§lNameManager commands §r§3----");
					sender.sendMessage("");
					sender.sendMessage("§3/nm prefix <player> <prefix>  §7»  §bSets a prefix for a player");
					sender.sendMessage("§3/nm suffix <player> <suffix>  §7»  §bSets a suffix for a player");
					sender.sendMessage("§3/nm clear [player]  §7»  §bResets a name");
					sender.sendMessage("§3/nm rainbow [player]  §7»  §bRainbow name §c(could cause lag)");
					sender.sendMessage("§3/nm uuid [player]  §7»  §bShows the UUID of a player");
					sender.sendMessage("");
					sender.sendMessage("§3All names are §ncase sensitive§r§3!");
					sender.sendMessage("");
					
				} else {
					sender.sendMessage(invalidPermission);
				} 
			}
			
			
			//Command /nm prefix
			else if (args[0].equalsIgnoreCase("prefix")) {
				
				if (sender.hasPermission("namemanager.prefix")) {
				
					if (args.length >= 3) {
						
						offlinePlayer = API.playerToOfflinePlayer(args[1]);
						
						String prefix = args[2];
						for(int i = 3; i < args.length; ++i) {
						     prefix += " " + args[i];
						}
	
						if (prefix.length() > 16) {
							sender.sendMessage("§3The prefix can only contain 16 Characters.");
						} else {
						
							if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
								
								API.setNametagPrefix(offlinePlayer, prefix);
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
				
			}
			
			
			
			//Command /nm suffix
			else if (args[0].equalsIgnoreCase("suffix")) {
				
				if (sender.hasPermission("namemanager.suffix")) {
				
					if (args.length >= 3) {
						
						offlinePlayer = API.playerToOfflinePlayer(args[1]);
						
						String suffix = args[2];
						for(int i = 3; i < args.length; ++i) {
						     suffix += " " + args[i];
						}
						
						if (suffix.length() > 16) {
							sender.sendMessage("§3The suffix can only contain 16 Characters.");
						} else {
							
							if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
								API.setNametagSuffix(offlinePlayer, suffix);
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
					
			}
			
			
			//Command /nm clear
			else if (args[0].equalsIgnoreCase("clear")) {
				
				if (sender.hasPermission("namemanager.clear")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							API.clearNametag(API.playerToOfflinePlayer(p.getName()));
							sender.sendMessage("§3Your name was cleared.");
							
						} else {
							sender.sendMessage("§3A player is required.");
						}
					} else if (args.length == 2) {
						
						offlinePlayer = API.playerToOfflinePlayer(args[1]);
						
						if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
							API.clearNametag(offlinePlayer);
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
				
			}
			
			
			//Command /nm uuid
			else if (args[0].equalsIgnoreCase("uuid")) {
				
				if (sender.hasPermission("namemanager.uuid")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							sender.sendMessage("§3Your UUID: §c" + p.getUniqueId());
							
						} else {
							sender.sendMessage("§cA player is required.");
						}
						
					} else if (args.length == 2) {
						
						offlinePlayer = API.playerToOfflinePlayer(args[1]);
						
						if (offlinePlayer != null) {
							sender.sendMessage("§3UUID of §c" + args[1] + "§3: §c" + offlinePlayer.getUniqueId());
						} else {
							sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
						}
						
					} else {
						sender.sendMessage("§cUsage: /nm uuid <player>");
					}
					
				} else {
					sender.sendMessage(invalidPermission);
				}
				
			}
			
			
			
			//Command /nm rainbow
			else if (args[0].equalsIgnoreCase("rainbow")) {
				
				if (sender.hasPermission("namemanager.rainbow")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							if (!map.containsKey(p)) {
								
								
								map.put(p, NameManager.board.getPlayerTeam(p));
								Rainbow.enableRainbow(p);
								sender.sendMessage("§3Rainbow activated");
								
							} else {
								
								Team team = map.get(p);
								if (team != null) {
									team.addPlayer(p);
								} else {
									NameManager.rainbow.removePlayer(p);
								}
								
								Rainbow.disableRainbow(p);
								map.remove(p);
								sender.sendMessage("§cRainbow deactivated");
							}
							
						} else {
							sender.sendMessage("§cA player is required.");
						}
						
						} else if (args.length == 2) {
							
							Player targetPlayer = plugin.getServer().getPlayer(args[1]);
							
							if (targetPlayer != null && targetPlayer.isOnline()) {
								
								if (!map.containsKey(targetPlayer)) {
									
									map.put(targetPlayer, NameManager.board.getPlayerTeam(targetPlayer));
									Rainbow.enableRainbow(targetPlayer);
									sender.sendMessage("§3Rainbow activated for §c" + targetPlayer.getName());
								
								} else {
									
									Team team = map.get(targetPlayer);
									if (team != null) {
										team.addPlayer(targetPlayer);
									} else {
										NameManager.rainbow.removePlayer(targetPlayer);
									}
									
									Rainbow.disableRainbow(targetPlayer);
									map.remove(targetPlayer);
									sender.sendMessage("§cRainbow deactivated for §c" + targetPlayer.getName());
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
			}
			
			
			//Group commands	
				
				
			//Command /nm groups
			else if (args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("groups")) {
				
				if (args.length == 1) {
					
					if(sender.hasPermission("namemanager.help")) {
						sender.sendMessage("");
						sender.sendMessage("§3---- §b§lNameManager group commands §r§3----");
						sender.sendMessage("");
						sender.sendMessage("§3/nm group prefix <group> <prefix>  §7»  §bSets a prefix for a group");
						sender.sendMessage("§3/nm group suffix <group> <suffix>  §7»  §bSets a suffix for a group");
						sender.sendMessage("§3/nm group clear <group>  §7»  §bRemoves a group");
						sender.sendMessage("§3/nm group rainbow <group>  §7»  §bRainbow name for all group members §c(could cause lag)");
						sender.sendMessage("");
					} else {
						sender.sendMessage(invalidPermission);
					}
					
					//Command /nm group prefix
				} else if(args[1].equalsIgnoreCase("prefix")) {
					
					if (sender.hasPermission("namemanager.group.prefix")) {
						
						if (args.length >= 3) {
							
							String prefix = args[3];
							for(int i = 4; i < args.length; ++i) {
							     prefix += " " + args[i];
							}
		
							if (prefix.length() > 16) {
								sender.sendMessage("§3The prefix can only contain 16 Characters.");
							} else {
								GroupAPI.setGroupNametagPrefix(args[2], prefix);
								sender.sendMessage("§3Set prefix '§c" + prefix + "§3' for group §c" + args[2]);
							}
						} else {
							sender.sendMessage("§cUsage: /nm group prefix <group> <prefix>");
						}
						
					} else {
						sender.sendMessage(invalidPermission);
					}
				} else if(args[1].equalsIgnoreCase("suffix")) {
					
					if (sender.hasPermission("namemanager.group.suffix")) {
						
						if (args.length >= 3) {
							
							String suffix = args[3];
							for(int i = 4; i < args.length; ++i) {
								suffix += " " + args[i];
							}
		
							if (suffix.length() > 16) {
								sender.sendMessage("§3The suffix can only contain 16 Characters.");
							} else {
								GroupAPI.setGroupNametagSuffix(args[2], suffix);
								sender.sendMessage("§3Set suffix '§c" + suffix + "§3' for group §c" + args[2]);
							}
						} else {
							sender.sendMessage("§cUsage: /nm group suffix <group> <prefix>");
						}
						
					} else {
						sender.sendMessage(invalidPermission);
					}
				}

			}
			
			
			
			
			
		}
		
		return true;
	}

}
