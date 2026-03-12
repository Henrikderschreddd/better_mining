package com.better_mining.item;

import net.kyori.adventure.text.Component;

import java.util.List;

public class Bonus {
    public final String title;
    public final String id;
    public final List<Component> description;
    public final int bonusThreshold;
    public Bonus (String id, String title, List<Component> description, int bonusThreshold) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.bonusThreshold = bonusThreshold;
    }
}
