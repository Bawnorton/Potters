package com.bawnorton.potters.client;

import com.bawnorton.potters.client.model.PottersEntityModelLayers;
import com.bawnorton.potters.client.render.PottersBlockEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class PottersClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		PottersBlockEntityRenderers.init();
		PottersEntityModelLayers.init();
	}
}