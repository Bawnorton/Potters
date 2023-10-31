package com.bawnorton.potters;

import com.bawnorton.potters.config.ConfigManager;
import com.bawnorton.potters.networking.Networking;
import com.bawnorton.potters.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Potters implements ModInitializer {
	public static final String MOD_ID = "potters";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.debug("Potters Initialized.");
		ConfigManager.loadConfigs();
		Networking.init();
		PottersRegistryKeys.init();
		PottersRegistries.init();
		PottersBlocks.init();
		PottersItems.init();
		PottersBlockEntityType.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}