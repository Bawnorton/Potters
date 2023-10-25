package com.bawnorton.potters.render;

import com.bawnorton.potters.blocks.entity.base.PottersDecoratedPotBlockEntityBase;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.gui.DrawContext;
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
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart top;
    private final ModelPart bottom;

    public PottersDecoratedPotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart modelPart = context.getLayerModelPart(EntityModelLayers.DECORATED_POT_BASE);
        this.neck = modelPart.getChild("neck");
        this.top = modelPart.getChild("top");
        this.bottom = modelPart.getChild("bottom");
        ModelPart modelPart2 = context.getLayerModelPart(EntityModelLayers.DECORATED_POT_SIDES);
        this.front = modelPart2.getChild("front");
        this.back = modelPart2.getChild("back");
        this.left = modelPart2.getChild("left");
        this.right = modelPart2.getChild("right");
    }

     static {
        baseTexture = Objects.requireNonNull(TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.DECORATED_POT_BASE_KEY));
     }

    public static TexturedModelData getTopBottomNeckTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.2F);
        Dilation dilation2 = new Dilation(-0.1F);
        modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(4.0F, 17.0F, 4.0F, 8.0F, 3.0F, 8.0F, dilation2).uv(0, 5).cuboid(5.0F, 20.0F, 5.0F, 6.0F, 1.0F, 6.0F, dilation), ModelTransform.of(0.0F, 37.0F, 16.0F, 3.1415927F, 0.0F, 0.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(-14, 13).cuboid(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
        modelPartData.addChild("top", modelPartBuilder, ModelTransform.of(1.0F, 16.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        modelPartData.addChild("bottom", modelPartBuilder, ModelTransform.of(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    public static TexturedModelData getSidesTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(1, 0).cuboid(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
        modelPartData.addChild("back", modelPartBuilder, ModelTransform.of(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, 3.1415927F));
        modelPartData.addChild("left", modelPartBuilder, ModelTransform.of(1.0F, 16.0F, 1.0F, 0.0F, -1.5707964F, 3.1415927F));
        modelPartData.addChild("right", modelPartBuilder, ModelTransform.of(15.0F, 16.0F, 15.0F, 0.0F, 1.5707964F, 3.1415927F));
        modelPartData.addChild("front", modelPartBuilder, ModelTransform.of(1.0F, 16.0F, 15.0F, 3.1415927F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Nullable
    private static SpriteIdentifier getTextureIdFromSherd(Item item) {
        SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.fromSherd(item));
        if (spriteIdentifier == null) {
            spriteIdentifier = TexturedRenderLayers.getDecoratedPotPatternTextureId(DecoratedPotPatterns.fromSherd(Items.BRICK));
        }

        return spriteIdentifier;
    }

    public void render(PottersDecoratedPotBlockEntityBase decoratedPotBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        matrixStack.push();
        Direction direction = decoratedPotBlockEntity.getHorizontalFacing();
        matrixStack.translate(0.5, 0.0, 0.5);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - direction.asRotation()));
        matrixStack.translate(-0.5, 0.0, -0.5);
        DecoratedPotBlockEntity.WobbleType wobbleType = decoratedPotBlockEntity.lastWobbleType;
        if (wobbleType != null && decoratedPotBlockEntity.getWorld() != null) {
            float g = ((float)(decoratedPotBlockEntity.getWorld().getTime() - decoratedPotBlockEntity.lastWobbleTime) + f) / (float)wobbleType.lengthInTicks;
            if (g >= 0.0F && g <= 1.0F) {
                float h;
                float k;
                if (wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
                    k = g * 6.2831855F;
                    float l = -1.5F * (MathHelper.cos(k) + 0.5F) * MathHelper.sin(k / 2.0F);
                    matrixStack.multiply(RotationAxis.POSITIVE_X.rotation(l * 0.015625F), 0.5F, 0.0F, 0.5F);
                    float m = MathHelper.sin(k);
                    matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(m * 0.015625F), 0.5F, 0.0F, 0.5F);
                } else {
                    h = MathHelper.sin(-g * 3.0F * 3.1415927F) * 0.125F;
                    k = 1.0F - g;
                    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(h * k), 0.5F, 0.0F, 0.5F);
                }
            }
        }

        VertexConsumer vertexConsumer = baseTexture.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        renderTopAndBottom(this.neck, this.top, this.bottom, matrixStack, vertexConsumer, i, j);
        renderDecoratedSides(this.front, this.back, this.left, this.right, matrixStack, vertexConsumerProvider, i, j, decoratedPotBlockEntity.getSherds());
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

    private static void renderTopAndBottom(ModelPart neck, ModelPart top, ModelPart bottom, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j) {
        neck.render(matrixStack, vertexConsumer, i, j);
        top.render(matrixStack, vertexConsumer, i, j);
        bottom.render(matrixStack, vertexConsumer, i, j);
    }

    private static void renderDecoratedSides(ModelPart front, ModelPart back, ModelPart left, ModelPart right, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, DecoratedPotBlockEntity.Sherds sherds) {
        renderDecoratedSide(front, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromSherd(sherds.front()));
        renderDecoratedSide(back, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromSherd(sherds.back()));
        renderDecoratedSide(left, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromSherd(sherds.left()));
        renderDecoratedSide(right, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromSherd(sherds.right()));
    }

    public static void renderItemStack(ItemStack decoratedPot, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrixStack.translate(-0.5, -0.5, -0.5);

        ModelPart sides = getSidesTexturedModelData().createModel();
        ModelPart topBottomNeck = getTopBottomNeckTexturedModelData().createModel();
        ModelPart front = sides.getChild("front");
        ModelPart back = sides.getChild("back");
        ModelPart left = sides.getChild("left");
        ModelPart right = sides.getChild("right");
        ModelPart neck = topBottomNeck.getChild("neck");
        ModelPart top = topBottomNeck.getChild("top");
        ModelPart bottom = topBottomNeck.getChild("bottom");

        DecoratedPotBlockEntity.Sherds sherds = DecoratedPotBlockEntity.Sherds.fromNbt(decoratedPot.getSubNbt("BlockEntityTag"));
        VertexConsumer vertexConsumer = baseTexture.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        renderDecoratedSides(front, back, left, right, matrixStack, vertexConsumerProvider, light, overlay, sherds);
        renderTopAndBottom(neck, top, bottom, matrixStack, vertexConsumer, light, overlay);

        matrixStack.pop();
    }
}
