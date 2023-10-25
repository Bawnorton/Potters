package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.blocks.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PottersBlocks {
    public static final Block IRON_DECORATED_POT;
    public static final Block GOLD_DECORATED_POT;
    public static final Block DIAMOND_DECORATED_POT;
    public static final Block NETHERITE_DECORATED_POT;
    public static final Block INFINITE_DECORATED_POT;

    static {
        IRON_DECORATED_POT = register("iron", IronDecoratedPotBlock::new);
        GOLD_DECORATED_POT = register("gold", GoldDecoratedPotBlock::new);
        DIAMOND_DECORATED_POT = register("diamond", DiamondDecoratedPotBlock::new);
        NETHERITE_DECORATED_POT = register("netherite", NetheriteDecoratedPotBlock::new);
        INFINITE_DECORATED_POT = register("infinite", InfiniteDecoratedPotBlock::new);
    }

    public static void init() {

    }

    private static <T extends Block> Block register(String name, Factory<T> block) {
        return Registry.register(
            Registries.BLOCK,
            Potters.id(name + "_decorated_pot"),
            block.create(FabricBlockSettings.copy(Blocks.DECORATED_POT))
        );
    }

    @FunctionalInterface
    private interface Factory<T extends Block> {
        T create(AbstractBlock.Settings settings);
    }
}
