package com.better_mining.block;

public class BlockLoot {
    private final int weight;
    private final int countMin;
    private final int countMax;
    private final String id;
    public BlockLoot (String id, int weight) {
        this.id = id;
        this.weight = weight;
        this.countMin = 1;
        this.countMax = 1;
    }
    public BlockLoot (String id, int weight, int countMin, int countMax) {
        this.id = id;
        this.weight = weight;
        this.countMin =countMin;
        this.countMax = countMax;
    }

    public String getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getCountMin() {
        return countMin;
    }

    public int getCountMax() {
        return countMax;
    }

    public int getCountRandom() {
        return countMin + (int)(Math.random() * (countMax - countMin));
    }
}
