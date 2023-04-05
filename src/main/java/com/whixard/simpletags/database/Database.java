package com.whixard.simpletags.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Database {

    private final Connection connection;

    public Database(String file) throws SQLException, ClassNotFoundException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        Class.forName("org.sqlite.JDBC");
        initDatabase();
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void initDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tags (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(36), tag VARCHAR(16))";
        this.connection.prepareStatement(sql).executeUpdate();
    }

    public void savePlayer(String uuid, String tag) {
        try {
            // Create a new SQL statement
            String sql = "INSERT INTO tags (uuid, tag) VALUES (?, ?)";
            // Prepare the statement
            PreparedStatement statement = this.connection.prepareStatement(sql);
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
            PreparedStatement statement = this.connection.prepareStatement(sql);
            // Set the values for the statement
            statement.setString(1, uuid);
            // Execute the statement
            return statement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getTag(UUID uniqueId) {
        try {
            // Create a new SQL statement
            String sql = "SELECT tag FROM tags WHERE uuid = ?";
            // Prepare the statement
            PreparedStatement statement = this.connection.prepareStatement(sql);
            // Set the values for the statement
            statement.setString(1, uniqueId.toString());
            // Execute the statement
            return statement.executeQuery().getString("tag");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePlayer(String string, String tag) {
        try {
            // Create a new SQL statement
            String sql = "UPDATE tags SET tag = ? WHERE uuid = ?";
            // Prepare the statement
            PreparedStatement statement = this.connection.prepareStatement(sql);
            // Set the values for the statement
            statement.setString(1, tag);
            statement.setString(2, string);
            // Execute the statement
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
