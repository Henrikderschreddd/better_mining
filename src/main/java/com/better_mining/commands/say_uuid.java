package com.better_mining.commands;

import com.better_mining.item.getItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class say_uuid implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player player = (Player) commandSender;
        player.sendMessage(player.getUniqueId().toString());
        ItemStack stack = new getItem().getTest();
        player.getInventory().addItem(stack);

        return true;
    }
}
