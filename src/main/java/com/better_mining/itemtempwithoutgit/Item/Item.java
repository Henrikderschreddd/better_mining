package com.better_mining.itemtempwithoutgit.Item;

import com.better_mining.itemtempwithoutgit.Quality;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Item {
    private final String id;
    private final String name;
    private final Quality quality;
    private final ItemStack itemStack;
    private final HashMap<String, Integer> stats;

    public Item (String id, String name, Quality quality, ItemStack itemStack, HashMap<String, Integer> stats) {
        this.id = id;
        this.name = name;
        this.quality = quality;
        this.itemStack = itemStack;
        this.stats = stats;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Quality getQuality() {
        return quality;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }
}
