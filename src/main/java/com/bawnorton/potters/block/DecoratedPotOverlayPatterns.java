package com.bawnorton.potters.block;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.registry.PottersRegistries;
import com.bawnorton.potters.registry.PottersRegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;

public class DecoratedPotOverlayPatterns {
    private static final Map<Block, Pattern> BLOCK_TO_PATTERN;

    static {
        BLOCK_TO_PATTERN = Util.make(new HashMap<>(), map -> PottersBlocks.forEach(block -> map.put(block, Pattern.of(PottersBlocks.getName(block)))));
    }

    public static Identifier getSideTextureId(RegistryKey<String> key) {
        return new Identifier("minecraft", key.getValue()
            .withPrefixedPath("entity/decorated_pot_side_overlay/")
            .getPath());
    }

    public static Identifier getBaseTextureId(RegistryKey<String> key) {
        return new Identifier("minecraft", key.getValue()
            .withPrefixedPath("entity/decorated_pot_base_overlay/")
            .getPath());
    }

    public static RegistryKey<String> sideFromBlock(Block block) {
        return BLOCK_TO_PATTERN.get(block).side();
    }

    public static RegistryKey<String> baseFromBlock(Block block) {
        return BLOCK_TO_PATTERN.get(block).base();
    }

    public static void register() {
        BLOCK_TO_PATTERN.forEach((block, pattern) -> {
            Registry.register(PottersRegistries.DECORATED_POT_SIDE_OVERLAY_PATTERN, pattern.side(), pattern.name());
            Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, pattern.base(), pattern.name());
        });
    }

    private record Pattern(String name, RegistryKey<String> side, RegistryKey<String> base) {
        private static RegistryKey<String> ofSide(String path) {
            return RegistryKey.of(PottersRegistryKeys.DECORATED_POT_SIDE_OVERLAY_PATTERN, Potters.id(path));
        }

        private static RegistryKey<String> ofBase(String path) {
            return RegistryKey.of(PottersRegistryKeys.DECORATED_POT_BASE_OVERLAY_PATTERN, Potters.id(path));
        }

        public static Pattern of(String pattern) {
            return new Pattern(pattern, ofSide(pattern), ofBase(pattern));
        }
    }
}
