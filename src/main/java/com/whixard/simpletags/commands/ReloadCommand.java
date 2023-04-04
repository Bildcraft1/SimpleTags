package com.whixard.simpletags.commands;

import com.whixard.simpletags.tools.ReloadManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("simpletags.reload")) {
            // Reload the config.yml file
            ReloadManager.reloadConfig();
            // Reload the messages.yml file
            ReloadManager.reloadMessages();
            // Send the player a message
            commandSender.sendMessage("§aSimpleTags has been reloaded");
        } else {
            commandSender.sendMessage("§cYou do not have permission to run this command");
        }
        return true;
    }
}
