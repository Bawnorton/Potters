package com.bawnorton.potters.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
    @ModifyReturnValue(method = "getInventoryAt(Lnet/minecraft/world/World;DDD)Lnet/minecraft/inventory/Inventory;", at = @At("RETURN"))
    private static Inventory removeDecoratedPotInventory(Inventory original) { // passes inventory requests to the transfer api
        if(original instanceof DecoratedPotBlockEntity) return null;
        return original;
    }
}
