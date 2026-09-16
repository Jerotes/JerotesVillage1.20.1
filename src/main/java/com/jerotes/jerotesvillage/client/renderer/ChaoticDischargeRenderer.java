package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.ChaoticDischargeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ChaoticDischargeRenderer extends EntityRenderer<ChaoticDischargeEntity> {
    private static final ResourceLocation RAY_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    public ChaoticDischargeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ChaoticDischargeEntity entityIn, float p_115267_, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int p_115271_) {
        if (entityIn.life < 5) return;
        poseStack.pushPose();
        if (entityIn.life >= 5 && entityIn.life <= 25) {
            renderRangeIndicator(entityIn, partialTicks, poseStack, bufferIn);
        }
        poseStack.popPose();

        if (entityIn.life < 20 || entityIn.life > 40) return;

        float fadeFactor;
        if (entityIn.life < 25) {
            fadeFactor = (entityIn.life - 20) / 5.0f;
        } else if (entityIn.life > 30) {
            fadeFactor = (40 - entityIn.life) / 10.0f;
        } else {
            fadeFactor = 1.0f;
        }
        fadeFactor = Mth.clamp(fadeFactor, 0.0f, 1.0f);

        float[] afloat = new float[8];
        float[] afloat1 = new float[8];
        float f = 0.0F;
        float f1 = 0.0F;
        RandomSource randomsource = RandomSource.create(entityIn.seed);

        for(int i = 7; i >= 0; --i) {
            afloat[i] = f;
            afloat1[i] = f1;
            f += (float)(randomsource.nextInt(11) - 5);
            f1 += (float)(randomsource.nextInt(11) - 5);
        }

        VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.lightning());
        Matrix4f matrix4f = poseStack.last().pose();

        for(int j = 0; j < 4; ++j) {
            RandomSource randomsource1 = RandomSource.create(entityIn.seed);
            for(int k = 0; k < 3; ++k) {
                int l = 7;
                int i1 = 0;
                if (k > 0) { l = 7 - k; }
                if (k > 0) { i1 = l - 2; }

                float f2 = afloat[l] - f;
                float f3 = afloat1[l] - f1;

                for(int j1 = l; j1 >= i1; --j1) {
                    float f4 = f2;
                    float f5 = f3;
                    if (k == 0) {
                        f2 += (float)(randomsource1.nextInt(11) - 5);
                        f3 += (float)(randomsource1.nextInt(11) - 5);
                    } else {
                        f2 += (float)(randomsource1.nextInt(31) - 15);
                        f3 += (float)(randomsource1.nextInt(31) - 15);
                    }

                    float widthScale = 0.1F + 0.9F * fadeFactor;
                    float f10 = (0.05F + (float)j * 0.1F) * widthScale;
                    if (k == 0) {
                        f10 *= (float)j1 * 0.05F + 0.5F;
                    }
                    float f11 = (0.05F + (float)j * 0.1F) * widthScale;
                    if (k == 0) {
                        f11 *= ((float)j1 - 1.0F) * 0.05F + 0.5F;
                    }

                    float alpha = fadeFactor * 0.8F;

                    quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5,
                            0.0F, 0.302F, 0.62F, f10, f11, alpha,
                            false, false, true, false);
                    quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5,
                            0.0F, 0.302F, 0.62F, f10, f11, alpha,
                            true, false, true, true);
                    quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5,
                            0.0F, 0.302F, 0.62F, f10, f11, alpha,
                            true, true, false, true);
                    quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5,
                            0.0F, 0.302F, 0.62F, f10, f11, alpha,
                            false, true, false, false);
                }
            }
        }
    }

    private void renderRangeIndicator(ChaoticDischargeEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
        float radius = 2.25f;
        float innerRadius = 2f;
        int segments = 32;
        float y = 0.0f;

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(RAY_LOCATION));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMat = pose.normal();

        int alpha = (int) (80 * (entity.life >= 15 ? (1 - (entity.life - 15)/10f) : (entity.life - 5)/10f));
        int r = 119, g = 185, b = 255;

        for (int i = 0; i < segments; i++) {
            float angle1 = (float) i / segments * 2.0f * (float) Math.PI;
            float angle2 = (float) (i + 1) / segments * 2.0f * (float) Math.PI;

            float x1o = radius * (float) Math.cos(angle1);
            float z1o = radius * (float) Math.sin(angle1);
            float x2o = radius * (float) Math.cos(angle2);
            float z2o = radius * (float) Math.sin(angle2);

            float x1i = innerRadius * (float) Math.cos(angle1);
            float z1i = innerRadius * (float) Math.sin(angle1);
            float x2i = innerRadius * (float) Math.cos(angle2);
            float z2i = innerRadius * (float) Math.sin(angle2);

            consumer.vertex(matrix, x1o, y, z1o).color(r, g, b, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x2o, y, z2o).color(r, g, b, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x2i, y, z2i).color(r, g, b, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x1i, y, z1i).color(r, g, b, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
        }
    }

    private static void quad(Matrix4f matrix, VertexConsumer consumer,
                             float p1, float p2, int height,
                             float p4, float p5,
                             float r, float g, float b,
                             float width1, float width2,
                             float alpha,
                             boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        consumer.vertex(matrix, p1 + (flag1 ? width2 : -width2), (float)(height * 16), p2 + (flag2 ? width2 : -width2))
                .color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, p4 + (flag1 ? width1 : -width1), (float)((height + 1) * 16), p5 + (flag2 ? width1 : -width1))
                .color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, p4 + (flag3 ? width1 : -width1), (float)((height + 1) * 16), p5 + (flag4 ? width1 : -width1))
                .color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, p1 + (flag3 ? width2 : -width2), (float)(height * 16), p2 + (flag4 ? width2 : -width2))
                .color(r, g, b, alpha).endVertex();
    }

    public ResourceLocation getTextureLocation(ChaoticDischargeEntity p_115264_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}