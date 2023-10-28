package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.*;
import com.bawnorton.potters.block.base.FiniteDecoratedPotBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
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
        IRON_DECORATED_POT = register("iron", new IronDecoratedPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT), () -> Items.IRON_INGOT, 4));
        GOLD_DECORATED_POT = register("gold", new GoldDecoratedPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT), () -> Items.GOLD_INGOT, 16));
        DIAMOND_DECORATED_POT = register("diamond", new DiamondDecoratedPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT), () -> Items.DIAMOND, 64));
        NETHERITE_DECORATED_POT = register("netherite", new NetheriteDecoratedPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT), () -> Items.NETHERITE_INGOT, 256));
        BOTTOMLESS_DECORATED_POT = register("bottomless", new BottomlessDecoratedPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT)));
    }

    public static void init() {
        // no-op
    }

    private static <T extends Block> Block register(String name, Block block) {
        Block value = Registry.register(
            Registries.BLOCK,
            Potters.id(name + "_decorated_pot"),
            block
        );
        if(value instanceof FiniteDecoratedPotBlock pottersBlock && pottersBlock.getStackCount() != -1) FINITE_DECORATED_POTS.add(value);
        return value;
    }
}
