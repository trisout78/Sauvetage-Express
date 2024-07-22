package fr.etercube.sauvetageexpress;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

    private SauvetageExpressCommand sauvetageExpressCommand;
    static int defaultinvulnerabilityTime = 600;
    static int defaultgameDuration = 300;
    static boolean stopwitheterevents;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        defaultinvulnerabilityTime = this.getConfig().getInt("timers.invulnerability");
        defaultgameDuration = this.getConfig().getInt("timers.gameDuration");
        stopwitheterevents = this.getConfig().getBoolean("stopwitheterevents");
        sauvetageExpressCommand = new SauvetageExpressCommand(this);
        this.getCommand("sauvetageexpress").setExecutor(sauvetageExpressCommand);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            sauvetageExpressCommand.update();

        }, 0L, 20L);
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

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (sauvetageExpressCommand.getSelectedPlayer() != null) {
            if (event.getPlayer().equals(sauvetageExpressCommand.getSelectedPlayer())) {
                event.setCancelled(true);
            } else {
                event.getPlayer().teleport(sauvetageExpressCommand.getSelectedPlayer().getLocation());
                event.getPlayer().sendMessage("§7[§6§lSauvetageExpress§7] §aVous avez été téléporté vers §6" + sauvetageExpressCommand.getSelectedPlayer().getName() + "§a.");
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (item != null && item.getType() == Material.POTION) {
                event.setCancelled(true);
                player.sendMessage("§7[§6§lSauvetageExpress§7] §cLes potions sont désactivées.");
            }
        }
    }

}