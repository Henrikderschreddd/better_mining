package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public record Item(String id, String name, Quality quality, ItemStack item, HashMap<String, Integer> stats) {
}
