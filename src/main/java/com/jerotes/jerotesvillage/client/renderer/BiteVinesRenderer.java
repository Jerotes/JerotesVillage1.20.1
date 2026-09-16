package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbite_vines;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.BiteVinesEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BiteVinesRenderer extends EntityRenderer<BiteVinesEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bite_vines.png");
    private final Modelbite_vines<BiteVinesEntity> model;

    public BiteVinesRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new Modelbite_vines(context.bakeLayer(Modelbite_vines.LAYER_LOCATION));
    }

    @Override
    public void render(BiteVinesEntity biteVines, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        float f3 = biteVines.getAnimationProgress(f2);
        if (f3 != 0.0F) {
            float f4 = 2.0F;
            if (f3 > 0.9F) {
                f4 *= (1.0F - f3) / 0.1F;
            }

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - biteVines.getYRot()));
            poseStack.scale(-f4, -f4, f4);
            float f5 = 0.03125F;
            poseStack.translate(0.0D, -0.55D, 0.0D);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setupAnim(biteVines, f3, 0.0F, 0.0F, biteVines.getYRot(), biteVines.getXRot());
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexconsumer, n, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
            super.render(biteVines, f, f2, poseStack, multiBufferSource, n);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BiteVinesEntity entity) {
        return TEXTURE_LOCATION;
    }
}

