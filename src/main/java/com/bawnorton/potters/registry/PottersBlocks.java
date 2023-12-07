package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.BottomlessDecoratedPotBlock;
import com.bawnorton.potters.block.FiniteDecoratedPotBlock;
import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.config.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PottersBlocks {
    public static final FiniteDecoratedPotBlock REDSTONE_DECORATED_POT;
    public static final FiniteDecoratedPotBlock COPPER_DECORATED_POT;
    public static final FiniteDecoratedPotBlock IRON_DECORATED_POT;
    public static final FiniteDecoratedPotBlock LAPIS_DECORATED_POT;
    public static final FiniteDecoratedPotBlock GOLD_DECORATED_POT;
    public static final FiniteDecoratedPotBlock AMETHYST_DECORATED_POT;
    public static final FiniteDecoratedPotBlock DIAMOND_DECORATED_POT;
    public static final FiniteDecoratedPotBlock EMERALD_DECORATED_POT;
    public static final FiniteDecoratedPotBlock NETHERITE_DECORATED_POT;
    public static final BottomlessDecoratedPotBlock BOTTOMLESS_DECORATED_POT;
    private static final List<PottersDecoratedPotBlockBase> ALL;
    private static final List<FiniteDecoratedPotBlock> FINITE_DECORATED_POTS;
    private static final Map<Block, String> BLOCK_TO_NAME;

    static {
        ALL = new ArrayList<>();
        FINITE_DECORATED_POTS = new ArrayList<>();
        BLOCK_TO_NAME = new HashMap<>();
        COPPER_DECORATED_POT = register("copper", new FiniteDecoratedPotBlock(() -> Items.COPPER_INGOT, () -> Items.DECORATED_POT, () -> ConfigManager.getConfig().copperStackCount));
        LAPIS_DECORATED_POT = register("lapis", new FiniteDecoratedPotBlock(() -> Items.LAPIS_LAZULI, COPPER_DECORATED_POT::asItem, () -> ConfigManager.getConfig().lapisStackCount));
        REDSTONE_DECORATED_POT = register("redstone", new FiniteDecoratedPotBlock(() -> Items.REDSTONE, LAPIS_DECORATED_POT::asItem, () -> ConfigManager.getConfig().redstoneStackCount));
        EMERALD_DECORATED_POT = register("emerald", new FiniteDecoratedPotBlock(() -> Items.EMERALD, REDSTONE_DECORATED_POT::asItem, () -> ConfigManager.getConfig().emeraldStackCount));
        AMETHYST_DECORATED_POT = register("amethyst", new FiniteDecoratedPotBlock(() -> Items.AMETHYST_SHARD, EMERALD_DECORATED_POT::asItem, () -> ConfigManager.getConfig().amethystStackCount));
        IRON_DECORATED_POT = register("iron", new FiniteDecoratedPotBlock(() -> Items.IRON_INGOT, () -> Items.DECORATED_POT, () -> ConfigManager.getConfig().ironStackCount));
        GOLD_DECORATED_POT = register("gold", new FiniteDecoratedPotBlock(() -> Items.GOLD_INGOT, IRON_DECORATED_POT::asItem, () -> ConfigManager.getConfig().goldStackCount));
        DIAMOND_DECORATED_POT = register("diamond", new FiniteDecoratedPotBlock(() -> Items.DIAMOND, GOLD_DECORATED_POT::asItem, () -> ConfigManager.getConfig().diamondStackCount));
        NETHERITE_DECORATED_POT = register("netherite", new FiniteDecoratedPotBlock(() -> Items.NETHERITE_INGOT, DIAMOND_DECORATED_POT::asItem, () -> ConfigManager.getConfig().netheriteStackCount, 1));
        BOTTOMLESS_DECORATED_POT = register("bottomless", new BottomlessDecoratedPotBlock(() -> Items.ENDER_PEARL));
        /* Progression:
         * copper => lapis => redstone => emerald => amethyst
         * iron => gold => diamond => netherite => bottomless
         * normal + 4 copper => copper
         * copper + 4 lapis => lapis
         * lapis + 4 redstone => redstone
         * redstone + 4 emerald => emerald
         * emerald + 4 amethyst => amethyst
         * normal + 4 iron => iron
         * iron + 4 gold => gold
         * gold + 4 diamond => diamond
         * diamond + netherite => netherite
         * netherite + 2 ender pearl + nether star/dragon egg => bottomless
         * copper + 2 iron => iron
         * lapis + 2 gold => gold
         * redstone + 2 diamond => diamond
         * emerald + netherite => netherite
         * amethyst + 4 ender pearl + nether star/dragon egg => bottomless
        */
        /* Costs:
         * Copper: 4 copper ingot + 4 bricks
         * Lapis: 4 lapis lazuli + 4 copper ingot + 4 bricks
         * Redstone: 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         * Emerald: 4 emerald + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         * Amethyst: 4 amethyst + 4 emerald + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         * Iron: 4 iron ingot + 4 bricks
         *      2 iron ingot + 4 copper ingot + 4 bricks
         * Gold: 4 gold ingot + 4 iron ingot + 4 bricks
         *      2 gold ingot + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      4 gold ingot + 2 iron ingot + 4 copper ingot + 4 bricks
         * Diamond: 4 diamond + 4 gold ingot + 4 iron ingot + 4 bricks
         *      2 diamond + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      4 diamond + 2 gold ingot + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      4 diamond + 4 gold ingot + 2 iron ingot + 4 copper ingot + 4 bricks
         * Netherite: netherite ingot + 4 diamond + 4 gold ingot + 4 iron ingot + 4 bricks
         *      nehterite ingot + 4 emerald + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      netherite ingot + 2 diamond + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      netherite ingot + 4 diamond + 2 gold ingot + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      netherite ingot + 4 diamond + 4 gold ingot + 2 iron ingot + 4 copper ingot + 4 bricks
         * Bottomless: 2 ender pearl + nether star + netherite ingot + 4 diamond + 4 gold ingot + 4 iron ingot + 4 bricks
         *      2 ender pearl + nether star + netherite ingot + 4 emerald + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      2 ender pearl + nether star + netherite ingot + 2 diamond + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      2 ender pearl + nether star + netherite ingot + 4 diamond + 2 gold ingot + 4 lapis lazuli + 4 copper ingot + 4 bricks
         *      2 ender pearl + nether star + netherite ingot + 4 diamond + 4 gold ingot + 2 iron ingot + 4 copper ingot + 4 bricks
         *      4 ender pearl + nether star + 4 amethyst + 4 emerald + 4 redstone + 4 lapis lazuli + 4 copper ingot + 4 bricks
        */
    }

    public static void init() {
        // no-op
    }

    public static String getName(Block block) {
        return BLOCK_TO_NAME.get(block);
    }

    public static Stream<PottersDecoratedPotBlockBase> stream() {
        return ALL.stream();
    }

    public static void forEach(Consumer<PottersDecoratedPotBlockBase> blockConsumer) {
        ALL.forEach(blockConsumer);
    }

    public static void forEachFinite(Consumer<FiniteDecoratedPotBlock> blockConsumer) {
        FINITE_DECORATED_POTS.forEach(blockConsumer);
    }

    private static <T extends PottersDecoratedPotBlockBase> T register(String name, T block) {
        T value = Registry.register(Registries.BLOCK, Potters.id(name + "_decorated_pot"), block);
        ALL.add(value);
        BLOCK_TO_NAME.put(value, name);
        if (value instanceof FiniteDecoratedPotBlock finite) FINITE_DECORATED_POTS.add(finite);
        return value;
    }
}
