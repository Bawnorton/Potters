package com.bawnorton.potters.datagen;

import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.tag.PottersItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PottersItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public PottersItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        PottersBlocks.forEach(block -> this.getOrCreateTagBuilder(PottersItemTags.DECORATED_POT_MATERIALS).add(block.getMaterial()));
        this.getOrCreateTagBuilder(PottersItemTags.BOTTOMLESS_DECORATED_POT_CORE).add(Items.NETHER_STAR).add(Items.DRAGON_EGG);
        this.getOrCreateTagBuilder(PottersItemTags.POT_BLACKLIST).add(Items.SHULKER_BOX).add(PottersBlocks.BOTTOMLESS_DECORATED_POT.asItem());
    }
}
