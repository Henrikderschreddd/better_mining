package com.better_mining.item;

import com.better_mining.Keys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Slots {
    private final Keys keys;

    public final ItemStack emptySlot;
    public final ItemStack unusedSlot;
    public final ItemStack emptySlotDrill;
    public final ItemStack emptySlotModul;
    public final ItemStack emptySlotEngine;
    public final ItemStack emptySlotDrillhead;
    public final ItemStack emptySlotPiston;
    public final ItemStack air;

    public Slots(Keys keys) {
        this.keys = keys;
        this.emptySlot = setSlot("", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.unusedSlot = setSlot("", Material.BLACK_STAINED_GLASS_PANE);
        this.emptySlotDrill = setSlot("Insert Drill", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.emptySlotModul = setSlot("Insert Modul", Material.PINK_STAINED_GLASS_PANE);
        this.emptySlotEngine = setSlot("Insert Engine", Material.BLUE_STAINED_GLASS_PANE);
        this.emptySlotDrillhead = setSlot("Insert Drill Head", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.emptySlotPiston = setSlot("Insert Piston", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.air = new ItemStack(Material.AIR);
    }

    private ItemStack setSlot (String title, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(title).decoration(TextDecoration.ITALIC,false));
        meta.getPersistentDataContainer().set(keys.imovable, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
        return item;
    }


}
