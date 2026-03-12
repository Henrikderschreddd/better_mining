package com.better_mining.commands;

import com.better_mining.Keys;
import com.better_mining.inventories.Inventories;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class open_gui implements CommandExecutor , TabCompleter {

    private final Plugin plugin;
    private final Keys keys;
    private final Inventories inventories;

    public open_gui (Plugin plugin, Keys keys, Inventories inventories) {
        this.plugin = plugin;
        this.keys = keys;
        this.inventories = inventories;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender c, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (c instanceof Player) {
            Player player = (Player) c;
            switch (args[0]) {
                case "edit" :
                    switch (args[1]) {
                        case "engine" :
                            player.openInventory(inventories.createEditEngineMenu());
                            break;
                        case "drill" :
                            player.openInventory(inventories.createEditDrillMenu());
                            break;
                    }
                    break;
                case "give" :
                    ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
                    item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts, PersistentDataType.LIST.strings(),List.of("a","b","c")));
                    PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                    item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.engine, PersistentDataType.TAG_CONTAINER, container));
                    item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.remove(keys.parts));
                    player.getInventory().addItem(item);
                    if (item.getPersistentDataContainer().has(keys.parts)) {
                        player.sendMessage("idk");
                    }
                    if (item.getPersistentDataContainer().get(keys.engine,PersistentDataType.TAG_CONTAINER).has(keys.parts)) {
                        player.sendMessage("nested");
                    }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return List.of("edit","give");
        }
        if (args.length == 2) {
            return List.of("engine", "drill");
        }
        return List.of();
    }
}
