package com.better_mining.block;

import com.better_mining.item.CustomItemMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlockLootTable {
    private final List<BlockLoot> lootTable;
    private final CustomItemMap customItemMap;
    public BlockLootTable (String filePath, CustomItemMap customItemMap) {
        this.lootTable = new ArrayList<>();
        this.customItemMap = customItemMap;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line =reader.readLine()) != null) {
                builder.append(line);
            }
            String josnString = builder.toString();
            JSONObject jsonObject = new JSONObject(josnString);
            JSONArray lootArray = jsonObject.getJSONArray("loot");
            for (int i = 0; i < lootArray.length(); i++) {
                JSONObject lootInArray = lootArray.getJSONObject(i);
                BlockLoot loot = new BlockLoot(
                        lootInArray.getString("id"),
                        lootInArray.getInt("weight"),
                        lootInArray.getInt("countMin"),
                        lootInArray.getInt("countMax"));
                if (!(customItemMap.customItemMap.containsKey(loot.getId()))) {
                    continue;
                }
                lootTable.add(loot);
            }
        }
        catch (IOException ignored) {

        }
    }
    public ItemStack getLoot (int fortune, int luck) {
        if (luck == -100) {
            return null;
        }
        HashMap<BlockLoot, Integer> lootWithLuckMap = new HashMap<>();
        int sum = 0;
        for (BlockLoot blockLoot : lootTable) {
            int weight = (int)Math.pow(blockLoot.getWeight(), ((double) 100 /(100 + luck)));
            sum += weight;
            lootWithLuckMap.put(blockLoot, weight);
        }
        int random = ThreadLocalRandom.current().nextInt(sum + 1);
        System.out.println(sum + " " + random);
        for (BlockLoot blockLoot : lootTable) {
            sum -= lootWithLuckMap.get(blockLoot);
            if (random >= sum) {
                int amount = ThreadLocalRandom.current().nextInt(blockLoot.getCountMin(), blockLoot.getCountMax() + 1);
                amount = amount * (1 + fortune/100 + (ThreadLocalRandom.current().nextInt(100) + fortune - 100 * (fortune / 100)) / 100);
                ItemStack item = customItemMap.customItemMap.get(blockLoot.getId()).getItem();
                item.setAmount(amount);
                return item;
            }
        }
        return new ItemStack(Material.AIR);
    }
}
