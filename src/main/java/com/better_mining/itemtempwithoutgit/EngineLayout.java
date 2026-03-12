package com.better_mining.itemtempwithoutgit;

public enum EngineLayout {
    I1(new int[]{22}, 100),
    I2(new int[]{22, 23}, 100),
    I3(new int[]{22, 23, 24}, 100),
    I4(new int[]{22, 23, 24, 25}, 100),
    V4(new int[]{13, 14, 31, 32}, 90),
    V6(new int[]{13, 14, 15, 31, 32, 33}, 100),
    V8(new int[]{13, 14, 15, 16, 31, 32, 33, 34}, 90),
    V10(new int[]{12, 13, 14, 15, 16,30, 31, 32, 33, 34}, 80),
    W16(new int[]{3, 4, 5, 6, 13, 14, 15, 16, 30, 31, 32, 33, 40, 41, 42, 43}, 65);

    private final int[] pistonSlots;
    private final int efficiency;

    EngineLayout (int[] pistonSlots, int efficiency) {
        this.pistonSlots = pistonSlots;
        this.efficiency = efficiency;
    }

    public int[] getPistonSlots() {
        return pistonSlots;
    }

    public int getEfficiency() {
        return efficiency;
    }
}
