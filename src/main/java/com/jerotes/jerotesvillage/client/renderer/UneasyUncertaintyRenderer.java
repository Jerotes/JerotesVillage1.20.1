package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray.UneasyUncertaintyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


public class UneasyUncertaintyRenderer<T extends UneasyUncertaintyEntity> extends EntityRenderer<T> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/item/magic_missile.png");
    private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    private final boolean fullBright;

    public UneasyUncertaintyRenderer(EntityRendererProvider.Context context, float f, boolean bl) {
        super(context);
        this.fullBright = bl;
    }

    public UneasyUncertaintyRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0f, true);
    }

    @Override
    protected int getBlockLightLevel(T t, BlockPos blockPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(t, blockPos);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.showBeam() && !entity.isFirst()) {
            poseStack.pushPose();
            renderBeam(entity, partialTick, poseStack, buffer, LightTexture.FULL_BRIGHT);
            poseStack.popPose();
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    // 主光束：外层雾罩(beamLightII) + 光芯(beamLightIII) + 点缀螺旋/光尘(beamLightIV) + 逐段光圈
    private void renderBeam(T entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Vec3 renderPos = entity.getPosition(partialTick);

        Vec3 startWorld = new Vec3(entity.getLastX(), entity.getLastY(), entity.getLastZ());
        Vec3 endWorld = renderPos;

        double raiseY = entity.getBbHeight() / 3.0;

        Vec3 startLocal = startWorld.subtract(renderPos).add(0.0, raiseY, 0.0);
        Vec3 endLocal = new Vec3(0.0, raiseY, 0.0);

        Vec3 dirLocal = endLocal.subtract(startLocal);
        float len = (float) dirLocal.length();
        if (len < 0.001F) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(startLocal.x, startLocal.y, startLocal.z);

        float horizontalDistance = Mth.sqrt((float) (dirLocal.x * dirLocal.x + dirLocal.z * dirLocal.z));

        float yaw;
        float pitch;
        if (horizontalDistance < 1.0E-4F) {
            yaw = 0.0F;
            pitch = dirLocal.y > 0.0 ? -(float) Math.PI / 2F : (float) Math.PI / 2F;
        } else {
            yaw = (float) Math.atan2(dirLocal.x, dirLocal.z);
            pitch = (float) Math.asin(-dirLocal.y / dirLocal.length());
        }

        poseStack.mulPose(Axis.YP.rotation(yaw));
        poseStack.mulPose(Axis.XP.rotation(pitch));

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();

        int outerColor = entity.beamLightII();
        int outerR = redOf(outerColor), outerG = greenOf(outerColor), outerB = blueOf(outerColor);

        int coreColor = entity.beamLightIII();
        int coreR = redOf(coreColor), coreG = greenOf(coreColor), coreB = blueOf(coreColor);

        float half = entity.getMaxLife() / 2.0f;
        float lifeRay = entity.getLifeRay() + partialTick;
        float lifeAbout = Mth.clamp((lifeRay - half) / half, 0.0f, 1.0f);
        float alpha = 1 - Math.max(0, 3 - lifeRay) / 3f;
        int outerAlpha = (int) (170 * alpha);
        int coreAlpha = (int) (255 * alpha);

        float baseScale;
        if (lifeAbout <= 5f) {
            float p = lifeAbout / 5f;
            float smooth = p * p * (3f - 2f * p);
            baseScale = 1f + 1.5f * (1f - smooth);
        } else if (lifeAbout <= 15f) {
            float p = (lifeAbout - 5f) / 10f;
            float smooth = p * p * (3f - 2f * p);
            baseScale = 1f + 0.125f * smooth;
        } else {
            baseScale = 1.0f;
        }
        float scale = entity.beamScale() * (1 - lifeAbout) * baseScale;

        float outerWidth = 0.09f * scale;
        float coreWidth = 0.035f * scale;
        float halfOuter = outerWidth / 2.0f;
        float halfCore = coreWidth / 2.0f;

        // 外层雾罩（beamLightII，加色混合）
        VertexConsumer hazeConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(BEAM_TEXTURE));
        renderQuadrilateralPipeAlpha(hazeConsumer, matrix, normal, len, halfOuter, outerR, outerG, outerB, (int) (outerAlpha * 0.5f), packedLight, false);

        // 光芯（beamLightIII，正常混合）
        VertexConsumer coreConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(BEAM_TEXTURE));
        renderQuadrilateralPipe(coreConsumer, matrix, normal, len, halfCore, coreR, coreG, coreB, coreAlpha, packedLight, false);

        // 点缀螺旋丝线（beamLightIV），紧贴光束缠绕
        renderLashSpiral(entity, poseStack, buffer, len, halfOuter);

        // 飘散的点缀光尘（beamLightIV）
        renderGoldDust(entity, poseStack, buffer, len);

        // 本节实体在起点处的光圈——多节实体连起来就是“一连串光圈接连绽放”
        renderSegmentIrisRing(entity, poseStack, buffer);

        // 命中效果：地面凝视印记 + 虹膜睁眼/眨眼合拢
        renderHitEffects(entity, partialTick, poseStack, buffer, len);

        poseStack.popPose();
    }

    // 逐段光圈：每一节分段实体在自己的起点画一圈瓣片，按 rayCount 交替 beamLightIII/IV 与旋向
    private void renderSegmentIrisRing(T entity, PoseStack poseStack, MultiBufferSource buffer) {
        int rayCount = entity.getRayCount();
        if (rayCount <= 0) return;

        float life = entity.getLifeRay();
        boolean altColor = (rayCount % 2 == 0);
        int packed = altColor ? entity.beamLightIV() : entity.beamLightIII();
        int r = redOf(packed), g = greenOf(packed), b = blueOf(packed);

        float spin = (life * 0.06f + rayCount * 0.9f) * (altColor ? 1f : -1f);
        float pulse = 0.15f * Mth.sin(life * 0.35f + rayCount);
        float ringRadius = (0.11f + pulse) * entity.beamScale();
        if (ringRadius < 0.01f) return;

        int alpha = (int) (190 * Mth.clamp(1f - (life / (entity.getMaxLife() * 1.3f)), 0f, 1f));
        if (alpha < 6) return;

        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotation(spin));

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(BEAM_TEXTURE));

        int blades = 6;
        float innerR = ringRadius * 0.4f;
        for (int i = 0; i < blades; i++) {
            float a1 = (float) i / blades * (float) (2 * Math.PI);
            float a2 = a1 + (float) (2 * Math.PI) / blades * 0.55f; // 留缝隙，形似光圈瓣片

            float ix1 = Mth.cos(a1) * innerR, iy1 = Mth.sin(a1) * innerR;
            float ix2 = Mth.cos(a2) * innerR, iy2 = Mth.sin(a2) * innerR;
            float ox1 = Mth.cos(a1) * ringRadius, oy1 = Mth.sin(a1) * ringRadius;
            float ox2 = Mth.cos(a2) * ringRadius, oy2 = Mth.sin(a2) * ringRadius;

            addVertex(consumer, matrix, normal, new Vec3(ix1, iy1, 0), r, g, b, alpha, 0, 0);
            addVertex(consumer, matrix, normal, new Vec3(ox1, oy1, 0), r, g, b, 0, 1, 0);
            addVertex(consumer, matrix, normal, new Vec3(ox2, oy2, 0), r, g, b, 0, 1, 1);
            addVertex(consumer, matrix, normal, new Vec3(ix2, iy2, 0), r, g, b, alpha, 0, 1);
        }

        poseStack.popPose();
    }

    // 点缀螺旋丝线（beamLightIV）：细窄丝线沿光束缠绕
    private void renderLashSpiral(T entity, PoseStack poseStack, MultiBufferSource buffer, float length, float outerRadius) {
        if (length < 0.001f || outerRadius < 0.001f) return;

        int packed = entity.beamLightIV();
        int lashR = redOf(packed), lashG = greenOf(packed), lashB = blueOf(packed);

        float life = entity.getLifeRay();
        float maxLife = entity.getMaxLife();
        float progressLife = Mth.clamp(life / maxLife, 0f, 1f);

        int segments = 14;
        float step = 1.0f / segments;
        float rayCountBase = entity.getRayCount();
        float spinSpeed = 0.18f;
        float maxTwist = 1.4f * (float) Math.PI;

        float lashRadius = outerRadius * 1.6f;

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        Vec3[] leftPoints = new Vec3[segments + 1];
        Vec3[] rightPoints = new Vec3[segments + 1];
        float[] alphas = new float[segments + 1];

        for (int i = 0; i <= segments; i++) {
            float progress = (float) i / segments;
            float z = progress * length;

            float offset = (rayCountBase + i) * step;
            float angle = life * spinSpeed + progress * maxTwist + offset * maxTwist;
            float cos = Mth.cos(angle);
            float sin = Mth.sin(angle);

            float alpha = 0.75f * (1f - progressLife * 0.55f) * (1f - Math.abs(progress - 0.5f) * 0.6f);
            alpha = Mth.clamp(alpha, 0f, 1f);

            Vec3 offsetVec = new Vec3(cos * lashRadius, sin * lashRadius, 0);
            Vec3 point = new Vec3(0, 0, z).add(offsetVec);

            float bandWidth = 0.014f * (1f - progressLife * 0.5f);
            Vec3 tangent = new Vec3(-sin, cos, 0).normalize();
            leftPoints[i] = point.add(tangent.scale(-bandWidth));
            rightPoints[i] = point.add(tangent.scale(bandWidth));
            alphas[i] = alpha;
        }

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(BEAM_TEXTURE));
        for (int i = 0; i < segments; i++) {
            Vec3 p0l = leftPoints[i];
            Vec3 p0r = rightPoints[i];
            Vec3 p1l = leftPoints[i + 1];
            Vec3 p1r = rightPoints[i + 1];

            int a0 = (int) (alphas[i] * 235);
            int a1 = (int) (alphas[i + 1] * 235);

            addVertex(consumer, matrix, normal, p0l, lashR, lashG, lashB, a0, 0, 0);
            addVertex(consumer, matrix, normal, p0r, lashR, lashG, lashB, a0, 1, 0);
            addVertex(consumer, matrix, normal, p1r, lashR, lashG, lashB, a1, 1, 1);
            addVertex(consumer, matrix, normal, p1l, lashR, lashG, lashB, a1, 0, 1);
        }
    }

    // 飘散光尘（beamLightIV）：沿本节光束随机漂移的细小微粒
    private void renderGoldDust(T entity, PoseStack poseStack, MultiBufferSource buffer, float length) {
        if (length < 0.2f) return;

        int packed = entity.beamLightIV();
        int dustR = redOf(packed), dustG = greenOf(packed), dustB = blueOf(packed);

        int motes = 5;
        long seed = entity.getId() * 12289L + entity.getRayCount() * 97L;
        java.util.Random rand = new java.util.Random(seed);

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(BEAM_TEXTURE));

        float life = entity.getLifeRay();
        int alpha = (int) (200 * Mth.clamp(1f - life / (entity.getMaxLife() * 1.5f), 0f, 1f));
        if (alpha < 8) return;

        for (int i = 0; i < motes; i++) {
            float z = rand.nextFloat() * length;
            float driftAngle = rand.nextFloat() * (float) (2 * Math.PI) + life * 0.4f;
            float driftRadius = 0.06f + rand.nextFloat() * 0.1f;
            float x = Mth.cos(driftAngle) * driftRadius;
            float y = Mth.sin(driftAngle) * driftRadius;
            float size = 0.015f + rand.nextFloat() * 0.01f;

            addVertex(consumer, matrix, normal, new Vec3(x - size, y - size, z), dustR, dustG, dustB, alpha, 0, 0);
            addVertex(consumer, matrix, normal, new Vec3(x + size, y - size, z), dustR, dustG, dustB, alpha, 1, 0);
            addVertex(consumer, matrix, normal, new Vec3(x + size, y + size, z), dustR, dustG, dustB, alpha, 1, 1);
            addVertex(consumer, matrix, normal, new Vec3(x - size, y + size, z), dustR, dustG, dustB, alpha, 0, 1);
        }
    }

    // 命中效果调度：地面凝视印记 + 虹膜睁眼/眨眼合拢
    private void renderHitEffects(T entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, float beamLength) {
        if (!entity.isHitUseful() || entity.isUseful()) {
            return;
        }
        float maxClamp = 15.0f;
        float lifeBeam = Mth.clamp(entity.getLifeRayLast() + partialTick, 1.0f, maxClamp);
        poseStack.pushPose();
        poseStack.translate(0, 0, beamLength);
        renderGazePool(entity, poseStack, buffer, lifeBeam, 9f, maxClamp);
        poseStack.popPose();
        renderEyeBurstCore(entity, lifeBeam, poseStack, buffer, beamLength);
    }

    private void renderEyeBurstCore(T entity, float lifeBeam, PoseStack poseStack, MultiBufferSource buffer, float beamLength) {
        float openEnd = 3.5f;
        float holdEnd = 8.0f;
        float blinkEnd = 12.0f;

        float openProgress;
        float holdAlpha;
        float blinkProgress;

        if (lifeBeam <= openEnd) {
            openProgress = lifeBeam / openEnd;
            holdAlpha = openProgress;
            blinkProgress = 0f;
        } else if (lifeBeam <= holdEnd) {
            openProgress = 1f;
            holdAlpha = 1f;
            blinkProgress = 0f;
        } else if (lifeBeam <= blinkEnd) {
            openProgress = 1f;
            blinkProgress = (lifeBeam - holdEnd) / (blinkEnd - holdEnd);
            holdAlpha = 1f - blinkProgress;
        } else {
            openProgress = 0f;
            blinkProgress = 1f;
            holdAlpha = 0f;
        }

        openProgress = Mth.clamp(openProgress, 0f, 1f);
        blinkProgress = Mth.clamp(blinkProgress, 0f, 1f);
        holdAlpha = Mth.clamp(holdAlpha, 0f, 1f);

        if (openProgress <= 0.01f && blinkProgress >= 0.99f) {
            return;
        }

        float maxRadius = 2.4f;
        float radius = maxRadius * openProgress;
        if (radius < 0.02f) return;

        // 球冠张角：睁眼期间张开，眨眼期间收窄至近乎闭合
        float coverage = 0.5f * (1f - blinkProgress * 0.92f);
        coverage = Mth.clamp(coverage, 0.01f, 0.9f);
        float maxPhi = (float) Math.acos(1.0 - coverage);
        float centerOffset = radius * (float) Math.cos(maxPhi);

        float spin = entity.getLifeRay() * 0.12f + blinkProgress * blinkProgress * 6.0f;
        int baseAlpha = (int) (255 * holdAlpha * (1f - blinkProgress * 0.3f) + 255 * blinkProgress * 0.15f);
        baseAlpha = Mth.clamp(baseAlpha, 0, 255);

        poseStack.pushPose();
        poseStack.translate(0, 0, beamLength + centerOffset);
        poseStack.mulPose(Axis.XP.rotation(-(float) Math.PI / 2));


        poseStack.popPose();
    }

    private void renderGazePool(T entity, PoseStack poseStack, MultiBufferSource buffer, float lifeBeam, float burstFadeEnd, float maxClamp) {
        float poolStart = 2.0f;
        float poolGrowEnd = 5.0f;

        float poolProgress = (lifeBeam - poolStart) / Math.max(1f, poolGrowEnd - poolStart);
        poolProgress = Mth.clamp(poolProgress, 0f, 1f);

        float fadeProgress = (lifeBeam - burstFadeEnd) / Math.max(1f, maxClamp - burstFadeEnd);
        fadeProgress = Mth.clamp(fadeProgress, 0f, 1f);

        float poolAlpha = (1f - fadeProgress) * 0.5f;
        if (poolAlpha < 0.02f || poolProgress <= 0f) {
            return;
        }

        int outerPacked = entity.beamLightII();
        int outerR = redOf(outerPacked), outerG = greenOf(outerPacked), outerB = blueOf(outerPacked);
        int innerPacked = entity.beamLightIII();
        int innerR = redOf(innerPacked), innerG = greenOf(innerPacked), innerB = blueOf(innerPacked);

        float poolRadius = 3.2f * poolProgress;

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(BEAM_TEXTURE));

        poseStack.pushPose();

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        int segments = 20;
        Vec3 up = new Vec3(0, 0, 1); // 在当前坐标系中，Z轴朝发射方向
        int innerAlpha = (int) (255 * poolAlpha * 0.8f);

        for (int i = 0; i < segments; i++) {
            float a1 = (float) i / segments * (float) (2 * Math.PI);
            float a2 = (float) (i + 1) / segments * (float) (2 * Math.PI);

            // 在 XY 平面上画圆，Z 方向为光束方向
            float x1 = Mth.cos(a1) * poolRadius;
            float y1 = Mth.sin(a1) * poolRadius;
            float x2 = Mth.cos(a2) * poolRadius;
            float y2 = Mth.sin(a2) * poolRadius;

            // 圆环：两个三角形组成一个扇形，从原点向外辐射
            // 顶点0：原点（内圈，高透明度）
            // 顶点1/2：外圈两个点（低透明度）
            addVertexBall(consumer, matrix, normal, 0, 0, 0, innerR, innerG, innerB, innerAlpha, 0.5f, 0.5f, up);
            addVertexBall(consumer, matrix, normal, x1, y1, 0, outerR, outerG, outerB, 0, 0, 0, up);
            addVertexBall(consumer, matrix, normal, x2, y2, 0, outerR, outerG, outerB, 0, 1, 0, up);
        }

        poseStack.popPose();
    }

    // 颜色拆分辅助（沿用项目里 (color >> 16) & 0xFF 的一贯写法，只是抽成方法减少重复）
    private static int redOf(int packedColor) {
        return (packedColor >> 16) & 0xFF;
    }

    private static int greenOf(int packedColor) {
        return (packedColor >> 8) & 0xFF;
    }

    private static int blueOf(int packedColor) {
        return packedColor & 0xFF;
    }

    // 通用顶点/几何辅助方法（与原实现保持一致，未改动）
    private void addVertexBall(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMat, float x, float y, float z, int r, int g, int b, int a, float u, float v, Vec3 normal) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMat, (float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
    }

    private void renderQuadrilateralPipeAlpha(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, float distance, float halfWidth, int r, int g, int b, int a, int light, boolean bl) {
        float alpha = a / 255f;
        r = (int) (r * alpha);
        g = (int) (g * alpha);
        b = (int) (b * alpha);
        if (bl) {
            consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();

            consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
        }
        consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();

        consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();

        consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();

        consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
    }

    private void renderQuadrilateralPipe(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, float distance, float halfWidth, int r, int g, int b, int a, int light, boolean bl) {
        if (bl) {
            consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
            consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();

            consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
            consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
        }
        consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();

        consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();

        consumer.vertex(matrix, -halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
        consumer.vertex(matrix, -halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();

        consumer.vertex(matrix, halfWidth, -halfWidth, 0).color(r, g, b, a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, -halfWidth, distance).color(r, g, b, a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, distance).color(r, g, b, a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
        consumer.vertex(matrix, halfWidth, halfWidth, 0).color(r, g, b, a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos, int r, int g, int b, int a, float u, float v) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0, 1, 0)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(UneasyUncertaintyEntity tex) {
        return TEXTURE_LOCATION;
    }
}
