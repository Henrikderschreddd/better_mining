package com.better_mining.item;


import com.better_mining.Keys;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomItemMap {
    public HashMap<String, CustomItemBase> customItemMap;
    private final HashMap<String, TextComponent> qualityColor;
    private final HashMap<String, TextComponent> statName;
    private final HashMap<String, Layout> layoutMap;
    private final HashMap<String, Bonus> bonusMap;
    private final Bonus emptyBonus;
    private final Keys keys;
    private final ItemStack dummyItem;
    PersistentDataAdapterContext ctx;

    public CustomItemMap(Keys keys) {
        this.customItemMap = new HashMap<>();
        this.keys = keys;
        this.qualityColor = new HashMap<>();
        this.statName = new HashMap<>();
        this.layoutMap = new HashMap<>();
        this.bonusMap = new HashMap<>();
        this.emptyBonus = new Bonus("none", "none", new ArrayList<>(List.of()), 999);

        // create dummy item
        ItemStack item = new ItemStack(Material.STONE_PICKAXE);
        item.unsetData(DataComponentTypes.TOOL);
        item.unsetData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        item.unsetData(DataComponentTypes.MAX_DAMAGE);
        item.unsetData(DataComponentTypes.DAMAGE);
        this.dummyItem = item;
        this.ctx = dummyItem.getPersistentDataContainer().getAdapterContext();

        // define quality font
        qualityColor.put("rubbish",Component.text("").color(TextColor.color(0xE6E6E6)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("decent",Component.text("").color(TextColor.color(0x5BEE63)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("mediocre",Component.text("").color(TextColor.color(0x264DDD)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("excellent",Component.text("").color(TextColor.color(0x700CDD)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("pristine",Component.text("").color(TextColor.color(0xF1A800)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("exquisite",Component.text("").color(TextColor.color(0xFF4FF9)).decoration(TextDecoration.ITALIC,false));
        qualityColor.put("celestial",Component.text("").color(TextColor.color(0x4AEBFF)).decoration(TextDecoration.ITALIC,false));
        // define stat font
        statName.put("neutral_light",Component.text("").color(TextColor.color(0xB3B3B3)).decoration(TextDecoration.ITALIC,false));
        statName.put("neutral",Component.text("").color(TextColor.color(0x666666)).decoration(TextDecoration.ITALIC,false));
        statName.put("breaking_power",Component.text("Breaking Power ").color(TextColor.color(0x404040)).decoration(TextDecoration.ITALIC,false));
        statName.put("mining_speed",Component.text("Mining Speed: +").color(TextColor.color(0xF1A800)).decoration(TextDecoration.ITALIC,false));
        statName.put("mining_fortune",Component.text("Mining Fortune: +").color(TextColor.color(0xF1A800)).decoration(TextDecoration.ITALIC,false));
        statName.put("mining_spread",Component.text("Mining Spread: +").color(TextColor.color(0xF1A800)).decoration(TextDecoration.ITALIC,false));
        //statName.put("mining_speed",Component.text("mining Speed").color(TextColor.color(0xF1A800)).decoration(TextDecoration.ITALIC,false));

        // define layouts
        layoutMap.put("I1", new Layout(new int[]{22}, 100));
        layoutMap.put("I2", new Layout(new int[]{22, 23}, 100));
        layoutMap.put("I3", new Layout(new int[]{22, 23, 24}, 100));
        layoutMap.put("I4", new Layout(new int[]{22, 23, 24, 25}, 100));
        layoutMap.put("V4", new Layout(new int[]{13, 14, 31, 32}, 90));
        layoutMap.put("V6", new Layout(new int[]{13, 14, 15, 31, 32, 33}, 100));
        layoutMap.put("V8", new Layout(new int[]{13, 14, 15, 16, 31, 32, 33, 34}, 90));
        layoutMap.put("V10", new Layout(new int[]{12, 13, 14, 15, 16,30, 31, 32, 33, 34}, 80));
        layoutMap.put("W16", new Layout(new int[]{3, 4, 5, 6, 13, 14, 15, 16, 30, 31, 32, 33, 40, 41, 42, 43}, 65));

        // define bonus
        addBonus("test_bonus", "Test Bonus", new String[] {"This is a test Bonus, that does", "absolutely nothing and is just here", "for testing purpose"}, statName.get("neutral_light").style(), 5);
        addBonus("test_bonus_2", "Other Test Bonus", new String[] {"This is another", "test Bonus, that also", "does nothing"}, statName.get("neutral_light").style(), 3);

        // define items
        //test engine
        HashMap<String,Integer> stats = new HashMap<>();
        stats.put("mining_speed", 100);
        addEngine("test_engine", "Test Engine", "pristine", Material.DIAMOND_NAUTILUS_ARMOR, stats, "V8");
        // test engine 2
        stats.clear();
        stats.put("mining_speed", 70);
        addEngine("test_engine_2", "Test Engine", "excellent", Material.IRON_NAUTILUS_ARMOR, stats, "V6");
        // small engine
        stats.clear();
        stats.put("mining_speed", 10);
        stats.put("mining_spread", 25);
        addEngine("test_engine_3", "Test Engine", "mediocre", Material.COPPER_NAUTILUS_ARMOR, stats, "I4");
        // big engine
        stats.clear();
        stats.put("mining_speed", 800);
        stats.put("mining_spread", 125);
        stats.put("mining_fortune", 250);
        addEngine("test_engine_wr16", "WR16-Engine", "celestial", Material.NETHERITE_NAUTILUS_ARMOR, stats, "W16");
        //test piston
        stats.clear();
        stats.put("mining_speed", 50);
        stats.put("mining_spread", 15);
        addPiston("test_piston_1", "Test Piston", "mediocre", Material.IRON_SHOVEL, stats, new Bonus[] {bonusMap.get("test_bonus_2")});
        // piston 2
        stats.clear();
        stats.put("mining_speed", 230);
        stats.put("mining_fortune", 40);
        addPiston("test_piston_2", "Test Piston", "excellent", Material.NETHERITE_SHOVEL, stats, new Bonus[] {bonusMap.get("test_bonus")});
        // piston 3
        stats.clear();
        stats.put("mining_speed", 170);
        stats.put("mining_fortune", 80);
        addPiston("test_piston_3", "Test Piston", "pristine", Material.COPPER_SHOVEL, stats, new Bonus[] {bonusMap.get("test_bonus"),bonusMap.get("test_bonus_2")});
        stats.clear();
        addPart("test_tank", "Test Tank", "excellent", Material.COMPOSTER, stats, 1, "tank");
        addDrill("test_drill", "Test Drill", "pristine", Material.PRISMARINE_SHARD, stats,  4);
        addDrill("test_drill_2", "Test Drill", "exquisite", Material.PRISMARINE_SHARD, stats,  6);
        addDrill("test_drill_3", "Test Drill", "celestial", Material.PRISMARINE_SHARD, stats,  8);
        stats.put("breaking_power", 5);
        addPart("test_drill_head", "Test Drill Head", "pristine", Material.HOPPER, stats, 1, "drillhead");
        stats.clear();
        stats.put("mining_speed",600);
        stats.put("mining_fortune", 150);
        addEngine("amberpolished_drill_engine", "Amber Polished Drill Engine", "pristine", Material.GOLDEN_NAUTILUS_ARMOR, stats, "V10");
        stats.clear();


        // add Items
        // copper
        addItem("copper_chunk", "Copper Chunk", "rubbish", Material.RAW_COPPER, 99);
        addItem("big_copper_chunk", "Pile of Copper", "mediocre", Material.RAW_COPPER, 99);
    }

    private void addEngine(String id, String name, String quality, Material material, HashMap<String,Integer> stats, String layout) {
        if (!(layoutMap.containsKey(layout))) {
            layout = "I1";
        }
        ItemStack item = dummyItem.clone();
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(material.getKey().toString()));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);

        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.custom_item_id,PersistentDataType.STRING,id));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.partType,PersistentDataType.STRING,"engine"));
        int i = Integer.parseInt(layout.replaceAll("[^0-9]", ""));
        PersistentDataContainer[] parts = new PersistentDataContainer[i];
        PersistentDataContainer empty = getEmptyPDC();
        empty.set(keys.custom_item_id,PersistentDataType.STRING,"none");
        Arrays.fill(parts, empty);
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,parts));
        Engine customItem = new Engine(id, name, quality, item, stats, layout);
        updateItemPrivate(item, name, quality,stats, layout);
        //customItem.setItem(item);
        this.customItemMap.put(id,customItem);
    }
    private void addPiston(String id, String name, String quality, Material material, HashMap<String,Integer> stats, Bonus[] bonuses) {
        ItemStack item = dummyItem.clone();
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(material.getKey().toString()));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.custom_item_id,PersistentDataType.STRING,id));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.partType,PersistentDataType.STRING,"piston"));
        List<PersistentDataContainer> bonusContainer = new ArrayList<>();
        for (Bonus bonus : bonuses) {
            PersistentDataContainer bonusPDC = getEmptyPDC();
            bonusPDC.set(keys.custom_item_id, PersistentDataType.STRING, bonus.id);
            bonusContainer.add(bonusPDC);
        }
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.bonus,PersistentDataType.TAG_CONTAINER_ARRAY,bonusContainer.toArray(new PersistentDataContainer[0])));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.count,PersistentDataType.INTEGER,0));
        Piston customItem = new Piston(id, name, quality, item, stats, bonuses);
        this.customItemMap.put(id,customItem);
        updateItemPrivate(item, name, quality, stats, "");
        this.customItemMap.put(id,customItem);
    }
    private void addPart(String id, String name, String quality, Material material, HashMap<String,Integer> stats, int maxStackSize, String partType) {
        ItemStack item = dummyItem.clone();
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(material.getKey().toString()));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, maxStackSize);
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.custom_item_id,PersistentDataType.STRING,id));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.partType,PersistentDataType.STRING,partType));
        updateItemPrivate(item, name, quality, stats, "");
        Part customItem = new Part(id, name, quality, item, stats, partType);
        this.customItemMap.put(id,customItem);
    }
    private void addItem(String id, String name, String quality, Material material, int maxStackSize) {
        ItemStack item = dummyItem.clone();
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(material.getKey().toString()));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, maxStackSize);
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.custom_item_id,PersistentDataType.STRING,id));
        CustomItem customItem = new CustomItem(id, name, quality, item);
        this.customItemMap.put(id,customItem);
        updateItem(item);
    }
    private void addDrill(String id, String name, String quality, Material material, HashMap<String,Integer> stats, int modulslots) {
        ItemStack item = dummyItem.clone();
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(material.getKey().toString()));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        PersistentDataContainer none = getEmptyPDC();
        none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.custom_item_id,PersistentDataType.STRING,id));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.partType,PersistentDataType.STRING,"drill"));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.engine,PersistentDataType.TAG_CONTAINER,none));
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.drillhead,PersistentDataType.TAG_CONTAINER,none));
        PersistentDataContainer[] parts = new PersistentDataContainer[modulslots];
        Arrays.fill(parts, none);
        item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,parts));
        updateItemPrivate(item, name, quality, stats, "");
        Drill customItem = new Drill(id, name, quality, item, stats, modulslots);
        this.customItemMap.put(id,customItem);
    }
    private void addBonus (String id, String title, String[] description, Style style, int bonusThreshold) {
        List<Component> lore = new ArrayList<>();
        for (String line : description) {
            lore.add(Component.text(line).style(style));
        }
        bonusMap.put(id, new Bonus(id, title, lore, bonusThreshold));
    }

    public void updateItem (ItemStack item) {
        CustomItemBase customItem = customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""));
        String layout;
        if (customItem instanceof Engine) {
            layout = ((Engine) customItem).layout;
        }
        else {
            layout = "";
        }
        HashMap<String, Integer> stats = new HashMap<>();
        if (item.getPersistentDataContainer().has(keys.stats)) {
            PersistentDataContainer container = item.getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
            assert container != null;
            for (NamespacedKey key : container.getKeys()) {
                String stat = key.getKey();
                stats.put(stat, container.get(key, PersistentDataType.INTEGER));
            }
        }
        updateItemPrivate(item, customItem.name, customItem.quality, stats, layout);
    }
    private void updateItemPrivate (ItemStack item, String name, String quality, HashMap<String, Integer> stats, String layout) {
        String partType = item.getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"");
        switch (partType) {
            case "drill" :
                ItemMeta metaDrill = item.getItemMeta();
                Style styleDrill = Quality.valueOf(quality.toUpperCase()).getStyle();
                PersistentDataContainer[] parts = item.getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY, new PersistentDataContainer[]{});
                StringBuilder partNames = new StringBuilder(" ");
                for (PersistentDataContainer part : parts) {
                    String partID = part.getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "none");
                    if (partID.equals("none")) {
                        partNames.append("○");
                    }
                    else {
                        partNames.append("⏺");
                    }
                }
                Component partsListName = Component.text(name).style(styleDrill).append(Component.text(partNames.toString()).style(styleDrill));
                metaDrill.displayName(partsListName);
                item.setItemMeta(metaDrill);
                item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.stats, PersistentDataType.TAG_CONTAINER, getStatsAsPDC(item, stats)));
                updateLoreDrill(item, quality);
                break;
            case "engine" :
                ItemMeta metaEngine = item.getItemMeta();
                Style styleEngine = Quality.valueOf(quality.toUpperCase()).getStyle();
                metaEngine.displayName(Component.text(name).style(styleEngine));
                item.setItemMeta(metaEngine);
                item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY, getBonusAsPDC(item)));
                item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.stats, PersistentDataType.TAG_CONTAINER, getStatsAsPDC(item, stats)));
                updateLoreEngine(item, quality, layout);
                break;
            case "tank","drillhead","piston" :
                ItemMeta metaModul = item.getItemMeta();
                Style styleModul = Quality.valueOf(quality.toUpperCase()).getStyle();
                metaModul.displayName(Component.text(name).style(styleModul));
                item.setItemMeta(metaModul);
                item.editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.stats, PersistentDataType.TAG_CONTAINER, getStatsAsPDC(item, stats)));
                switch (partType) {
                    case "tank" :
                        updateLoreModul(item, quality, "tank");
                        break;
                    case "drillhead" :
                        updateLoreModul(item, quality, "drill head");
                        break;
                    case "piston" :
                        updateLorePiston(item, quality);
                        break;
                }
                break;
            case "" :
                ItemMeta metaItem = item.getItemMeta();
                Style styleItem = Quality.valueOf(quality.toUpperCase()).getStyle();
                metaItem.displayName(Component.text(name).style(styleItem));
                item.setItemMeta(metaItem);
                updateLoreItem(item, quality);
                break;
        }
    }

    private PersistentDataContainer getStatsAsPDC (ItemStack item, HashMap<String, Integer> stats) {
        String partType = item.getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"");
        PersistentDataContainer container = getEmptyPDC();
        switch (partType) {
            case "engine" :
                ArrayList<PersistentDataContainer> partsEngine = new ArrayList<>(Arrays.asList(item.getItemMeta().getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[] {})));
                if (customItemMap.containsKey(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id, PersistentDataType.STRING, ""))) {
                    stats = new HashMap<>(customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "")).getStats());
                }
                // get all parts and loop through all of them
                for (PersistentDataContainer partContainer : partsEngine) {
                    String part = partContainer.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"");
                    if (!(customItemMap.containsKey(part))) {
                        continue;
                    }
                    // get stat of part and loop through all
                    HashMap<String, Integer> statsPart = new HashMap<>();
                    for (NamespacedKey stat: partContainer.getOrDefault(keys.stats,PersistentDataType.TAG_CONTAINER, getEmptyPDC()).getKeys()) {
                        statsPart.put(stat.getKey(), partContainer.get(keys.stats,PersistentDataType.TAG_CONTAINER).getOrDefault(stat, PersistentDataType.INTEGER, 0));
                    }
                    if (statsPart.isEmpty()) {
                        continue;
                    }
                    for (String stat : statsPart.keySet()) {
                        // add stats together
                        if (stats.containsKey(stat)) {
                            stats.put(stat, stats.get(stat) + statsPart.get(stat));
                        }
                        else {
                            stats.put(stat, statsPart.get(stat));
                        }
                    }
                }
                for (String stat : stats.keySet()) {
                    container.set(new NamespacedKey("stat",stat), PersistentDataType.INTEGER,stats.get(stat));
                }
                return container;
            case "drill" :
                ArrayList<PersistentDataContainer> partsDrill = new ArrayList<>(Arrays.asList(item.getItemMeta().getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[] {})));
                partsDrill.add(item.getPersistentDataContainer().getOrDefault(keys.drillhead,PersistentDataType.TAG_CONTAINER,getEmptyPDC()));
                partsDrill.add(item.getPersistentDataContainer().getOrDefault(keys.engine,PersistentDataType.TAG_CONTAINER,getEmptyPDC()));
                partsDrill.add(item.getItemMeta().getPersistentDataContainer());
                if (customItemMap.containsKey(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
                    stats = new HashMap<>(customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "")).getStats());
                }
                // get all parts and loop through all of them
                for (PersistentDataContainer part : partsDrill) {
                    String partID = part.getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "");
                    if (!(customItemMap.containsKey(partID))) {
                        continue;
                    }
                    // get stat of part and loop through all
                    HashMap<String, Integer> statsPart = customItemMap.get(partID).getStats();
                    if (statsPart.isEmpty()) {
                        continue;
                    }
                    for (String stat : statsPart.keySet()) {
                        // add stats together
                        if (stats.containsKey(stat)) {
                            stats.put(stat, stats.get(stat)+ statsPart.get(stat));
                        }
                        else {
                            stats.put(stat, statsPart.get(stat));
                        }
                    }
                }
                // get engine stats
                if (item.getPersistentDataContainer().has(keys.engine,PersistentDataType.TAG_CONTAINER)) {
                    PersistentDataContainer enginestats = item.getItemMeta().getPersistentDataContainer().get(keys.engine, PersistentDataType.TAG_CONTAINER).getOrDefault(keys.stats, PersistentDataType.TAG_CONTAINER, getEmptyPDC());
                    for (NamespacedKey key : enginestats.getKeys()) {
                        String skey = key.getKey();
                        if (stats.containsKey(skey)) {
                            stats.put(skey, stats.get(skey) + enginestats.get(key,PersistentDataType.INTEGER));
                        }
                        else {
                            stats.put(skey, enginestats.get(key,PersistentDataType.INTEGER));
                        }
                    }
                }
                for (String stat : stats.keySet()) {
                    container.set(new NamespacedKey("stat",stat), PersistentDataType.INTEGER,stats.get(stat));
                }
                return container;
            case "tank","drillhead" :
                if (stats.isEmpty()) {
                    return container;
                }
                for (String stat : stats.keySet()) {
                    container.set(new NamespacedKey("stat",stat), PersistentDataType.INTEGER,stats.get(stat));
                }
                return container;
            case "piston" :
                if (customItemMap.containsKey(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id, PersistentDataType.STRING, ""))) {
                    stats = new HashMap<>(customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "")).getStats());
                }
                if (stats.isEmpty()) {
                    return container;
                }

                for (String stat : stats.keySet()) {
                    stats.put(stat, (stats.get(stat) * item.getPersistentDataContainer().getOrDefault(keys.efficiency,PersistentDataType.INTEGER, 100)) / 100);
                    container.set(new NamespacedKey("stat",stat), PersistentDataType.INTEGER,stats.get(stat));
                }
                return container;
        }





        return container;
    }
    private PersistentDataContainer[] getBonusAsPDC (ItemStack item) {
        ArrayList<PersistentDataContainer> bonusList = new ArrayList<>();
        HashMap<String, Integer> bonusSum = getBonusCount(item);
        for (String bonusID : bonusSum.keySet()) {
            if (bonusMap.getOrDefault(bonusID, emptyBonus).bonusThreshold <= bonusSum.get(bonusID)) {
                PersistentDataContainer bonus = getEmptyPDC();
                bonus.set(keys.custom_item_id, PersistentDataType.STRING, bonusID);
                bonusList.add(bonus);
            }
        }
        return bonusList.toArray( new PersistentDataContainer[0]);
    }
    public HashMap<String, Integer> getBonusCount (ItemStack item){
        HashMap<String, Integer> bonusSum = new HashMap<>();
        PersistentDataContainer[] pistons = item.getItemMeta().getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[] {});
        for (PersistentDataContainer piston : pistons) {
            if ("none".equals(piston.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
                continue;
            }
            for (PersistentDataContainer bonus : piston.get(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY)) {
                String bonusID = bonus.getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "");
                if (bonusSum.containsKey(bonusID)) {
                    bonusSum.put(bonusID, bonusSum.get(bonusID) + 1);
                } else {
                    bonusSum.put(bonusID, 1);
                }
            }
        }
        return bonusSum;
    }

    // update Item Lore
    private void updateLoreItem (ItemStack item, String quality) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        Component itemType = Component.text(quality.toUpperCase() + " MATERIAL").style(Quality.valueOf(quality.toUpperCase()).getStyle());
        lore.add(itemType);
        meta.lore(lore);
        item.setItemMeta(meta);
    }
    private void updateLoreDrill (ItemStack item, String quality) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        PersistentDataContainer stats = meta.getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
        NamespacedKey breaking_power = new NamespacedKey("stat","breaking_power");
        if (stats.has(breaking_power)) {
            lore.add(statName.get("breaking_power").append(Component.text(stats.getOrDefault(breaking_power, PersistentDataType.INTEGER, 0).toString()).style(statName.get("breaking_power").style())));
            lore.add(Component.text(""));
        }
        stats.remove(new NamespacedKey("stat","breaking_power"));
        for (NamespacedKey key : stats.getKeys()) {
            int statValue = stats.getOrDefault(key,PersistentDataType.INTEGER,0);
            Component statLine = statName.get(key.getKey()).append(Component.text(formatNumber(statValue)).style(statName.get(key.getKey()).style()));
            lore.add(statLine);
        }
        lore.add(Component.text(""));
        Component itemType = Component.text(quality.toUpperCase() + " DRILL").style(Quality.valueOf(quality.toUpperCase()).getStyle());
        lore.add(itemType);
        meta.lore(lore);
        item.setItemMeta(meta);

    }
    private void updateLoreEngine (ItemStack item, String quality, String layout) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        PersistentDataContainer stats = meta.getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
        PersistentDataContainer[] pistons = meta.getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[] {});
        if (Arrays.stream(pistons).allMatch(PersistentDataContainer -> "none".equals(PersistentDataContainer.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")))) {
            // add stats to lore
            for (NamespacedKey key : stats.getKeys()) {
                int statValue = stats.getOrDefault(key,PersistentDataType.INTEGER,0);
                Component statLine = statName.get(key.getKey()).append(Component.text(formatNumber(statValue)).style(statName.get(key.getKey()).style()));
                lore.add(statLine);
            }
            // add layout to lore
            lore.add(Component.text(""));
            lore.add(Component.text("Layout: ").style(statName.get("neutral").style()).append(Component.text(layout).style(statName.get("neutral").style())));
            // add piston: none
            lore.add(Component.text("Piston: ").style(statName.get("neutral").style().decoration(TextDecoration.BOLD, true)).append(Component.text("None").color(TextColor.color(0xFF0000)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.BOLD, false)));
        }
        else {
            // get sum list of all pistons
            HashMap<String, Integer> pistonsSum = new HashMap<>();
            HashMap<String, PersistentDataContainer> pistonExample = new HashMap<>();
            for (PersistentDataContainer piston : pistons) {
                if ("none".equals(piston.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
                    continue;
                }
                String id = piston.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"");
                if (pistonsSum.containsKey(piston.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
                    pistonsSum.put(id,pistonsSum.get(id) + 1);
                }
                else {
                    pistonsSum.put(id, 1);
                    pistonExample.put(id, piston);
                }
            }
            // add stats to lore
            for (NamespacedKey key : stats.getKeys()) {
                int statValue = stats.getOrDefault(key,PersistentDataType.INTEGER,0);
                TextComponent.Builder builder = Component.text();
                // get the final stat (all summed up)
                builder.append(statName.get(key.getKey()).append(Component.text(formatNumber(statValue)).style(statName.get(key.getKey()).style())));
                String id = item.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"");
                if (!(statValue == customItemMap.get(id).getStats().getOrDefault(key.getKey(), 0))) {
                    StringBuilder statSum = new StringBuilder(" (");
                    if (customItemMap.containsKey(id) && customItemMap.get(id).getStats().getOrDefault(key.getKey(), 0) > 0) {
                        statSum.append("+").append(customItemMap.get(id).getStats().getOrDefault(key.getKey(), 0));
                    }
                    for (String piston : pistonsSum.keySet()) {
                        int pistonStat = pistonExample.get(piston).getOrDefault(keys.stats,PersistentDataType.TAG_CONTAINER,getEmptyPDC()).getOrDefault(key,PersistentDataType.INTEGER,0);
                        if (pistonStat > 0) {
                            statSum.append(" +").append(pistonsSum.get(piston)).append("x").append(formatNumber(pistonStat));
                        }
                    }
                    statSum.append(")");
                    builder.append(Component.text(statSum.toString()).style(statName.get("neutral").style()));
                }
                lore.add(builder.build());
            }
            // add layout to lore
            lore.add(Component.text(""));
            lore.add(Component.text("Layout: ").style(statName.get("neutral").style()).append(Component.text(layout).style(statName.get("neutral").style())));
            // add pistons to lore
            lore.add(Component.text("Piston: ").style(statName.get("neutral").style().decoration(TextDecoration.BOLD, true)));
            for (String piston : pistonsSum.keySet()) {
                if (customItemMap.containsKey(piston)) {
                    lore.add(Component.text("   " + pistonsSum.get(piston) + "x ").style(statName.get("neutral").style()).append(Component.text(customItemMap.get(piston).name).style(qualityColor.get(customItemMap.get(piston).quality).style())));
                }
            }
        }
        if (item.getPersistentDataContainer().has(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY) && item.getPersistentDataContainer().getOrDefault(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY, new PersistentDataContainer[0]).length > 0) {
            lore.addAll(getBonusLore(item));
        }
        Component itemType = Component.text(quality.toUpperCase() + " ENGINE").style(qualityColor.getOrDefault(quality, qualityColor.get("rubbish")).style().decoration(TextDecoration.BOLD, true));
        lore.add(itemType);
        meta.lore(lore);
        item.setItemMeta(meta);

    }
    private void updateLoreModul (ItemStack item, String quality, String partType) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        PersistentDataContainer stats = meta.getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
        for (NamespacedKey key : stats.getKeys()) {
            int statValue = stats.getOrDefault(key,PersistentDataType.INTEGER,0);
            Component statLine = statName.get(key.getKey()).append(Component.text(statValue + "").style(statName.get(key.getKey()).style()));
            lore.add(statLine);
        }
        lore.add(Component.text(""));
        Component itemType = Component.text(quality.toUpperCase() + " " + partType.toUpperCase()).style(qualityColor.getOrDefault(quality, qualityColor.get("rubbish")).style().decoration(TextDecoration.BOLD, true));
        lore.add(itemType);
        meta.lore(lore);
        item.setItemMeta(meta);
    }
    private void updateLorePiston (ItemStack item, String quality) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        PersistentDataContainer stats = meta.getPersistentDataContainer().get(keys.stats,PersistentDataType.TAG_CONTAINER);
        for (NamespacedKey key : stats.getKeys()) {
            int statValue = stats.getOrDefault(key,PersistentDataType.INTEGER,0);
            Component statLine = statName.get(key.getKey()).append(Component.text(statValue + "").style(statName.get(key.getKey()).style()));
            lore.add(statLine);
        }
        lore.add(Component.text(""));
        lore.addAll(getBonusLore(item));
        Component itemType = Component.text(quality.toUpperCase() + " " + "PISTON").style(qualityColor.getOrDefault(quality, qualityColor.get("rubbish")).style().decoration(TextDecoration.BOLD, true));
        lore.add(itemType);
        meta.lore(lore);
        item.setItemMeta(meta);

    }

    // sub methods for lore
    private List<Component> getBonusLore (ItemStack item) {
        PersistentDataContainer[] bonuses = item.getPersistentDataContainer().get(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY);
        String partType = item.getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "");
        List<Component> bonusLore = new ArrayList<>();
        assert bonuses != null;
        for (PersistentDataContainer bonus : bonuses) {
            if (!(bonusMap.containsKey(bonus.getOrDefault(keys.custom_item_id, PersistentDataType.STRING, "")))) {
                continue;
            }
            bonusLore.add(Component.text(""));
            Bonus bonusFromMap = bonusMap.get(bonus.get(keys.custom_item_id, PersistentDataType.STRING));
            if (partType.equals("piston")) {
                Style style;
                int count = bonus.getOrDefault(keys.count, PersistentDataType.INTEGER, 0);
                int bonusThreshold = bonusFromMap.bonusThreshold;
                if (count >= bonusThreshold) {
                    style = qualityColor.get("pristine").style();
                }
                else {
                    style = statName.get("neutral_light").style();
                }
                bonusLore.add(Component.text("Set Bonus: " + bonusFromMap.title + " (" + count + "/" + bonusThreshold + ")").style(style));
            }
            else {
                bonusLore.add(Component.text("Set Bonus: " + bonusFromMap.title).style(qualityColor.get("pristine").style()));
            }
            bonusLore.addAll(bonusFromMap.description);
        }
        bonusLore.add(Component.text(""));
        return bonusLore;
    }
    private List<Component> getStatLore (ItemStack item) {
        List<Component> statLore = new ArrayList<>();
        return statLore;
    }
    private String formatNumber (int number) {
        return String.format("%,d", number);
    }
    public int[] getLayoutSlots (String layout) {
        return layoutMap.getOrDefault(layout, new Layout(new int[] {}, 0)).pistonSlots;
    }
    public int getLayoutEfficiency (String layout) {
        return layoutMap.getOrDefault(layout, new Layout(new int[] {}, 0)).efficiency;
    }
    //private String getFuelType (ItemStack drill) {return "";}
    public PersistentDataContainer getEmptyPDC() {
        return ctx.newPersistentDataContainer();
    }

}
