package com.bawnorton.potters.inventory;

import com.bawnorton.potters.config.Config;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

public class AdditionalDecoratedPotInventory extends SimpleInventory {
    private final DecoratedPotBlockEntity blockEntity;
    private float fillPercentage = 0;

    public static AdditionalDecoratedPotInventory forEntity(DecoratedPotBlockEntity blockEntity) {
        return new AdditionalDecoratedPotInventory(blockEntity);
    }

    private AdditionalDecoratedPotInventory(DecoratedPotBlockEntity blockEntity) {
        super(Config.getInstance().numberOfStacks * 64);
        this.blockEntity = blockEntity;
        addListener(this::calculateFillPercentage);
    }

    private void calculateFillPercentage(Inventory inventory) {
        float fullness = (float) blockEntity.getStack().getCount() / blockEntity.getStack().getMaxCount();
        for(int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            fullness += (float) stack.getCount() / stack.getMaxCount();
        }
        if (fullness >= Config.getInstance().numberOfStacks) fullness = Config.getInstance().numberOfStacks;
        fillPercentage = fullness / Config.getInstance().numberOfStacks;
    }

    public boolean isFull() {
        return getFillPercentage() >= 1;
    }

    public boolean willOverflowWith(ItemStack stack) {
        return getFillPercentage() + ((float) stack.getCount() / stack.getMaxCount() / Config.getInstance().numberOfStacks) > 1;
    }

    public float getFillPercentage() {
        calculateFillPercentage(this);
        return fillPercentage;
    }

    public int getSlotWhichCanCombine(ItemStack otherStack) {
        for(int i = 0; i < stacks.size(); i++) {
            if(ItemStack.canCombine(stacks.get(i), otherStack)) return i;
        }
        return -1;
    }

    public int nextNonEmptySlot() {
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public int nextEmptySlot() {
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private boolean canAddToInv(ItemStack stack) {
        return !isFull() && !willOverflowWith(stack);
    }

    @Override
    public ItemStack addStack(ItemStack stack) {
        if(canAddToInv(stack)) {
            return super.addStack(stack);
        }
        return stack;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return canAddToInv(stack) && super.canInsert(stack);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return canAddToInv(stack) && super.isValid(slot, stack);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        blockEntity.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }
}
