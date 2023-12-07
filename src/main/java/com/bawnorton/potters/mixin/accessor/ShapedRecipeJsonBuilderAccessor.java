package com.bawnorton.potters.mixin.accessor;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(ShapedRecipeJsonBuilder.class)
public interface ShapedRecipeJsonBuilderAccessor {
    @Accessor
    Map<Character, Ingredient> getInputs();

    @Accessor
    List<String> getPattern();

    @Accessor
    Item getOutput();
}
