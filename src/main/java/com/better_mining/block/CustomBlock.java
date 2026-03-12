package com.better_mining.block;

import org.bukkit.block.data.BlockData;

public class CustomBlock {
    private final String id;
    private final BlockData blockData;
    private final BlockLootTable lootTable;
    private final int toughness;
    private final int strength;
    public CustomBlock (String id, BlockData blockData, BlockLootTable lootTable, int toughness, int strength) {
        this.blockData = blockData;
        this.id = id;
        this.lootTable = lootTable;
        this.toughness = toughness;
        this.strength = strength;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public BlockLootTable getLootTable() {
        return lootTable;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }
}
