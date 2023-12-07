package com.bawnorton.potters.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;

public class UpgradingPottersRecipe extends NbtPreservingRecipe {
    private UpgradingPottersRecipe(CraftingRecipeCategory category, Recipe<? super Inventory> simple) {
        super(category, simple, ((input, output) -> {
            Item inputItem = input.getItem();
            System.out.println(inputItem + " => " + output.getItem());
            if(Block.getBlockFromItem(inputItem) instanceof DecoratedPotBlock) {
                System.out.println(input.getNbt());
            }
        }));
    }

    public UpgradingPottersRecipe(CraftingRecipeCategory category) {
        super(category, null, null);
    }

    public static UpgradingPottersRecipe fromSimple(Recipe<? super Inventory> recipe) {
        return new UpgradingPottersRecipe(CraftingRecipeCategory.MISC, recipe);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PottersRecipeSerializer.UPGRADING_POTTERS;
    }
}
