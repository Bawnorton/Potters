package com.bawnorton.potters;

import com.bawnorton.potters.blocks.entity.base.PottersDecoratedPotBlockEntityBase;
import com.bawnorton.potters.registry.PottersBlockEntityType;
import com.bawnorton.potters.registry.PottersItems;
import com.bawnorton.potters.render.PottersDecoratedPotBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class PottersClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		for(BlockEntityType<? extends PottersDecoratedPotBlockEntityBase> blockEntityType : PottersBlockEntityType.ALL) {
			BlockEntityRendererFactories.register(blockEntityType, PottersDecoratedPotBlockEntityRenderer::new);
		}
		for(Item item: PottersItems.ALL) {
			BuiltinItemRendererRegistry.INSTANCE.register(item, PottersDecoratedPotBlockEntityRenderer::renderItemStack);
		}

		HudRenderCallback.EVENT.register(((drawContext, tickDelta) -> {
			Entity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
            if (!(hitResult instanceof BlockHitResult blockHitResult)) return;

            BlockPos blockPos = blockHitResult.getBlockPos();
            if (blockPos == null) return;

            BlockEntity blockEntity = player.getWorld().getBlockEntity(blockPos);
            if (!(blockEntity instanceof PottersDecoratedPotBlockEntityBase pottersBlockEntity)) return;

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            drawContext.drawText(textRenderer, String.valueOf(pottersBlockEntity.getItemCount()), 10, 10, 0xFFFFFF, false);
        }));
	}
}