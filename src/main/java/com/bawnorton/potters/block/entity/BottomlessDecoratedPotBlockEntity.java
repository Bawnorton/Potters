package com.bawnorton.potters.block.entity;

import com.bawnorton.potters.block.BottomlessDecoratedPotBlock;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.storage.BottomlessDecoratedPotStorage;
import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class BottomlessDecoratedPotBlockEntity extends PottersDecoratedPotBlockEntityBase {
    private final BottomlessDecoratedPotStorage storage;

    public BottomlessDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(PottersBlockEntityType.BOTTOMLESS_DECORATED_POT, pos, state);
        storage = new BottomlessDecoratedPotStorage();
        storage.addListener(storageView -> {
            if (world == null) return;
            if (world.isClient) return;

            if (storageView.isResourceBlank()) {
                world.setBlockState(pos, state.with(BottomlessDecoratedPotBlock.EMTPY, true), Block.NO_REDRAW);
            } else {
                world.setBlockState(pos, state.with(BottomlessDecoratedPotBlock.EMTPY, false), Block.NO_REDRAW);
            }
        });
    }

    @Override
    public PottersDecoratedPotStorageBase getStorage() {
        return storage;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
