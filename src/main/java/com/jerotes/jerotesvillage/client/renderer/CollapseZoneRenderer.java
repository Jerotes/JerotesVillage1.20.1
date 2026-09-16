package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.CollapseZoneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class CollapseZoneRenderer extends EntityRenderer<CollapseZoneEntity> {
    private static final ResourceLocation RAY_LOCATION =
            new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");

    public CollapseZoneRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CollapseZoneEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferIn, int packedLight) {
        float alpha = this.getAlpha(entity);
        if (alpha <= 0f) return;
        poseStack.pushPose();
        renderRangeFrame(entity, poseStack, bufferIn, alpha);
        poseStack.popPose();
    }

    private float getAlpha(CollapseZoneEntity entity) {
        float life = entity.life;
        int maxLife = entity.getMaxLife();
        if (life < 5f) return 0f;
        if (life < 15f) return (life - 5f) / 10f;
        if (life > maxLife - 20f) return Math.max(0f, (maxLife - life) / 20f);
        return 1f;
    }

    private void renderRangeFrame(CollapseZoneEntity entity, PoseStack poseStack, MultiBufferSource buffer, float alphaFade) {
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(RAY_LOCATION));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMat = pose.normal();

        int alpha = (int) (80 * alphaFade);
        int r = 234, g = 173, b = 20;

        float outer = 3.0f;
        float inner = 2.75f;
        float[] outerX = {-outer, outer, outer, -outer};
        float[] outerZ = {-outer, -outer, outer, outer};
        float[] innerX = {-inner, inner, inner, -inner};
        float[] innerZ = {-inner, -inner, inner, inner};

        for (int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            consumer.vertex(matrix, outerX[i], 0f, outerZ[i]).color(r, g, b, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, outerX[j], 0f, outerZ[j]).color(r, g, b, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, innerX[j], 0f, innerZ[j]).color(r, g, b, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, innerX[i], 0f, innerZ[i]).color(r, g, b, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(CollapseZoneEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
