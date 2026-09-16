package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.TorchGazeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class TorchGazeRenderer extends EntityRenderer<TorchGazeEntity> {
    private static final ResourceLocation RAY_LOCATION =
            new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    private static final ResourceLocation FLAME_LOCATION =
            new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/flame_column.png");
    private static final ResourceLocation FLAME_LOCATION_OUT =
            new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/flame_column_out.png");

    private static final int WARN_START = 5;
    private static final int WARN_END   = 25;
    private static final int FALL_END   = 32;
    private static final int BURN_END   = 60;

    private static final float RADIUS        = 2.25f;
    private static final float COLUMN_HEIGHT = 18f;

    private static final float MAIN_OUTER_U_TILE = 8f;
    private static final float MAIN_OUTER_V_TILE = 8f;
    private static final float STRAND_OUTER_U_TILE = 6f;

    public TorchGazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TorchGazeEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferIn, int packedLight) {
        if (entity.life < WARN_START) return;
        poseStack.pushPose();

        if (entity.life <= WARN_END) {
            renderRangeIndicator(entity, partialTicks, poseStack, bufferIn);
        }
        renderFlameColumn(entity, partialTicks, poseStack, bufferIn, packedLight);
        poseStack.popPose();
    }

    private void renderRangeIndicator(TorchGazeEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
        float radius = RADIUS;
        float innerRadius = RADIUS - 0.25f;
        int segments = 32;
        float y = 0.0f;

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(RAY_LOCATION));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMat = pose.normal();

        int alpha = (int) (80 * (entity.life >= 15 ? (1 - (entity.life - 15) / 10f) : (entity.life - 5) / 10f));
        int r = 234, g = 173, b = 20;

        for (int i = 0; i < segments; i++) {
            float angle1 = (float) i / segments * 2.0f * Mth.PI;
            float angle2 = (float) (i + 1) / segments * 2.0f * Mth.PI;

            float x1o = radius * Mth.cos(angle1), z1o = radius * Mth.sin(angle1);
            float x2o = radius * Mth.cos(angle2), z2o = radius * Mth.sin(angle2);
            float x1i = innerRadius * Mth.cos(angle1), z1i = innerRadius * Mth.sin(angle1);
            float x2i = innerRadius * Mth.cos(angle2), z2i = innerRadius * Mth.sin(angle2);

            consumer.vertex(matrix, x1o, y, z1o).color(r, g, b, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x2o, y, z2o).color(r, g, b, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x2i, y, z2i).color(r, g, b, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
            consumer.vertex(matrix, x1i, y, z1i).color(r, g, b, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
        }
    }

    private void renderFlameColumn(TorchGazeEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float life = entity.life + partialTick;
        float smoothLife = life;
        int seed = entity.getId();

        float topY = Math.min(COLUMN_HEIGHT, COLUMN_HEIGHT * ((life - 10) / 5f));
        float alpha = 1f;

        if (entity.life >= FALL_END) {
            if (entity.life > BURN_END - 10) {
                float fadeT = Mth.clamp((life - (BURN_END - 10)) / 10f, 0f, 1f);
                alpha = 1f - fadeT;
            }
        }
        if (alpha <= 0f) return;

        float widthScale = 1f;

        if (life >= 15 && life <= 20) {
            widthScale = (life - 15) / 5f;
        } else if (life >= 30 && life <= 60) {
            float t = (life - 30) / 30f;
            widthScale = 1f - 0.7f * t;
        }
        else if (life < 15) {
            widthScale = 0;
        }

        float currentRadius = RADIUS * widthScale;
        float currentInnerRadius = RADIUS * 0.25f * widthScale;

        renderCylinderWall(FLAME_LOCATION_OUT, entity, poseStack, buffer, currentRadius, 0f, topY, alpha,
                255, 255, 255, 130, packedLight, MAIN_OUTER_U_TILE, MAIN_OUTER_V_TILE);
        renderCylinderWall(FLAME_LOCATION, entity, poseStack, buffer, currentInnerRadius, 0f, topY, alpha,
                255, 225, 170, 190, packedLight, 1f, 1f);

        renderSpiralStrands(entity, smoothLife, seed, poseStack, buffer, topY, alpha, packedLight, widthScale);
        renderTopCap(entity, smoothLife, seed, poseStack, buffer, topY, alpha, packedLight, widthScale);
        renderBottomCap(entity, smoothLife, seed, poseStack, buffer, alpha, packedLight, widthScale);
    }

    private void renderCylinderWall(ResourceLocation tex, TorchGazeEntity entity, PoseStack poseStack, MultiBufferSource buffer,
                                    float radius, float bottomY, float topY, float alpha,
                                    int r, int g, int b, int baseAlpha, int light, float uTile, float vTile) {
        if (radius <= 0) return;
        int segments = 28;
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(tex));

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMat = pose.normal();

        int a = (int) (baseAlpha * alpha);
        float scroll = (entity.tickCount % 20) / 20f * vTile;

        for (int i = 0; i < segments; i++) {
            float angle1 = (float) i / segments * 2f * Mth.PI;
            float angle2 = (float) (i + 1) / segments * 2f * Mth.PI;

            float x1 = radius * Mth.cos(angle1), z1 = radius * Mth.sin(angle1);
            float x2 = radius * Mth.cos(angle2), z2 = radius * Mth.sin(angle2);

            float u1 = (float) i / segments * uTile, u2 = (float) (i + 1) / segments * uTile;
            float vTop = vTile - scroll, vBottom = 2f * vTile - scroll;

            float nx1 = -Mth.sin(angle1), nz1 = Mth.cos(angle1);
            float nx2 = -Mth.sin(angle2), nz2 = Mth.cos(angle2);

            consumer.vertex(matrix, x1, topY, z1).color(r, g, b, a).uv(u1, vTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMat, nx1, 0, nz1).endVertex();
            consumer.vertex(matrix, x2, topY, z2).color(r, g, b, a).uv(u2, vTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMat, nx2, 0, nz2).endVertex();
            consumer.vertex(matrix, x2, bottomY, z2).color(r, g, b, a).uv(u2, vBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMat, nx2, 0, nz2).endVertex();
            consumer.vertex(matrix, x1, bottomY, z1).color(r, g, b, a).uv(u1, vBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMat, nx1, 0, nz1).endVertex();
        }
    }

    private void renderSpiralStrands(TorchGazeEntity entity, float time, int seed,
                                     PoseStack poseStack, MultiBufferSource buffer, float topY, float alpha, int light, float widthScale) {
        renderStrandGroup(entity, time, seed, poseStack, buffer, topY, alpha, light,
                FLAME_LOCATION, 3, widthScale);
        renderStrandGroup(entity, time, seed + 31, poseStack, buffer, topY, alpha, light,
                FLAME_LOCATION_OUT, 3, 1.15f * widthScale);
    }

    private void renderStrandGroup(TorchGazeEntity entity, float time, int seed,
                                   PoseStack poseStack, MultiBufferSource buffer, float topY, float alpha, int light,
                                   ResourceLocation tex, int strandCount, float radiusMul) {
        int segments = 24;
        float drillSpeed = 0.18f;
        float maxTwist = (float) (3.0 * Math.PI);
        float wrapRadius = RADIUS * 0.85f;

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(tex));

        for (int s = 0; s < strandCount; s++) {
            float strandPhase = (float) (s * (2 * Math.PI / strandCount));

            Vec3[] left = new Vec3[segments + 1];
            Vec3[] right = new Vec3[segments + 1];
            int[] aa = new int[segments + 1];

            for (int i = 0; i <= segments; i++) {
                float progress = (float) i / segments;
                float y = progress * topY;

                float offset = (seed % 7 + i + s * 7) * (1f / segments);
                float angle = time * drillSpeed + progress * maxTwist + offset * maxTwist + strandPhase;

                float wobble = 0.12f * Mth.sin(time * 0.3f + progress * 8f + s);
                float radius = wrapRadius * (1f + wobble) * (1f - progress * 0.1f) * radiusMul;

                float cos = Mth.cos(angle), sin = Mth.sin(angle);
                Vec3 point = new Vec3(cos * radius, y, sin * radius);

                float bandWidth = 0.45f * (1f - progress * 0.2f);
                Vec3 tangent = new Vec3(-sin, 0, cos).normalize();
                left[i] = point.add(tangent.scale(-bandWidth));
                right[i] = point.add(tangent.scale(bandWidth));

                float flick = 0.6f + 0.4f * Mth.sin(progress * (float) Math.PI);
                aa[i] = (int) Mth.clamp(160 * flick * alpha, 10f, 200f);
            }

            for (int i = 0; i < segments; i++) {
                float u0 = (float) i / segments * STRAND_OUTER_U_TILE, u1 = (float) (i + 1) / segments * STRAND_OUTER_U_TILE;

                addVertex(consumer, matrix, normal, left[i], 255, 255, 255, aa[i], u0, 0, light);
                addVertex(consumer, matrix, normal, right[i], 255, 255, 255, aa[i], u0, 1, light);
                addVertex(consumer, matrix, normal, right[i + 1], 255, 255, 255, aa[i + 1], u1, 1, light);
                addVertex(consumer, matrix, normal, left[i + 1], 255, 255, 255, aa[i + 1], u1, 0, light);
            }
        }
    }

    private void renderTopCap(TorchGazeEntity entity, float time, int seed,
                              PoseStack poseStack, MultiBufferSource buffer, float topY, float alpha, int light, float widthScale) {
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        VertexConsumer innerConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(FLAME_LOCATION));
        VertexConsumer outerConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(FLAME_LOCATION));

        int crownSegments = 32;
        float crownRadius = RADIUS * 0.25f * widthScale;
        float crownBandHeight = 0.9f * widthScale;
        for (int i = 0; i < crownSegments; i++) {
            float a1 = (float) i / crownSegments * 2f * Mth.PI;
            float a2 = (float) (i + 1) / crownSegments * 2f * Mth.PI;

            float wave1 = Mth.sin(time * 0.5f + a1 * 3f) * 0.3f * widthScale;
            float wave2 = Mth.sin(time * 0.5f + a2 * 3f) * 0.3f * widthScale;

            Vec3 topP1 = new Vec3(crownRadius * Mth.cos(a1), topY + crownBandHeight + wave1, crownRadius * Mth.sin(a1));
            Vec3 topP2 = new Vec3(crownRadius * Mth.cos(a2), topY + crownBandHeight + wave2, crownRadius * Mth.sin(a2));
            Vec3 botP1 = new Vec3(crownRadius * Mth.cos(a1), topY - 0.2f * widthScale, crownRadius * Mth.sin(a1));
            Vec3 botP2 = new Vec3(crownRadius * Mth.cos(a2), topY - 0.2f * widthScale, crownRadius * Mth.sin(a2));

            float u1 = (float) i / crownSegments * 8f, u2 = (float) (i + 1) / crownSegments * 8f;
            int crownAlpha = (int) (170 * alpha);

            addVertex(outerConsumer, matrix, normal, topP1, 255, 255, 255, crownAlpha, u1, 0, light);
            addVertex(outerConsumer, matrix, normal, topP2, 255, 255, 255, crownAlpha, u2, 0, light);
            addVertex(outerConsumer, matrix, normal, botP2, 255, 255, 255, (int) (crownAlpha * 0.3f), u2, 1, light);
            addVertex(outerConsumer, matrix, normal, botP1, 255, 255, 255, (int) (crownAlpha * 0.3f), u1, 1, light);
        }

        int petals = 14;
        float spin = time * 0.06f;
        for (int i = 0; i < petals; i++) {
            float angle = (float) (i * 2 * Math.PI / petals) + spin;
            float flick = 0.6f * Mth.sin(time * 0.4f + i * 1.7f + seed);
            float petalLength = (RADIUS * 1.75f + flick) * widthScale;
            float petalWidth = 0.5f * widthScale;

            Vec3 base = new Vec3(RADIUS * 0.25f * widthScale * Mth.cos(angle), topY, RADIUS * 0.25f * widthScale * Mth.sin(angle));
            Vec3 dirOut = new Vec3(Mth.cos(angle), 0.9f, Mth.sin(angle)).normalize();
            Vec3 tip = base.add(dirOut.scale(petalLength));

            Vec3 perp = new Vec3(-Mth.sin(angle), 0, Mth.cos(angle)).scale(petalWidth);
            Vec3 v0 = base.subtract(perp);
            Vec3 v1 = base.add(perp);

            int alphaBase = (int) (210 * alpha);
            int alphaTip = 0;

            addVertex(innerConsumer, matrix, normal, v0, 255, 150, 50, alphaBase, 0, 0, light);
            addVertex(innerConsumer, matrix, normal, v1, 255, 150, 50, alphaBase, 1, 0, light);
            addVertex(innerConsumer, matrix, normal, tip, 255, 230, 170, alphaTip, 0.5f, 1, light);
            addVertex(innerConsumer, matrix, normal, tip, 255, 230, 170, alphaTip, 0.5f, 1, light);
        }

        int rays = 16;
        float raySpin = -time * 0.03f;
        for (int i = 0; i < rays; i++) {
            float angle = (float) (i * 2 * Math.PI / rays) + raySpin + hash(seed + i) * 0.4f;
            float flick = 0.5f + 0.5f * Mth.sin(time * 0.25f + i * 2.1f + seed);
            float rayLength = RADIUS * 1.4f * (0.6f + 0.4f * flick) * widthScale;
            float rayHalfWidth = 0.12f * widthScale;

            Vec3 origin = new Vec3(0, topY + 0.15f * widthScale, 0);
            Vec3 outDir = new Vec3(Mth.cos(angle), 0.15f, Mth.sin(angle)).normalize();
            Vec3 tip = origin.add(outDir.scale(rayLength));
            Vec3 perp = new Vec3(-Mth.sin(angle), 0, Mth.cos(angle)).scale(rayHalfWidth);

            Vec3 v0 = origin.subtract(perp);
            Vec3 v1 = origin.add(perp);

            int alphaBase = (int) (110 * (0.6f + 0.4f * flick) * alpha);

            addVertex(outerConsumer, matrix, normal, v0, 255, 255, 255, alphaBase, 0, 0, light);
            addVertex(outerConsumer, matrix, normal, v1, 255, 255, 255, alphaBase, 1, 0, light);
            addVertex(outerConsumer, matrix, normal, tip, 255, 255, 255, 0, 0.5f, 1, light);
            addVertex(outerConsumer, matrix, normal, tip, 255, 255, 255, 0, 0.5f, 1, light);
        }
    }
    private void renderBottomCap(TorchGazeEntity entity, float time, int seed,
                                 PoseStack poseStack, MultiBufferSource buffer, float alpha, int light, float widthScale) {
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        VertexConsumer outerConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(FLAME_LOCATION));
        renderGroundGlowRing(outerConsumer, matrix, normal, RADIUS * 0.45f * widthScale, 0.6f * widthScale, (int) (150 * alpha), light);
        renderGroundGlowRing(outerConsumer, matrix, normal, RADIUS * 0.9f * widthScale, 1.1f * widthScale, (int) (80 * alpha), light);

        int rays = 20;
        int segsPerRay = 4;
        float spin = time * 0.015f;
        for (int i = 0; i < rays; i++) {
            float angle = (float) i / rays * 2f * Mth.PI + spin;
            float jag = 0.8f + 0.3f * hash(i + seed);
            float rayLength = RADIUS * jag * 1.6f * widthScale;
            float width = 0.35f * widthScale;

            Vec3 dir = new Vec3(Mth.cos(angle), 0, Mth.sin(angle));
            Vec3 perp = new Vec3(-Mth.sin(angle), 0, Mth.cos(angle)).scale(width);

            for (int s = 0; s < segsPerRay; s++) {
                float t0 = (float) s / segsPerRay, t1 = (float) (s + 1) / segsPerRay;
                float w0 = width * (1f - t0 * 0.7f);
                float w1 = width * (1f - t1 * 0.7f);
                Vec3 perp0 = new Vec3(-Mth.sin(angle), 0, Mth.cos(angle)).scale(w0);
                Vec3 perp1 = new Vec3(-Mth.sin(angle), 0, Mth.cos(angle)).scale(w1);

                Vec3 p0 = dir.scale(rayLength * t0).add(0, 0.02f * widthScale, 0);
                Vec3 p1 = dir.scale(rayLength * t1).add(0, 0.02f * widthScale, 0);

                int a0 = (int) (140 * (1f - t0) * alpha);
                int a1 = (int) (140 * (1f - t1) * alpha);

                addVertex(outerConsumer, matrix, normal, p0.subtract(perp0), 255, 255, 255, a0, 0, t0, light);
                addVertex(outerConsumer, matrix, normal, p0.add(perp0), 255, 255, 255, a0, 1, t0, light);
                addVertex(outerConsumer, matrix, normal, p1.add(perp1), 255, 255, 255, a1, 1, t1, light);
                addVertex(outerConsumer, matrix, normal, p1.subtract(perp1), 255, 255, 255, a1, 0, t1, light);
            }
        }
    }
    private void renderGroundGlowRing(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
                                      float radius, float ringWidth, int baseAlpha, int light) {
        int segments = 28;
        for (int i = 0; i < segments; i++) {
            float a1 = (float) i / segments * 2f * Mth.PI;
            float a2 = (float) (i + 1) / segments * 2f * Mth.PI;

            float ro = radius + ringWidth * 0.5f, ri = radius - ringWidth * 0.5f;

            Vec3 o1 = new Vec3(ro * Mth.cos(a1), 0.02f, ro * Mth.sin(a1));
            Vec3 o2 = new Vec3(ro * Mth.cos(a2), 0.02f, ro * Mth.sin(a2));
            Vec3 i1 = new Vec3(ri * Mth.cos(a1), 0.02f, ri * Mth.sin(a1));
            Vec3 i2 = new Vec3(ri * Mth.cos(a2), 0.02f, ri * Mth.sin(a2));

            float u1 = (float) i / segments * 6f, u2 = (float) (i + 1) / segments * 6f;

            addVertex(consumer, matrix, normal, o1, 255, 255, 255, 0, u1, 0, light);
            addVertex(consumer, matrix, normal, o2, 255, 255, 255, 0, u2, 0, light);
            addVertex(consumer, matrix, normal, i2, 255, 255, 255, baseAlpha, u2, 1, light);
            addVertex(consumer, matrix, normal, i1, 255, 255, 255, baseAlpha, u1, 1, light);
        }
    }

    private static void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
                                  int r, int g, int b, int a, float u, float v, int light) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0f, 1f, 0f)
                .endVertex();
    }

    private static float hash(float seed) {
        float x = Mth.sin(seed * 12.9898f) * 43758.5453f;
        return x - Mth.floor(x);
    }

    public ResourceLocation getTextureLocation(TorchGazeEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}