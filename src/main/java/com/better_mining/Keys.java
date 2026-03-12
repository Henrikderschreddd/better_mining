package com.better_mining;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class Keys {

    public NamespacedKey imovable;
    public NamespacedKey parts;
    public NamespacedKey engine;
    public NamespacedKey drillhead;
    public NamespacedKey partType;
    public NamespacedKey custom_item_id;
    public NamespacedKey quality;
    public NamespacedKey drill;
    public NamespacedKey stats;
    public NamespacedKey efficiency;
    public NamespacedKey count;
    public NamespacedKey bonus;

    public Keys (Plugin plugin) {
        this.imovable = new NamespacedKey(plugin,"imovable");
        this.parts = new NamespacedKey(plugin,"parts");
        this.engine = new NamespacedKey(plugin, "engine");
        this.drillhead = new NamespacedKey(plugin,"drillhead");
        this.partType = new NamespacedKey(plugin, "partType");
        this.custom_item_id = new NamespacedKey(plugin, "custom_item_id");
        this.quality = new NamespacedKey(plugin, "quality");
        this.drill = new NamespacedKey(plugin, "drill");
        this.stats = new NamespacedKey(plugin, "stats");
        this.efficiency = new NamespacedKey(plugin, "efficiency");
        this.count = new NamespacedKey(plugin, "count");
        this.bonus = new NamespacedKey(plugin, "bonus");
    }
}
