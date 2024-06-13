package fr.etercube.dontkillyourself;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandDontKillYourself implements CommandExecutor{

    private final Plugin plugin;
    private Player selectedPlayer = null; // Ajout de la variable selectedPlayer

    public CommandDontKillYourself(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "start":
                    List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
                    if (players.size() > 0) {
                        for (int i = 0; i < 5; i++) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                for (Player player : players) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                                    player.sendTitle("§7Tirage au sort...", "", 10, 70, 20);
                                }
                            }, 20L * i);
                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            for (Player player : players) {
                                if (!player.equals(selectedPlayer)) {
                                    ItemStack compass = new ItemStack(Material.COMPASS);
                                    player.getInventory().addItem(compass);
                                    player.setCompassTarget(selectedPlayer.getLocation());
                                }
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                                player.sendTitle("§7Joueur sélectionné:", "§c§l" + selectedPlayer.getName(), 10, 70, 20);
                            }
                        }, 20L * 5);
                    } else {
                        sender.sendMessage("No players online to select.");
                    }
                    int selectedIndex = new Random().nextInt(players.size());
                    selectedPlayer = players.get(selectedIndex);
                    break;
                case "stop":
                    selectedPlayer = null;
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

    public void updateActionBar() {
        if (selectedPlayer != null) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.sendActionBar("§7Joueurs à protéger: §c" + selectedPlayer.getName());
            }
        }
    }
}