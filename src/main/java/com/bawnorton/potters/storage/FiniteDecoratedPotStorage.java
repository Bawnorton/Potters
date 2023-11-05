package com.bawnorton.potters.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FiniteDecoratedPotStorage extends PottersDecoratedPotStorageBase {
    protected Supplier<Integer> stackCountSupplier;

    public FiniteDecoratedPotStorage(Supplier<Integer> stackCountSupplier) {
        this.stackCountSupplier = stackCountSupplier;
    }

    public List<ItemStack> getStacks() {
        long count = getCount().longValue();
        List<ItemStack> stacks = new ArrayList<>();
        Item resource = getResource().getItem();
        NbtCompound nbt = getResource().getNbt();
        for (int amount = 0; amount < count; amount += resource.getMaxCount()) {
            ItemStack stack = new ItemStack(resource, (int) Math.min(resource.getMaxCount(), count - amount));
            stack.setNbt(nbt);
            stacks.add(stack);
        }
        return stacks;
    }

    @Override
    protected boolean canInsert(ItemVariant variant) {
        return super.canInsert(variant) && getAmount() < getCapacity();
    }

    @Override
    public Number getCount() {
        return getAmount();
    }

    @Override
    public void setCount(Number number) {
        amount = number.longValue();
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
    public void writeNbt(NbtCompound nbt) {
        nbt.put("variant", variant.toNbt());
        nbt.putLong("count", getAmount());
    }

    @Override
    protected long getCapacity(ItemVariant variant) {
        return stackCountSupplier.get().longValue() * variant.getItem().getMaxCount();
    }
}
