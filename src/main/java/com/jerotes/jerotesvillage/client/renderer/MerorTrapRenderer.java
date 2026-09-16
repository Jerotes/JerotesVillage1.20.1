package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelmeror_trap;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.MerorTrapEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MerorTrapRenderer extends EntityRenderer<MerorTrapEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_trap.png");
    private final Modelmeror_trap<MerorTrapEntity> model;

    public MerorTrapRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new Modelmeror_trap(context.bakeLayer(Modelmeror_trap.LAYER_LOCATION));
    }

    @Override
    protected int getBlockLightLevel(MerorTrapEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(MerorTrapEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        float f3 = entity.getAnimationProgress(f2);
        if (f3 == 0.0f) {
            return;
        }
        float f4 = 2.0f;
        if (f3 < 0.1f) {
            f4 *= (0.0f + f3) / 0.1f;
        }
        if (f3 > 0.9f) {
            f4 *= (1.0f - f3) / 0.1f;
        }
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f - entity.getYRot()));
        poseStack.scale(-f4, -f4, f4);
        float f5 = 0.03125f;
        poseStack.translate(0.0, -0.626, 0.0);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        this.model.setupAnim(entity, f3, 0.0f, 0.0f, entity.getYRot(), entity.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(MerorTrapEntity entity) {
        return TEXTURE_LOCATION;
    }
}

