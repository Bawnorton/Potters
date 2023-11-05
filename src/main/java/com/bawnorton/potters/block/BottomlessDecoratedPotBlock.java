package com.bawnorton.potters.block;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.block.entity.BottomlessDecoratedPotBlockEntity;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.function.Supplier;

public class BottomlessDecoratedPotBlock extends PottersDecoratedPotBlockBase {
    public static final Identifier WITH_CONTENT = Potters.id("with_content");
    public static final BooleanProperty EMTPY = BooleanProperty.of("empty");
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public BottomlessDecoratedPotBlock(Supplier<Item> materialSupplier) {
        super(materialSupplier, FabricBlockSettings.copy(Blocks.DECORATED_POT));
        this.setDefaultState(this.stateManager.getDefaultState()
                                 .with(FACING, Direction.NORTH)
                                 .with(WATERLOGGED, Boolean.FALSE)
                                 .with(EMTPY, Boolean.TRUE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState state = this.getDefaultState()
            .with(FACING, ctx.getHorizontalPlayerFacing())
            .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
            .with(EMTPY, Boolean.TRUE);

        ItemStack stack = ctx.getStack();
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return state;

        NbtCompound blockEntityTag = nbt.getCompound("BlockEntityTag");
        if (blockEntityTag.isEmpty()) return state;

        String count = blockEntityTag.getString("count");
        try {
            int amount = Integer.parseInt(count);
            if (amount > 0) {
                state = state.with(EMTPY, Boolean.FALSE);
            }
        } catch (NumberFormatException ignored) {}

        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, EMTPY);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BottomlessDecoratedPotBlockEntity(pos, state);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.DECORATED_POT;
    }

    @Override
    protected BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType() {
        return PottersBlockEntityType.BOTTOMLESS_DECORATED_POT;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof BottomlessDecoratedPotBlockEntity bottomlessDecoratedPotBlockEntity) {
            builder.addDynamicDrop(WITH_CONTENT, lootConsumer -> {
                ItemStack stack = bottomlessDecoratedPotBlockEntity.asStack(PottersBlockEntityType.BOTTOMLESS_DECORATED_POT, this.asItem());
                NbtCompound blockEntityTag = stack.getOrCreateNbt().getCompound("BlockEntityTag");
                bottomlessDecoratedPotBlockEntity.getStorage().writeNbt(blockEntityTag);
                stack.getOrCreateNbt().put("BlockEntityTag", blockEntityTag);
                lootConsumer.accept(stack);
            });
        }

        List<ItemStack> droppedStacks = super.getDroppedStacks(state, builder);
        // correct nbt for bottomless decorated pot with sherds
        for(ItemStack stack: droppedStacks) {
            if (!(Block.getBlockFromItem(stack.getItem()) instanceof BottomlessDecoratedPotBlock)) continue;

            NbtCompound nbt = stack.getNbt();
            if(nbt == null) continue;

            NbtCompound blockEntityTag = nbt.getCompound("BlockEntityTag");
            if(blockEntityTag.isEmpty()) continue;

            if(blockEntityTag.contains("sherds")) {
                if (blockEntityTag.contains("id")) continue;

                Identifier id = Registries.BLOCK_ENTITY_TYPE.getId(PottersBlockEntityType.BOTTOMLESS_DECORATED_POT);
                if(id == null) continue;

                blockEntityTag.putString("id", id.toString());
            }
        }
        return droppedStacks;
    }

    @Override
    public boolean isFinite() {
        return false;
    }
}
