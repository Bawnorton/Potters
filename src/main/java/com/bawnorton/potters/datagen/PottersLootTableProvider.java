package com.bawnorton.potters.datagen;

import com.bawnorton.potters.block.BottomlessDecoratedPotBlock;
import com.bawnorton.potters.block.FiniteDecoratedPotBlock;
import com.bawnorton.potters.registry.PottersBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class PottersLootTableProvider extends FabricBlockLootTableProvider {
    public PottersLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        PottersBlocks.forEachFinite(block -> addDrop(block, this::finiteDecoratedPotDrops));
        addDrop(PottersBlocks.BOTTOMLESS_DECORATED_POT, this::infiniteDecoratedPotDrops);
    }

    private LootTable.Builder finiteDecoratedPotDrops(Block block) {
        return LootTable.builder()
            .pool(LootPool.builder()
                      .rolls(ConstantLootNumberProvider.create(1))
                      .with(DynamicEntry.builder(FiniteDecoratedPotBlock.MATERIAL_AND_SHERDS)
                                .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                   .properties(StatePredicate.Builder.create()
                                                                   .exactMatch(FiniteDecoratedPotBlock.CRACKED, true)))
                                .alternatively(ItemEntry.builder(block)
                                                   .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                              .withOperation("sherds", "BlockEntityTag.sherds")))));
    }

    private LootTable.Builder infiniteDecoratedPotDrops(Block block) {
        return LootTable.builder()
            .pool(LootPool.builder()
                      .rolls(ConstantLootNumberProvider.create(1))
                      .with(DynamicEntry.builder(BottomlessDecoratedPotBlock.WITH_CONTENT)
                                .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                   .properties(StatePredicate.Builder.create()
                                                                   .exactMatch(BottomlessDecoratedPotBlock.EMTPY, false)))
                                .alternatively(ItemEntry.builder(block)
                                                   .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                              .withOperation("sherds", "BlockEntityTag.sherds")))));
    }
}
