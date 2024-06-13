package fr.etercube.dontkillyourself;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

    private CommandDontKillYourself commandDontKillYourself;

    @Override
    public void onEnable() {
        commandDontKillYourself = new CommandDontKillYourself(this);
        this.getCommand("dontkillyourself").setExecutor(commandDontKillYourself);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            commandDontKillYourself.update();

        }, 0L, 20L);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (commandDontKillYourself.getSelectedPlayer() != null) {
            if (event.getEntity().equals(commandDontKillYourself.getSelectedPlayer())) {
                event.setDeathMessage("§c§l" + event.getEntity().getName() + "§c s'est suicidé !");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.equals(commandDontKillYourself.getSelectedPlayer()) && commandDontKillYourself.getInvulnerabilityTime() != null) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}