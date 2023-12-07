package com.bawnorton.potters.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

public interface PottersRecipeSerializer<T extends Recipe<?>> extends RecipeSerializer<T> {
    RecipeSerializer<NbtPreservingRecipe> UPGRADING_POTTERS = RecipeSerializer.register("upgrading_potters", new SpecialRecipeSerializer<>(UpgradingPottersRecipe::new));

    static void init() {
        // no-op
    }
}
