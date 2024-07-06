package fr.etercube.sauvetageexpress;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener{

    public static boolean vote;
    private SauvetageExpressCommand sauvetageExpressCommand;
    static int defaultinvulnerabilityTime = 600;
    static int defaultgameDuration = 300;
    static boolean stopwitheterevents;
    private List<String> winnerList = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        defaultinvulnerabilityTime = this.getConfig().getInt("timers.invulnerability");
        defaultgameDuration = this.getConfig().getInt("timers.gameDuration");
        stopwitheterevents = this.getConfig().getBoolean("stopwitheterevents");
        sauvetageExpressCommand = new SauvetageExpressCommand(this);
        this.getCommand("sauvetageexpress").setExecutor(sauvetageExpressCommand);
        this.getCommand("vote").setExecutor(new VoteCommand());
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            sauvetageExpressCommand.update();

        }, 0L, 20L);
        long voteCooldownTicks = this.getConfig().getLong("timers.voteCooldown") * 20L;
        long voteDurationTicks = this.getConfig().getLong("timers.voteDuration") * 20L;
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            vote = true;
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.sendTitle("§9VOTE", "§c§lVous avez 30 secondes pour voter", 10, 70, 20);
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
                player.sendMessage("[§9VOTE§r] §c§lUtilisez /vote <player> pou voter");
            }
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                vote = false;
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendTitle("§9VOTE", "§c§lLe vote est terminé", 10, 70, 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
                }
                String winner = VoteCommand.getWinner();
                if (winner != null) {
                    Bukkit.getServer().broadcastMessage("Le joueur avec le plus de vote est: " + winner);
                    winnerList.add(winner);
                } else {
                    Bukkit.getServer().broadcastMessage("Pas assez de votes pour choisir un joueur");
                }
            }, 0L, voteDurationTicks);
        }, 0L, voteCooldownTicks);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (sauvetageExpressCommand.getSelectedPlayer() != null) {
            if (event.getEntity().equals(sauvetageExpressCommand.getSelectedPlayer())) {
                event.setDeathMessage("§c§l" + event.getEntity().getName() + "§c s'est suicidé !");
                sauvetageExpressCommand.tempsrestant = 1;
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (sauvetageExpressCommand.getInvulnerabilityTime() != null) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}