package com.bawnorton.potters.mixin;

import com.bawnorton.potters.recipe.UpgradingPottersRecipe;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    @Unique
    private final Map<Identifier, RecipeEntry<?>> potters$templates = new HashMap<>();

    @ModifyExpressionValue(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;collect(Ljava/util/stream/Collector;)Ljava/lang/Object;", remap = false))
    private Object provideUpgradingPottersRecipes(Object original) {
        Map<RecipeType<?>, Map<Identifier, RecipeEntry<?>>> recipes = new HashMap<>((Map<RecipeType<?>, Map<Identifier, RecipeEntry<?>>>) original);
        Map<Identifier, RecipeEntry<?>> crafting = new HashMap<>(recipes.get(RecipeType.CRAFTING));
        crafting.forEach((identifier, recipeEntry) -> {
            if (recipeEntry.value()
                .getIngredients()
                .stream()
                .flatMap(ingredient -> Arrays.stream(ingredient.getMatchingStacks()))
                .noneMatch(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof DecoratedPotBlock))
                return;
            String path = identifier.getPath();
            if (!path.contains("_simple")) return;

            path = path.replace("_simple", "");
            Identifier target = new Identifier(identifier.getNamespace(), path);
            potters$templates.put(target, recipeEntry);
        });

        crafting.forEach(((identifier, recipeEntry) -> {
            if (!potters$templates.containsKey(identifier)) return;

            RecipeEntry<?> template = potters$templates.get(identifier);
            crafting.put(identifier, new RecipeEntry<>(identifier, UpgradingPottersRecipe.fromSimple((Recipe<? super Inventory>) template.value())));
        }));
        recipes.put(RecipeType.CRAFTING, crafting);
        return recipes;
    }

    @ModifyExpressionValue(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;", remap = false))
    private ImmutableMap<Identifier, RecipeEntry<?>> provideUpgradingPottersRecipesToById(ImmutableMap<Identifier, RecipeEntry<?>> original) {
        ImmutableMap.Builder<Identifier, RecipeEntry<?>> builder = new ImmutableMap.Builder<>();
        builder.putAll(original.entrySet()
                           .stream()
                           .filter(entry -> !potters$templates.containsKey(entry.getKey()))
                           .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue)));
        original.forEach(((identifier, recipeEntry) -> {
            if (!potters$templates.containsKey(identifier)) return;

            RecipeEntry<?> template = potters$templates.get(identifier);
            builder.put(identifier, new RecipeEntry<>(identifier, UpgradingPottersRecipe.fromSimple((Recipe<? super Inventory>) template.value())));
        }));
        return builder.build();
    }
}
