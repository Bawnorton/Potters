package com.bawnorton.potters.blocks;

import com.bawnorton.potters.blocks.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.blocks.entity.GoldDecoratedPotBlockEntity;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.blocks.entity.base.PottersDecoratedPotBlockEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class GoldDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public GoldDecoratedPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
            this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(CRACKED, Boolean.FALSE)
        );
    }

    @Override
    protected BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType() {
        return PottersBlockEntityType.GOLD_DECORATED_POT;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GoldDecoratedPotBlockEntity(pos, state);
    }
}
