package com.bawnorton.potters.client.model;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.client.render.PottersDecoratedPotBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class PottersEntityModelLayers {
    public static final EntityModelLayer DECORATED_POT_SIDES_OVERLAY;
    public static final EntityModelLayer DECORATED_POT_BASE_OVERLAY;

    static {
        DECORATED_POT_SIDES_OVERLAY = register(
            "decorated_pot_sides_overlay", PottersDecoratedPotBlockEntityRenderer::getSidesTexturedModelData
        );
        DECORATED_POT_BASE_OVERLAY = register(
            "decorated_pot_base_overlay", PottersDecoratedPotBlockEntityRenderer::getTopBottomNeckTexturedModelData
        );
    }

    public static void init() {
        // no-op
    }

    private static EntityModelLayer register(String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer modelLayer = new EntityModelLayer(Potters.id(name), "main");
        EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
        return modelLayer;
    }
}
