package com.whixard.simpletags.events;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.whixard.simpletags.SimpleTags;
import com.whixard.simpletags.tools.ChatFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;

public class TagsMenu {
    public static void openTagsMenu(Player p) {

        String tagsMenuName;

        // Get the name of the menu from the config.yml
        if (SimpleTags.getMessage().getString("messages.tags-menu-name") != null) {
            tagsMenuName = SimpleTags.getMessage().getString("messages.tags-menu-name");
        } else {
            SimpleTags.getPlugin().getLogger().warning("The tags-menu-name key in the messages.yml file is missing. Using default value.");
            tagsMenuName = "Tags Menu";
        }

        // Create a GUI with 3 rows (27 slots)
        assert tagsMenuName != null;
        SGMenu tagsMenu = SimpleTags.spiGUI.create(ChatFormatter.format(tagsMenuName), 3);

        // Create a button
        SGButton myAwesomeButton = new SGButton(
                // Includes an ItemBuilder class with chainable methods to easily
                // create menu items.
                new ItemBuilder(Material.WOODEN_AXE).build()
        ).withListener((InventoryClickEvent event) -> {
            // Events are cancelled automatically, unless you turn it off
            // for your plugin or for this inventory.
            event.getWhoClicked().sendMessage("You clicked my awesome button!");
        });

        // Add the button to your GUI
        tagsMenu.addButton(myAwesomeButton);

        // Show the GUI
        p.openInventory(tagsMenu.getInventory());
    }
}
