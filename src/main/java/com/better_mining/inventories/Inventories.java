package com.better_mining.inventories;

import com.better_mining.Keys;
import com.better_mining.item.Slots;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {
    private final Keys keys;
    private final Slots slots;

    public Inventories (Keys keys, Slots slots) {
        this.keys = keys;
        this.slots = slots;

    }

    public Inventory createEditEngineMenu() {
        EditEngineMenuHolder holder = new EditEngineMenuHolder(5*9, "Edit Engine");
        Inventory inv = holder.getInventory();

        setAll(inv, slots.unusedSlot);
        inv.setItem(19, slots.emptySlotEngine);

        return inv;
    }
    public Inventory createEditDrillMenu() {
        EditDrillMenuHolder holder = new EditDrillMenuHolder(6*9, "Edit Drill");
        Inventory inv = holder.getInventory();

        setAll(inv, slots.unusedSlot);
        inv.setItem(19, slots.emptySlotDrill);

        return inv;
    }

    private void setAll (Inventory inv, ItemStack item) {
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, item);
        }
    }

}
