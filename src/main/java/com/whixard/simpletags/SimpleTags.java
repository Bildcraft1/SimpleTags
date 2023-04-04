package com.whixard.simpletags;

import com.samjakob.spigui.SpiGUI;
import com.whixard.simpletags.commands.AboutCommand;
import com.whixard.simpletags.commands.ReloadCommand;
import com.whixard.simpletags.commands.TagsCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public final class SimpleTags extends JavaPlugin {
    public static String version = "1.0.0";
    public static SpiGUI spiGUI;

    // Create a new instance of the main class
    public static SimpleTags getPlugin() {
        return getPlugin(SimpleTags.class);
    }

    // Create a new file configuration for the messages.yml file copied from the jar
    public static FileConfiguration getMessage() {
        File file = new File(getPlugin().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            getPlugin().saveResource("messages.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register Commands
        Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("simpletags")).setExecutor(new AboutCommand());
        Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("tags")).setExecutor(new TagsCommand());
        Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("streload")).setExecutor(new ReloadCommand());

        // Create a new instance of SpiGUI
        spiGUI = new SpiGUI(this);

        // Create the messages.yml file if it doesn't exist
        saveConfig();
        saveResource("messages.yml", false);

        // Load the config.yml file and the messages.yml file
        reloadConfig();
        getMessage();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
