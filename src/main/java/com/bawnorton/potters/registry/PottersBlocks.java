package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.*;
import com.bawnorton.potters.block.base.FinitePottersDecoratedPotBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class PottersBlocks {
    public static final List<Block> FINITE_DECORATED_POTS;
    public static final Block IRON_DECORATED_POT;
    public static final Block GOLD_DECORATED_POT;
    public static final Block DIAMOND_DECORATED_POT;
    public static final Block NETHERITE_DECORATED_POT;
    public static final Block BOTTOMLESS_DECORATED_POT;

    static {
        FINITE_DECORATED_POTS = new ArrayList<>();
        IRON_DECORATED_POT = register("iron", IronDecoratedPotBlock::new);
        GOLD_DECORATED_POT = register("gold", GoldDecoratedPotBlock::new);
        DIAMOND_DECORATED_POT = register("diamond", DiamondDecoratedPotBlock::new);
        NETHERITE_DECORATED_POT = register("netherite", NetheriteDecoratedPotBlock::new);
        BOTTOMLESS_DECORATED_POT = register("bottomless", BottomlessDecoratedPotBlock::new);
    }

    public static void init() {
        // no-op
    }

    private static <T extends Block> Block register(String name, Factory<T> block) {
        Block value = Registry.register(
            Registries.BLOCK,
            Potters.id(name + "_decorated_pot"),
            block.create(FabricBlockSettings.copy(Blocks.DECORATED_POT))
        );
        if(value instanceof FinitePottersDecoratedPotBlock pottersBlock && pottersBlock.getStackCount() != -1) FINITE_DECORATED_POTS.add(value);
        return value;
    }

    @FunctionalInterface
    private interface Factory<T extends Block> {
        T create(AbstractBlock.Settings settings);
    }
}
