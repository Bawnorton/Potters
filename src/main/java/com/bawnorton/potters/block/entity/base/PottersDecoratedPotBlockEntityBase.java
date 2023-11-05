package com.bawnorton.potters.block.entity.base;

import com.bawnorton.potters.storage.PottersDecoratedPotStorageBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public abstract class PottersDecoratedPotBlockEntityBase extends DecoratedPotBlockEntity {
    public static final ThreadLocal<BlockEntityType<?>> TYPE = ThreadLocal.withInitial(() -> null);

    protected PottersDecoratedPotBlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(setType(pos, type), state);
    }

    private static BlockPos setType(BlockPos pos, BlockEntityType<?> type) {
        TYPE.set(type);
        return pos;
    }

    public static ItemStack getStackWith(BlockEntityType<? extends BlockEntity> type, Item asItem, DecoratedPotBlockEntity.Sherds sherds) {
        ItemStack itemStack = asItem.getDefaultStack();
        NbtCompound nbtCompound = sherds.toNbt(new NbtCompound());
        BlockItem.setBlockEntityNbt(itemStack, type, nbtCompound);
        return itemStack;
    }

    public abstract PottersDecoratedPotStorageBase getStorage();

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        getStorage().readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        getStorage().writeNbt(nbt);
    }

    @Override
    public abstract BlockEntityUpdateS2CPacket toUpdatePacket();

    @Override
    public abstract NbtCompound toInitialChunkDataNbt();

    public ItemStack asStack(BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> type, Item asItem) {
        return getStackWith(type, asItem, this.getSherds());
    }
}
