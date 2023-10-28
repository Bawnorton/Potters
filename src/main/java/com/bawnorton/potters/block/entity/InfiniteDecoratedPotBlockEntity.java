package com.bawnorton.potters.block.entity;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.storage.InfiniteDecoratedPotStorage;
import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class InfiniteDecoratedPotBlockEntity extends PottersDecoratedPotBlockEntityBase {
    private final InfiniteDecoratedPotStorage storage;

    public InfiniteDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(PottersBlockEntityType.INFINITE_DECORATED_POT, pos, state);
        storage = new InfiniteDecoratedPotStorage();
    }

    @Override
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
