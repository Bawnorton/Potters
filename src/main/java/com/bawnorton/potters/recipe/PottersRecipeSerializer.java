package com.bawnorton.potters.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

public interface PottersRecipeSerializer<T extends Recipe<?>> extends RecipeSerializer<T> {
    RecipeSerializer<CraftingPottersDecoratedPotRecipe> CRAFTING_POTTERS_DECORATED_POT = RecipeSerializer.register("crafting_potters_decorated_pot", new SpecialRecipeSerializer<>(CraftingPottersDecoratedPotRecipe::new));

    static void init() {
        // no-op
    }
}
