package com.bawnorton.potters.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class NbtPreservingRecipe extends SpecialCraftingRecipe {
    private final @Nullable Recipe<? super Inventory> simple;
    private final @Nullable NbtPreservingStrategy nbtPreservingStrategy;

    protected NbtPreservingRecipe(CraftingRecipeCategory category, Recipe<? super Inventory> simple, @Nullable NbtPreservingStrategy nbtPreservingStrategy) {
        super(category);
        this.simple = simple;
        this.nbtPreservingStrategy = nbtPreservingStrategy;
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        if(!fits(inventory.getWidth(), inventory.getHeight())) return false;
        assert simple != null; // fits already checks this
        return simple.matches(inventory, world);
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        assert simple != null; // matcher already checks this
        ItemStack output = simple.craft(inventory, registryManager);
        if(nbtPreservingStrategy == null) return output;
        inventory.getHeldStacks().forEach(stack -> {
            if(stack.getItem() == Items.AIR) return;
            nbtPreservingStrategy.apply(stack, output);
        });
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        if(simple == null) throw new IllegalStateException("NbtPreservingRecipe's simple recipe was never provided! (Likely compat issue)");
        return simple.fits(width, height);
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();
}
