package com.better_mining.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public abstract class CustomItemBase {
    public final String id;
    private ItemStack item;
    public final String quality;
    public final String name;

    public CustomItemBase (String id, String name, String quality, ItemStack item) {
        this.item = item;
        this.id = id;
        this.quality = quality;
        this.name = name;
    }
    public void addPDC(NamespacedKey key,PersistentDataType type, Object c) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, type, c);
        item.setItemMeta(meta);
    }
    public HashMap<String, Integer> getStats () {
        HashMap<String ,Integer> temp = new HashMap<>();
        temp.put("fu", 10);
        return temp;
    }
    public void setItem (ItemStack item) {
        this.item = item;
    }
    public ItemStack getItem () {
        return item.clone();
    }


}
