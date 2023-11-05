package com.bawnorton.potters.block.entity;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.storage.FiniteDecoratedPotStorage;
import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Supplier;

public class FiniteDecoratedPotBlockEntity extends PottersDecoratedPotBlockEntityBase {
    private final FiniteDecoratedPotStorage storage;

    public FiniteDecoratedPotBlockEntity(BlockPos pos, BlockState state, Supplier<Integer> stackCountSupplier) {
        super(PottersBlockEntityType.FINITE_DECORATED_POT, pos, state);
        this.storage = new FiniteDecoratedPotStorage(stackCountSupplier);
    }

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

    public DefaultedList<ItemStack> getAndClearStacks() {
        List<ItemStack> stacks = storage.getStacks();
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(stacks.size(), ItemStack.EMPTY);
        for (int i = 0; i < stacks.size(); i++) {
            defaultedList.set(i, stacks.get(i));
        }
        storage.clear();
        return defaultedList;
    }
}
