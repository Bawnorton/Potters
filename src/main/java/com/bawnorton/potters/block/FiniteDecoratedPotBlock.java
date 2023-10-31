package com.bawnorton.potters.block;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.block.entity.FiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class FiniteDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    public static final Identifier MATERIAL_AND_SHERDS = Potters.id("material_and_sherds");
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final Supplier<Item> materialSupplier;
    private final Supplier<Integer> stackCountSupplier;
    private final int materialDropCount;

    public FiniteDecoratedPotBlock(Supplier<Item> materialSupplier, Supplier<Integer> stackCountSupplier, int materialDropCount) {
        super(FabricBlockSettings.copy(Blocks.DECORATED_POT));
        this.materialSupplier = materialSupplier;
        this.stackCountSupplier = stackCountSupplier;
        this.materialDropCount = materialDropCount;
        this.setDefaultState(
            this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, Boolean.FALSE)
                .with(CRACKED, Boolean.FALSE)
        );
    }

    public FiniteDecoratedPotBlock(Supplier<Item> materialSupplier, Supplier<Integer> stackCountSupplier) {
        this(materialSupplier, stackCountSupplier, 4);
    }

    public Supplier<Integer> getStackCountSupplier() {
        return stackCountSupplier;
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
        return new FiniteDecoratedPotBlockEntity(pos, state, getStackCountSupplier());
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
                for(int i = 0; i < materialDropCount; i++) {
                    lootConsumer.accept(getMaterial().getDefaultStack());
                }
            });
        }

        return super.getDroppedStacks(state, builder);
    }
}
