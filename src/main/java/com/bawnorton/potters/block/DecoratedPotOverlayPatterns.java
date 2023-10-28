package com.bawnorton.potters.block;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.registry.PottersRegistries;
import com.bawnorton.potters.registry.PottersRegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Map;

public class DecoratedPotOverlayPatterns {
    private static final String IRON_PATTERN = "iron";
    private static final String GOLD_PATTERN = "gold";
    private static final String DIAMOND_PATTERN = "diamond";
    private static final String NETHERITE_PATTERN = "netherite";
    private static final String BOTTOMLESS_PATTERN = "bottomless";
    private static final Map<Block, Pattern> BLOCK_TO_PATTERN = Map.ofEntries(
        Map.entry(PottersBlocks.IRON_DECORATED_POT, Pattern.IRON),
        Map.entry(PottersBlocks.GOLD_DECORATED_POT, Pattern.GOLD),
        Map.entry(PottersBlocks.DIAMOND_DECORATED_POT, Pattern.DIAMOND),
        Map.entry(PottersBlocks.NETHERITE_DECORATED_POT, Pattern.NETHERITE),
        Map.entry(PottersBlocks.BOTTOMLESS_DECORATED_POT, Pattern.BOTTOMLESS)
    );

    private static RegistryKey<String> ofSide(String path) {
        return RegistryKey.of(PottersRegistryKeys.DECORATED_POT_SIDE_OVERLAY_PATTERN, Potters.id(path));
    }

    private static RegistryKey<String> ofBase(String path) {
        return RegistryKey.of(PottersRegistryKeys.DECORATED_POT_BASE_OVERLAY_PATTERN, Potters.id(path));
    }

    public static Identifier getSideTextureId(RegistryKey<String> key) {
        return new Identifier("minecraft", key.getValue().withPrefixedPath("entity/decorated_pot_side_overlay/").getPath());
    }

    public static Identifier getBaseTextureId(RegistryKey<String> key) {
        return new Identifier("minecraft", key.getValue().withPrefixedPath("entity/decorated_pot_base_overlay/").getPath());
    }

    public static RegistryKey<String> sideFromBlock(PottersDecoratedPotBlockBase block) {
        return BLOCK_TO_PATTERN.get(block).side();
    }

    public static RegistryKey<String> baseFromBlock(PottersDecoratedPotBlockBase block) {
        return BLOCK_TO_PATTERN.get(block).base();
    }

    public static void register() {
        Registry.register(PottersRegistries.DECORATED_POT_SIDES_OVERLAY_PATTERN, Pattern.IRON.side(), IRON_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_SIDES_OVERLAY_PATTERN, Pattern.GOLD.side(), GOLD_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_SIDES_OVERLAY_PATTERN, Pattern.DIAMOND.side(), DIAMOND_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_SIDES_OVERLAY_PATTERN, Pattern.NETHERITE.side(), NETHERITE_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_SIDES_OVERLAY_PATTERN, Pattern.BOTTOMLESS.side(), BOTTOMLESS_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, Pattern.IRON.base(), IRON_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, Pattern.GOLD.base(), GOLD_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, Pattern.DIAMOND.base(), DIAMOND_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, Pattern.NETHERITE.base(), NETHERITE_PATTERN);
        Registry.register(PottersRegistries.DECORATED_POT_BASE_OVERLAY_PATTERN, Pattern.BOTTOMLESS.base(), BOTTOMLESS_PATTERN);
    }

    private record Pattern(RegistryKey<String> side, RegistryKey<String> base) {
        public static final Pattern IRON = new Pattern(ofSide(IRON_PATTERN), ofBase(IRON_PATTERN));
        public static final Pattern NETHERITE = new Pattern(ofSide(NETHERITE_PATTERN), ofBase(NETHERITE_PATTERN));
        public static final Pattern GOLD = new Pattern(ofSide(GOLD_PATTERN), ofBase(GOLD_PATTERN));
        public static final Pattern DIAMOND = new Pattern(ofSide(DIAMOND_PATTERN), ofBase(DIAMOND_PATTERN));
        public static final Pattern BOTTOMLESS = new Pattern(ofSide(BOTTOMLESS_PATTERN), ofBase(BOTTOMLESS_PATTERN));
    }
}
