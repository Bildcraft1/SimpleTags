package com.whixard.simpletags.commands;

import com.whixard.simpletags.SimpleTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AboutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("simpletags.about")) {
                // Get the version of the plugin from the main class
                String version = SimpleTags.version;
                // Send the player a message
                player.sendMessage("§aSimpleTags v" + version + " by Whixard");
            } else {
                player.sendMessage("§cYou do not have permission to run this command");
            }
            return true;
        }
        return false;
    }
}
