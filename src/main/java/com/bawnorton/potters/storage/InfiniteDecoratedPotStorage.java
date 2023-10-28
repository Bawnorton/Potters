package com.bawnorton.potters.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.NbtCompound;

import java.math.BigInteger;

public class InfiniteDecoratedPotStorage extends PottersDecoratedPotStorageBase {
    private BigInteger count = BigInteger.ZERO;

    {
        amount = 1; // set to allow extracting to work
    }

    @Override
    public long getCapacity(ItemVariant variant) {
        throw new UnsupportedOperationException("Infinite storage has infinite capacity");
    }

    @Override
    public long insert(ItemVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(insertedVariant, maxAmount);

        if ((insertedVariant.equals(variant) || variant.isBlank()) && canInsert(insertedVariant)) {
            BigInteger insertedAmount = BigInteger.valueOf(maxAmount);

            if (insertedAmount.compareTo(BigInteger.ZERO) > 0) {
                updateSnapshots(transaction);

                if (variant.isBlank()) {
                    variant = insertedVariant;
                    count = insertedAmount;
                } else {
                    count = count.add(insertedAmount);
                }

                return insertedAmount.longValue();
            }
        }

        return 0;
    }

    @Override
    public long extract(ItemVariant extractedVariant, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(extractedVariant, maxAmount);

        if (extractedVariant.equals(variant) && canExtract(extractedVariant)) {
            BigInteger extractedAmount = BigInteger.valueOf(maxAmount);

            if (extractedAmount.compareTo(BigInteger.ZERO) > 0) {
                updateSnapshots(transaction);
                count = count.subtract(extractedAmount);

                if (count.compareTo(BigInteger.ZERO) <= 0) {
                    variant = getBlankVariant();
                }

                return extractedAmount.longValue();
            }
        }

        return 0;
    }

    @Override
    public Number getCount() {
        return count;
    }

    @Override
    public float getPitch() {
        return 0.7F;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        variant = ItemVariant.fromNbt(nbt.getCompound("variant"));
        count = new BigInteger(nbt.getString("count"));
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("variant", variant.toNbt());
        nbt.putString("count", count.toString());
    }
}
