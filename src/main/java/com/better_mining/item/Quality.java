package com.better_mining.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum Quality {
    RUBBISH(0xE6E6E6),
    DECENT(0x5BEE63),
    MEDIOCRE(0x264DDD),
    EXCELLENT(0x700CDD),
    PRISTINE(0xF1A800),
    EXQUISITE(0xFF4FF9),
    CELESTIAL(0x4AEBFF);

    private final Style style;

    Quality (int color) {
        this.style = Component.text("").color(TextColor.color(color)).decoration(TextDecoration.ITALIC,false).style();
    }

    public Style getStyle() {
        return style;
    }
}
