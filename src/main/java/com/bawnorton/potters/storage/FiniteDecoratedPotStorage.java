package com.bawnorton.potters.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.nbt.NbtCompound;

public class FiniteDecoratedPotStorage extends PottersDecoratedPotStorageBase {
    protected long stackCount;

    public FiniteDecoratedPotStorage(long stackCount) {
        this.stackCount = stackCount;
    }

    @Override
    protected boolean canInsert(ItemVariant variant) {
        return super.canInsert(variant) && getAmount() < getCapacity();
    }

    @Override
    public float getPitch() {
        return 0.7F + 0.5F * ((float) getCount().longValue() / getCapacity());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        variant = ItemVariant.fromNbt(nbt.getCompound("variant"));
        amount = nbt.getLong("count");
    }

    @Override
    public Number getCount() {
        return getAmount();
    }

    @Override
    protected long getCapacity(ItemVariant variant) {
        return stackCount * variant.getItem().getMaxCount();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("variant", variant.toNbt());
        nbt.putLong("count", getAmount());
    }
}
