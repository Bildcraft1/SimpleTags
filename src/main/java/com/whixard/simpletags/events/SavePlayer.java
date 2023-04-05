package com.whixard.simpletags.events;

import com.whixard.simpletags.SimpleTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SavePlayer implements Listener {
    // Get the database connection from the main class
    private static final Connection connection = SimpleTags.sql.getConnection();

    // Save the player's tag to the database
    public static void savePlayer(String uuid, String tag) {
        try {
            // Create a new SQL statement
            String sql = "INSERT INTO tags (uuid, tag) VALUES (?, ?)";
            // Prepare the statement
            PreparedStatement statement = connection.prepareStatement(sql);
            // Set the values for the statement
            statement.setString(1, uuid);
            statement.setString(2, tag);
            // Execute the statement
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkPlayer(String uuid) {
        try {
            // Create a new SQL statement
            String sql = "SELECT * FROM tags WHERE uuid = ?";
            // Prepare the statement
            PreparedStatement statement = connection.prepareStatement(sql);
            // Set the values for the statement
            statement.setString(1, uuid);
            // Execute the statement
            return statement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        // Check if the player has a tag in the database
        if (SimpleTags.getPlugin().isDebugEnabled()) {
           SimpleTags.getPlugin().getLogger().info("Checking if player has a tag in the database...");
        }

        if (!checkPlayer(uuid)) {
            if (SimpleTags.getPlugin().isDebugEnabled()) {
                SimpleTags.getPlugin().getLogger().info("Player does not have a tag in the database.");
            }
            // If the player does not have a tag in the database, save their tag
            savePlayer(uuid, null);
            if (SimpleTags.getPlugin().isDebugEnabled()) {
                SimpleTags.getPlugin().getLogger().info("Player's tag has been saved to the database.");
            }
        } else {
            if (SimpleTags.getPlugin().isDebugEnabled()) {
                SimpleTags.getPlugin().getLogger().info("Player has a tag in the database.");
            }
        }
    }

}
