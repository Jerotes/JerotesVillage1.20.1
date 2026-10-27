package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.AstralJavelinPointEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.AstralJavelinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class AstralJavelinPointRenderer extends EntityRenderer<AstralJavelinPointEntity> {
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");

    private static final int SEGMENTS = 6;
    private static final float CROSS_WIDTH_MUL = 0.7f;
    private static final float HALF_WIDTH = 0.062f;
    private static final float WIDTH_TAPER = 0.8f;
    private static final float RIBBON_BULGE = 0.30f;
    private static final float WAVE_AMP = 0.055f;
    private static final float WAVE_K = 2.6f;
    private static final float WAVE_SPEED = 3.0f;
    private static final float FADE_LIFE = 120.0f;
    private static final int FRESH_TICKS = 2;

    public AstralJavelinPointRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AstralJavelinPointEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        Entity targetPoint = entity.getTargetPoint();
        if (targetPoint == null) {
            return;
        }

        AstralJavelinEntity javelin = entity.getMain() instanceof AstralJavelinEntity j ? j : null;
        Vec3 origin = entity.getPosition(partialTicks);
        Vec3 ownPos = origin;
        Vec3 tailPos = targetPoint.getPosition(partialTicks);

        float lifeF = (float) entity.getLifeTick() + partialTicks;
        float progress = Mth.clamp(lifeF / FADE_LIFE, 0.0f, 1.0f);
        float retract = Mth.clamp(lifeF - FADE_LIFE, 0.0f, 1.0f);
        float visibleHead = 0.0f;
        float visibleTail = 1.0f - retract;
        if (visibleTail - visibleHead <= 1.0E-4F) {
            return;
        }

        boolean anchorToJavelin = false;
        Vec3 headPos = ownPos;
        if (javelin != null && (javelin.getTargetPoint() == entity || entity.getLifeTick() <= FRESH_TICKS)) {
            headPos = javelin.getPosition(partialTicks);
            anchorToJavelin = true;
        }
        if (!anchorToJavelin && entity.getLifeTick() <= FRESH_TICKS) {
            return;
        }

        Vec3 localHead = headPos.subtract(origin);
        Vec3 localTail = tailPos.subtract(origin);
        Vec3 diff = localTail.subtract(localHead);
        if (diff.lengthSqr() < 1.0E-6D) {
            return;
        }

        Vec3 dir = diff.normalize();
        Vec3 sideRef = Math.abs(dir.y) < 0.9 ? new Vec3(0.0D, 1.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
        Vec3 waveAxis = dir.cross(sideRef);
        if (waveAxis.lengthSqr() < 1.0E-6D) {
            waveAxis = new Vec3(1.0D, 0.0D, 0.0D);
        }
        waveAxis = waveAxis.normalize();
        Vec3 sideAxis = dir.cross(waveAxis).normalize();

        int colorI = entity.beamLightI();
        int colorIII = entity.beamLightIII();
        int nodePhase = entity.getId() % 97;

        float edgeR = (colorI >> 16 & 255) / 255.0f;
        float edgeG = (colorI >> 8 & 255) / 255.0f;
        float edgeB = (colorI & 255) / 255.0f;
        float coreR = (colorIII >> 16 & 255) / 255.0f;
        float coreG = (colorIII >> 8 & 255) / 255.0f;
        float coreB = (colorIII & 255) / 255.0f;

        float timeSeed = ((float) entity.level().getGameTime() + partialTicks) * WAVE_SPEED;

        Vec3[] centerLine = new Vec3[SEGMENTS + 1];
        Vec3[] waveAxes = new Vec3[SEGMENTS + 1];
        Vec3[] sideAxes = new Vec3[SEGMENTS + 1];
        float[] halfWidths = new float[SEGMENTS + 1];

        for (int i = 0; i <= SEGMENTS; i++) {
            float sliceT = (float) i / SEGMENTS;
            float drawT = Mth.lerp(sliceT, visibleHead, visibleTail);
            Vec3 base = localHead.add(diff.scale(drawT));
            Vec3 world = origin.add(base);

            float worldPhase = (float) (world.x * 1.7D + world.y * 1.3D + world.z * 1.9D) * WAVE_K
                    + timeSeed + nodePhase * 0.618f;
            float wave = Mth.sin(worldPhase) * WAVE_AMP;
            float drift = Mth.cos(worldPhase * 0.61f + 1.7f) * WAVE_AMP * 0.4f;

            centerLine[i] = base.add(waveAxis.scale(wave)).add(sideAxis.scale(drift));
            waveAxes[i] = waveAxis;
            sideAxes[i] = sideAxis;

            float ageAt = Mth.clamp(progress + sliceT / FADE_LIFE, 0.0f, 1.0f);
            halfWidths[i] = HALF_WIDTH * (1.0f - WIDTH_TAPER * ageAt) * (1.0f + RIBBON_BULGE * Mth.sin(sliceT * Mth.PI));
        }

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningOverlay(GLOW_LOCATION));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        int alpha = (int) (Mth.clamp(0.9f * (1.0f - 0.35f * progress) * (1.0f - retract), 0.0f, 1.0f) * 200.0f);
        for (int i = 0; i < SEGMENTS; i++) {
            float t0 = (float) i / SEGMENTS;
            float t1 = (float) (i + 1) / SEGMENTS;
            for (int cross = 0; cross < 2; cross++) {
                float crossMul = cross == 0 ? 1.0f : CROSS_WIDTH_MUL;
                float width0 = halfWidths[i] * crossMul;
                float width1 = halfWidths[i + 1] * crossMul;
                Vec3 axis0 = cross == 0 ? waveAxes[i] : sideAxes[i];
                Vec3 axis1 = cross == 0 ? waveAxes[i + 1] : sideAxes[i + 1];
                Vec3 spine0 = centerLine[i];
                Vec3 spine1 = centerLine[i + 1];
                for (int side = -1; side <= 1; side += 2) {
                    Vec3 edge0 = spine0.add(axis0.scale(width0 * side));
                    Vec3 edge1 = spine1.add(axis1.scale(width1 * side));
                    addVertex(consumer, matrix, normal, spine0, coreR, coreG, coreB, alpha, 0.5F, t0);
                    addVertex(consumer, matrix, normal, edge0, edgeR, edgeG, edgeB, alpha, side > 0 ? 1.0F : 0.0F, t0);
                    addVertex(consumer, matrix, normal, edge1, edgeR, edgeG, edgeB, alpha, side > 0 ? 1.0F : 0.0F, t1);
                    addVertex(consumer, matrix, normal, spine1, coreR, coreG, coreB, alpha, 0.5F, t1);
                }
            }
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
                                  float r, float g, float b, int a, float u, float v) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(AstralJavelinPointEntity entity) {
        return entity.getTextureLocation();
    }

    @Override
    public boolean shouldRender(AstralJavelinPointEntity entity, Frustum frustum, double d, double d2, double d3) {
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
