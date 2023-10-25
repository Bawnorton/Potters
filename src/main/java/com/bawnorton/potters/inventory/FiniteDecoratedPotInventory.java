package com.bawnorton.potters.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class FiniteDecoratedPotInventory extends PottersDecoratedPotInventoryBase {
    private final int stackCount;
    private int maxCount;
    private int count;

    public FiniteDecoratedPotInventory(int stackCount) {
        this.stackCount = stackCount;
        this.maxCount = stackCount * getType().getMaxCount();
    }

    @Override
    public float getPitch() {
        return 0.7F + 0.5F * ((float) count / (float) maxCount);
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(super.canInsert(stack)) return count < maxCount;
        return false;
    }

    @Override
    public ItemStack addStack(ItemStack stack) {
        if(isEmpty()) {
            type = stack.getItem();
            maxCount = stackCount * type.getMaxCount();
            count = Math.min(stack.getCount(), maxCount);
            stack.setCount(stack.getCount() - count);
        } else if(stack.getItem() == type) {
            int remainder = Math.min(stack.getCount(), maxCount - count);
            count += remainder;
            stack.setCount(stack.getCount() - remainder);
        }
        markDirty();
        if(stack.isEmpty()) return ItemStack.EMPTY;
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = type.getDefaultStack();
        stack.setCount(Math.min(amount, count));
        count -= stack.getCount();
        if(count == 0) type = Items.AIR;
        maxCount = stackCount * type.getMaxCount();
        markDirty();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return removeStack(slot, maxCount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        count += stack.getCount();
        markDirty();
    }

    public void toNbt(NbtCompound nbt) {
        super.toNbt(nbt);
        nbt.putInt("count", count);
    }

    @Override
    public void fromNbt(NbtCompound nbt) {
        super.fromNbt(nbt);
        count = nbt.getInt("count");
    }
}
