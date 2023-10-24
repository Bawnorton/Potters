package com.bawnorton.potters.mixin;

import com.bawnorton.potters.extend.DecoratedPotBlockEntityExtender;
import com.bawnorton.potters.inventory.AdditionalDecoratedPotInventory;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockBlockEntityMixin extends BlockEntity implements SingleStackInventory, DecoratedPotBlockEntityExtender {
    @Unique
    private CombinedStorage<ItemVariant, Storage<ItemVariant>> potters$inventoryWrapper;
    @Unique
    private AdditionalDecoratedPotInventory potters$additionalInventory;

    protected DecoratedPotBlockBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        potters$additionalInventory = potters$getAdditionalInventory();
        potters$inventoryWrapper = potters$getInventoryWrapper();
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void writeAdditionalInventory(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("AdditionalInventory", potters$getAdditionalInventory().toNbtList());
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void readAdditionalInventory(NbtCompound nbt, CallbackInfo ci) {
        potters$getAdditionalInventory().readNbtList(nbt.getList("AdditionalInventory", 10));
    }

    @ModifyReturnValue(method = "decreaseStack", at = @At("RETURN"))
    private ItemStack decrementAdditionalInventory(ItemStack original, int count) {
        if (original != ItemStack.EMPTY) return original;

        int slot = potters$getAdditionalInventory().nextNonEmptySlot();
        if (slot == -1) return original;

        ItemStack stack = potters$getAdditionalInventory().getStack(slot);
        stack.decrement(count);
        if (stack.isEmpty()) {
            stack = ItemStack.EMPTY;
            potters$getAdditionalInventory().setStack(slot, stack);
        }
        return stack;
    }

    @Override
    public AdditionalDecoratedPotInventory potters$getAdditionalInventory() {
        if(potters$additionalInventory == null) potters$additionalInventory = AdditionalDecoratedPotInventory.forEntity((DecoratedPotBlockEntity) (Object) this);
        return potters$additionalInventory;
    }

    @Override
    public CombinedStorage<ItemVariant, Storage<ItemVariant>> potters$getInventoryWrapper() {
        if(potters$inventoryWrapper == null) potters$inventoryWrapper = new CombinedStorage<>(List.of(
            InventoryStorage.of(this, null),
            InventoryStorage.of(potters$getAdditionalInventory(), null)
        ));
        return potters$inventoryWrapper;
    }
}
