package com.bawnorton.potters.storage;

import com.bawnorton.potters.tag.PottersItemTags;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.impl.transfer.transaction.TransactionManagerImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class PottersDecoratedPotStorageBase extends SingleItemStorage {
    private final List<Consumer<PottersDecoratedPotStorageBase>> listeners = new ArrayList<>();

    public void addListener(Consumer<PottersDecoratedPotStorageBase> listener) {
        listeners.add(listener);
    }

    public void clear() {
        variant = getBlankVariant();
        setCount(0);
    }

    @Override
    protected boolean canInsert(ItemVariant variant) {
        if (variant.getItem().getDefaultStack().isIn(PottersItemTags.POT_BLACKLIST)) return false;
        if (isResourceBlank()) return true;
        if (!variant.equals(getResource())) return false;
        return getResource().nbtMatches(variant.getNbt());
    }

    @Override
    public abstract void writeNbt(NbtCompound nbt);

    public boolean canInsert(ItemStack stack) {
        return canInsert(ItemVariant.of(stack));
    }

    @SuppressWarnings("UnstableApiUsage")
    public void insert(ItemStack stack) {
        TransactionManagerImpl transactionManager = TransactionManagerImpl.MANAGERS.get();
        TransactionContext openTransaction;
        if (transactionManager.isOpen()) {
            openTransaction = transactionManager.getCurrentUnsafe();
            if (openTransaction == null) openTransaction = transactionManager.openOuter();
        } else {
            openTransaction = transactionManager.openOuter();
        }

        insert(ItemVariant.of(stack), 1, openTransaction);
        openTransaction.getOpenTransaction(openTransaction.nestingDepth()).commit();
        listeners.forEach(listener -> listener.accept(this));
    }

    @SuppressWarnings("UnstableApiUsage")
    public ItemStack extract() {
        TransactionManagerImpl transactionManager = TransactionManagerImpl.MANAGERS.get();
        TransactionContext openTransaction;
        long extracted;
        if (transactionManager.isOpen()) {
            openTransaction = transactionManager.getCurrentUnsafe();
            if (openTransaction == null) openTransaction = transactionManager.openOuter();
        } else {
            openTransaction = transactionManager.openOuter();
        }
        Item item = getResource().getItem();
        NbtCompound nbt = getResource().getNbt();
        // can't guarantee that getResource() will return the same after the transaction
        extracted = extract(getResource(), 1, openTransaction);
        openTransaction.getOpenTransaction(openTransaction.nestingDepth()).commit();
        listeners.forEach(listener -> listener.accept(this));
        ItemStack stack = new ItemStack(item, (int) extracted);
        stack.setNbt(nbt);
        return stack;
    }

    public abstract Number getCount();

    public abstract void setCount(Number number);

    public abstract float getPitch();

    @Override
    public abstract void readNbt(NbtCompound nbt);
}
