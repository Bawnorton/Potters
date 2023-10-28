package com.bawnorton.potters.block.base;

import com.bawnorton.potters.block.entity.FiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class FinitePottersDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    private final int stackCount;

    protected FinitePottersDecoratedPotBlock(Settings settings, int stackCount) {
        super(settings);
        this.stackCount = stackCount;
    }

    public int getStackCount() {
        return stackCount;
    }

    @Override
    protected BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType() {
        return PottersBlockEntityType.FINITE_DECORATED_POT;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FiniteDecoratedPotBlockEntity(pos, state, getStackCount());
    }
}
