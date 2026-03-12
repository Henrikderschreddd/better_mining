package com.better_mining.listerners;

import com.better_mining.Keys;
import com.better_mining.block.*;
import com.better_mining.item.CustomItemMap;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class mining implements Listener {

    private final Plugin plugin;
    private final Keys keys;
    private final CustomItemMap customItemMap;
    private HashMap<BlockPos, UUID> blockOccupationMap;
    private HashMap<BlockPos, MultiStageBlockLocal> blockMap;
    private final HashMap<String, MultiStageBlock> multiStageBlockMap;
    private HashMap<String, BlockLootTable> lootTableMap;
    private HashMap<UUID, BukkitTask> mineFullBlock;
    private HashMap<UUID, BukkitTask> nextMiningStage;
    private HashMap<UUID, List<BlockPos>> blocksMined;

    public mining (Plugin plugin, Keys keys, CustomItemMap customItemMap) {
        this.plugin = plugin;
        this.keys = keys;
        this.customItemMap = customItemMap;
        this.blockOccupationMap = new HashMap<>();
        this.blockMap = new HashMap<>();
        this.multiStageBlockMap = new HashMap<>();
        this.lootTableMap = new HashMap<>();
        this.mineFullBlock = new HashMap<>();
        lootTableMap.put("test_loot_table", new BlockLootTable("C:\\Users\\johan\\Desktop\\server\\mining\\loottables\\test.json\\", customItemMap));
        lootTableMap.put("empty_loot_table", new BlockLootTable("C:\\Users\\johan\\Desktop\\server\\mining\\loottables\\none.json\\", customItemMap));
        HashMap<Integer, CustomBlock> blocks = new HashMap<>();
        blocks.put(0, new CustomBlock("copper_empty", Bukkit.createBlockData(Material.BEDROCK), lootTableMap.get("empty_loot_table"), 1000, 5));
        blocks.put(1, new CustomBlock("copper_stage_1", Bukkit.createBlockData(Material.WAXED_OXIDIZED_CUT_COPPER), lootTableMap.get("test_loot_table"), 1000, 5));
        blocks.put(2, new CustomBlock("copper_stage_2", Bukkit.createBlockData(Material.WAXED_WEATHERED_CUT_COPPER), lootTableMap.get("test_loot_table"), 1000, 5));
        blocks.put(3, new CustomBlock("copper_stage_3", Bukkit.createBlockData(Material.WAXED_EXPOSED_CUT_COPPER), lootTableMap.get("test_loot_table"), 1000, 5));
        blocks.put(4, new CustomBlock("copper_stage_4", Bukkit.createBlockData(Material.WAXED_CUT_COPPER), lootTableMap.get("test_loot_table"), 1000, 5));
        multiStageBlockMap.put("test_block", new MultiStageBlock("test_block",blocks));
    }

    @EventHandler
    public void hitBlock (BlockDamageEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("hit block");
        Block block = event.getBlock();
        getSpreadBlocks(block, 0);
        if (player.getInventory().getItem(0) == null) {
            blockMap.put(new BlockPos(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()), new MultiStageBlockLocal(multiStageBlockMap.get("test_block")));
        }
        else {
            BlockPos pos = new BlockPos(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
            if (blockMap.containsKey(pos)) {
                if ((blockOccupationMap.containsKey(pos) && !(blockOccupationMap.get(pos).equals(player.getUniqueId())))) {
                    return;
                }
                BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin,() -> {
                    MultiStageBlockLocal multiStageBlockLocal = blockMap.get(pos);
                    player.getInventory().addItem(multiStageBlockMap.get(multiStageBlockLocal.getId()).getCustomBlock(multiStageBlockLocal.getStage()).getLootTable().getLoot(0, 0));
                    multiStageBlockLocal.removeStage();
                    event.getBlock().setBlockData(multiStageBlockMap.get(multiStageBlockLocal.getId()).getCustomBlock(multiStageBlockLocal.getStage()).getBlockData());
                    mineFullBlock.remove(player.getUniqueId());
                }, multiStageBlockMap.get(blockMap.get(pos).getId()).getCustomBlock(blockMap.get(pos).getStage()).getToughness() / 50);
                mineFullBlock.put( player.getUniqueId(), task);

            }
        }
    }
    @EventHandler
    public void onMine (PlayerArmSwingEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("mine block");
        player.getUniqueId();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPhysics (BlockPhysicsEvent event) {
        if (event.getBlock().getBlockData() instanceof NoteBlock) {
            event.setCancelled(true);
            event.getBlock().setBlockData(event.getBlock().getBlockData(),false);
            event.getBlock().getState().update(false, true);
            Bukkit.broadcastMessage("noteblock");
        }
    }

    // --- Prevent players from changing pitch / power ---
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            Action action = event.getAction();

            // Allow mining (left click)
            if (action == Action.LEFT_CLICK_BLOCK) {
                return;
            }
            event.setCancelled(true);

            // Block all other interactions (right-click, etc.)
        }
    }

    // --- Force the note block to the desired state ---
    private void forceNoteBlockData(Block block) {
        if (block.getType() != Material.NOTE_BLOCK) return;

        NoteBlock nb = (NoteBlock) block.getBlockData();

        // Set your locked instrument, note, and power here
        //nb.setInstrument(Instrument.PIANO);
        //nb.setNote(org.bukkit.Note.natural(1, org.bukkit.Note.Tone.C));
        //nb.setPowered(false);

        // Apply changes to the world
        block.setBlockData(nb, false); // false = do not apply physics, prevents recursion
    }

    private List<BlockPos> getSpreadBlocks (Block block, int spread) {
        List<BlockPos> spreadPos = new ArrayList<>();
        if (!(block.getBlockData() instanceof NoteBlock)) {
            System.out.println("no noteblock");
            return null;
        }
        NoteBlock noteBlock = (NoteBlock) block.getBlockData();
        Instrument instrument = noteBlock.getInstrument();
        Note note = noteBlock.getNote();
        boolean powered = noteBlock.isPowered();
        //HashMap<>
        return spreadPos;
    }

}
