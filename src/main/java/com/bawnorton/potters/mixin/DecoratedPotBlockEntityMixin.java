package com.bawnorton.potters.mixin;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockEntityMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;<init>(Lnet/minecraft/block/entity/BlockEntityType;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    private static BlockEntityType<?> passCorrectBlockEntityType(BlockEntityType<?> type) {
        BlockEntityType<?> pottersType = PottersDecoratedPotBlockEntityBase.TYPE.get();
        if(pottersType == null) return type;

        PottersDecoratedPotBlockEntityBase.TYPE.remove();
        return pottersType;
    }
}
