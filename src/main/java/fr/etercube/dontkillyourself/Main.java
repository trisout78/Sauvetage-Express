package fr.etercube.dontkillyourself;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private CommandDontKillYourself commandDontKillYourself;

    @Override
    public void onEnable() {
        commandDontKillYourself = new CommandDontKillYourself(this);
        this.getCommand("dontkillyourself").setExecutor(commandDontKillYourself);

        // Schedule the action bar update task to run every second
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            commandDontKillYourself.updateActionBar();
        }, 0L, 20L); // 20 ticks = 1 second
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}