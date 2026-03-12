package com.better_mining.listerners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.better_mining.player.CustomPlayerData;
import com.better_mining.player.PlayerManager;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;


public class inv_changes implements Listener {
    PlayerManager playerManager;

    public inv_changes (PlayerManager playerManager) {
        this.playerManager = playerManager;
    }


    @EventHandler
    public void equipmentChange (PlayerArmorChangeEvent event) {
        CustomPlayerData playerData = playerManager.getPlayer(event.getPlayer().getUniqueId());
        if (event.getNewItem().isEmpty()) {
            playerData.removeSource(getSlotName(event.getSlot().ordinal()));
        }
        else {
            playerData.setEquipment(getSlotName(event.getSlot().ordinal()), event.getNewItem());
        }
    }
    @EventHandler
    public void changeMainhand (PlayerInventorySlotChangeEvent event) {
        CustomPlayerData playerData = playerManager.getPlayer(event.getPlayer().getUniqueId());
        if (event.getSlot() == event.getPlayer().getInventory().getHeldItemSlot()) {
            playerData.setEquipment("mainhand", event.getNewItemStack());
        }
        event.getPlayer().sendMessage(playerData.getStats().toString());
    }
    @EventHandler
    public void changeSlot (PlayerItemHeldEvent event) {
        CustomPlayerData playerData = playerManager.getPlayer(event.getPlayer().getUniqueId());
        playerData.setEquipment("mainhand", event.getPlayer().getInventory().getItem(event.getNewSlot()));
        event.getPlayer().sendMessage(playerData.getStats().toString());
    }
    private String getSlotName (int slot) {
        if (slot == 36) {
            return "boots";
        }
        if (slot == 37) {
            return "legs";
        }
        if (slot == 38) {
            return "chest";
        }
        if (slot == 39) {
            return "helmet";
        }
        return "";

    }
}
