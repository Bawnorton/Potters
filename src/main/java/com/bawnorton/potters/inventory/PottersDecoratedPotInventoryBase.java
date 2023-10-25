package com.bawnorton.potters.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class PottersDecoratedPotInventoryBase implements Inventory, RecipeInputProvider {
    private final List<InventoryChangedListener> listeners;
    protected Item type;

    protected PottersDecoratedPotInventoryBase() {
        this.type = Items.AIR;
        this.listeners = new ArrayList<>();
    }

    public void addListener(InventoryChangedListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(InventoryChangedListener listener) {
        this.listeners.remove(listener);
    }

    public boolean canInsert(ItemStack stack) {
        if(isEmpty()) return true;
        return stack.getItem() == type;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return true;
//        return canInsert(stack);
    }

    public int size() {
        return 1;
    }

    @Override
    public ItemStack getStack(int slot) {
        return type.getDefaultStack();
    }

    public abstract Number getCount();

    public abstract ItemStack addStack(ItemStack stack);

    public abstract float getPitch();

    @Override
    public boolean isEmpty() {
        return type == Items.AIR;
    }

    @Override
    public void markDirty() {
        for (InventoryChangedListener listener : this.listeners) {
            listener.onInventoryChanged(this);
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        type = Items.AIR;
        markDirty();
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        if (!isEmpty()) {
            finder.addInput(type.getDefaultStack());
        }
    }

    public void fromNbt(NbtCompound nbt) {
        clear();
        if(nbt.contains("type")) {
            type = Registries.ITEM.get(new Identifier(nbt.getString("type")));
        }
    }

    public void toNbt(NbtCompound nbt) {
        if(!isEmpty()) {
            nbt.putString("type", Registries.ITEM.getId(type).toString());
        }
    }

    public Item getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PottersDecoratedPotInventoryBase{" +
                "type=" + type +
                '}';
    }
}
