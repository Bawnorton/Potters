package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.blocks.entity.*;
import com.bawnorton.potters.blocks.entity.base.PottersDecoratedPotBlockEntityBase;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class PottersBlockEntityType {
    public static final List<BlockEntityType<? extends PottersDecoratedPotBlockEntityBase>> ALL;
    public static final BlockEntityType<IronDecoratedPotBlockEntity> IRON_DECORATED_POT;
    public static final BlockEntityType<GoldDecoratedPotBlockEntity> GOLD_DECORATED_POT;
    public static final BlockEntityType<DiamondDecoratedPotBlockEntity> DIAMOND_DECORATED_POT;
    public static final BlockEntityType<NetheriteDecoratedPotBlockEntity> NETHERITE_DECORATED_POT;
    public static final BlockEntityType<InfiniteDecoratedPotBlockEntity> INFINITE_DECORATED_POT;

    static {
        ALL = new ArrayList<>();
        IRON_DECORATED_POT = register("iron", IronDecoratedPotBlockEntity::new, PottersBlocks.IRON_DECORATED_POT);
        GOLD_DECORATED_POT = register("gold", GoldDecoratedPotBlockEntity::new, PottersBlocks.GOLD_DECORATED_POT);
        DIAMOND_DECORATED_POT = register("diamond", DiamondDecoratedPotBlockEntity::new, PottersBlocks.DIAMOND_DECORATED_POT);
        NETHERITE_DECORATED_POT = register("netherite", NetheriteDecoratedPotBlockEntity::new, PottersBlocks.NETHERITE_DECORATED_POT);
        INFINITE_DECORATED_POT = register("infinite", InfiniteDecoratedPotBlockEntity::new, PottersBlocks.INFINITE_DECORATED_POT);
    }

    public static void init() {
        ItemStorage.SIDED.registerForBlockEntities(((blockEntity, context) -> {
            if(blockEntity instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity) {
                return pottersBlockEntity.getInventoryWrapper();
            }
            return null;
        }), ALL.toArray(new BlockEntityType[]{}));
    }

    private static <T extends PottersDecoratedPotBlockEntityBase> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        BlockEntityType<T> blockEntityType = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Potters.id(name + "_decorated_pot"),
            FabricBlockEntityTypeBuilder.create(factory, blocks).build()
        );
        ALL.add(blockEntityType);
        return blockEntityType;
    }
}
