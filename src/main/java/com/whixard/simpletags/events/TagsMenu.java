package com.whixard.simpletags.events;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.whixard.simpletags.SimpleTags;
import com.whixard.simpletags.tools.ChatFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;

import java.util.Objects;

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

        // Create a button with a nametag as the icon for every tag contained in the config file
        for (String tag : SimpleTags.getPlugin().getConfig().getConfigurationSection("tags").getKeys(false)) {
            // Get the lore name form the config and save it in a variable
            String name = SimpleTags.getPlugin().getConfig().getString("tags." + tag + ".name");
            String lore = SimpleTags.getPlugin().getConfig().getString("tags." + tag + ".lore");
            // Create a new button with the nametag as the icon
            assert name != null;
            assert lore != null;
            SGButton tagButton = new SGButton(new ItemBuilder(Material.NAME_TAG).name(ChatFormatter.format(name)).lore(lore).build());
            // Set the action of the button to add the tag to the player's name
            tagButton.setListener(event -> {
                // Get the player from the event
                Player player = (Player) event.getWhoClicked();
                // Get the player's current name
                String playerName = player.getDisplayName();
                // Get the tag from the config
                String tagToAdd = SimpleTags.getPlugin().getConfig().getString("tags." + tag + ".tag");
                // Add the tag to the player's name
                assert tagToAdd != null;
                player.setDisplayName(ChatFormatter.format(tagToAdd + playerName));
                // Close the GUI
                player.closeInventory();
            });

            // Add the button to your GUI
            tagsMenu.addButton(tagButton);
        }

        // Show the GUI
        p.openInventory(tagsMenu.getInventory());
    }
}
