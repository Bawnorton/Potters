package com.bawnorton.potters.extend;

import com.bawnorton.potters.inventory.AdditionalDecoratedPotInventory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

public interface DecoratedPotBlockEntityExtender {
    Storage<ItemVariant> potters$getInventoryWrapper();
    AdditionalDecoratedPotInventory potters$getAdditionalInventory();
}
