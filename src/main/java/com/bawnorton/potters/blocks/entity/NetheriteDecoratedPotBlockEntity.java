package com.bawnorton.potters.blocks.entity;

import com.bawnorton.potters.blocks.entity.base.FiniteDecoratedPotBlockEntity;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class NetheriteDecoratedPotBlockEntity extends FiniteDecoratedPotBlockEntity {
    public NetheriteDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(PottersBlockEntityType.NETHERITE_DECORATED_POT, pos, state, 81);
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
