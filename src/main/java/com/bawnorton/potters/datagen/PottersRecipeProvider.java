package com.bawnorton.potters.datagen;

import com.bawnorton.potters.recipe.PottersRecipeSerializer;
import com.bawnorton.potters.registry.PottersItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.Registries;

public class PottersRecipeProvider extends FabricRecipeProvider {
    public PottersRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        PottersItems.forEach(item -> ComplexRecipeJsonBuilder.create(PottersRecipeSerializer.CRAFTING_POTTERS_DECORATED_POT).offerTo(exporter, Registries.ITEM.getId(item)));
    }
}
