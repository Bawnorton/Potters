package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.*;
import com.bawnorton.potters.block.FiniteDecoratedPotBlock;
import com.bawnorton.potters.config.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PottersBlocks {
    private static final List<Block> ALL;
    private static final List<Block> FINITE_DECORATED_POTS;
    public static final Block COPPER_DECORATED_POT;
    public static final Block IRON_DECORATED_POT;
    public static final Block LAPIS_DECORATED_POT;
    public static final Block GOLD_DECORATED_POT;
    public static final Block AMETHYST_DECORATED_POT;
    public static final Block DIAMOND_DECORATED_POT;
    public static final Block EMERALD_DECORATED_POT;
    public static final Block NETHERITE_DECORATED_POT;
    public static final Block BOTTOMLESS_DECORATED_POT;

    static {
        ALL = new ArrayList<>();
        FINITE_DECORATED_POTS = new ArrayList<>();
        COPPER_DECORATED_POT = register("copper", new FiniteDecoratedPotBlock(() -> Items.COPPER_INGOT, () -> ConfigManager.getConfig().copperStackCount));
        IRON_DECORATED_POT = register("iron", new FiniteDecoratedPotBlock(() -> Items.IRON_INGOT, () -> ConfigManager.getConfig().ironStackCount));
        LAPIS_DECORATED_POT = register("lapis", new FiniteDecoratedPotBlock(() -> Items.LAPIS_LAZULI, () -> ConfigManager.getConfig().lapisStackCount));
        GOLD_DECORATED_POT = register("gold", new FiniteDecoratedPotBlock(() -> Items.GOLD_INGOT, () -> ConfigManager.getConfig().goldStackCount));
        AMETHYST_DECORATED_POT = register("amethyst", new FiniteDecoratedPotBlock(() -> Items.AMETHYST_SHARD, () -> ConfigManager.getConfig().amethystStackCount));
        EMERALD_DECORATED_POT = register("emerald", new FiniteDecoratedPotBlock(() -> Items.EMERALD, () -> ConfigManager.getConfig().emeraldStackCount));
        DIAMOND_DECORATED_POT = register("diamond", new FiniteDecoratedPotBlock(() -> Items.DIAMOND, () -> ConfigManager.getConfig().diamondStackCount));
        NETHERITE_DECORATED_POT = register("netherite", new FiniteDecoratedPotBlock(() -> Items.NETHERITE_INGOT, () -> ConfigManager.getConfig().netheriteStackCount, 1));
        BOTTOMLESS_DECORATED_POT = register("bottomless", new BottomlessDecoratedPotBlock());
    }

    public static void init() {
        // no-op
    }

    public static void forEach(Consumer<Block> blockConsumer) {
        ALL.forEach(blockConsumer);
    }

    public static void forEachFinite(Consumer<Block> blockConsumer) {
        FINITE_DECORATED_POTS.forEach(blockConsumer);
    }

    private static Block register(String name, Block block) {
        Block value = Registry.register(
            Registries.BLOCK,
            Potters.id(name + "_decorated_pot"),
            block
        );
        ALL.add(value);
        if(value instanceof FiniteDecoratedPotBlock) FINITE_DECORATED_POTS.add(value);
        return value;
    }
}
