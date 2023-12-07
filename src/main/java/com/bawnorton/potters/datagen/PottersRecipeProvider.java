package com.bawnorton.potters.datagen;

import com.bawnorton.potters.recipe.UpgradingPottersRecipe;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.tag.PottersItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;

public class PottersRecipeProvider extends FabricRecipeProvider {
    public PottersRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        PottersBlocks.forEachFinite(pottersBlock -> {
            if (pottersBlock == PottersBlocks.NETHERITE_DECORATED_POT) return;
            Item item = pottersBlock.asItem();

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, item)
                .input('#', pottersBlock.getUpgradeCore())
                .input('M', pottersBlock.getMaterial())
                .pattern("M M")
                .pattern(" # ")
                .pattern("M M")
                .criterion("has_decorated_pot_material", conditionsFromTag(PottersItemTags.DECORATED_POT_MATERIALS))
                .offerTo(exporter, Registries.ITEM.getId(item).withSuffixedPath("_simple"));
            ComplexRecipeJsonBuilder.create(UpgradingPottersRecipe::new).offerTo(exporter, Registries.ITEM.getId(item));
        });
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(PottersBlocks.DIAMOND_DECORATED_POT.asItem()), Ingredient.ofItems(Items.NETHERITE_INGOT), RecipeCategory.DECORATIONS, PottersBlocks.NETHERITE_DECORATED_POT.asItem())
            .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT))
            .offerTo(exporter, Registries.ITEM.getId(PottersBlocks.NETHERITE_DECORATED_POT.asItem())
                .withSuffixedPath("_smithing"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, PottersBlocks.BOTTOMLESS_DECORATED_POT.asItem())
            .input('C', PottersItemTags.BOTTOMLESS_DECORATED_POT_CORE)
            .input('M', Items.ENDER_PEARL)
            .input('#', PottersBlocks.NETHERITE_DECORATED_POT.asItem())
            .pattern("M M")
            .pattern(" # ")
            .pattern(" C ")
            .criterion("has_bottomless_core", conditionsFromTag(PottersItemTags.BOTTOMLESS_DECORATED_POT_CORE))
            .offerTo(exporter, Registries.ITEM.getId(PottersBlocks.BOTTOMLESS_DECORATED_POT.asItem()).withSuffixedPath("_simple"));
        ComplexRecipeJsonBuilder.create(UpgradingPottersRecipe::new).offerTo(exporter, Registries.ITEM.getId(PottersBlocks.BOTTOMLESS_DECORATED_POT.asItem()));

    }
}
