package com.bawnorton.potters.datagen;

import com.bawnorton.potters.registry.PottersBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PottersLangProvider extends FabricLanguageProvider {
    public PottersLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        PottersBlocks.forEach(block -> {
            String patternName = PottersBlocks.getName(block);
            String capitalized = patternName.substring(0, 1).toUpperCase() + patternName.substring(1);
            translationBuilder.add(block, "%s Decorated Pot".formatted(capitalized));
        });
        translationBuilder.add("itemGroup.potters", "Potters");
    }
}
