package com.bawnorton.potters.block;

import com.bawnorton.potters.block.base.FiniteDecoratedPotBlock;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;

public class DiamondDecoratedPotBlock extends FiniteDecoratedPotBlock {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public DiamondDecoratedPotBlock(Settings settings, Supplier<Item> materialSupplier, int stackCount) {
        super(settings, materialSupplier, stackCount);
        this.setDefaultState(
            this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(CRACKED, Boolean.FALSE)
        );
    }

    @Override
    protected int getMaterialDropCount() {
        return 4;
    }
}