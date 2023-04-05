package com.whixard.simpletags;

import com.whixard.simpletags.tools.ChatFormatter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
            if (player == null) {
                return null;
            }
            if (SimpleTags.sql.checkPlayer(player.getUniqueId().toString())) {
                if (!Objects.equals(SimpleTags.sql.getTag(player.getUniqueId()), "None")) {
                    String userTag = SimpleTags.sql.getTag(player.getUniqueId());
                    String tagFormat = SimpleTags.getPlugin().getConfig().getString("tags." + userTag + ".tag-format");
                    if (tagFormat == null) {
                        return "";
                    }
                    return ChatFormatter.format(tagFormat);
                } else {
                    return "";
                }
            }
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
