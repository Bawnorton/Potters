package com.bawnorton.potters.block;

import com.bawnorton.potters.block.base.FinitePottersDecoratedPotBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class GoldDecoratedPotBlock extends FinitePottersDecoratedPotBlock {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public GoldDecoratedPotBlock(Settings settings) {
        super(settings, 9);
        this.setDefaultState(
            this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(CRACKED, Boolean.FALSE)
        );
    }
}
