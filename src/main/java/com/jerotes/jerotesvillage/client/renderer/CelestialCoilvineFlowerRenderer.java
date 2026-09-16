package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ClothesLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcelestial_coilvine_flower;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.CelestialCoilvineFlowerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CelestialCoilvineFlowerRenderer extends MobRenderer<CelestialCoilvineFlowerEntity, Modelcelestial_coilvine_flower<CelestialCoilvineFlowerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine_flower.png");
    private static final ResourceLocation FLOWER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine_flower_flower.png");
    public CelestialCoilvineFlowerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelcelestial_coilvine_flower<>(context.bakeLayer(Modelcelestial_coilvine_flower.LAYER_LOCATION)), 0f);
         this.addLayer(new ClothesLayer<>(this, new Modelcelestial_coilvine_flower(context.bakeLayer(Modelcelestial_coilvine_flower.LAYER_LOCATION)), LOCATION));
    }
    @Override
    protected float getFlipDegrees(CelestialCoilvineFlowerEntity entity) {
        return 0.0f;
    }
    @Override
    public ResourceLocation getTextureLocation(CelestialCoilvineFlowerEntity entity) {
        return LOCATION;
    }


    @Override
    public void render(CelestialCoilvineFlowerEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        //锁定渲染
        if (entity.getStartTick() > 100) {
            poseStack.pushPose();
            float f7 = entity.getLightLockX();
            float f8 = entity.getLightLockY();
            float f9 = entity.getLightLockZ();
            float f10 = (float)(f7 - Mth.lerp((double)f2, entity.xo, entity.getX()));
            float f11 = (float)(f8 - Mth.lerp((double)f2, entity.yo, entity.getY()));
            float f12 = (float)(f9 - Mth.lerp((double)f2, entity.zo, entity.getZ()));
            renderLightLock(f10, f11, f12, f2, entity.tickCount, poseStack, multiBufferSource, n);
            poseStack.popPose();
        }
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
    }
    @Override
    protected int getBlockLightLevel(CelestialCoilvineFlowerEntity entity, BlockPos blockPos) {
        return 15;
    }

    private static final RenderType BEAM = RenderType.entitySmoothCutout(FLOWER_LOCATION);
    public static void renderLightLock(float f, float f2, float f3, float f4, int n, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2) {
        float f5 = Mth.sqrt(f * f + f3 * f3);
        float f6 = Mth.sqrt(f * f + f2 * f2 + f3 * f3);
        poseStack.pushPose();
        poseStack.translate(0.0f, 0.5f, 0.0f);
        poseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2(f3, f)) - 1.5707964f));
        poseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2(f5, f2)) - 1.5707964f));
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(BEAM);
        float f7 = 0.0f - ((float)n + f4) * 0.01f;
        float f8 = Mth.sqrt(f * f + f2 * f2 + f3 * f3) / 32.0f - ((float)n + f4) * 0.01f;
        int n3 = 8;
        float f9 = 0.0f;
        float f10 = 0.5f;
        float f11 = 0.0f;
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        for (int i = 1; i <= 8; ++i) {
            float f12 = Mth.sin((float)i * 6.2831855f / 8.0f) * 0.15f;
            float f13 = Mth.cos((float)i * 6.2831855f / 8.0f) * 0.15f;
            float f14 = (float)i / 8.0f;
            vertexConsumer.vertex(matrix4f, f9, f10, 0.0f).color(255, 255, 255, 255).uv(f11, f7).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, f9, f10, f6).color(255, 255, 255, 255).uv(f11, f8).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, f12, f13, f6).color(255, 255, 255, 255).uv(f14, f8).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, f12, f13, 0.0f).color(255, 255, 255, 255).uv(f14, f7).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            f9 = f12;
            f10 = f13;
            f11 = f14;
        }
        poseStack.popPose();
    }



    @Override
    public boolean shouldRender(CelestialCoilvineFlowerEntity entity, Frustum frustum, double d, double d2, double d3) {
        if (!entity.shouldRender(d, d2, d3)) {
            return false;
        } else if (entity.noCulling) {
            return true;
        } else {
            AABB aabb = entity.getBoundingBoxForCulling().inflate(128.0D);
            if (aabb.hasNaN() || aabb.getSize() == 0.0D) {
                aabb = new AABB(entity.getX() - 128.0D, entity.getY() - 128.0D, entity.getZ() - 128.0D,
                        entity.getX() + 128.0D, entity.getY() + 128.0D, entity.getZ() + 128.0D);
            }
            return frustum.isVisible(aabb);
        }
    }
}

