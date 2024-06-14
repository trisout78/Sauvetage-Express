package fr.etercube.sauvetageexpress;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import fr.etercube.sauvetageexpress.utils.ConvertSecondToMinutesAndSeconds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SauvetageExpressCommand implements CommandExecutor{

    private final Plugin plugin;
    private BossBar bossBar;
    private Player selectedPlayer = null;
    private Integer invulnerabilityTime = null;
    private Integer tempsrestant = null;

    public SauvetageExpressCommand(Plugin plugin) {
        this.plugin = plugin;
    }
    public Player getSelectedPlayer() {
        return this.selectedPlayer;
    }

    public Integer getInvulnerabilityTime() {
        return this.invulnerabilityTime;
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
                            int selectedIndex = new Random().nextInt(players.size());
                            selectedPlayer = players.get(selectedIndex);
                            invulnerabilityTime = 300;
                            bossBar = Bukkit.createBossBar("§7Activation des dégats dans §a§l300 §r§7secondes", BarColor.GREEN, BarStyle.SEGMENTED_6);
                            bossBar.setVisible(true);
                            bossBar.setProgress(1.0);
                            for (Player player : players) {
                                if (!player.equals(selectedPlayer)) {
                                    ItemStack compass = new ItemStack(Material.COMPASS);
                                    ItemMeta meta = compass.getItemMeta();
                                    meta.setDisplayName("§a§lTracker");
                                    compass.setItemMeta(meta);
                                    compass.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                                    player.getInventory().addItem(compass);
                                }
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                                player.sendTitle("§7Joueur sélectionné:", "§c§l" + selectedPlayer.getName(), 10, 70, 20);
                                bossBar.addPlayer(player);
                            }
                        }, 20L * 5);
                    } else {
                        sender.sendMessage("No players online to select.");
                    }
                    break;
                case "stop":
                    selectedPlayer = null;
                    sender.sendMessage("Game Stopped");
                    bossBar.removeAll();
                    break;
                default:
                    sender.sendMessage("Usage: /sauvetageexpress <start|stop>");
                    break;
            }
        } else {
            sender.sendMessage("Usage: /sauvetageexpress <start|stop>");
        }
        return true;
    }

    public void update() {
        if (selectedPlayer != null) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.sendActionBar("§7Joueurs à protéger: §c" + selectedPlayer.getName());
                player.setCompassTarget(selectedPlayer.getLocation());
            }
            if (invulnerabilityTime != null) {
                invulnerabilityTime--;
                double progress = invulnerabilityTime / 300.0;
                bossBar.setProgress(progress);
                String timeLeft = ConvertSecondToMinutesAndSeconds.convertSecondsToMinutesAndSeconds(invulnerabilityTime);
                bossBar.setTitle("§7Activation des dégats dans §a§l" + timeLeft);
                if (invulnerabilityTime == 240) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 4 minutes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 180) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 3 minutes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 120) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 2 minutes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 60) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 1 minute", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 30) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 30 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 20) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 20 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 10) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 10 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 5) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 5 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 4) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 4 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 3) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 3 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 2) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 2 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime == 1) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lFin dans 1 seconde", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (invulnerabilityTime <= 0) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Invulnérabilité:", "§c§lTerminée", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                    invulnerabilityTime = null;
                    tempsrestant = 600;
                    double progresst = tempsrestant / 600.0;
                    bossBar.setProgress(progresst);
                    String timeLeftt = ConvertSecondToMinutesAndSeconds.convertSecondsToMinutesAndSeconds(tempsrestant);
                    bossBar.setTitle("§7Temps Restant §c§l" + timeLeftt);
                    bossBar.setStyle(BarStyle.SEGMENTED_10);
                    bossBar.setColor(BarColor.RED);
                }
            }
            else if (tempsrestant != null) {
                tempsrestant--;
                double progress = tempsrestant / 600.0;
                bossBar.setProgress(progress);
                String timeLeft = ConvertSecondToMinutesAndSeconds.convertSecondsToMinutesAndSeconds(tempsrestant);
                bossBar.setTitle("§7Temps Restant §c§l" + timeLeft);
                if (tempsrestant == 240) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§l4 minutes restantes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 180) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 3 minutes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 120) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 2 minutes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 60) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 1 minute", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 30) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 30 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 20) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 20 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 10) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 10 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 5) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 5 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 4) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 4 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 3) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 3 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 2) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 2 secondes", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant == 1) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin dans 1 seconde", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
                else if (tempsrestant <= 0) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle("§7Temps Restant:", "§c§lFin de partie !", 10, 70, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                    tempsrestant = null;
                }
            }
        }
    }
}