package com.better_mining.commands;

import com.better_mining.item.CustomItemMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class giveCustomItem implements CommandExecutor, TabCompleter {
    private CustomItemMap customItemMap;

    public giveCustomItem (CustomItemMap customItemMap) {
        this.customItemMap = customItemMap;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (customItemMap.customItemMap.containsKey(args[0])) {
            Player player = (Player) commandSender;
            player.getInventory().addItem(customItemMap.customItemMap.get(args[0]).getItem());
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1)
            return List.of(customItemMap.customItemMap.keySet().toArray(new String[0]));
        return List.of();
    }
}
