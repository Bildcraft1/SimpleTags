package com.whixard.simpletags;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class SimpleExtension extends PlaceholderExpansion {

    private final SimpleTags plugin;

    public SimpleExtension(SimpleTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simpletags";
    }

    @Override
    public @NotNull String getAuthor() {
        return "WhiXard";
    }

    @Override
    public @NotNull String getVersion() {
        return SimpleTags.version;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equals("version")) {
            return SimpleTags.version;
        }

        if (params.equals("tag")) {
            return plugin.getConfig().getString("tag");
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
