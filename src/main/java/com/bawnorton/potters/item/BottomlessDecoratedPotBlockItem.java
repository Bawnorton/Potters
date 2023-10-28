package com.bawnorton.potters.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.nbt.NbtCompound;

public class BottomlessDecoratedPotBlockItem extends BlockItem {
    public BottomlessDecoratedPotBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        if(!nbt.contains("tag")) return;
        System.out.println(nbt);
    }
}
