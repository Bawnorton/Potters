package com.bawnorton.potters.client.render;

import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.registry.PottersItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class PottersBlockEntityRenderers {
    public static void init() {
        PottersBlockEntityType.forEach(blockEntityType -> BlockEntityRendererFactories.register(blockEntityType, PottersDecoratedPotBlockEntityRenderer::new));
        PottersItems.forEach(item -> BuiltinItemRendererRegistry.INSTANCE.register(item, PottersDecoratedPotBlockEntityRenderer::renderItemStack));
    }
}
