package com.better_mining;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public enum KeySet {
    INSTANCE("better_mining");

    public final NamespacedKey immovable;
    public final NamespacedKey parts;
    public final NamespacedKey engine;
    public final NamespacedKey drillhead;
    public final NamespacedKey partType;
    public final NamespacedKey custom_item_id;
    public final NamespacedKey quality;
    public final NamespacedKey drill;
    public final NamespacedKey stats;
    public final NamespacedKey efficiency;
    public final NamespacedKey count;
    public final NamespacedKey bonus;

    KeySet (String plugin) {
        this.immovable = new NamespacedKey(plugin,"immovable");
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
