package com.bawnorton.potters.client.mixin;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.client.render.PottersTexturedRenderLayers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(BakedModelManager.class)
public abstract class BakedModelManagerMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/Map;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;", remap = false))
    private static Map<Identifier, Identifier> addPottersAtlases(Map<Identifier, Identifier> original) {
        Map<Identifier, Identifier> modified = new HashMap<>(original);
        modified.put(PottersTexturedRenderLayers.DECORATED_POT_SIDES_OVERLAY_ATLAS_TEXTURE, Potters.id("decorated_pot_side_overlay"));
        modified.put(PottersTexturedRenderLayers.DECORATED_POT_BASE_OVERLAY_ATLAS_TEXTURE, Potters.id("decorated_pot_base_overlay"));
        return modified;
    }
}
