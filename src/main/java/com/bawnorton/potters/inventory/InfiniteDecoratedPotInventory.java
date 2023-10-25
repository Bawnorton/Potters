package com.bawnorton.potters.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.math.BigInteger;

public class InfiniteDecoratedPotInventory extends PottersDecoratedPotInventoryBase {
    private BigInteger count = BigInteger.ZERO;

    @Override
    public float getPitch() {
        return 0.7F;
    }

    @Override
    public BigInteger getCount() {
        return count;
    }

    @Override
    public ItemStack addStack(ItemStack stack) {
        if(isEmpty()) {
            type = stack.getItem();
            count = BigInteger.valueOf(stack.getCount());
        } else {
            count = count.add(BigInteger.valueOf(stack.getCount()));
        }
        markDirty();
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = type.getDefaultStack();
        stack.setCount(Math.min(amount, count.intValue()));
        count = count.subtract(BigInteger.valueOf(stack.getCount()));
        if(count.equals(BigInteger.ZERO)) type = Items.AIR;
        markDirty();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return removeStack(slot, Integer.MAX_VALUE);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        count = count.add(BigInteger.valueOf(stack.getCount()));
        markDirty();
    }

    @Override
    public void toNbt(NbtCompound nbt) {
        super.toNbt(nbt);
        nbt.putString("count", count.toString());
    }

    @Override
    public void fromNbt(NbtCompound nbt) {
        super.fromNbt(nbt);
        count = new BigInteger(nbt.getString("count"));
    }
}
