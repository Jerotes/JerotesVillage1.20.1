package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.client.model.Modelcarved_iron_golem;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedIronGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;

public class CarvedIronGolemFlowerLayer
extends RenderLayer<CarvedIronGolemEntity, Modelcarved_iron_golem<CarvedIronGolemEntity>> {
    private final BlockRenderDispatcher blockRenderer;

    public CarvedIronGolemFlowerLayer(RenderLayerParent<CarvedIronGolemEntity, Modelcarved_iron_golem<CarvedIronGolemEntity>> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
        super(renderLayerParent);
        this.blockRenderer = blockRenderDispatcher;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, CarvedIronGolemEntity ironGolem, float f, float f2, float f3, float f4, float f5, float f6) {
        if (ironGolem.getOfferFlowerTick() == 0) {
            return;
        }
        poseStack.pushPose();
        ModelPart modelPart = this.getParentModel().getFlowerHoldingArm();
        modelPart.translateAndRotate(poseStack);
        poseStack.translate(-1.1875f, 1.0625f, -0.9375f);
        poseStack.translate(0.5f, 0.5f, 0.5f);
        float f7 = 0.5f;
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0f));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        this.blockRenderer.renderSingleBlock(Blocks.POPPY.defaultBlockState(), poseStack, multiBufferSource, n, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}

