package com.bawnorton.potters.registry;

import com.bawnorton.potters.Potters;
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
import java.util.function.Consumer;

import static com.bawnorton.potters.registry.PottersBlocks.IRON_DECORATED_POT;

public class PottersItems {
    public static final ItemGroup POTTERS_ITEM_GROUP;
    private static final List<Item> ALL;

    static {
        ALL = new ArrayList<>();
        PottersBlocks.forEach(PottersItems::register);

        POTTERS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Potters.id("item_group"), FabricItemGroup.builder()
            .icon(() -> IRON_DECORATED_POT.asItem().getDefaultStack())
            .displayName(Text.translatable("itemGroup.potters"))
            .entries(((displayContext, entries) -> entries.addAll(ALL.stream().map(Item::getDefaultStack).toList())))
            .build());
    }

    public static void init() {
        // no-op
    }

    public static void forEach(Consumer<Item> itemConsumer) {
        ALL.forEach(itemConsumer);
    }

    private static void register(Block block) {
        register(new BlockItem(block, new Item.Settings()));
    }

    private static void register(BlockItem item) {
        register(item.getBlock(), item);
    }

    private static void register(Block block, Item item) {
        register(Registries.BLOCK.getId(block), item);
    }

    private static void register(Identifier id, Item item) {
        register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    private static void register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem blockItem) blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        ALL.add(item);
        Registry.register(Registries.ITEM, key, item);
    }
}
