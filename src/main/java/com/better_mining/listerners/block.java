package com.better_mining.listerners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class block implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrample(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        if (event.getClickedBlock() == null) return;

        if (event.getClickedBlock().getType() == Material.FARMLAND) {
            // Player trampled farmland
            event.setCancelled(true); // prevent trampling
        }
    }

    @EventHandler
    public void onCoralFade(BlockFadeEvent event) {
        switch (event.getBlock().getType()) {
            case BRAIN_CORAL:
            case BUBBLE_CORAL:
            case FIRE_CORAL:
            case HORN_CORAL:
            case TUBE_CORAL:
            case BRAIN_CORAL_FAN:
            case BUBBLE_CORAL_FAN:
            case FIRE_CORAL_FAN:
            case HORN_CORAL_FAN:
            case TUBE_CORAL_FAN:
                event.setCancelled(true);
                break;
            default:
                break;
        }
    }
}
