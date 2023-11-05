package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.FiniteDecoratedPotBlock;
import com.bawnorton.potters.block.entity.FiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.BottomlessDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PottersBlockEntityType {
    public static final BlockEntityType<FiniteDecoratedPotBlockEntity> FINITE_DECORATED_POT;
    public static final BlockEntityType<BottomlessDecoratedPotBlockEntity> BOTTOMLESS_DECORATED_POT;
    private static final List<BlockEntityType<? extends PottersDecoratedPotBlockEntityBase>> ALL;

    static {
        ALL = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();
        PottersBlocks.forEachFinite(blocks::add);
        FINITE_DECORATED_POT = register("finite", FabricBlockEntityTypeBuilder.create((pos, state) -> {
            FiniteDecoratedPotBlock block = (FiniteDecoratedPotBlock) state.getBlock();
            return new FiniteDecoratedPotBlockEntity(pos, state, block.getStackCountSupplier());
        }, blocks.toArray(new Block[]{})).build());
        BOTTOMLESS_DECORATED_POT = register("bottomless", FabricBlockEntityTypeBuilder.create(BottomlessDecoratedPotBlockEntity::new, PottersBlocks.BOTTOMLESS_DECORATED_POT)
            .build());
    }

    public static void forEach(Consumer<BlockEntityType<? extends PottersDecoratedPotBlockEntityBase>> blockEntityTypeConsumer) {
        ALL.forEach(blockEntityTypeConsumer);
    }

    public static void init() {
        ItemStorage.SIDED.registerForBlockEntities(((blockEntity, context) -> {
            if (blockEntity instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity) {
                return pottersBlockEntity.getStorage();
            }
            return null;
        }), ALL.toArray(new BlockEntityType[]{}));
    }

    private static <T extends PottersDecoratedPotBlockEntityBase> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BlockEntityType<T> blockEntityType = Registry.register(Registries.BLOCK_ENTITY_TYPE, Potters.id(name + "_decorated_pot"), type);
        ALL.add(blockEntityType);
        return blockEntityType;
    }
}
