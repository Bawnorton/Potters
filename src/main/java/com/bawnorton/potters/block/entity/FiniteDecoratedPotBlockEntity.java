package com.bawnorton.potters.block.entity;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.storage.FiniteDecoratedPotStorage;
import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class FiniteDecoratedPotBlockEntity extends PottersDecoratedPotBlockEntityBase {
    private final FiniteDecoratedPotStorage storage;

    public FiniteDecoratedPotBlockEntity(BlockPos pos, BlockState state, int stackCount) {
        super(PottersBlockEntityType.FINITE_DECORATED_POT, pos, state);
        storage = new FiniteDecoratedPotStorage(stackCount);
    }

    public PottersDecoratedPotStorageBase getStorage() {
        return storage;
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
