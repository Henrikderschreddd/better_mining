package com.better_mining.player;

import com.google.gson.Gson;
import com.better_mining.Database;
import com.better_mining.Keys;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, CustomPlayerData> currentPlayerMap;
    private Plugin plugin;
    Database database;
    Keys keys;
    public PlayerManager (Database database, Plugin plugin, Keys keys) {
        this.currentPlayerMap = new HashMap<>();
        this.plugin = plugin;
        this.database = database;
        this.keys = keys;
    }

    public void loadPlayer (UUID uuid) {
        String sql = "SELECT json FROM player_data WHERE uuid = ?";
        try (PreparedStatement ps = database.getConnection().prepareStatement(sql)) {
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("load successful");
                String json = rs.getString("json");
                Gson gson = new Gson();
                CustomPlayerData playerData = gson.fromJson(json, CustomPlayerData.class);
                currentPlayerMap.put(uuid, playerData);
            }
            else {
                currentPlayerMap.put(uuid, new CustomPlayerData(keys));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void savePlayer (UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (!(currentPlayerMap.containsKey(uuid))) {
                return;
            }
            Gson gson = new Gson();
            String json = gson.toJson(currentPlayerMap.get(uuid));

            String sql = """
                INSERT INTO player_data (uuid, json)
                VALUES (?, ?)
                ON CONFLICT(uuid)
                DO UPDATE SET json = excluded.json
                """;

            try (PreparedStatement ps = database.getConnection().prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.setString(2, json);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
    public CustomPlayerData getPlayer (UUID uuid) {
        return currentPlayerMap.getOrDefault(uuid, new CustomPlayerData(keys));
    }
    
}
