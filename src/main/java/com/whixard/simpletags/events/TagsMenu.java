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
            // Check for the permssion to use the tag
            if (SimpleTags.getPlugin().getConfig().getString("tags." + tag + ".permission") != null) {
                if (!p.hasPermission(Objects.requireNonNull(SimpleTags.getPlugin().getConfig().getString("tags." + tag + ".permission")))) {
                    continue;
                } else {
                    if (SimpleTags.getPlugin().isDebugEnabled()) {
                        SimpleTags.getPlugin().getLogger().info("Player has permission to use tag " + tag);
                    }
                }
            }
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
                // save the selected tag in the database
                SimpleTags.sql.updatePlayer(player.getUniqueId().toString(), tag);
            });

            // Add the button to your GUI
            tagsMenu.addButton(tagButton);
        }

        SGButton removeTagButton = new SGButton(new ItemBuilder(Material.BARRIER).name(ChatFormatter.format("&cRemove Tag")).lore(ChatFormatter.format("&7Click to remove your tag")).build());

        removeTagButton.setListener(event -> {
            Player player = (Player) event.getWhoClicked();
            SimpleTags.sql.updatePlayer(player.getUniqueId().toString(), "None");
            player.closeInventory();
        });

        tagsMenu.addButton(removeTagButton);

        // Show the GUI
        p.openInventory(tagsMenu.getInventory());
    }
}
