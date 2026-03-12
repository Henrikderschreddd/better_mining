package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Engine extends Part{
    public final String layout;

    public Engine (String id, String name, String quality, ItemStack item, HashMap<String,Integer> stats, String layout) {
        super(id, name, quality, item, stats, "engine");
        this.layout = layout;
    }
}
