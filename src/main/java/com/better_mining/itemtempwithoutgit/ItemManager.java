package com.better_mining.itemtempwithoutgit;

import com.better_mining.itemtempwithoutgit.Item.Item;
import org.bukkit.inventory.ItemStack;

public enum ItemManager {
    INSTANCE;

    private ItemFactory factory;
    private ItemList itemList;
    private ItemUpdater updater;

    ItemManager () {
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
