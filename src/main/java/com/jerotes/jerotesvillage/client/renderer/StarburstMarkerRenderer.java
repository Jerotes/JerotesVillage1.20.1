package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicShoot.BaseMagicBoltEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.StarburstMarkerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class StarburstMarkerRenderer<T extends StarburstMarkerEntity> extends EntityRenderer<T> {
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation("jerotes", "textures/entity/beam/ray.png");

    private static final int SUBDIV = 3;
    private static final int FINS = 4;
    private static final float MAX_FIN_LEN = 0.20f;
    private static final float FIN_SHORT_MUL = 0.55f;
    private static final float FIN_START_T = 0.30f;
    private static final float FIN_ALPHA_MUL = 0.75f;
    private static final float TWIST = 2.8f;
    private static final float SPIN_SPEED = 0.30f;

    private static final int HELIX_STRANDS = 2;
    private static final float HELIX_RADIUS = 0.26f;
    private static final float HELIX_TURNS = 1.4f;
    private static final float HELIX_SPIN = 0.35f;
    private static final float HELIX_HALF_WIDTH = 0.028f;
    private static final float HELIX_START_T = 0.12f;
    private static final float HELIX_ALPHA_MUL = 0.95f;

    private static final float WAVE_AMP = 0.085f;
    private static final float WAVE_FREQ = 1.6f;
    private static final float WAVE_SPEED = 3.0f;
    private static final float WAVE_CENTER_MUL = 0.5f;
    private static final float MAX_CORE_HALF_WIDTH = 0.06f;
    private static final float CORE_ALPHA_MUL = 0.85f;
    private static final float TAIL_ALPHA_MUL = 0.12f;
    private static final float TIP_PURPLE_BLEND = 0.75f;
    private static final float CORE_YELLOW_BLEND = 0.7f;

    public StarburstMarkerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected int getBlockLightLevel(T t, BlockPos blockPos) {
        return 15;
    }

    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        this.renderStarTrail(entityIn, partialTicks, poseStack, bufferIn);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    private void renderStarTrail(T entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
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
        Vec3[] path = new Vec3[total];

        for (int s = 0; s < total; ++s) {
            float t = (float) s / (float) (total - 1);
            float fi = t * (float) (pointCount - 1);
            int index = Mth.clamp((int) fi, 0, pointCount - 2);
            float local = fi - (float) index;
            Vec3 point0 = trail.get(index).getPosition(partialTick);
            Vec3 point1 = trail.get(index + 1).getPosition(partialTick);
            path[s] = point0.lerp(point1, (double) local).subtract(entityPos);
        }

        //整条轨迹只定一次参考轴，避免中途 |dir.y| 跨过 0.9 时整颗星突然翻面
        Vec3 globalDir = path[total - 1].subtract(path[0]);
        if (globalDir.lengthSqr() < 1.0E-8) {
            //拖尾点全叠在一起（卡住不动）时轨迹推不出方向，改用实体自身朝向，别让星无缘无故换朝向
            globalDir = lookVector(entity);
        }
        globalDir = globalDir.normalize();
        Vec3 globalRef = Math.abs(globalDir.y) < 0.9 ? new Vec3((double) 0.0F, (double) 1.0F, (double) 0.0F) : new Vec3((double) 1.0F, (double) 0.0F, (double) 0.0F);

        int currentLife = entity.life;
        int maxLife = entity.getMaxLife();
        int trailMaxLife = entity.trailMaxLife();
        float lifeMul = 0.3f + 0.7f * Mth.clamp((float) currentLife / (float) maxLife, 0.0f, 1.0f);
        float timeSeed = ((float) currentLife + partialTick) * WAVE_SPEED;
        float phase = (float) (entity.getId() % 97) * 0.618f * Mth.TWO_PI;

        int colorI = entity.beamLightI();
        int colorII = entity.beamLightII();
        int colorIII = entity.beamLightIII();

        Vec3[] centers = new Vec3[total];
        Vec3[] waveAxes = new Vec3[total];
        Vec3[] sideAxes = new Vec3[total];
        Vec3[][] tips = new Vec3[FINS][total];
        Vec3[] helixRadial = new Vec3[total];
        Vec3[] helixTang = new Vec3[total];
        float[] finLens = new float[total];
        float[] helixRadii = new float[total];
        float[] alphas = new float[total];
        int[] coreRs = new int[total];
        int[] coreGs = new int[total];
        int[] coreBs = new int[total];
        int[] tipRs = new int[total];
        int[] tipGs = new int[total];
        int[] tipBs = new int[total];

        for (int s = 0; s < total; ++s) {
            float t = (float) s / (float) (total - 1);
            Vec3 dir = path[Math.min(total - 1, s + 1)].subtract(path[Math.max(0, s - 1)]);
            if (dir.lengthSqr() < 1.0E-8) {
                dir = globalDir;
            }
            dir = dir.normalize();

            Vec3 wave = dir.cross(globalRef);
            if (wave.lengthSqr() < 1.0E-8) {
                wave = new Vec3((double) 1.0F, (double) 0.0F, (double) 0.0F);
            }
            waveAxes[s] = wave.normalize();
            sideAxes[s] = dir.cross(waveAxes[s]).normalize();

            float envelope = Mth.sin(Mth.clamp(t, 0.02f, 0.98f) * Mth.PI);
            float offset = Mth.sin(t * WAVE_FREQ * Mth.TWO_PI + phase + timeSeed) * WAVE_AMP * envelope
                    + Mth.cos(t * WAVE_FREQ * Mth.PI * 1.3f + phase * 1.7f + timeSeed * 0.7f) * WAVE_AMP * 0.4f * envelope;
            centers[s] = path[s].add(waveAxes[s].scale((double) (offset * WAVE_CENTER_MUL)));

            //头重脚轻：所有半径都按 t 递增，越靠尾部越收成一根丝
            float headT = Mth.clamp(t, 0.0f, 1.0f);

            //四芒扭转星：主鳍(0/2)长、副鳍(1/3)短，只在靠头部一段出现
            float finGate = Mth.clamp((t - FIN_START_T) / (1.0f - FIN_START_T), 0.0f, 1.0f);
            finGate = (float) Math.pow((double) finGate, 0.6);
            float twist = t * TWIST + timeSeed * SPIN_SPEED + phase;
            for (int f = 0; f < FINS; ++f) {
                float ang = twist + (float) f * Mth.PI * 0.5f;
                Vec3 radial = waveAxes[s].scale((double) Mth.cos(ang)).add(sideAxes[s].scale((double) Mth.sin(ang)));
                float pulse = 0.9f + 0.1f * Mth.sin(timeSeed * 2.0f + (float) f * 1.7f + phase);
                float len = MAX_FIN_LEN * finGate * (float) Math.pow((double) (0.25f + 0.75f * headT), 1.1) * pulse;
                len *= (f & 1) == 0 ? 1.0f : FIN_SHORT_MUL;
                tips[f][s] = centers[s].add(radial.scale((double) len));
                if (f == 0) {
                    finLens[s] = len;
                }
            }

            //紫色缠绕：两条细丝绕在外围，半径也随 t 递增，靠尾部收拢
            float helixGate = Mth.clamp((t - HELIX_START_T) / (1.0f - HELIX_START_T), 0.0f, 1.0f);
            helixGate = (float) Math.pow((double) helixGate, 0.5);
            float helixAng = t * HELIX_TURNS * Mth.TWO_PI + timeSeed * HELIX_SPIN + phase;
            float hc = Mth.cos(helixAng);
            float hs = Mth.sin(helixAng);
            helixRadial[s] = waveAxes[s].scale((double) hc).add(sideAxes[s].scale((double) hs));
            helixTang[s] = sideAxes[s].scale((double) hc).subtract(waveAxes[s].scale((double) hs));
            helixRadii[s] = HELIX_RADIUS * helixGate * (float) Math.pow((double) (0.2f + 0.8f * headT), 0.9);

            int index = Mth.clamp((int) (t * (float) (pointCount - 1)), 0, pointCount - 1);
            int age = currentLife - trail.get(index).createdAt;
            float ageFade = 1.0f - Mth.clamp((float) age / (float) Math.max(1, trailMaxLife), 0.0f, 1.0f);
            alphas[s] = Mth.lerp(t, TAIL_ALPHA_MUL, 1.0f) * (0.25f + 0.75f * ageFade) * lifeMul * 0.9f;

            int ri = getInterpolatedColor(colorI >> 16 & 255, colorII >> 16 & 255, colorIII >> 16 & 255, t);
            int gi = getInterpolatedColor(colorI >> 8 & 255, colorII >> 8 & 255, colorIII >> 8 & 255, t);
            int bi = getInterpolatedColor(colorI & 255, colorII & 255, colorIII & 255, t);
            //中心偏浅黄（炽核），鳍尖偏紫（点缀），两段颜色顺序仍是 尾I→中II→头III
            coreRs[s] = (int) Mth.lerp(CORE_YELLOW_BLEND, (float) ri, (float) (colorII >> 16 & 255));
            coreGs[s] = (int) Mth.lerp(CORE_YELLOW_BLEND, (float) gi, (float) (colorII >> 8 & 255));
            coreBs[s] = (int) Mth.lerp(CORE_YELLOW_BLEND, (float) bi, (float) (colorII & 255));
            tipRs[s] = (int) Mth.lerp(TIP_PURPLE_BLEND, (float) ri, (float) (colorIII >> 16 & 255));
            tipGs[s] = (int) Mth.lerp(TIP_PURPLE_BLEND, (float) gi, (float) (colorIII >> 8 & 255));
            tipBs[s] = (int) Mth.lerp(TIP_PURPLE_BLEND, (float) bi, (float) (colorIII & 255));
        }

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSided(GLOW_LOCATION));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        //1) 四芒鳍
        for (int f = 0; f < FINS; ++f) {
            for (int s = 0; s < total - 1; ++s) {
                if (finLens[s] <= 0.002f && finLens[s + 1] <= 0.002f) {
                    continue;
                }
                float t0 = (float) s / (float) (total - 1);
                float t1 = (float) (s + 1) / (float) (total - 1);
                int a0 = (int) (Mth.clamp(alphas[s], 0.0f, 1.0f) * 200.0f * FIN_ALPHA_MUL);
                int a1 = (int) (Mth.clamp(alphas[s + 1], 0.0f, 1.0f) * 200.0f * FIN_ALPHA_MUL);
                addVertex(consumer, matrix, normal, centers[s], coreRs[s], coreGs[s], coreBs[s], a0, 0.0F, t0);
                addVertex(consumer, matrix, normal, tips[f][s], tipRs[s], tipGs[s], tipBs[s], a0, 1.0F, t0);
                addVertex(consumer, matrix, normal, tips[f][s + 1], tipRs[s + 1], tipGs[s + 1], tipBs[s + 1], a1, 1.0F, t1);
                addVertex(consumer, matrix, normal, centers[s + 1], coreRs[s + 1], coreGs[s + 1], coreBs[s + 1], a1, 0.0F, t1);
            }
        }

        //2) 外围紫色缠绕丝（纯 III 紫，不混黄，免得被亮芯加成白）
        int purpleR = colorIII >> 16 & 255;
        int purpleG = colorIII >> 8 & 255;
        int purpleB = colorIII & 255;
        for (int strand = 0; strand < HELIX_STRANDS; ++strand) {
            float strandSign = strand == 0 ? 1.0f : -1.0f;
            for (int s = 0; s < total - 1; ++s) {
                if (helixRadii[s] <= 0.002f && helixRadii[s + 1] <= 0.002f) {
                    continue;
                }
                Vec3 r0 = helixRadial[s].scale((double) (strandSign * helixRadii[s]));
                Vec3 r1 = helixRadial[s + 1].scale((double) (strandSign * helixRadii[s + 1]));
                float w0 = HELIX_HALF_WIDTH * (0.6f + 0.4f * (float) s / (float) (total - 1));
                float w1 = HELIX_HALF_WIDTH * (0.6f + 0.4f * (float) (s + 1) / (float) (total - 1));
                Vec3 l0 = centers[s].add(r0).subtract(helixTang[s].scale((double) w0));
                Vec3 rr0 = centers[s].add(r0).add(helixTang[s].scale((double) w0));
                Vec3 l1 = centers[s + 1].add(r1).subtract(helixTang[s + 1].scale((double) w1));
                Vec3 rr1 = centers[s + 1].add(r1).add(helixTang[s + 1].scale((double) w1));
                float t0 = (float) s / (float) (total - 1);
                float t1 = (float) (s + 1) / (float) (total - 1);
                float flicker0 = 0.8f + 0.2f * Mth.sin(timeSeed * 3.0f + (float) s);
                float flicker1 = 0.8f + 0.2f * Mth.sin(timeSeed * 3.0f + (float) (s + 1));
                int a0 = (int) (Mth.clamp(alphas[s] * flicker0, 0.0f, 1.0f) * 200.0f * HELIX_ALPHA_MUL);
                int a1 = (int) (Mth.clamp(alphas[s + 1] * flicker1, 0.0f, 1.0f) * 200.0f * HELIX_ALPHA_MUL);
                addVertex(consumer, matrix, normal, l0, purpleR, purpleG, purpleB, a0, 0.0F, t0);
                addVertex(consumer, matrix, normal, rr0, purpleR, purpleG, purpleB, a0, 1.0F, t0);
                addVertex(consumer, matrix, normal, rr1, purpleR, purpleG, purpleB, a1, 1.0F, t1);
                addVertex(consumer, matrix, normal, l1, purpleR, purpleG, purpleB, a1, 0.0F, t1);
            }
        }

        //3) 炽核：贴着中心线的细带，头粗尾细
        Vec3[] coreLeft = new Vec3[total];
        Vec3[] coreRight = new Vec3[total];
        for (int s = 0; s < total; ++s) {
            float t = (float) s / (float) (total - 1);
            float w = MAX_CORE_HALF_WIDTH * (float) Math.pow((double) (0.15f + 0.85f * Mth.clamp(t, 0.0f, 1.0f)), 0.7);
            coreLeft[s] = centers[s].subtract(waveAxes[s].scale((double) w));
            coreRight[s] = centers[s].add(waveAxes[s].scale((double) w));
        }
        for (int s = 0; s < total - 1; ++s) {
            float t0 = (float) s / (float) (total - 1);
            float t1 = (float) (s + 1) / (float) (total - 1);
            int a0 = (int) (Mth.clamp(alphas[s], 0.0f, 1.0f) * 200.0f * CORE_ALPHA_MUL);
            int a1 = (int) (Mth.clamp(alphas[s + 1], 0.0f, 1.0f) * 200.0f * CORE_ALPHA_MUL);
            addVertex(consumer, matrix, normal, coreLeft[s], coreRs[s], coreGs[s], coreBs[s], a0, 0.0F, t0);
            addVertex(consumer, matrix, normal, coreRight[s], coreRs[s], coreGs[s], coreBs[s], a0, 1.0F, t0);
            addVertex(consumer, matrix, normal, coreRight[s + 1], coreRs[s + 1], coreGs[s + 1], coreBs[s + 1], a1, 1.0F, t1);
            addVertex(consumer, matrix, normal, coreLeft[s + 1], coreRs[s + 1], coreGs[s + 1], coreBs[s + 1], a1, 0.0F, t1);
        }
    }

    private static Vec3 lookVector(Entity entity) {
        float f = entity.getXRot() * ((float) Math.PI / 180F);
        float f1 = -entity.getYRot() * ((float) Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        Vec3 vec3 = new Vec3((double) (f3 * f4), (double) (-f5), (double) (f2 * f4));
        if (vec3.lengthSqr() < 1.0E-8) {
            return new Vec3((double) 0.0F, (double) 0.0F, (double) 1.0F);
        }
        return vec3.normalize();
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
