package com.bawnorton.potters.registry;

import com.bawnorton.potters.block.DecoratedPotOverlayPatterns;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class PottersRegistries {
    public static final Registry<String> DECORATED_POT_SIDE_OVERLAY_PATTERN;
    public static final Registry<String> DECORATED_POT_BASE_OVERLAY_PATTERN;

    static {
        DECORATED_POT_SIDE_OVERLAY_PATTERN = create(PottersRegistryKeys.DECORATED_POT_SIDE_OVERLAY_PATTERN);
        DECORATED_POT_BASE_OVERLAY_PATTERN = create(PottersRegistryKeys.DECORATED_POT_BASE_OVERLAY_PATTERN);
    }

    public static void init() {
        DecoratedPotOverlayPatterns.register();
    }

    private static <T> Registry<T> create(RegistryKey<Registry<T>> key) {
        return FabricRegistryBuilder.createSimple(key)
            .attribute(RegistryAttribute.SYNCED)
            .attribute(RegistryAttribute.MODDED)
            .buildAndRegister();
    }
}
