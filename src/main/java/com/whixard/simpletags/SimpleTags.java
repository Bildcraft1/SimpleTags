package com.whixard.simpletags;

import com.samjakob.spigui.SpiGUI;
import com.whixard.simpletags.commands.AboutCommand;
import com.whixard.simpletags.commands.ReloadCommand;
import com.whixard.simpletags.commands.TagsCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public final class SimpleTags extends JavaPlugin {
    public static String version = "1.0.0";
    public static SpiGUI spiGUI;

    // Create a new instance of the main class
    public static @NotNull SimpleTags getPlugin() {
        return getPlugin(SimpleTags.class);
    }

    // Create a new file configuration for the messages.yml file copied from the jar
    public static @NotNull FileConfiguration getMessage() {
        File file = new File(getPlugin().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            getPlugin().saveResource("messages.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void createDefaultConfigurations() {
        // Create the config.yml file if it doesn't exist
        getPlugin().saveDefaultConfig();
        // Create the messages.yml file if it doesn't exist
        File file = new File(getPlugin().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            getPlugin().saveResource("messages.yml", false);
        }
    }

    @Override
    public void onEnable() {

        // Plugin startup logic
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // Register the placeholder

            new SimpleExtension(this).register();

            // Register Commands
            Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("simpletags")).setExecutor(new AboutCommand());
            Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("tags")).setExecutor(new TagsCommand());
            Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("streload")).setExecutor(new ReloadCommand());

            // Create a new instance of SpiGUI
            spiGUI = new SpiGUI(this);

            // Create the messages.yml file if it doesn't exist
            createDefaultConfigurations();

            // Load the config.yml file and the messages.yml file
            reloadConfig();

        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
