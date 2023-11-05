package com.bawnorton.potters.recipe;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.tag.PottersItemTags;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;

import java.util.Optional;

public class CraftingPottersDecoratedPotRecipe extends SpecialCraftingRecipe {
    public CraftingPottersDecoratedPotRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        if(!this.fits(inventory.getWidth(), inventory.getHeight())) return false;
        ItemStack[] sherds = new ItemStack[] {
            inventory.getStack(1),
            inventory.getStack(3),
            inventory.getStack(5),
            inventory.getStack(7)
        };
        for(ItemStack sherd: sherds) {
            if(!sherd.isIn(ItemTags.DECORATED_POT_INGREDIENTS)) return false;
        }

        ItemStack[] corners = new ItemStack[] {
            inventory.getStack(0),
            inventory.getStack(2),
            inventory.getStack(6),
            inventory.getStack(8)
        };
        ItemStack material = corners[0];
        for(ItemStack corner: corners) {
            if(!ItemStack.areItemsEqual(corner, material)) return false;
            if(!corner.isIn(PottersItemTags.DECORATED_POT_MATERIALS)) return false;
        }

        if(material.getItem().equals(PottersBlocks.BOTTOMLESS_DECORATED_POT.getMaterial())) {
            ItemStack center = inventory.getStack(4);
            return center.isIn(PottersItemTags.BOTTOMLESS_DECORATED_POT_CORE);
        }

        return true;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        DecoratedPotBlockEntity.Sherds sherds = new DecoratedPotBlockEntity.Sherds(
            inventory.getStack(1).getItem(),
            inventory.getStack(3).getItem(),
            inventory.getStack(5).getItem(),
            inventory.getStack(7).getItem()
        );
        Item material = inventory.getStack(0).getItem();
        Optional<ItemStack> result = PottersBlocks.stream()
            .filter(block -> block.getMaterial().equals(material))
            .findFirst()
            .map(block -> PottersDecoratedPotBlockEntityBase.getStackWith(block.isFinite() ? PottersBlockEntityType.FINITE_DECORATED_POT : PottersBlockEntityType.BOTTOMLESS_DECORATED_POT, block.asItem(), sherds));
        return result.orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean fits(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PottersRecipeSerializer.CRAFTING_POTTERS_DECORATED_POT;
    }
}
