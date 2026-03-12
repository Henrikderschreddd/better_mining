package com.better_mining.item;

import java.util.HashMap;

public class ItemList {
    private HashMap<String, Item> itemMap;

    public Item getItem (String item) {
        return itemMap.get(item);
    }

    public boolean contains (String item) {
        return itemMap.containsKey(item);
    }

    public void addItem (Item item) {
        if (itemMap.containsKey(item.id())) {
            return;
        }
        itemMap.put(item.id(), item);
    }
    public void removeItem (String item) {
        itemMap.remove(item);
    }
}
