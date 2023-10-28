package com.bawnorton.potters.block.base;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.entity.FiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public abstract class FiniteDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    public static final Identifier MATERIAL_AND_SHERDS = Potters.id("material_and_sherds");
    private final Supplier<Item> materialSupplier;
    private final int stackCount;

    protected FiniteDecoratedPotBlock(Settings settings, Supplier<Item> materialSupplier, int stackCount) {
        super(settings);
        this.materialSupplier = materialSupplier;
        this.stackCount = stackCount;
    }

    public int getStackCount() {
        return stackCount;
    }

    public Item getMaterial() {
        return materialSupplier.get();
    }

    @Override
    protected BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType() {
        return PottersBlockEntityType.FINITE_DECORATED_POT;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FiniteDecoratedPotBlockEntity(pos, state, getStackCount());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof FiniteDecoratedPotBlockEntity finiteDecoratedPotBlockEntity) {
            DefaultedList<ItemStack> stacks = finiteDecoratedPotBlockEntity.getStacks();
            ItemScatterer.spawn(world, pos, stacks);
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof FiniteDecoratedPotBlockEntity finiteDecoratedPotBlockEntity) {
            builder.addDynamicDrop(MATERIAL_AND_SHERDS, lootConsumer -> {
                finiteDecoratedPotBlockEntity.getSherds().stream().map(Item::getDefaultStack).forEach(lootConsumer);
                for(int i = 0; i < getMaterialDropCount(); i++) {
                    lootConsumer.accept(getMaterial().getDefaultStack());
                }
            });
        }

        return super.getDroppedStacks(state, builder);
    }

    protected abstract int getMaterialDropCount();
}
