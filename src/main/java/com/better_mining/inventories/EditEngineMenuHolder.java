package com.better_mining.inventories;

import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;

public class EditEngineMenuHolder extends BaseMenuHolder {
    public ArrayList<Integer> pistonSlots;
    public ArrayList<PersistentDataContainer> parts;
    public EditEngineMenuHolder(int size, String title) {
        super(size, title);
        this.pistonSlots = new ArrayList<>();
        this.parts = new ArrayList<>();
    }

    public void setPistonSlots (int[] pistonSlots) {
        this.pistonSlots.clear();
        for (int i : pistonSlots) {
            this.pistonSlots.add(i);
        }
    }
}
