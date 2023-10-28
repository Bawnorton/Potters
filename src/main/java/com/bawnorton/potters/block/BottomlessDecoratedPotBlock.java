package com.bawnorton.potters.block;

import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.block.entity.InfiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BottomlessDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public BottomlessDecoratedPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
            this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(CRACKED, Boolean.FALSE)
        );
    }

    @Override
    protected BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType() {
        return PottersBlockEntityType.INFINITE_DECORATED_POT;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteDecoratedPotBlockEntity(pos, state);
    }
}
