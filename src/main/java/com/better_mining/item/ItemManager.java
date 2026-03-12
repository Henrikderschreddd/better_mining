package com.better_mining.item;

import org.bukkit.inventory.ItemStack;

public class ItemManager {
    private ItemFactory factory;
    private ItemList itemList;
    private ItemUpdater updater;

    public ItemManager () {
        this.factory = new ItemFactory();
        this.itemList = new ItemList();
        this.updater = new ItemUpdater(this.itemList);
    }
    public void updateItemData (ItemStack itemStack) {
        updater.updateData(itemStack);
    }
    public void updateLore (ItemStack itemStack) {
        updater.updateLore(itemStack);
    }

    private Item createItem () {
        Item item = factory.createItem();
        if (!(item == null)) {
            itemList.addItem(item);
        }
        return item;
    }


}
