package fr.etercube.dontkillyourself;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("dontkillyourself").setExecutor(new CommandDontKillYourself());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
