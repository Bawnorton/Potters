package com.bawnorton.potters.client.render;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.DecoratedPotOverlayPatterns;
import com.bawnorton.potters.registry.PottersRegistries;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PottersTexturedRenderLayers {
    public static final Identifier DECORATED_POT_SIDES_OVERLAY_ATLAS_TEXTURE = Potters.id("textures/atlas/decorated_pot_sides_overlay.png");
    public static final Identifier DECORATED_POT_BASE_OVERLAY_ATLAS_TEXTURE = Potters.id("textures/atlas/decorated_pot_base_overlay.png");
    private static final Map<RegistryKey<String>, SpriteIdentifier> DECORATED_POT_OVERLAY_PATTERN_TEXTURES = PottersRegistries.DECORATED_POT_SIDE_OVERLAY_PATTERN.getKeys()
        .stream()
        .collect(Collectors.toMap(Function.identity(), PottersTexturedRenderLayers::createSideOverlayPatternTextureId));
    private static final Map<RegistryKey<String>, SpriteIdentifier> DECORATED_POT_BASE_OVERLAY_PATTERN_TEXTURES = PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN.getKeys()
        .stream()
        .collect(Collectors.toMap(Function.identity(), PottersTexturedRenderLayers::createBaseOverlayPatternTextureId));

    public static SpriteIdentifier createSideOverlayPatternTextureId(RegistryKey<String> key) {
        return new SpriteIdentifier(DECORATED_POT_SIDES_OVERLAY_ATLAS_TEXTURE, DecoratedPotOverlayPatterns.getSideTextureId(key));
    }

    public static SpriteIdentifier createBaseOverlayPatternTextureId(RegistryKey<String> key) {
        return new SpriteIdentifier(DECORATED_POT_BASE_OVERLAY_ATLAS_TEXTURE, DecoratedPotOverlayPatterns.getBaseTextureId(key));
    }

    public static SpriteIdentifier getSideOverlayPatternTextureId(@Nullable RegistryKey<String> key) {
        return key == null ? null : DECORATED_POT_OVERLAY_PATTERN_TEXTURES.get(key);
    }

    public static SpriteIdentifier getBaseOverlayPatternTextureId(@Nullable RegistryKey<String> key) {
        return key == null ? null : DECORATED_POT_BASE_OVERLAY_PATTERN_TEXTURES.get(key);
    }
}
