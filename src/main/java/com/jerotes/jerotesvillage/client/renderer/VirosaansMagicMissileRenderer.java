package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.VirosaansMagicMissileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VirosaansMagicMissileRenderer<T extends VirosaansMagicMissileEntity> extends EntityRenderer<T> {
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation("jerotes", "textures/entity/beam/ray.png");
    private static final int SUBDIV = 3;
    private static final float MAX_HALF_WIDTH = 0.13f;
    private static final float CROSS_WIDTH_MUL = 0.7f;
    private static final float WAVE_AMP = 0.085f;
    private static final float WAVE_FREQ = 1.6f;
    private static final float WAVE_SPEED = 3.0f;
    private static final float TAIL_ALPHA_MUL = 0.12f;

    public VirosaansMagicMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected int getBlockLightLevel(T t, BlockPos blockPos) {
        return 15;
    }

    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        this.renderFlameTrail(entityIn, partialTicks, poseStack, bufferIn);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    private void renderFlameTrail(T entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
        if (!entity.showBeam()) {
            return;
        }
        List<BaseMagicBoltEntity.TrailPoint> trail = entity.getTrailPoints();
        int pointCount = trail.size();
        if (pointCount < 2) {
            return;
        }

        Vec3 entityPos = new Vec3(
                Mth.lerp((double) partialTick, entity.xOld, entity.getX()),
                Mth.lerp((double) partialTick, entity.yOld, entity.getY()),
                Mth.lerp((double) partialTick, entity.zOld, entity.getZ()));

        int total = (pointCount - 1) * SUBDIV + 1;
        Vec3[] centerLine = new Vec3[total];
        Vec3[] waveAxis = new Vec3[total];
        Vec3[] sideAxis = new Vec3[total];

        for (int s = 0; s < total; ++s) {
            float t = (float) s / (float) (total - 1);
            float fi = t * (float) (pointCount - 1);
            int index = Mth.clamp((int) fi, 0, pointCount - 2);
            float local = fi - (float) index;

            Vec3 point0 = trail.get(index).getPosition(partialTick);
            Vec3 point1 = trail.get(index + 1).getPosition(partialTick);
            centerLine[s] = point0.lerp(point1, (double) local).subtract(entityPos);
        }

        for (int s = 0; s < total; ++s) {
            Vec3 dir = centerLine[Math.min(total - 1, s + 1)].subtract(centerLine[Math.max(0, s - 1)]);
            if (dir.lengthSqr() < 1.0E-8) {
                dir = new Vec3((double) 0.0F, (double) 0.0F, (double) 1.0F);
            }
            dir = dir.normalize();
            Vec3 sideRef = Math.abs(dir.y) < 0.9 ? new Vec3((double) 0.0F, (double) 1.0F, (double) 0.0F) : new Vec3((double) 1.0F, (double) 0.0F, (double) 0.0F);
            Vec3 wave = dir.cross(sideRef);
            if (wave.lengthSqr() < 1.0E-8) {
                wave = new Vec3((double) 1.0F, (double) 0.0F, (double) 0.0F);
            }
            waveAxis[s] = wave.normalize();
            sideAxis[s] = dir.cross(waveAxis[s]).normalize();
        }

        int currentLife = entity.life;
        int maxLife = entity.getMaxLife();
        int trailMaxLife = entity.trailMaxLife();
        float lifeMul = 0.3f + 0.7f * Mth.clamp((float) currentLife / (float) maxLife, 0.0f, 1.0f);
        float timeSeed = ((float) currentLife + partialTick) * WAVE_SPEED;
        float phase = (float) (entity.getId() % 97) * 0.618f * Mth.TWO_PI;

        int colorI = entity.beamLightI();
        int colorII = entity.beamLightII();
        int colorIII = entity.beamLightIII();

        float[] offsets = new float[total];
        float[] halfWidths = new float[total];
        float[] alphas = new float[total];
        int[] rs = new int[total];
        int[] gs = new int[total];
        int[] bs = new int[total];

        for (int s = 0; s < total; ++s) {
            float t = (float) s / (float) (total - 1);
            float envelope = Mth.sin(Mth.clamp(t, 0.02f, 0.98f) * Mth.PI);
            offsets[s] = Mth.sin(t * WAVE_FREQ * Mth.TWO_PI + phase + timeSeed) * WAVE_AMP * envelope
                    + Mth.cos(t * WAVE_FREQ * Mth.PI * 1.3f + phase * 1.7f + timeSeed * 0.7f) * WAVE_AMP * 0.4f * envelope;
            halfWidths[s] = MAX_HALF_WIDTH * (float) Math.pow((double) envelope, 0.7);

            int index = Mth.clamp((int) (t * (float) (pointCount - 1)), 0, pointCount - 1);
            int age = currentLife - trail.get(index).createdAt;
            float ageFade = 1.0f - Mth.clamp((float) age / (float) Math.max(1, trailMaxLife), 0.0f, 1.0f);
            alphas[s] = Mth.lerp(t, TAIL_ALPHA_MUL, 1.0f) * (0.25f + 0.75f * ageFade) * lifeMul * 0.9f;

            rs[s] = getInterpolatedColor(colorI >> 16 & 255, colorII >> 16 & 255, colorIII >> 16 & 255, t);
            gs[s] = getInterpolatedColor(colorI >> 8 & 255, colorII >> 8 & 255, colorIII >> 8 & 255, t);
            bs[s] = getInterpolatedColor(colorI & 255, colorII & 255, colorIII & 255, t);
        }

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.entityTranslucent(GLOW_LOCATION));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        Vec3[][] ribbonLeft = new Vec3[2][total];
        Vec3[][] ribbonRight = new Vec3[2][total];
        for (int s = 0; s < total; ++s) {
            for (int cross = 0; cross < 2; ++cross) {
                Vec3 axis = cross == 0 ? waveAxis[s] : sideAxis[s];
                float widthMul = cross == 0 ? 1.0f : CROSS_WIDTH_MUL;
                float offset = offsets[s] * (cross == 0 ? 1.0f : -0.6f);
                Vec3 point = centerLine[s].add(axis.scale((double) offset));
                float w = halfWidths[s] * widthMul;
                ribbonLeft[cross][s] = point.subtract(axis.scale((double) w));
                ribbonRight[cross][s] = point.add(axis.scale((double) w));
            }
        }

        for (int cross = 0; cross < 2; ++cross) {
            for (int s = 0; s < total - 1; ++s) {
                float t0 = (float) s / (float) (total - 1);
                float t1 = (float) (s + 1) / (float) (total - 1);
                int a0 = (int) (Mth.clamp(alphas[s], 0.0f, 1.0f) * 200.0f);
                int a1 = (int) (Mth.clamp(alphas[s + 1], 0.0f, 1.0f) * 200.0f);
                addVertex(consumer, matrix, normal, ribbonLeft[cross][s], rs[s], gs[s], bs[s], a0, 0.0F, t0);
                addVertex(consumer, matrix, normal, ribbonRight[cross][s], rs[s], gs[s], bs[s], a0, 1.0F, t0);
                addVertex(consumer, matrix, normal, ribbonRight[cross][s + 1], rs[s + 1], gs[s + 1], bs[s + 1], a1, 1.0F, t1);
                addVertex(consumer, matrix, normal, ribbonLeft[cross][s + 1], rs[s + 1], gs[s + 1], bs[s + 1], a1, 0.0F, t1);
            }
        }
    }

    private static int getInterpolatedColor(int c1, int c2, int c3, float t) {
        if (t < 0.5F) {
            float u = t / 0.5F;
            return (int) ((float) c1 + (float) (c2 - c1) * u);
        } else {
            float u = (t - 0.5F) / 0.5F;
            return (int) ((float) c2 + (float) (c3 - c2) * u);
        }
    }

    private static void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos, int r, int g, int b, int a, float u, float v) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public ResourceLocation getTextureLocation(T t) {
        return t.TextureLocation();
    }
}
