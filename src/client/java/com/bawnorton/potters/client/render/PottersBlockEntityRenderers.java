package com.bawnorton.potters.client.render;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.registry.PottersItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.Item;

public class PottersBlockEntityRenderers {
    public static void init() {
        for(BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> blockEntityType : PottersBlockEntityType.ALL) {
            BlockEntityRendererFactories.register(blockEntityType, PottersDecoratedPotBlockEntityRenderer::new);
        }
        for(Item item: PottersItems.ALL) {
            BuiltinItemRendererRegistry.INSTANCE.register(item, PottersDecoratedPotBlockEntityRenderer::renderItemStack);
        }
    }
}
