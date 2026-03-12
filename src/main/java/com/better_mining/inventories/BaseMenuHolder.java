package com.better_mining.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BaseMenuHolder implements InventoryHolder {

    Inventory inventory;

    public BaseMenuHolder (int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}

