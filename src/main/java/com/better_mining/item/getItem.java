package com.better_mining.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class getItem {
    private ItemStack stack;
    public ItemStack getTest () {
        final ItemStack test = ItemStack.of(Material.ACACIA_BOAT);
        test.setData(DataComponentTypes.ITEM_NAME, Component.text("test"));
        test.setData(DataComponentTypes.MAX_STACK_SIZE, 99);
        test.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);


        return test;
    }
}
