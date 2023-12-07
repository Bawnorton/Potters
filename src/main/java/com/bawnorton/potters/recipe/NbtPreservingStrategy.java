package com.bawnorton.potters.recipe;

import net.minecraft.item.ItemStack;

public interface NbtPreservingStrategy {
    void apply(ItemStack input, ItemStack output);
}
