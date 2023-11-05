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
        COPPER_DECORATED_POT = register("copper", new FiniteDecoratedPotBlock(() -> Items.COPPER_INGOT, () -> ConfigManager.getConfig().copperStackCount));
        IRON_DECORATED_POT = register("iron", new FiniteDecoratedPotBlock(() -> Items.IRON_INGOT, () -> ConfigManager.getConfig().ironStackCount));
        LAPIS_DECORATED_POT = register("lapis", new FiniteDecoratedPotBlock(() -> Items.LAPIS_LAZULI, () -> ConfigManager.getConfig().lapisStackCount));
        GOLD_DECORATED_POT = register("gold", new FiniteDecoratedPotBlock(() -> Items.GOLD_INGOT, () -> ConfigManager.getConfig().goldStackCount));
        AMETHYST_DECORATED_POT = register("amethyst", new FiniteDecoratedPotBlock(() -> Items.AMETHYST_SHARD, () -> ConfigManager.getConfig().amethystStackCount));
        EMERALD_DECORATED_POT = register("emerald", new FiniteDecoratedPotBlock(() -> Items.EMERALD, () -> ConfigManager.getConfig().emeraldStackCount));
        DIAMOND_DECORATED_POT = register("diamond", new FiniteDecoratedPotBlock(() -> Items.DIAMOND, () -> ConfigManager.getConfig().diamondStackCount));
        NETHERITE_DECORATED_POT = register("netherite", new FiniteDecoratedPotBlock(() -> Items.NETHERITE_INGOT, () -> ConfigManager.getConfig().netheriteStackCount, 1));
        BOTTOMLESS_DECORATED_POT = register("bottomless", new BottomlessDecoratedPotBlock(() -> Items.ENDER_PEARL));
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
