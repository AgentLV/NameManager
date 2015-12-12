package net.agentlv.namemanager;

import net.agentlv.namemanager.api.NameManagerAPI;
import net.agentlv.namemanager.api.NameManagerGroupAPI;
import net.agentlv.namemanager.util.PlayerGroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author AgentLV
 */
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
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {

        String invalidPermission = "§cYou don't have permission.";

        //Sender -> Player
        Player player = null;
        if (sender instanceof Player)
            player = (Player) sender;

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

                        NameManagerAPI.getOfflinePlayer(args[1], new UUIDCallback() {
                            @Override
                            public void done(OfflinePlayer offlinePlayer) {
                                String prefix = args[2];
                                for(int i = 3; i < args.length; ++i) {
                                    prefix += " " + args[i];
                                }

                                if (prefix.length() > 16) {
                                    sender.sendMessage("§3The prefix can only contain 16 Characters.");
                                } else {

                                    if (offlinePlayer.hasPlayedBefore()) {

                                        NameManagerAPI.setNametagPrefix(offlinePlayer, prefix);
                                        sender.sendMessage("§3Prefix '§c" + prefix + "§3' set for §c" + args[1]);

                                    } else {
                                        sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                                    }

                                }
                            }

                            @Override
                            public void fail(Exception e) {
                                sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                            }
                        });

                    } else {
                        sender.sendMessage("§cUsage: /nm prefix <player> <prefix>");
                    }

                } else {
                    sender.sendMessage(invalidPermission);
                }

            } else if (args[0].equalsIgnoreCase("suffix")) {

                if (sender.hasPermission("namemanager.suffix")) {

                    if (args.length >= 3) {

                        NameManagerAPI.getOfflinePlayer(args[1], new UUIDCallback() {
                            @Override
                            public void done(OfflinePlayer offlinePlayer) {
                                String suffix = args[2];
                                for(int i = 3; i < args.length; ++i) {
                                    suffix += " " + args[i];
                                }

                                if (suffix.length() > 16) {
                                    sender.sendMessage("§3The suffix can only contain 16 Characters.");
                                } else {

                                    if (offlinePlayer.hasPlayedBefore()) {
                                        NameManagerAPI.setNametagSuffix(offlinePlayer, suffix);
                                        sender.sendMessage("§3Suffix '§c" + suffix + "§3' set for §c" + args[1]);

                                    } else {
                                        sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                                    }
                                }
                            }

                            @Override
                            public void fail(Exception e) {
                                sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                            }
                        });

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

                            if (!Rainbow.rainbowEnabled(player.getName())) {
                                NameManagerAPI.getOfflinePlayer(player.getName(), new UUIDCallback() {
                                    @Override
                                    public void done(OfflinePlayer offlinePlayer) {
                                        NameManagerAPI.clearNametag(offlinePlayer);
                                    }

                                    @Override
                                    public void fail(Exception e) {
                                        // This shouldn't happen
                                    }
                                });

                                sender.sendMessage("§3Your name was cleared.");
                            } else {
                                sender.sendMessage("§cTo clear your nametag, please turn off your rainbow");
                            }

                        } else {
                            sender.sendMessage("§3A player is required.");
                        }
                    } else if (args.length == 2) {

                        NameManagerAPI.getOfflinePlayer(args[1], new UUIDCallback() {
                            @Override
                            public void done(OfflinePlayer offlinePlayer) {
                                if (offlinePlayer.hasPlayedBefore()) {

                                    if (!Rainbow.rainbowEnabled(offlinePlayer.getName())) {
                                        NameManagerAPI.clearNametag(offlinePlayer);
                                        sender.sendMessage("§3Name cleared for §c" + args[1]);
                                    } else {
                                        sender.sendMessage("§cTo clear the nametag, please turn off the rainbow of §3" + offlinePlayer.getName());
                                    }

                                } else {
                                    sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                                }
                            }

                            @Override
                            public void fail(Exception e) {
                                sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                            }
                        });



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

                            sender.sendMessage("§3Your UUID: §c" + player.getUniqueId());

                        } else {
                            sender.sendMessage("§cA player is required.");
                        }

                    } else if (args.length == 2) {

                        NameManagerAPI.getOfflinePlayer(args[1], new UUIDCallback() {
                            @Override
                            public void done(OfflinePlayer offlinePlayer) {
                                sender.sendMessage("§3UUID of §c" + args[1] + "§3: §c" + offlinePlayer.getUniqueId().toString());
                            }

                            @Override
                            public void fail(Exception e) {
                                sender.sendMessage("§cPlayer §3" + args[1] + "§c not found.");
                            }
                        });

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

                            if (!Rainbow.enableRainbow(player)) {

                                sender.sendMessage("§3Rainbow activated");

                            } else {

                                Rainbow.disableRainbow(player.getName());
                                sender.sendMessage("§3Rainbow deactivated");
                            }

                        } else {
                            sender.sendMessage("§cA player is required.");
                        }

                    } else if (args.length == 2) {

                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        if (targetPlayer != null && targetPlayer.isOnline()) {

                            if (!Rainbow.enableRainbow(targetPlayer)) {

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
                    NameManager.getMultiScoreboard().removeGroups();

                    for (Player reloadPlayer : Bukkit.getOnlinePlayers()) {
                        NameManager.getMultiScoreboard().unregisterPlayerTeam(reloadPlayer.getName());
                    }

                    plugin.reloadConfig();
                    NameManager.getPlayerConfig().reloadConfig();
                    NameManager.getGroupConfig().reloadConfig();
                    NameManagerGroupAPI.getGroups().clear();

                    NameManager.getGroupConfig().initGroups();

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
                        sender.sendMessage("§3/nm group add <player> <group>  §7§  §bTemporarily add a player to a group");
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
                                NameManagerGroupAPI.setGroupPrefix(args[2], prefix);
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
                                NameManagerGroupAPI.setGroupSuffix(args[2], suffix);
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
                        for (String s : NameManagerGroupAPI.getGroups().keySet()) {
                            sender.sendMessage("§3" + s);
                        }
                    } else {
                        sender.sendMessage(invalidPermission);
                    }

                }  else if (cmd.getName().equals("add")) {

                    if (sender.hasPermission("namemanager.group.add")) {

                        if (args.length == 4) {

                            Player targetPlayer = Bukkit.getPlayer(args[3]);

                            if (targetPlayer != null && targetPlayer.isOnline()) {

                                if (NameManagerGroupAPI.getGroups().containsKey(args[4])) {

                                    NameManagerGroupAPI.addPlayer(args[4], targetPlayer.getName());

                                    sender.sendMessage("§3Temporarily added §c" + targetPlayer.getName() + " §3to Group §c" + args[4]);

                                } else {
                                    sender.sendMessage("§cGroup §3" + args[4] + " §cnot found.");
                                }

                            } else {
                                sender.sendMessage("§cPlayer §3" + args[3] + " §cnot found.");
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
