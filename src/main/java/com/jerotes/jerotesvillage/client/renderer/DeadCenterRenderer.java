package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.DeadCenterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DeadCenterRenderer extends EntityRenderer<DeadCenterEntity> {
    private static final ResourceLocation TEXTURE_LOCATION =
            new ResourceLocation(JerotesWarehouse.MODID,
                    "textures/item/magic_missile.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    private static final float BEAM_FADE_RAMP = 0.5F;

    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public DeadCenterRenderer(EntityRendererProvider.Context context, float scale,
                          boolean fullBright) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public DeadCenterRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, true);
    }

    @Override
    protected int getBlockLightLevel(DeadCenterEntity entity, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    @Override
    public void render(DeadCenterEntity entity,
                       float entityYaw,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {

        if (entity.showRound() && entity.getTargetHeight() > 0) {
            renderRound(entity, partialTick, poseStack, buffer);
        }
        poseStack.pushPose();

        poseStack.scale(this.scale, this.scale, this.scale);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        this.itemRenderer.renderStatic(
                entity.getItem(),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
        if (entity.life > 8 && entity.life <= 13) {
            renderBeam(entity, partialTick, poseStack, buffer, packedLight);
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderBeam(DeadCenterEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.getOwner() == null) return;
        Vec3 renderPos = entity.getPosition(partialTick);
        Entity owner = entity.getOwner();
        Vec3 startWorld;
        if (owner instanceof CelestialCoilvineEntity caster) {
            float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
            double x = caster.getX() + 4.5 * Mth.cos((float) (caster.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
            double z = caster.getZ() + 4.5 * Mth.sin((float) (caster.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
            startWorld = new Vec3(x, caster.getY() + caster.getBbHeight() * 0.775f, z);
        }
        else {
            startWorld = owner.position().add(0, owner.getBbHeight() * 0.7f, 0);
        }
        Vec3 endWorld = renderPos.add(0, entity.getBbHeight() * 0.7f, 0);

        Vec3 startLocal = startWorld.subtract(renderPos);
        Vec3 endLocal = endWorld.subtract(renderPos);

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

        int innerR = (entity.roundLightI() >> 16) & 0xFF;
        int innerG = (entity.roundLightI() >> 8) & 0xFF;
        int innerB = entity.roundLightI() & 0xFF;
        int outerR = (entity.roundLightII() >> 16) & 0xFF;
        int outerG = (entity.roundLightII() >> 8) & 0xFF;
        int outerB = entity.roundLightII() & 0xFF;
        float half = entity.getMaxLife() / 2.0f;
        float lifeRay = entity.life - 10 + partialTick;
        float lifeAbout = Mth.clamp((lifeRay - half) / half, 0.0f, 1.0f);
        float fade = beamFade(entity, partialTick);
        int innerAlpha = (int) (255 * fade);
        int outerAlpha = (int) (180 * fade);

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
        float scale = 0.25f * (1 - lifeAbout) * baseScale;

        float outerWidth = (0.065f) * scale;
        float innerWidth = (0.055f) * scale;
        float halfOuter = outerWidth / 2.0f;
        float halfInner = innerWidth / 2.0f;

        VertexConsumer consumer3 = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png")));
        renderQuadrilateralPipeAlpha(consumer3, matrix, normal, len, halfOuter * 2f, outerR, outerG, outerB, (int) (outerAlpha * 0.45f), packedLight, false);
        VertexConsumer consumer2 = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png")));
        renderQuadrilateralPipe(consumer2, matrix, normal, len, halfOuter, outerR, outerG, outerB, outerAlpha, packedLight, false);
        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png")));
        renderQuadrilateralPipe(consumer, matrix, normal, len, halfInner, innerR, innerG, innerB, innerAlpha, packedLight, false);
        renderSpiralWrap(entity, partialTick, poseStack, buffer, packedLight, len, halfOuter, fade);

        poseStack.popPose();
    }

    private float beamFade(DeadCenterEntity entity, float partialTick) {
        float start = entity.getUseLife() - 2;
        float end = entity.getMaxLife() - entity.getUseLife() + 3;
        float span = end - start;
        float ramp = Math.max(0.01f, span * BEAM_FADE_RAMP);
        float t = (entity.life + partialTick) - start;
        float in = Mth.clamp(t / ramp, 0.0f, 1.0f);
        float out = Mth.clamp((span - t) / ramp, 0.0f, 1.0f);
        return smooth(in) * smooth(out);
    }

    private static float smooth(float t) {
        return t * t * (3.0f - 2.0f * t);
    }

    private void renderSpiralWrap(DeadCenterEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float length, float outerRadius, float fade) {
        if (length < 0.001f || outerRadius < 0.001f) return;

        float life = entity.life - 10;
        float maxLife = entity.getMaxLife();
        float progressLife = Mth.clamp(life / maxLife, 0f, 1f);

        int segments = 18;
        float step = 1.0f / segments;
        float rayCountBase = 1;
        float drillSpeed = 0.2f;
        float maxTwist = 2.0f * (float) Math.PI;

        float wrapRadius = outerRadius + outerRadius * (Math.min(16, 1 + rayCountBase * rayCountBase * 0.15f));

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        Vec3[] leftPoints = new Vec3[segments + 1];
        Vec3[] rightPoints = new Vec3[segments + 1];
        float[] alphas = new float[segments + 1];

        for (int i = 0; i <= segments; i++) {
            float progress = (float) i / segments;
            float z = progress * length;

            float offset = (rayCountBase + i) * step;
            float angle = life * drillSpeed + progress * maxTwist + offset * maxTwist;
            float cos = Mth.cos(angle);
            float sin = Mth.sin(angle);

            float currentRadius = wrapRadius;

            float alpha = 0.8f * (1f - progressLife * 0.6f) * (1f - Math.abs(progress - 0.5f) * 0.5f) * fade;
            alpha = Math.max(0f, Math.min(1f, alpha));

            Vec3 offsetVec = new Vec3(cos * currentRadius, sin * currentRadius, 0);
            Vec3 point = new Vec3(0, 0, z).add(offsetVec);

            float bandWidth = 0.025f * (1f - progressLife * 0.5f);
            Vec3 tangent = new Vec3(-sin, cos, 0).normalize();
            leftPoints[i] = point.add(tangent.scale(-bandWidth));
            rightPoints[i] = point.add(tangent.scale(bandWidth));
            alphas[i] = alpha;
        }

        int colorI = entity.roundLightI();
        int r = (colorI >> 16) & 0xFF;
        int g = (colorI >> 8) & 0xFF;
        int b = colorI & 0xFF;

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(
                new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png")));
        for (int i = 0; i < segments; i++) {
            Vec3 p0l = leftPoints[i];
            Vec3 p0r = rightPoints[i];
            Vec3 p1l = leftPoints[i + 1];
            Vec3 p1r = rightPoints[i + 1];

            int a0 = (int) (alphas[i] * 250);
            int a1 = (int) (alphas[i + 1] * 250);

            addVertex(consumer, matrix, normal, p0l, r, g, b, a0, 0, 0);
            addVertex(consumer, matrix, normal, p0r, r, g, b, a0, 1, 0);
            addVertex(consumer, matrix, normal, p1r, r, g, b, a1, 1, 1);
            addVertex(consumer, matrix, normal, p1l, r, g, b, a1, 0, 1);
        }
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

    private void renderRound(DeadCenterEntity entity,
                             float partialTick,
                             PoseStack poseStack,
                             MultiBufferSource buffer) {

        float growProgress = 0;
        float fadeAlpha = 0;

        int lifeNeed = entity.getUseLife()/2;
        if (entity.life > 2 && entity.life < lifeNeed) {
            growProgress = (entity.life + partialTick) / (float) lifeNeed;
            fadeAlpha = 1.0F;
        }
        else if (entity.life > 2) {
            growProgress = 1.0F - ((entity.life - lifeNeed + partialTick) / (float) (entity.getMaxLife() - lifeNeed));
            growProgress = Mth.clamp(growProgress, 0F, 1F);
            fadeAlpha = 1.0F;
            fadeAlpha = Mth.clamp(fadeAlpha, 0F, 1F);
        }

        float halfWidth = entity.getSize() * growProgress * 1.1f + 0.15f;
        float height = entity.getSize() * growProgress * 1.2F + 0.5f;

        int topColor = entity.roundLightI();
        int bottomColor = entity.roundLightII();

        int tr = (topColor >> 16) & 255;
        int tg = (topColor >> 8) & 255;
        int tb = topColor & 255;

        int br = (bottomColor >> 16) & 255;
        int bg = (bottomColor >> 8) & 255;
        int bb = bottomColor & 255;

        int bottomAlpha = (int) (220 * fadeAlpha);

        VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(GLOW_LOCATION));

        PoseStack.Pose pose = poseStack.last();

        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();

        float yBottom = 0.0F - entity.getTargetHeight() / 2f;
        float yTop = height;

        addQuad(consumer, matrix, normal, -halfWidth, yBottom, halfWidth, -halfWidth, yTop, halfWidth, halfWidth, yTop, halfWidth, halfWidth, yBottom, halfWidth, br, bg, bb, tr, tg, tb, bottomAlpha);
        addQuad(consumer, matrix, normal, halfWidth, yBottom, -halfWidth, halfWidth, yTop, -halfWidth, -halfWidth, yTop, -halfWidth, -halfWidth, yBottom, -halfWidth, br, bg, bb, tr, tg, tb, bottomAlpha);
        addQuad(consumer, matrix, normal, -halfWidth, yBottom, -halfWidth, -halfWidth, yTop, -halfWidth, -halfWidth, yTop, halfWidth, -halfWidth, yBottom, halfWidth, br, bg, bb, tr, tg, tb, bottomAlpha);
        addQuad(consumer, matrix, normal, halfWidth, yBottom, halfWidth, halfWidth, yTop, halfWidth, halfWidth, yTop, -halfWidth, halfWidth, yBottom, -halfWidth, br, bg, bb, tr, tg, tb, bottomAlpha);
    }

    private void addQuad(VertexConsumer consumer,
                         Matrix4f matrix,
                         Matrix3f normal,

                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         float x3, float y3, float z3,
                         float x4, float y4, float z4,

                         int br, int bg, int bb,
                         int tr, int tg, int tb,
                         int bottomAlpha) {

        consumer.vertex(matrix, x1, y1, z1)
                .color(br, bg, bb, bottomAlpha)
                .uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0, 1, 0)
                .endVertex();

        consumer.vertex(matrix, x2, y2, z2)
                .color(tr, tg, tb, 0)
                .uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0, 1, 0)
                .endVertex();

        consumer.vertex(matrix, x3, y3, z3)
                .color(tr, tg, tb, 0)
                .uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0, 1, 0)
                .endVertex();

        consumer.vertex(matrix, x4, y4, z4)
                .color(br, bg, bb, bottomAlpha)
                .uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normal, 0, 1, 0)
                .endVertex();
    }

    @Override
    public boolean shouldRender(DeadCenterEntity entity, Frustum frustum, double camX, double camY, double camZ) {
        return entity.getOwner() != null && entity.getTarget() != null;
    }

    @Override
    public ResourceLocation getTextureLocation(DeadCenterEntity entity) {
        return TEXTURE_LOCATION;
    }
}
