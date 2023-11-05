package com.bawnorton.potters.block.base;

import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
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

import java.util.function.Supplier;

public abstract class PottersDecoratedPotBlockBase extends DecoratedPotBlock {
    private final Supplier<Item> materialSupplier;

    protected PottersDecoratedPotBlockBase(Supplier<Item> materialSupplier, Settings settings) {
        super(settings);
        this.materialSupplier = materialSupplier;
    }

    public Item getMaterial() {
        return materialSupplier.get();
    }

    public abstract boolean isFinite();

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity)) return ActionResult.PASS;

        ItemStack playerStack = player.getStackInHand(hand);
        PottersDecoratedPotStorageBase storage = pottersBlockEntity.getStorage();
        if (player.isSneaking() && !storage.isResourceBlank() && playerStack.isEmpty()) {
            ItemStack extracted = storage.extract();
            if (!extracted.isEmpty() && !player.giveItemStack(extracted)) {
                player.dropItem(extracted, false);
            }
            pottersBlockEntity.wobble(DecoratedPotBlockEntity.WobbleType.POSITIVE);
            world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.updateComparators(pos, this);
        } else if (!playerStack.isEmpty() && storage.canInsert(playerStack)) {
            pottersBlockEntity.wobble(DecoratedPotBlockEntity.WobbleType.POSITIVE);
            player.incrementStat(Stats.USED.getOrCreateStat(playerStack.getItem()));
            ItemStack toInsert = player.isCreative() ? playerStack.copyWithCount(1) : playerStack.split(1);
            storage.insert(toInsert);
            world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 1.0F, storage.getPitch());

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
            }
            world.updateComparators(pos, this);
        } else {
            world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            pottersBlockEntity.wobble(DecoratedPotBlockEntity.WobbleType.NEGATIVE);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, getBlockEntityType())
                .ifPresent(blockEntity -> blockEntity.readNbtFromStack(itemStack));
        }
    }

    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos) instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity ? pottersBlockEntity.asStack(getBlockEntityType(), asItem()) : super.getPickStack(world, pos, state);
    }

    protected abstract BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> getBlockEntityType();
}
