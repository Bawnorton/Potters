package com.bawnorton.potters.extend;

import com.bawnorton.potters.inventory.AdditionalDecoratedPotInventory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;

public interface DecoratedPotBlockEntityExtender {
    CombinedStorage<ItemVariant, Storage<ItemVariant>> potters$getInventoryWrapper();
    AdditionalDecoratedPotInventory potters$getAdditionalInventory();
}
