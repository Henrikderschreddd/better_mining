package com.better_mining.itemtempwithoutgit.Item;

import com.better_mining.item.PartType;
import com.better_mining.itemtempwithoutgit.EngineLayout;
import com.better_mining.itemtempwithoutgit.Quality;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Engine extends Item{
    EngineLayout layout;
    //PartType partType = PartType

    public Engine(String id, String name, Quality quality, ItemStack itemStack, HashMap<String, Integer> stats, EngineLayout layout) {
        super(id, name, quality, itemStack, stats);
        this.layout = layout;
    }
}
