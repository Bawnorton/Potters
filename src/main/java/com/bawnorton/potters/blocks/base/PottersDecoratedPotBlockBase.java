package com.bawnorton.potters.blocks.base;

import com.bawnorton.potters.blocks.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.inventory.PottersDecoratedPotInventoryBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public abstract class PottersDecoratedPotBlockBase extends DecoratedPotBlock {
    protected PottersDecoratedPotBlockBase(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(!(blockEntity instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity)) return ActionResult.PASS;

        ItemStack playerStack = player.getStackInHand(hand);
        PottersDecoratedPotInventoryBase inventory = pottersBlockEntity.getInventory();
        if(playerStack.isEmpty() || !inventory.canInsert(playerStack)) {
            world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            pottersBlockEntity.wobble(DecoratedPotBlockEntity.WobbleType.NEGATIVE);
        } else {
            pottersBlockEntity.wobble(DecoratedPotBlockEntity.WobbleType.POSITIVE);
            player.incrementStat(Stats.USED.getOrCreateStat(playerStack.getItem()));
            ItemStack toInsert = player.isCreative() ? playerStack.copyWithCount(1) : playerStack.split(1);
            inventory.addStack(toInsert);
            world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 1.0F, inventory.getPitch());

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
            }
            world.updateComparators(pos, this);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, getBlockEntityType()).ifPresent(blockEntity -> blockEntity.readNbtFromStack(itemStack));
        }
    }

    protected abstract BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType();

    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos) instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity ? pottersBlockEntity.asStack(getBlockEntityType(), asItem()) : super.getPickStack(world, pos, state);
    }
}
