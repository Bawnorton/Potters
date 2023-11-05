package com.bawnorton.potters.tag;

import com.bawnorton.potters.Potters;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PottersItemTags {
    public static final TagKey<Item> DECORATED_POT_MATERIALS = of("decorated_pot_materials");
    public static final TagKey<Item> BOTTOMLESS_DECORATED_POT_CORE = of("bottomless_decorated_pot_core");
    public static final TagKey<Item> POT_BLACKLIST = of("pot_blacklist");

    private static TagKey<Item> of(String path) {
        return TagKey.of(RegistryKeys.ITEM, Potters.id(path));
    }
}
