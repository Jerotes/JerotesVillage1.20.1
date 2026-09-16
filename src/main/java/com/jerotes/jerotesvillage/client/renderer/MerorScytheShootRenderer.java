package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelmeror_scythe_shoot;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorScytheShootEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MerorScytheShootRenderer extends EntityRenderer<MerorScytheShootEntity> {
    private final Modelmeror_scythe_shoot model;
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_mcythe_shoot_model.png");

    public MerorScytheShootRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new Modelmeror_scythe_shoot(context.bakeLayer(Modelmeror_scythe_shoot.LAYER_LOCATION));
    }

    @Override
    protected int getBlockLightLevel(MerorScytheShootEntity t, BlockPos blockPos) {
        return 15;
    }
    @Override
    public void render(MerorScytheShootEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        poseStack.popPose();
        poseStack.translate(0, 0.125 - entityIn.getBbHeight()/2, 0);
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(MerorScytheShootEntity shoot) {
        return SPECTRAL_ARROW_LOCATION;
    }
}
