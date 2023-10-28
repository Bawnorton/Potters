package com.bawnorton.potters.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.impl.transfer.transaction.TransactionManagerImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public abstract class PottersDecoratedPotStorageBase extends SingleItemStorage { @Override
    protected boolean canInsert(ItemVariant variant) {
        if(isResourceBlank()) return true;
        return variant.equals(getResource());
    }

    public boolean canInsert(ItemStack stack) {
        return canInsert(ItemVariant.of(stack));
    }

    @SuppressWarnings("UnstableApiUsage")
    public void insert(ItemStack stack) {
        TransactionManagerImpl transactionManager = TransactionManagerImpl.MANAGERS.get();
        TransactionContext openTransaction;
        if(transactionManager.isOpen()) {
            openTransaction = transactionManager.getCurrentUnsafe();
            if(openTransaction == null) openTransaction = transactionManager.openOuter();
        } else {
            openTransaction = transactionManager.openOuter();
        }
        insert(ItemVariant.of(stack), 1, openTransaction);
        openTransaction.getOpenTransaction(openTransaction.nestingDepth()).commit();
    }

    @SuppressWarnings("UnstableApiUsage")
    public ItemStack extract() {
        TransactionManagerImpl transactionManager = TransactionManagerImpl.MANAGERS.get();
        TransactionContext openTransaction;
        long extracted;
        if(transactionManager.isOpen()) {
            openTransaction = transactionManager.getCurrentUnsafe();
            if(openTransaction == null) openTransaction = transactionManager.openOuter();
        } else {
            openTransaction = transactionManager.openOuter();
        }
        Item item = getResource().getItem();
        extracted = extract(getResource(), 1, openTransaction);
        openTransaction.getOpenTransaction(openTransaction.nestingDepth()).commit();
        return new ItemStack(item, (int) extracted);
    }

    public abstract Number getCount();

    public abstract float getPitch();

    @Override
    public abstract void readNbt(NbtCompound nbt);

    @Override
    public abstract void writeNbt(NbtCompound nbt);
}
