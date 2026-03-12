package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Drill extends Part{
    public int modulSlots;


    public Drill (String id, String name, String quality,ItemStack item, HashMap<String,Integer> stats, int modulSlots) {
        super(id, name, quality, item, stats, "drill");
        this.modulSlots = modulSlots;
    }

}
