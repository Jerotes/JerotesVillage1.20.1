package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelgemstone_throwing_knives;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.GemstoneThrowingKnivesShootEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GemstoneThrowingKnivesShootRenderer extends EntityRenderer<GemstoneThrowingKnivesShootEntity> {
    private final Modelgemstone_throwing_knives model;
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_throwing_knives_shoot_model.png");

    public GemstoneThrowingKnivesShootRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new Modelgemstone_throwing_knives(context.bakeLayer(Modelgemstone_throwing_knives.LAYER_LOCATION));
    }

    @Override
    public void render(GemstoneThrowingKnivesShootEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }



    @Override
    public ResourceLocation getTextureLocation(GemstoneThrowingKnivesShootEntity shoot) {
        return SPECTRAL_ARROW_LOCATION;
    }
}
