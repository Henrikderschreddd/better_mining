package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Part extends CustomItem{
    public String partType;
    public final HashMap<String,Integer> stats;

    public Part(String id, String name, String quality, ItemStack item, HashMap<String,Integer> stats, String partType) {
        super(id, name, quality, item);
        this.partType = partType;
        this.stats = new HashMap<>(stats);
    }
    @Override
    public HashMap<String, Integer> getStats () {
        return stats;
    }

}
