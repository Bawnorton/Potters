package com.bawnorton.potters.mixin;

import com.bawnorton.potters.block.BottomlessDecoratedPotBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.state.property.Property;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DecoratedPotBlock.class)
public abstract class DecoratedPotBlockMixin {
    @SuppressWarnings("ConstantValue")
    @WrapOperation(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
        ),
        slice = @Slice(
            from = @At(
                value = "FIELD",
                opcode = Opcodes.GETSTATIC,
                target = "Lnet/minecraft/block/DecoratedPotBlock;CRACKED:Lnet/minecraft/state/property/BooleanProperty;"
            )
        )
    )
    private Object dontApplyCrackedToBottomless(BlockState instance, Property<Boolean> property, Comparable<Boolean> comparable, Operation<Object> original) {
        if(!(((Object) this) instanceof BottomlessDecoratedPotBlock)) return original.call(instance, property, comparable);
        return instance;
    }
}
