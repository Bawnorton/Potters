package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class PottersRegistryKeys {
    public static final RegistryKey<Registry<String>> DECORATED_POT_SIDE_OVERLAY_PATTERN;
    public static final RegistryKey<Registry<String>> DECORATED_POT_BASE_OVERLAY_PATTERN;

    static {
        DECORATED_POT_SIDE_OVERLAY_PATTERN = create("decorated_pot_side_overlay_pattern");
        DECORATED_POT_BASE_OVERLAY_PATTERN = create("decorated_pot_base_overlay_pattern");
    }

    public static void init() {
        // no-op
    }

    private static <T> RegistryKey<Registry<T>> create(String path) {
        return RegistryKey.ofRegistry(Potters.id(path));
    }
}
