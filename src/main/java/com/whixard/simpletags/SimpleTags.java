package com.whixard.simpletags;

import com.whixard.simpletags.commands.AboutCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SimpleTags extends JavaPlugin {
    public static String version = "1.0.0";
    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(getPlugin(SimpleTags.class).getCommand("simpletags")).setExecutor(new AboutCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
