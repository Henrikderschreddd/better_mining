package com.better_mining;

import com.better_mining.commands.giveCustomItem;
import com.better_mining.commands.open_gui;
import com.better_mining.commands.say_uuid;
import com.better_mining.inventories.Inventories;
import com.better_mining.item.CustomItemMap;
import com.better_mining.item.Slots;
import com.better_mining.listerners.*;
import com.better_mining.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Better_mining extends JavaPlugin {

    @Override
    public void onEnable() {

        getDataFolder().mkdirs();
        File databaseFolder = new File(getDataFolder(), "database");
        databaseFolder.mkdirs();
        Database database = new Database(this);
        database.connect();
        database.createTables();

        Keys keys = new Keys(this);
        Slots slots = new Slots(keys);
        Inventories inventories = new Inventories(keys, slots);
        CustomItemMap customItemMap = new CustomItemMap(keys);
        PlayerManager playerManager = new PlayerManager(database, this, keys);
        commands(keys, inventories, customItemMap);
        pluginManger(keys, customItemMap, slots, playerManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void commands (Keys keys, Inventories inventories, CustomItemMap customItemMap) {

        getCommand("test").setExecutor(new say_uuid());
        getCommand("gui").setExecutor(new open_gui(this, keys, inventories));
        getCommand("giveCustomItem").setExecutor(new giveCustomItem(customItemMap));
    }

    public void pluginManger (Keys keys, CustomItemMap customItemMap, Slots slots, PlayerManager playerManager) {

        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(new connect(playerManager), this);
        pluginmanager.registerEvents(new block(), this);
        pluginmanager.registerEvents(new mining(this, keys, customItemMap), this);
        pluginmanager.registerEvents(new gui_events(this, keys, customItemMap, slots), this);
        pluginmanager.registerEvents(new inv_changes(playerManager), this);
        }
}
