package dev.mrblackreal.antilog4j;

import dev.mrblackreal.antilog4j.event.EventManager;
import dev.mrblackreal.antilog4j.util.file.ConfigManager;
import dev.mrblackreal.antilog4j.util.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AntiLog4J extends JavaPlugin {

    private final ConfigManager configManager = new ConfigManager();
    public ConfigManager getConfigManager() {
        return configManager;
    }

    private FileManager fileManager;
    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private static AntiLog4J instance;
    public static AntiLog4J getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        try {
            configManager.load();
            pluginManager.registerEvents(new EventManager(), this);
            fileManager = new FileManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getConfigManager().saveUser) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if (!EventManager.getExploiter().isEmpty()) {
                    try {
                        System.out.println(" ");
                        System.out.println(this.getName() + " >> Saving Exploiter...");
                        fileManager.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(this.getName() + " >> Saved Exploiter!");
                    System.out.println(" ");
                }
            },0L, configManager.saveDelay);
        }
    }

    @Override
    public void onDisable() {
        fileManager = null;
    }
}
