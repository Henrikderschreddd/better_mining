package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Piston extends Part{
    public final Bonus[] bonus;

    public Piston (String id, String name, String quality, ItemStack item, HashMap<String,Integer> stats, Bonus[] bonuses) {
        super(id, name, quality, item, stats, "piston");
        this.bonus = bonuses;
    }
}
