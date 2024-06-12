package fr.etercube.dontkillyourself;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandDontKillYourself implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "start":
                    List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
                    if (players.size() > 0) {
                        int selectedIndex = new Random().nextInt(players.size());
                        Player selectedPlayer = players.get(selectedIndex);

                        for (int i = 0; i < 5; i++) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this, () -> {
                                for (Player player : players) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                                    player.sendMessage(ChatColor.GRAY + "Tirage au sort...");
                                }
                            }, 20L * i);
                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this, () -> {
                            for (Player player : players) {
                                player.sendTitle(ChatColor.GRAY + "Joueur sélectionné: " + ChatColor.RED + ChatColor.BOLD + selectedPlayer.getName(), "", 10, 70, 20);
                            }
                        }, 20L * 5);
                    } else {
                        sender.sendMessage("No players online to select.");
                    }
                    break;
                case "stop":
                    sender.sendMessage("Game Stopped");
                    break;
                default:
                    sender.sendMessage("Usage: /dontkillyourself <start|stop>");
                    break;
            }
        } else {
            sender.sendMessage("Usage: /dontkillyourself <start|stop>");
        }
        return true;
    }
}