package com.bawnorton.potters;

import com.bawnorton.potters.config.ConfigManager;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.registry.PottersBlocks;
import com.bawnorton.potters.registry.PottersItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Potters implements ModInitializer {
	public static final String MOD_ID = "potters";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.debug("Potters Initialized.");
		ConfigManager.loadConfig();
		PottersBlocks.init();
		PottersItems.init();
		PottersBlockEntityType.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}