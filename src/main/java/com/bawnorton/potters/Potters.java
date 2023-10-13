package com.bawnorton.potters;

import com.bawnorton.potters.config.ConfigManager;
import com.bawnorton.potters.extend.DecoratedPotBlockEntityExtender;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Potters implements ModInitializer {
	public static final String MOD_ID = "potters";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.debug("Potters Initialized.");
		ConfigManager.loadConfig();
		ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
			if (blockEntity instanceof DecoratedPotBlockEntityExtender extender) {
				return extender.potters$getInventoryWrapper();
			}
			return null;
		}, BlockEntityType.DECORATED_POT);
	}
}