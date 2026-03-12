package com.better_mining.inventories;

import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;

public class EditDrillMenuHolder extends BaseMenuHolder{
    public ArrayList<Integer> modulSlots;
    public ArrayList<PersistentDataContainer> parts;
    public EditDrillMenuHolder (int size, String title) {
        super(size, title);
        this.modulSlots = new ArrayList<>();
        this.parts = new ArrayList<>();
    }

    public void setModulSlots (int[] pistonSlots) {
        this.modulSlots.clear();
        for (int i : pistonSlots) {
            this.modulSlots.add(i);
        }
    }
    public void addModulSlot (int slot) {
        modulSlots.add(slot);
    }
}
