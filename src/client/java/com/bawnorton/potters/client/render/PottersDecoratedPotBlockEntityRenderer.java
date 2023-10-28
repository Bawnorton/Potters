package com.bawnorton.potters.client.render;

import com.bawnorton.potters.block.DecoratedPotOverlayPatterns;
import com.bawnorton.potters.block.base.PottersDecoratedPotBlockBase;
import com.bawnorton.potters.block.entity.base.PottersDecoratedPotBlockEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class PottersDecoratedPotBlockEntityRenderer implements BlockEntityRenderer<PottersDecoratedPotBlockEntityBase> {
    private static final SpriteIdentifier baseTexture;

    private final ModelPart neck;
    private final ModelPart top;
    private final ModelPart bottom;

    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart left;
    private final ModelPart right;

    public PottersDecoratedPotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart base = context.getLayerModelPart(EntityModelLayers.DECORATED_POT_BASE);
        this.neck = base.getChild("neck");
        this.top = base.getChild("top");
        this.bottom = base.getChild("bottom");
        ModelPart sides = context.getLayerModelPart(EntityModelLayers.DECORATED_POT_SIDES);
        this.front = sides.getChild("front");
        this.back = sides.getChild("back");
        this.left = sides.getChild("left");
        this.right = sides.getChild("right");
    }

     static {
        baseTexture = Objects.requireNonNull(TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.DECORATED_POT_BASE_KEY));
     }

    public static TexturedModelData getTopBottomNeckTexturedModelData() {
        ModelData topAndBottom = new ModelData();
        ModelPartData partData = topAndBottom.getRoot();
        Dilation dilation = new Dilation(0.2F);
        Dilation dilation2 = new Dilation(-0.1F);
        partData.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(4.0F, 17.0F, 4.0F, 8.0F, 3.0F, 8.0F, dilation2).uv(0, 5).cuboid(5.0F, 20.0F, 5.0F, 6.0F, 1.0F, 6.0F, dilation), ModelTransform.of(0.0F, 37.0F, 16.0F, 3.1415927F, 0.0F, 0.0F));
        ModelPartBuilder partBuilder = ModelPartBuilder.create().uv(-14, 13).cuboid(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
        partData.addChild("top", partBuilder, ModelTransform.of(1.0F, 16.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        partData.addChild("bottom", partBuilder, ModelTransform.of(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(topAndBottom, 32, 32);
    }

    public static TexturedModelData getSidesTexturedModelData() {
        ModelData sides = new ModelData();
        ModelPartData partData = sides.getRoot();
        ModelPartBuilder partBuilder = ModelPartBuilder.create().uv(1, 0).cuboid(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
        partData.addChild("back", partBuilder, ModelTransform.of(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, 3.1415927F));
        partData.addChild("left", partBuilder, ModelTransform.of(1.0F, 16.0F, 1.0F, 0.0F, -1.5707964F, 3.1415927F));
        partData.addChild("right", partBuilder, ModelTransform.of(15.0F, 16.0F, 15.0F, 0.0F, 1.5707964F, 3.1415927F));
        partData.addChild("front", partBuilder, ModelTransform.of(1.0F, 16.0F, 15.0F, 3.1415927F, 0.0F, 0.0F));
        return TexturedModelData.of(sides, 16, 16);
    }

    @Nullable
    private static SpriteIdentifier getTextureIdFromSherd(Item item) {
        SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.fromSherd(item));
        if (spriteIdentifier == null) {
            spriteIdentifier = TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.fromSherd(Items.BRICK));
        }

        return spriteIdentifier;
    }

    private static SpriteIdentifier getSideOverlayTextureIdFromBlock(PottersDecoratedPotBlockBase block) {
        return PottersTexturedRenderLayers.getSideOverlayPatternTextureId(DecoratedPotOverlayPatterns.sideFromBlock(block));
    }

    private static SpriteIdentifier getBaseOverlayTextureIdFromBlock(PottersDecoratedPotBlockBase block) {
        return PottersTexturedRenderLayers.getBaseOverlayPatternTextureId(DecoratedPotOverlayPatterns.baseFromBlock(block));
    }


    public void render(PottersDecoratedPotBlockEntityBase decoratedPotBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrixStack.push();
        Direction direction = decoratedPotBlockEntity.getHorizontalFacing();
        matrixStack.translate(0.5, 0.0, 0.5);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - direction.asRotation()));
        matrixStack.translate(-0.5, 0.0, -0.5);
        DecoratedPotBlockEntity.WobbleType wobbleType = decoratedPotBlockEntity.lastWobbleType;
        if (wobbleType != null && decoratedPotBlockEntity.getWorld() != null) {
            float wobbleAmount = ((float)(decoratedPotBlockEntity.getWorld().getTime() - decoratedPotBlockEntity.lastWobbleTime) + f) / (float)wobbleType.lengthInTicks;
            if (wobbleAmount >= 0.0F && wobbleAmount <= 1.0F) {
                if (wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
                    float progression = wobbleAmount * 2 * MathHelper.PI;
                    matrixStack.multiply(RotationAxis.POSITIVE_X.rotation(-1.5F * (MathHelper.cos(progression) + 0.5F) * MathHelper.sin(progression / 2.0F) / 64), 0.5F, 0.0F, 0.5F);
                    matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.sin(progression) / 64), 0.5F, 0.0F, 0.5F);
                } else {
                    float progression = MathHelper.sin(-wobbleAmount * 3.0F * MathHelper.PI) / 8;
                    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(progression * (1.0F - wobbleAmount)), 0.5F, 0.0F, 0.5F);
                }
            }
        }

        VertexConsumer solid = baseTexture.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        Block block = decoratedPotBlockEntity.getCachedState().getBlock();
        renderBase(this.neck, this.top, this.bottom, matrixStack, solid, light, overlay);
        renderDecoratedSides(this.front, this.back, this.left, this.right, matrixStack, vertexConsumerProvider, light, overlay, decoratedPotBlockEntity.getSherds());
        renderBaseOverlay(this.neck, this.top, this.bottom, matrixStack, vertexConsumerProvider, light, overlay, block);
        renderSidesOverlay(this.front, this.back, this.left, this.right, matrixStack, vertexConsumerProvider, light, overlay, block);
        matrixStack.pop();
    }

    private static void renderDecoratedSide(ModelPart part, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, @Nullable SpriteIdentifier textureId) {
        if (textureId == null) {
            textureId = getTextureIdFromSherd(Items.BRICK);
        }

        if (textureId != null) {
            part.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        }
    }

    private static void renderBase(ModelPart neck, ModelPart top, ModelPart bottom, MatrixStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay) {
        neck.render(matrixStack, vertexConsumer, light, overlay);
        top.render(matrixStack, vertexConsumer, light, overlay);
        bottom.render(matrixStack, vertexConsumer, light, overlay);
    }

    private static void renderDecoratedSides(ModelPart front, ModelPart back, ModelPart left, ModelPart right, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay, DecoratedPotBlockEntity.Sherds sherds) {
        renderDecoratedSide(front, matrixStack, vertexConsumerProvider, light, overlay, getTextureIdFromSherd(sherds.front()));
        renderDecoratedSide(back, matrixStack, vertexConsumerProvider, light, overlay, getTextureIdFromSherd(sherds.back()));
        renderDecoratedSide(left, matrixStack, vertexConsumerProvider, light, overlay, getTextureIdFromSherd(sherds.left()));
        renderDecoratedSide(right, matrixStack, vertexConsumerProvider, light, overlay, getTextureIdFromSherd(sherds.right()));
    }

    private static void renderBaseOverlay(ModelPart neck, ModelPart top, ModelPart bottom, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay, Block block) {
        if(!(block instanceof PottersDecoratedPotBlockBase pottersBlock)) return;

        SpriteIdentifier textureId = getBaseOverlayTextureIdFromBlock(pottersBlock);
        if(textureId == null) return;

        VertexConsumer cutout = textureId.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);
        neck.render(matrixStack, cutout, light, overlay);
        top.render(matrixStack, cutout, light, overlay);
        bottom.render(matrixStack, cutout, light, overlay);
    }

    private static void renderSidesOverlay(ModelPart front, ModelPart back, ModelPart left, ModelPart right, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay, Block block) {
        if(!(block instanceof PottersDecoratedPotBlockBase pottersBlock)) return;

        SpriteIdentifier textureId = getSideOverlayTextureIdFromBlock(pottersBlock);
        if(textureId == null) return;

        VertexConsumer cutout = textureId.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);
        front.render(matrixStack, cutout, light, overlay);
        back.render(matrixStack, cutout, light, overlay);
        left.render(matrixStack, cutout, light, overlay);
        right.render(matrixStack, cutout, light, overlay);
    }

    public static void renderItemStack(ItemStack decoratedPot, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrixStack.translate(-0.5, -0.5, -0.5);

        ModelPart sides = getSidesTexturedModelData().createModel();
        ModelPart base = getTopBottomNeckTexturedModelData().createModel();
        ModelPart front = sides.getChild("front");
        ModelPart back = sides.getChild("back");
        ModelPart left = sides.getChild("left");
        ModelPart right = sides.getChild("right");
        ModelPart neck = base.getChild("neck");
        ModelPart top = base.getChild("top");
        ModelPart bottom = base.getChild("bottom");

        DecoratedPotBlockEntity.Sherds sherds = DecoratedPotBlockEntity.Sherds.fromNbt(decoratedPot.getSubNbt("BlockEntityTag"));
        VertexConsumer solid = baseTexture.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        Block block = Block.getBlockFromItem(decoratedPot.getItem());
        renderBase(neck, top, bottom, matrixStack, solid, light, overlay);
        renderDecoratedSides(front, back, left, right, matrixStack, vertexConsumerProvider, light, overlay, sherds);
        renderBaseOverlay(neck, top, bottom, matrixStack, vertexConsumerProvider, light, overlay, block);
        renderSidesOverlay(front, back, left, right, matrixStack, vertexConsumerProvider, light, overlay, block);

        matrixStack.pop();
    }
}
