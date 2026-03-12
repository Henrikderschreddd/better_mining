package com.better_mining.listerners;

import com.better_mining.Keys;
import com.better_mining.inventories.EditDrillMenuHolder;
import com.better_mining.inventories.EditEngineMenuHolder;
import com.better_mining.item.CustomItemMap;
import com.better_mining.item.Drill;
import com.better_mining.item.Engine;
import com.better_mining.item.Slots;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class gui_events implements Listener {

    private final Plugin plugin;
    private final Keys keys;
    private final CustomItemMap customItemMap;
    private final Slots slots;

    public gui_events(Plugin plugin, Keys keys, CustomItemMap customItemMap, Slots slots) {
        this.plugin = plugin;
        this.keys = keys;
        this.customItemMap = customItemMap;ItemStack emptySlot = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.slots = slots;
    }

    @EventHandler
    public void invclick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        ItemStack item = event.getCurrentItem();

        if (event.getInventory().getHolder() instanceof EditEngineMenuHolder) {
            editEngine(event, item);
        }
        if (event.getInventory().getHolder() instanceof EditDrillMenuHolder) {
            editDrill(event, item);
        }
    }

    @EventHandler
    public void invclose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        if (!(inv.getItem(19) == null) && inv.getItem(19).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("engine") && inv.getHolder() instanceof EditEngineMenuHolder) {
            event.getPlayer().getInventory().addItem(event.getInventory().getItem(19));
        }
        if (!(inv.getItem(19) == null) && inv.getItem(19).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("drill") && inv.getHolder() instanceof EditDrillMenuHolder) {
            event.getPlayer().getInventory().addItem(event.getInventory().getItem(19));
        }
    }


    private void editDrill(InventoryClickEvent event, ItemStack item) {
        // cancel if imovable item
        if (item.getPersistentDataContainer().has(keys.imovable)) {
            event.setCancelled(true);
            return;
        }

        EditDrillMenuHolder holder = (EditDrillMenuHolder) event.getInventory().getHolder();
        Inventory inv = event.getInventory();

        //get type of part clicked
        String partType = item.getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"");

        boolean drillIsInSlot = false;
        if (inv.getItem(19) != null &&inv.getItem(19).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("drill")) {
            drillIsInSlot = true;
        }
        int clickedSlot = event.getSlot();
        boolean clickedMenuInv = event.getClickedInventory().getHolder() instanceof EditDrillMenuHolder;
        boolean clickedPlayerInv = !clickedMenuInv;
        // select item part type
        switch (partType) {
            case "drill" :
                // put drill in slot
                if (!drillIsInSlot) {
                    event.getWhoClicked().sendMessage("add drill");
                    putDrillIn(event, holder, item, inv);
                }
                // remove drill from slot
                if (clickedSlot == 19 && clickedMenuInv) {
                    event.getWhoClicked().sendMessage("remove drill");
                    removeDrill(event, holder, item, inv);
                }
                break;
            case "drillhead" :
                // put drillhead in slot
                if (drillIsInSlot && clickedPlayerInv) {
                    event.getWhoClicked().sendMessage("add drillhead");
                    putDrillheadIn(event, item, inv);

                }
                // remove drillhead from slot
                if (clickedMenuInv) {
                    event.getWhoClicked().sendMessage("remove drillhead");
                    removeDrillhead(event, item, inv);

                }
                updateDrill(holder, inv);
                break;
            case "engine" :
                // put engine in slot
                if (drillIsInSlot && clickedPlayerInv) {
                    event.getWhoClicked().sendMessage("add engine");
                    putEngineInDrill(event, item, inv);

                }
                //remove engine from slot
                if (clickedMenuInv) {
                    event.getWhoClicked().sendMessage("remove engine");
                    removeEngineDrill(event, item, inv);

                }
                updateDrill(holder, inv);
                break;
            case "tank","fuelcatalyst" :
                // put modul in slot
                if (drillIsInSlot && clickedPlayerInv) {
                    event.getWhoClicked().sendMessage("add modul");
                    putModulIn(event, holder, item, inv);
                }
                // remove modul from slot
                if (clickedMenuInv) {
                    event.getWhoClicked().sendMessage("remove modul");
                    removeModul(event, holder, item, inv);
                }
                updateDrill(holder, inv);
                break;
        }

    }

    private void putDrillIn (InventoryClickEvent event, EditDrillMenuHolder holder, ItemStack item, Inventory inv) {
        PersistentDataContainer itemPDC = item.getItemMeta().getPersistentDataContainer();
        holder.parts = new ArrayList<>(Arrays.asList(itemPDC.getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY, new PersistentDataContainer[] {})));
        inv.setItem(19, item);
        event.setCancelled(true);
        event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
        // get modulslots
        int modulSlots = ((Drill) customItemMap.customItemMap.get(itemPDC.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))).modulSlots;
        holder.modulSlots.clear();
        // get engine
        PersistentDataContainer engine = itemPDC.get(keys.engine,PersistentDataType.TAG_CONTAINER);
        if (!(itemPDC.getOrDefault(keys.engine,PersistentDataType.TAG_CONTAINER, customItemMap.getEmptyPDC()).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"none").equals("none"))) {
            ItemStack engineItem = customItemMap.customItemMap.get(engine.get(keys.custom_item_id,PersistentDataType.STRING)).getItem();
            ItemMeta meta = engineItem.getItemMeta();
            engine.copyTo(meta.getPersistentDataContainer(), true);
            engineItem.setItemMeta(meta);
            customItemMap.updateItem(engineItem);
            inv.setItem(13, engineItem);
        }
        else {
            inv.setItem(13, slots.emptySlotEngine);
        }
        // get drill head
        PersistentDataContainer drillhead = itemPDC.getOrDefault(keys.drillhead,PersistentDataType.TAG_CONTAINER, customItemMap.getEmptyPDC());
        if (customItemMap.customItemMap.containsKey(drillhead.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
            ItemStack drillheadItem = customItemMap.customItemMap.get(drillhead.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")).getItem();
            ItemMeta meta = drillheadItem.getItemMeta();
            drillhead.copyTo(meta.getPersistentDataContainer(), true);
            drillheadItem.setItemMeta(meta);
            customItemMap.updateItem(drillheadItem);
            inv.setItem(12, drillheadItem);
        }
        else {
            inv.setItem(12, slots.emptySlotDrillhead);
        }
        //get moduls
        for (int i = 0; i <modulSlots && i < 10; i++) {
            int j = -1;
            if (i < 5) {
                j = i + 30;
            }
            if (i >= 5) {
                j = i + 34;
            }
            holder.addModulSlot(j);
            String id = holder.parts.get(i).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"none");
            if(customItemMap.customItemMap.containsKey(id)) {
                ItemStack modulItem = customItemMap.customItemMap.get(id).getItem();
                ItemMeta meta = modulItem.getItemMeta();
                holder.parts.get(i).copyTo(meta.getPersistentDataContainer(), true);
                modulItem.setItemMeta(meta);
                customItemMap.updateItem(modulItem);
                inv.setItem(j, modulItem);
            }
            else {
                inv.setItem(j, slots.emptySlotModul);
            }
        }

    }
    private void removeDrill (InventoryClickEvent event, EditDrillMenuHolder holder, ItemStack item, Inventory inv) {
        updateDrill(holder, inv);
        event.setCancelled(true);
        event.getWhoClicked().getInventory().addItem(item);
        inv.setItem(12, slots.unusedSlot);
        inv.setItem(13, slots.unusedSlot);
        for (int i = 30 ; i < 44; i++) {
            inv.setItem(i, slots.unusedSlot);
        }
        inv.setItem(19, slots.emptySlotDrill);
        holder.parts.clear();
        holder.modulSlots.clear();
    }
    private void putDrillheadIn (InventoryClickEvent event, ItemStack item, Inventory inv) {
        if (inv.getItem(12).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("drillhead")) {
            event.getWhoClicked().sendMessage("didnt work");
            return;
        }
        inv.setItem(12, item);
        event.setCancelled(true);
        event.getWhoClicked().getInventory().setItem(event.getSlot(),new ItemStack(Material.AIR));
    }
    private void removeDrillhead (InventoryClickEvent event, ItemStack item, Inventory inv) {
        event.setCancelled(true);
        event.getWhoClicked().getInventory().addItem(customItemMap.customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")).getItem());
        inv.setItem(12, slots.emptySlotDrillhead);
    }
    private void putEngineInDrill (InventoryClickEvent event, ItemStack item, Inventory inv) {
        if (inv.getItem(13).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("engine")) {
            event.getWhoClicked().sendMessage("didnt work");
            return;
        }
        inv.setItem(13, item);
        event.setCancelled(true);
        event.getWhoClicked().getInventory().setItem(event.getSlot(),new ItemStack(Material.AIR));
    }
    private void removeEngineDrill (InventoryClickEvent event, ItemStack item, Inventory inv) {
        event.setCancelled(true);
        customItemMap.updateItem(item);
        event.getWhoClicked().getInventory().addItem(item);
        inv.setItem(13, slots.emptySlotEngine);
    }
    private void putModulIn (InventoryClickEvent event, EditDrillMenuHolder holder, ItemStack item, Inventory inv) {
        for (int j = 0; j < holder.modulSlots.size(); j++) {
            if (holder.parts.get(j).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"none").equals("none")) {
                holder.parts.set(j, item.getItemMeta().getPersistentDataContainer());
                inv.setItem(holder.modulSlots.get(j), item);
                event.setCancelled(true);
                event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }
    private void removeModul (InventoryClickEvent event, EditDrillMenuHolder holder, ItemStack item, Inventory inv) {
        PersistentDataContainer none = customItemMap.getEmptyPDC();
        none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
        holder.parts.set(holder.modulSlots.indexOf(event.getSlot()), none);
        event.getWhoClicked().getInventory().addItem(customItemMap.customItemMap.get(item.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")).getItem());
        event.setCancelled(true);
        inv.setItem(event.getSlot(), slots.emptySlotModul);
    }

    private void updateDrill (EditDrillMenuHolder holder, Inventory inv) {
        PersistentDataContainer none = customItemMap.getEmptyPDC();
        none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
        //set parts
        inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,holder.parts.toArray(new PersistentDataContainer[0])));
        //set engine
        if (inv.getItem(13).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("engine")) {
            PersistentDataContainer container = Objects.requireNonNull(inv.getItem(13)).getItemMeta().getPersistentDataContainer();
            inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.engine,PersistentDataType.TAG_CONTAINER,container));
        }
        else {
            inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.engine,PersistentDataType.TAG_CONTAINER,none));
        }
        //set drillhead
        if (inv.getItem(12).getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("drillhead")) {
            inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.drillhead,PersistentDataType.TAG_CONTAINER,inv.getItem(12).getItemMeta().getPersistentDataContainer()));
        }
        else {
            inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.drillhead,PersistentDataType.TAG_CONTAINER,none));
        }
        customItemMap.updateItem(inv.getItem(19));
    }




    private void editEngine(InventoryClickEvent event, ItemStack item) {

        Inventory inv = event.getInventory();
        EditEngineMenuHolder holder = (EditEngineMenuHolder) inv.getHolder();
        ItemStack engine = inv.getItem(19);

        String partType = item.getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"");
        //click type
        boolean isShiftClick = event.getClick().isShiftClick();
        boolean isLeftClick = event.getClick().isLeftClick();
        boolean isNumClick = event.getClick().isKeyboardClick();
        // is engine installed
        boolean engineInSlot = engine.getPersistentDataContainer().getOrDefault(keys.partType,PersistentDataType.STRING,"").equals("engine");
        // clicked Inv
        boolean clickedMenu = event.getClickedInventory().getHolder() instanceof EditEngineMenuHolder;
        boolean clickedPlayerInv = !clickedMenu;
        Player player = (Player) event.getWhoClicked();


        if (isShiftClick) {
            if (item.getPersistentDataContainer().has(keys.imovable)) {
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            switch (partType) {
                case "engine" :
                    if (!engineInSlot && clickedPlayerInv) {
                        // put engine in slot
                        putEngineIn(holder, item, inv);
                        event.getWhoClicked().getInventory().setItem(event.getSlot(),slots.air);
                    } else if (engineInSlot && clickedMenu) {
                        // remove engine from slot
                        event.getWhoClicked().getInventory().addItem(removeEngine(event, holder, inv));
                    }
                    break;
                case "piston" :
                    if (engineInSlot && clickedPlayerInv) {
                        // put piston in first slot
                        int slot = getNextEmptyPistonFromHolder(holder);
                        if (slot == -1) {
                            break;
                        }
                        setPistonInSlot(holder, item, inv, slot);
                        event.getWhoClicked().getInventory().setItem(event.getSlot(), slots.air);
                        updateEngine(holder, inv);
                    } else if (clickedMenu) {
                        // remove piston from slot
                        event.getWhoClicked().getInventory().addItem(removePiston(holder, inv, holder.pistonSlots.indexOf(event.getSlot())));
                        updateEngine(holder, inv);
                    }
                    break;
            }

        }
        else if (isLeftClick) {
            if (clickedPlayerInv) {
                return;
            }
            event.setCancelled(true);
            switch (partType) {
                case "engine" :
                    if (event.getCursor().isEmpty() || event.getCursor().getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("engine")) {
                        event.setCursor(setEngineInSlot(holder, event.getCursor(), inv));
                    }
                    break;
                case "piston" :
                    if (event.getCursor().isEmpty() || event.getCursor().getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("piston")) {
                        event.setCursor(setPistonInSlot(holder, event.getCursor(), inv, holder.pistonSlots.indexOf(event.getSlot())));
                        updateEngine(holder, inv);
                    }
                    break;
                case "" :
                    if (event.getSlot() == 19 && event.getCursor().getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("engine")) {
                        event.setCursor(setEngineInSlot(holder, event.getCursor(), inv));

                        updateEngine(holder, inv);
                    } else if (engineInSlot && holder.pistonSlots.contains(event.getSlot()) && event.getCursor().getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("piston")) {
                        event.setCursor(setPistonInSlot(holder, event.getCursor(), inv, holder.pistonSlots.indexOf(event.getSlot())));
                        updateEngine(holder, inv);
                    }
                    break;
            }
        }
        else if (isNumClick) {
                if (clickedPlayerInv) {
                    return;
                }
                event.setCancelled(true);
                int slotSource = event.getHotbarButton();
                int slotTarget = event.getSlot();
                ItemStack itemSource = player.getInventory().getItem(slotSource);
                if (itemSource == null) {
                    itemSource = slots.air;
                }
            switch (partType) {
                case "engine" :
                    if (itemSource.isEmpty() || itemSource.getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("engine")) {
                        player.getInventory().setItem(slotSource, setEngineInSlot(holder, itemSource, inv));
                    }
                    break;
                case "piston" :
                    if (itemSource.isEmpty() || itemSource.getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("piston")) {
                        player.getInventory().setItem(slotSource,setPistonInSlot(holder, itemSource, inv, holder.pistonSlots.indexOf(slotTarget)));
                        updateEngine(holder, inv);
                    }
                    break;
                case "" :
                    if (slotTarget == 19 && itemSource.getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("engine")) {
                        player.getInventory().setItem(slotSource, setEngineInSlot(holder, itemSource, inv));
                        updateEngine(holder, inv);
                    } else if (engineInSlot && holder.pistonSlots.contains(slotTarget) && itemSource.getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING, "").equals("piston")) {
                        player.getInventory().setItem(slotSource,setPistonInSlot(holder, itemSource, inv, holder.pistonSlots.indexOf(slotTarget)));
                        updateEngine(holder, inv);
                    }
                    break;
            }
        }
        else {
                if (clickedMenu) {
                    event.setCancelled(true);
                }
        }


    }

    private ItemStack setEngineInSlot (EditEngineMenuHolder holder,ItemStack item, Inventory inv) {
        ItemStack temp = null;
        if (!(inv.getItem(19).getPersistentDataContainer().has(keys.imovable))) {
            temp = inv.getItem(19);
            for (int slot : holder.pistonSlots) {
                inv.setItem(slot, slots.unusedSlot);
            }
        }
        if (item.isEmpty() ) {
            holder.pistonSlots.clear();
            holder.parts.clear();
            inv.setItem(19, slots.emptySlotEngine);
            return temp;
        }
        // get parts from engine
        holder.parts = new ArrayList<>(Arrays.asList(item.getItemMeta().getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[] {})));
        // copy engine to slot
        inv.setItem(19, item);
        //get engine layout
        String layout =  ((Engine) customItemMap.customItemMap.get(item.getPersistentDataContainer().get(keys.custom_item_id,PersistentDataType.STRING))).layout;
        holder.setPistonSlots(customItemMap.getLayoutSlots(layout));

        // set pistons in slots
        for (int j = 0; j < holder.pistonSlots.size(); j++) {
            if (holder.parts.size() <= j) {
                PersistentDataContainer none = customItemMap.getEmptyPDC();
                none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
                holder.parts.add(j, none);
            }
            String id = holder.parts.get(j).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"");

            if (customItemMap.customItemMap.containsKey(id)) {
                ItemStack piston = customItemMap.customItemMap.get(id).getItem();
                int finalJ = j;
                ItemMeta meta = piston.getItemMeta();
                holder.parts.get(finalJ).copyTo(meta.getPersistentDataContainer(), true);
                piston.setItemMeta(meta);
                customItemMap.updateItem(piston);
                inv.setItem(holder.pistonSlots.get(j), piston);
            } else {
                inv.setItem(holder.pistonSlots.get(j), slots.emptySlotPiston);
                PersistentDataContainer none = customItemMap.getEmptyPDC();
                none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
                holder.parts.set(j, none);
            }
        }
        return temp;
    }

    private int getNextEmptyPistonFromHolder (EditEngineMenuHolder holder) {
        for (int j = 0; j < holder.pistonSlots.size(); j++) {
            if (holder.parts.get(j).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"").equals("none")) {
                return j;
            }
        }
        return -1;
    }
    private ItemStack removePiston (EditEngineMenuHolder holder, Inventory inv, int slot) {
        ItemStack item = setPistonInSlot(holder, slots.emptySlotPiston, inv, slot);
        // set pdc to none
        PersistentDataContainer none = customItemMap.getEmptyPDC();
        none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
        holder.parts.set(slot, none);
        return item;
    }
    private ItemStack setPistonInSlot (EditEngineMenuHolder holder, ItemStack item,Inventory inv, int slot) {
        ItemStack temp = null;
        if (!(inv.getItem(holder.pistonSlots.get(slot)).getPersistentDataContainer().has(keys.imovable))) {
            temp = customItemMap.customItemMap.get(inv.getItem(holder.pistonSlots.get(slot)).getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")).getItem();
        }
        if (item.isEmpty()) {
            inv.setItem(holder.pistonSlots.get(slot), slots.emptySlotPiston);
            PersistentDataContainer none = customItemMap.getEmptyPDC();
            none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
            holder.parts.set(slot, none);
        }
        else {
            inv.setItem(holder.pistonSlots.get(slot), item);
            holder.parts.set(slot, item.getItemMeta().getPersistentDataContainer());
        }
        return temp;
    }

    private void putEngineIn (EditEngineMenuHolder holder, ItemStack item, Inventory inv) {
        if (inv.getItem(19).getPersistentDataContainer().getOrDefault(keys.partType, PersistentDataType.STRING,"").equals("engine")) {
            return;
        }
        // get parts from engine
        holder.parts = new ArrayList<>(Arrays.asList(item.getItemMeta().getPersistentDataContainer().getOrDefault(keys.parts,PersistentDataType.TAG_CONTAINER_ARRAY,new PersistentDataContainer[0])));
        // copy engine to slot
        inv.setItem(19, item);
        //get engine layout
        String layout =  ((Engine) customItemMap.customItemMap.get(item.getPersistentDataContainer().get(keys.custom_item_id,PersistentDataType.STRING))).layout;
        holder.setPistonSlots(customItemMap.getLayoutSlots(layout));

        // set pistons in slots
        for (int j = 0; j < holder.pistonSlots.size(); j++) {
            if (holder.parts.size() <= j) {
                PersistentDataContainer none = customItemMap.getEmptyPDC();
                none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
                holder.parts.add(j, none);
            }
            String id = holder.parts.get(j).getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"");

            if (customItemMap.customItemMap.containsKey(id)) {
                ItemStack piston = customItemMap.customItemMap.get(id).getItem();
                int finalJ = j;
                ItemMeta meta = piston.getItemMeta();
                holder.parts.get(finalJ).copyTo(meta.getPersistentDataContainer(), true);
                piston.setItemMeta(meta);
                customItemMap.updateItem(piston);
                inv.setItem(holder.pistonSlots.get(j), piston);
            } else {
                inv.setItem(holder.pistonSlots.get(j), slots.emptySlotPiston);
                PersistentDataContainer none = customItemMap.getEmptyPDC();
                none.set(keys.custom_item_id,PersistentDataType.STRING,"none");
                holder.parts.set(j, none);
            }
        }
    }
    private ItemStack removeEngine (InventoryClickEvent event, EditEngineMenuHolder holder, Inventory inv) {
        event.setCancelled(true);
        updateEngine(holder, inv);
        ItemStack temp = inv.getItem(19).clone();
        inv.setItem(19, slots.emptySlotEngine);
        for (int j : holder.pistonSlots) {
            inv.setItem(j, slots.unusedSlot);
        }
        holder.parts.clear();
        holder.pistonSlots.clear();
        return temp;
    }


    private void updateEngine (EditEngineMenuHolder holder, Inventory inv) {
        inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts, PersistentDataType.TAG_CONTAINER_ARRAY, holder.parts.toArray(new PersistentDataContainer[0])));
        customItemMap.updateItem(inv.getItem(19));
        updatePiston(holder, inv);
        inv.getItem(19).editPersistentDataContainer(PersistentDataContainer -> PersistentDataContainer.set(keys.parts, PersistentDataType.TAG_CONTAINER_ARRAY, holder.parts.toArray(new PersistentDataContainer[0])));
        customItemMap.updateItem(inv.getItem(19));
    }
    private void updatePiston (EditEngineMenuHolder holder, Inventory inv) {
        HashMap<String, Integer> bonusCount = customItemMap.getBonusCount(inv.getItem(19));
        for (int j = 0; j < holder.pistonSlots.size(); j++) {
            ItemStack piston = inv.getItem(holder.pistonSlots.get(j));
            if (!(customItemMap.customItemMap.containsKey(piston.getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,"")))) {
                continue;
            }
            PersistentDataContainer container = holder.parts.get(j);
            container.set(keys.efficiency, PersistentDataType.INTEGER, customItemMap.getLayoutEfficiency(((Engine) customItemMap.customItemMap.get(inv.getItem(19).getPersistentDataContainer().getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))).layout));
            PersistentDataContainer[] bonuses = container.getOrDefault(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY, new PersistentDataContainer[0]);
            for (PersistentDataContainer bonus : bonuses) {
                if (bonusCount.containsKey(bonus.getOrDefault(keys.custom_item_id,PersistentDataType.STRING,""))) {
                    bonus.set(keys.count, PersistentDataType.INTEGER, bonusCount.get(bonus.get(keys.custom_item_id,PersistentDataType.STRING)));

                }
            }
            container.set(keys.bonus, PersistentDataType.TAG_CONTAINER_ARRAY, bonuses);
            ItemMeta meta = piston.getItemMeta();
            container.copyTo(meta.getPersistentDataContainer(), true);
            piston.setItemMeta(meta);
            customItemMap.updateItem(piston);
            holder.parts.set(j, piston.getItemMeta().getPersistentDataContainer());
            inv.setItem(holder.pistonSlots.get(j), piston);
        }
    }
}
