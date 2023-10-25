package com.bawnorton.potters.blocks.entity.base;

import com.bawnorton.potters.inventory.FiniteDecoratedPotInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class FiniteDecoratedPotBlockEntity extends PottersDecoratedPotBlockEntityBase {
    private final FiniteDecoratedPotInventory inventory;

    protected FiniteDecoratedPotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int stackCount) {
        super(type, pos, state);
        inventory = new FiniteDecoratedPotInventory(stackCount);
    }

    @Override
    public FiniteDecoratedPotInventory getInventory() {
        return inventory;
    }
}
