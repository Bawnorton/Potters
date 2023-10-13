package com.bawnorton.potters.mixin;

import com.bawnorton.potters.inventory.AdditionalDecoratedPotInventory;
import com.bawnorton.potters.extend.DecoratedPotBlockEntityExtender;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotBlock.class)
public abstract class DecoratedPotBlockMixin extends BlockWithEntity {
    protected DecoratedPotBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0))
    private boolean checkIfPotIsFull(boolean original, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        DecoratedPotBlockEntity blockEntity = (DecoratedPotBlockEntity) world.getBlockEntity(pos);
        if(blockEntity == null) return original;

        AdditionalDecoratedPotInventory additionalInventory = ((DecoratedPotBlockEntityExtender) blockEntity).potters$getAdditionalInventory();
        if(additionalInventory.isFull()) return true;
        return original;
    }

    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 1), cancellable = true)
    private void tryAddToAdditionalInv(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        DecoratedPotBlockEntity blockEntity = (DecoratedPotBlockEntity) world.getBlockEntity(pos);
        if(blockEntity == null) return;

        AdditionalDecoratedPotInventory additionalInventory = ((DecoratedPotBlockEntityExtender) blockEntity).potters$getAdditionalInventory();
        ItemStack playerStack = player.getStackInHand(hand);
        if(playerStack.isEmpty()) return;

        int targetSlot = additionalInventory.getSlotWhichCanCombine(playerStack);
        if(targetSlot == -1) targetSlot = additionalInventory.nextEmptySlot();
        if(targetSlot == -1) return;
        if(additionalInventory.willOverflowWith(playerStack.copyWithCount(1))) return;

        ItemStack fromPlayer = player.isCreative() ? playerStack.copyWithCount(1) : playerStack.split(1);
        blockEntity.method_54301(DecoratedPotBlockEntity.class_8837.POSITIVE);
        player.incrementStat(Stats.USED.getOrCreateStat(playerStack.getItem()));
        ItemStack fromInventory = additionalInventory.getStack(targetSlot);
        if(fromInventory.isEmpty()) {
            additionalInventory.setStack(targetSlot, fromPlayer);
        } else {
            fromInventory.increment(1);
        }
        float pitch = additionalInventory.getFillPercentage();
        world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 1.0F, 0.7F + 0.5F * pitch);
        if(world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
        }

        world.updateComparators(pos, this);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @ModifyArg(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 0), index = 5)
    private float playCorrectPitch(float original, @Local DecoratedPotBlockEntity blockEntity) {
        AdditionalDecoratedPotInventory additionalInventory = ((DecoratedPotBlockEntityExtender) blockEntity).potters$getAdditionalInventory();
        return 0.7F + 0.5F * additionalInventory.getFillPercentage();
    }

    @Inject(method = "onStateReplaced", at = @At("HEAD"))
    private void scatterAdditionalInv(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        DecoratedPotBlockEntityExtender extender = (DecoratedPotBlockEntityExtender) world.getBlockEntity(pos);
        if(extender == null) return;

        AdditionalDecoratedPotInventory additionalInventory = extender.potters$getAdditionalInventory();
        ItemScatterer.spawn(world, pos, additionalInventory);
    }

    @ModifyReturnValue(method = "getComparatorOutput", at = @At("RETURN"))
    private int addAdditionalInv(int original, BlockState state, World world, BlockPos pos) {
        DecoratedPotBlockEntityExtender extender = (DecoratedPotBlockEntityExtender) world.getBlockEntity(pos);
        if(extender == null) return original;

        AdditionalDecoratedPotInventory additionalInventory = extender.potters$getAdditionalInventory();
        return MathHelper.lerpPositive(additionalInventory.getFillPercentage(), 0, 15);
    }
}
