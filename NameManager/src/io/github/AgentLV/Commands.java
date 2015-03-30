package io.github.AgentLV;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	NameManager plugin;
	Map<Player, String[]> map = new HashMap<Player, String[]>();
	
	public Commands(NameManager plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String mainAuthor = plugin.getDescription().getAuthors().get(0);
		String pluginName = plugin.getDescription().getName();
		String pluginVersion = plugin.getDescription().getVersion();
		String invalidPermission = "§cYou don't have permission.";
		
		//Sender -> Player
		Player p = (Player) sender;
			
		OfflinePlayer offlinePlayer = null;
		
		//Command /namemanager
		if (cmd.getName().equalsIgnoreCase("namemanager")) {
			
			if (args.length == 0) {
				sender.sendMessage("§3" + pluginName + "§7 v" + pluginVersion + " by §3" + mainAuthor);
				sender.sendMessage("§7Commands: /nm help");
				return true;
			}
			
			//Command /nm help
			if (args[0].equalsIgnoreCase("help")) {
				if(sender.hasPermission("namemanager.help")) {
					sender.sendMessage("");
					sender.sendMessage("§r§3---- §b§lNameManager Commands §r§3----");
					sender.sendMessage("");
					sender.sendMessage("  §3/nm prefix <player> <prefix>  §7»  §bSets a prefix");
					sender.sendMessage("  §3/nm suffix <player> <suffix>  §7»  §bSets a suffix");
					sender.sendMessage("  §3/nm clear [player]  §7»  §bResets a name");
					sender.sendMessage("  §3/nm rainbow [player]  §7»  §bRainbow name §c(could cause lag)");
					sender.sendMessage("  §3/nm uuid [player]  §7»  §bShows the UUID of a player");
					sender.sendMessage("");
					sender.sendMessage("§3All names are §ncase sensitive§r§3!");
					sender.sendMessage("");
					
				} else {
					sender.sendMessage(invalidPermission);
				} 
			}
			
			
			//Command /nm prefix
			if (args[0].equalsIgnoreCase("prefix")) {
				
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
				
			}
			
			
			
			//Command /nm suffix
			if (args[0].equalsIgnoreCase("suffix")) {
				
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
					
			}
			
			
			//Command /nm clear
			if (args[0].equalsIgnoreCase("clear")) {
				
				if (sender.hasPermission("namemanager.clear")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							NameManagerAPI.clearNametag(p);
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
				
			}
			
			
			//Command /nm uuid
			if (args[0].equalsIgnoreCase("uuid")) {
				
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
			if (args[0].equalsIgnoreCase("rainbow")) {
				
				if (sender.hasPermission("namemanager.rainbow")) {
					
					if (args.length == 1) {
						
						if (sender instanceof Player) {
							
							if (!map.containsKey(p)) {
								
								String[] prefixAndSuffix = { NameManagerAPI.getNametagPrefix(p), NameManagerAPI.getNametagSuffix(p) };
								map.put(p, prefixAndSuffix);
								Rainbow.enableRainbow(p);
								sender.sendMessage("§3Rainbow activated");
								
							} else {
								
								String[] stringlist = map.get(p);
								Rainbow.disableRainbow(p);
								NameManagerAPI.setNametag(stringlist[0], p, stringlist[1]);
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
									
									String[] prefixAndSuffix = { NameManagerAPI.getNametagPrefix(targetPlayer), NameManagerAPI.getNametagSuffix(targetPlayer) };
									map.put(targetPlayer, prefixAndSuffix);
									Rainbow.enableRainbow(targetPlayer);
									sender.sendMessage("§3Rainbow activated for §c" + targetPlayer.getName());
								
								} else {
									
									String[] stringlist = map.get(targetPlayer);
									Rainbow.disableRainbow(targetPlayer);
									NameManagerAPI.setNametag(stringlist[0], targetPlayer, stringlist[1]);
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

		}
		
		return true;
	}

}
