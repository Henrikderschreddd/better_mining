package com.better_mining.listerners;

import com.better_mining.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class connect implements Listener {
    PlayerManager playerManager;
    public connect (PlayerManager playerManager) {
        this.playerManager = playerManager;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.YELLOW + player.getName() + " hat das Spiel betreten");
        playerManager.loadPlayer(player.getUniqueId());

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.YELLOW + player.getName() + " hat das Spiel verlassen");
        playerManager.savePlayer(player.getUniqueId());

    }
}
