package com.whixard.simpletags.commands;

import com.whixard.simpletags.events.TagsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TagsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("simpletags.use")) {
                TagsMenu.openTagsMenu(player);
            }
        } else {
            commandSender.sendMessage("§cThis command can only be run by a player");
        }
        return true;
    }
}
