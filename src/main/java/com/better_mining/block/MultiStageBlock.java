package com.better_mining.block;

import com.better_mining.itemtempwithoutgit.ItemManager;

import java.util.HashMap;

public class MultiStageBlock {
    private final int stage;
    private final HashMap<Integer, CustomBlock> blockHashMap;
    private final String id;
    public MultiStageBlock (String id, HashMap<Integer, CustomBlock> blockHashMap) {
        this.id = id;
        this.stage = blockHashMap.size() - 1;
        this.blockHashMap = blockHashMap;
    }
    public CustomBlock getCustomBlock(int stage) {
        return blockHashMap.get(stage);
    }
    public int getMaxStage () {
        return stage;
    }

    public String getId() {
        return id;
    }
}
