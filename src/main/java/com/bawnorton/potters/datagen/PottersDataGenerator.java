package com.bawnorton.potters.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PottersDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(PottersLootTableProvider::new);
        pack.addProvider(PottersModelProvider::new);
        pack.addProvider(PottersLangProvider::new);
        pack.addProvider(PottersRecipeProvider::new);
        pack.addProvider(PottersItemTagProvider::new);
    }
}
