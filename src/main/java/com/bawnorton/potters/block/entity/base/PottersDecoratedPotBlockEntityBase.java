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
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public abstract class PottersDecoratedPotBlockEntityBase extends BlockEntity {
    private DecoratedPotBlockEntity.Sherds sherds;

    public DecoratedPotBlockEntity.WobbleType lastWobbleType;
    public long lastWobbleTime;

    protected PottersDecoratedPotBlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.sherds = DecoratedPotBlockEntity.Sherds.DEFAULT;
    }

    public abstract PottersDecoratedPotStorageBase getStorage();

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.sherds.toNbt(nbt);
        getStorage().writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.sherds = DecoratedPotBlockEntity.Sherds.fromNbt(nbt);
        getStorage().readNbt(nbt);
    }

    @Override
    public abstract Packet<ClientPlayPacketListener> toUpdatePacket();

    @Override
    public abstract NbtCompound toInitialChunkDataNbt();

    public Direction getHorizontalFacing() {
        return this.getCachedState().get(Properties.HORIZONTAL_FACING);
    }

    public DecoratedPotBlockEntity.Sherds getSherds() {
        return sherds;
    }

    public void readNbtFromStack(ItemStack stack) {
        this.sherds = DecoratedPotBlockEntity.Sherds.fromNbt(BlockItem.getBlockEntityNbt(stack));
    }

    public ItemStack asStack(BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> type, Item item) {
        return getStackWith(type, item, this.sherds);
    }

    public static ItemStack getStackWith(BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> type, Item item, DecoratedPotBlockEntity.Sherds sherds) {
        ItemStack itemStack = item.getDefaultStack();
        NbtCompound nbtCompound = sherds.toNbt(new NbtCompound());
        BlockItem.setBlockEntityNbt(itemStack, type, nbtCompound);
        return itemStack;
    }

    public void wobble(DecoratedPotBlockEntity.WobbleType wobbleType) {
        if (this.world != null && !this.world.isClient()) {
            this.world.addSyncedBlockEvent(this.getPos(), this.getCachedState().getBlock(), 1, wobbleType.ordinal());
        }
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (this.world != null && type == 1 && data >= 0 && data < DecoratedPotBlockEntity.WobbleType.values().length) {
            this.lastWobbleTime = this.world.getTime();
            this.lastWobbleType = DecoratedPotBlockEntity.WobbleType.values()[data];
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }
}
