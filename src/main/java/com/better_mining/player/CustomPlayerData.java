package com.better_mining.player;

import com.better_mining.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.HashMap;

public class CustomPlayerData {
    private Keys keys;
    private Stats stats;
    public CustomPlayerData (Keys keys) {
        this.stats = new Stats();
        this.keys = keys;
    }

    public void setEquipment (String source ,ItemStack item) {
        if (item == null || !(item.getPersistentDataContainer().has(keys.stats,PersistentDataType.TAG_CONTAINER))) {
            removeSource(source);
            return;
        }
        PersistentDataContainer statContainer = item.getItemMeta().getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
        if (statContainer == null) {
            removeSource(source);
            return;
        }
        HashMap<String, Integer> statMap = new HashMap<>();
        for (NamespacedKey key : statContainer.getKeys()) {
            statMap.put(key.getKey(), statContainer.getOrDefault(key, PersistentDataType.INTEGER, 0));
        }
        stats.addSource(source, statMap);

    }
    public void removeSource (String source) {
        stats.removeSource(source);
    }
    public HashMap<String, Integer> getStats () {
        return stats.getStats();
    }
    public int getStat (String stat) {
        return stats.getStat(stat);
    }
    public void sayHi (Player player) {
        player.sendMessage("hi from you custom player data");
    }
}
