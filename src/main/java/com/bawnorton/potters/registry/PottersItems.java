package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
import com.bawnorton.potters.item.BottomlessDecoratedPotBlockItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PottersItems {
    public static final List<Item> ALL;
    public static final Item IRON_DECORATED_POT;
    public static final Item GOLD_DECORATED_POT;
    public static final Item DIAMOND_DECORATED_POT;
    public static final Item NETHERITE_DECORATED_POT;
    public static final Item BOTTOMLESS_DECORATED_POT;

    public static final ItemGroup POTTERS_ITEM_GROUP;

    static {
        ALL = new ArrayList<>();
        IRON_DECORATED_POT = register(PottersBlocks.IRON_DECORATED_POT);
        GOLD_DECORATED_POT = register(PottersBlocks.GOLD_DECORATED_POT);
        DIAMOND_DECORATED_POT = register(PottersBlocks.DIAMOND_DECORATED_POT);
        NETHERITE_DECORATED_POT = register(PottersBlocks.NETHERITE_DECORATED_POT);
        BOTTOMLESS_DECORATED_POT = register(new BottomlessDecoratedPotBlockItem(PottersBlocks.BOTTOMLESS_DECORATED_POT, new Item.Settings()));

        POTTERS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Potters.id("item_group"), FabricItemGroup.builder()
            .icon(IRON_DECORATED_POT::getDefaultStack)
            .displayName(Text.translatable("itemGroup.potters"))
            .entries(((displayContext, entries) -> entries.addAll(ALL.stream().map(Item::getDefaultStack).toList())))
            .build());
    }

    public static void init() {
        // no-op
    }

    private static Item register(Block block) {
        return register(new BlockItem(block, new Item.Settings()));
    }

    private static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    private static Item register(Block block, Item item) {
        return register(Registries.BLOCK.getId(block), item);
    }

    private static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    private static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem blockItem) blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        ALL.add(item);
        return Registry.register(Registries.ITEM, key, item);
    }
}
