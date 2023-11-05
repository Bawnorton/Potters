package com.bawnorton.potters.datagen;

import com.bawnorton.potters.datagen.model.PottersBlockModel;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.registry.PottersItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class PottersModelProvider extends FabricModelProvider {
    public PottersModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        PottersBlocks.forEach(block -> blockStateModelGenerator.registerBuiltin(block, Blocks.TERRACOTTA)
            .includeWithoutItem(block));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        TextureMap textureMap = new TextureMap().put(TextureKey.PARTICLE, new Identifier("entity/decorated_pot/decorated_pot_side"));
        PottersItems.forEach(item -> {
            PottersBlockModel model = new PottersBlockModel();
            model.upload(ModelIds.getItemModelId(item), textureMap, itemModelGenerator.writer);
        });
    }
}
