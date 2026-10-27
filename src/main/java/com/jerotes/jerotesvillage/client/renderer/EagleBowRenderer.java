package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modeleagle_bow;
import com.jerotes.jerotesvillage.entity.Boss.EagleBowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

import java.util.Objects;

public class EagleBowRenderer extends MobRenderer<EagleBowEntity, Modeleagle_bow<EagleBowEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/eagle_bow.png");
    public EagleBowRenderer(EntityRendererProvider.Context context) {
        super(context, new Modeleagle_bow(context.bakeLayer(Modeleagle_bow.LAYER_LOCATION)), 0.90f);
        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(EagleBowEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.75f, 1.75f, 1.75f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(EagleBowEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        entity.thisTickRenderTime += 1;
        float attackAnim = entity.getAttackAnim();
        float shootAnim = entity.getShootAnim();
        float shadowAnim = entity.getShadowAnim();
        float fs = entity.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        entity.attackAnimProgress = Mth.lerp(smoothFactor, entity.attackAnimProgress, attackAnim);
        entity.shootAnimProgress = Mth.lerp(smoothFactor, entity.shootAnimProgress, shootAnim);
        entity.shadowAnimProgress = Mth.lerp(smoothFactor, entity.shadowAnimProgress, shadowAnim);
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
        if (shadowAnim > 0) {
            this.renderShadowBefore(entity, f, f2, poseStack, multiBufferSource, n);
        }
        else {
            while (!entity.listRenderer1.isEmpty()) {
                entity.listRenderer1.remove(entity.listRenderer1.size() - 1);
                entity.listRenderer2.remove(entity.listRenderer2.size() - 1);
                entity.listRenderer3.remove(entity.listRenderer3.size() - 1);
                entity.listX.remove(entity.listX.size() - 1);
                entity.listY.remove(entity.listY.size() - 1);
                entity.listZ.remove(entity.listZ.size() - 1);
                entity.listXRot.remove(entity.listXRot.size() - 1);
                entity.listYRot.remove(entity.listYRot.size() - 1);
                entity.listXRotO.remove(entity.listXRotO.size() - 1);
                entity.listYRotO.remove(entity.listYRotO.size() - 1);
                entity.listAttackTime.remove(entity.listAttackTime.size() - 1);
                entity.listYBodyRot.remove(entity.listYBodyRot.size() - 1);
                entity.listYHeadRot.remove(entity.listYHeadRot.size() - 1);
                entity.listYBodyRotO.remove(entity.listYBodyRotO.size() - 1);
                entity.listYHeadRotO.remove(entity.listYHeadRotO.size() - 1);
                entity.listWalkSpeed.remove(entity.listWalkSpeed.size() - 1);
                entity.listBob.remove(entity.listBob.size() - 1);
                entity.listWalkPosition.remove(entity.listWalkPosition.size() - 1);
                entity.listRiding.remove(entity.listRiding.size() - 1);
                entity.listRidingLivingEntity.remove(entity.listRidingLivingEntity.size() - 1);
                entity.listAgeInTicks.remove(entity.listAgeInTicks.size() - 1);
            }
        }
    }
    public void renderShadowBefore(EagleBowEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        entity.renderTick ++;
        if (entity.renderTick > EagleBowEntity.SHADOW_COOLDOWN) {
            entity.listRenderer1.add(0, f);
            entity.listRenderer2.add(0, f2);
            entity.listRenderer3.add(0, n);
            entity.listX.add(0, entity.getX());
            entity.listY.add(0, entity.getY());
            entity.listZ.add(0, entity.getZ());
            entity.listXRot.add(0, entity.getXRot());
            entity.listYRot.add(0, entity.getYRot());
            entity.listXRotO.add(0, entity.xRotO);
            entity.listYRotO.add(0, entity.yRotO);
            entity.listAttackTime.add(0, this.getAttackAnim(entity, f2));
            boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
            boolean shouldSitLivingEntity = shouldSit && entity.getVehicle() instanceof LivingEntity;
            entity.listRiding.add(0, shouldSit);
            entity.listRidingLivingEntity.add(0, shouldSitLivingEntity);
            entity.listYBodyRot.add(0, shouldSitLivingEntity && entity.getVehicle() instanceof LivingEntity livingentity ? livingentity.yBodyRot : entity.yBodyRot);
            entity.listYHeadRot.add(0, entity.yHeadRot);
            entity.listYBodyRotO.add(0, shouldSitLivingEntity && entity.getVehicle() instanceof LivingEntity livingentity ? livingentity.yBodyRotO : entity.yBodyRotO);
            entity.listYHeadRotO.add(0, entity.yHeadRotO);
            float speed = entity.walkAnimation.speed(f2);
            float position = entity.walkAnimation.position(f2);
            entity.listWalkSpeed.add(0, speed);
            entity.listWalkPosition.add(0, position);
            entity.listBob.add(0, getBob(entity, f2));
            entity.listAgeInTicks.add(0, entity.tickCount + f2);

            while (entity.listRenderer1.size() > EagleBowEntity.MAX_SHADOW_HISTORY) {
                entity.listRenderer1.remove(entity.listRenderer1.size() - 1);
                entity.listRenderer2.remove(entity.listRenderer2.size() - 1);
                entity.listRenderer3.remove(entity.listRenderer3.size() - 1);
                entity.listX.remove(entity.listX.size() - 1);
                entity.listY.remove(entity.listY.size() - 1);
                entity.listZ.remove(entity.listZ.size() - 1);
                entity.listXRot.remove(entity.listXRot.size() - 1);
                entity.listYRot.remove(entity.listYRot.size() - 1);
                entity.listXRotO.remove(entity.listXRotO.size() - 1);
                entity.listYRotO.remove(entity.listYRotO.size() - 1);
                entity.listAttackTime.remove(entity.listAttackTime.size() - 1);
                entity.listYBodyRot.remove(entity.listYBodyRot.size() - 1);
                entity.listYHeadRot.remove(entity.listYHeadRot.size() - 1);
                entity.listYBodyRotO.remove(entity.listYBodyRotO.size() - 1);
                entity.listYHeadRotO.remove(entity.listYHeadRotO.size() - 1);
                entity.listWalkSpeed.remove(entity.listWalkSpeed.size() - 1);
                entity.listBob.remove(entity.listBob.size() - 1);
                entity.listWalkPosition.remove(entity.listWalkPosition.size() - 1);
                entity.listRiding.remove(entity.listRiding.size() - 1);
                entity.listRidingLivingEntity.remove(entity.listRidingLivingEntity.size() - 1);
                entity.listAgeInTicks.remove(entity.listAgeInTicks.size() - 1);
            }
            entity.renderTick = 0;
        }

        if (entity.listX.size() > 1) {
            for (int i = 1; i < entity.listX.size(); i++) {
                double oldX = entity.listX.get(i);
                double oldY = entity.listY.get(i);
                double oldZ = entity.listZ.get(i);
                poseStack.pushPose();
                poseStack.translate(oldX - entity.getX(), oldY - entity.getY(), oldZ - entity.getZ());
                this.renderShadow(entity, i, entity.listRenderer1.get(i), entity.listRenderer2.get(i), poseStack, multiBufferSource, entity.listRenderer3.get(i));
                poseStack.popPose();
            }
        }
    }
    public void renderShadow(EagleBowEntity entity, int type, float baseF, float baseF2, PoseStack poseStack, MultiBufferSource multiBufferSource, int baseN) {
        float alpha = Mth.clamp(1f - (((float) type + (float) entity.renderTick / (float) EagleBowEntity.SHADOW_COOLDOWN) / (float) EagleBowEntity.MAX_SHADOW_HISTORY), 0, 1);
        if (alpha < 0.05f) return;
        poseStack.pushPose();
        this.model.attackTime = entity.listAttackTime.get(type);
        boolean shouldSit = entity.listRiding.get(type);
        this.model.riding = shouldSit;
        this.model.young = entity.isBaby();
        float f = Mth.rotLerp(baseF2, entity.listYBodyRotO.get(type), entity.listYBodyRot.get(type));
        float f1 = Mth.rotLerp(baseF2, entity.listYHeadRotO.get(type), entity.listYHeadRot.get(type));
        float f2 = f1 - f;
        if (entity.listRidingLivingEntity.get(type)) {
            f = Mth.rotLerp(baseF2, entity.yBodyRotO, entity.listYBodyRot.get(type));
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }
            f2 = f1 - f;
        }
        float f6 = Mth.lerp(baseF2, entity.listXRotO.get(type), entity.listXRot.get(type));
        if (isEntityUpsideDown(entity)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }
        if (entity.hasPose(Pose.SLEEPING)) {
            Direction direction = entity.getBedOrientation();
            if (direction != null) {
                float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
                poseStack.translate((float)(-direction.getStepX()) * f4, 0.0F, (float)(-direction.getStepZ()) * f4);
            }
        }
        float f7 = entity.listBob.get(type);
        float ageInTicks = entity.listAgeInTicks.get(type);
        this.setupRotationsShadow(entity, poseStack, type, f7, f, baseF2);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, poseStack, baseF2);
        poseStack.translate(0.0F, -1.501F, 0.0F);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            f8 = entity.listWalkSpeed.get(type);
            f5 = entity.listWalkPosition.get(type);
            if (entity.isBaby()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }
        this.model.prepareMobModel(entity, f5, f8, baseF2);
        this.model.setupAnim(entity, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(entity);
        boolean flag1 = !flag && !entity.isInvisibleTo(Objects.requireNonNull(minecraft.player));
        boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.getRenderType(entity, flag, flag1, flag2);
        if (rendertype != null) {
            ResourceLocation armorLocation = LOCATION;
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(armorLocation));
            int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, baseF2));
            this.model.renderToBuffer(poseStack, vertexConsumer, baseN, i, 1.0F, 1.0F, 1.0F, 0.6f * (alpha) * Mth.clamp(entity.shadowAnimProgress / 10.0f, 0.0f, 1.0f));
        }
        poseStack.popPose();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<>(entity, this, baseF2, poseStack, multiBufferSource, baseN));
    }
    protected void setupRotationsShadow(EagleBowEntity entity, PoseStack p_115318_, int type, float p_115319_, float p_115320_, float p_115321_) {
        if (!entity.hasPose(Pose.SLEEPING)) {
            p_115318_.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
        }
        if (entity.deathTime > 0) {
            float f = ((float)entity.deathTime + p_115321_ - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }
            p_115318_.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(entity)));
        } else if (entity.isAutoSpinAttack()) {
            p_115318_.mulPose(Axis.XP.rotationDegrees(-90.0F - entity.listXRot.get(type)));
            p_115318_.mulPose(Axis.YP.rotationDegrees(((float)entity.listAgeInTicks.get(type)) * -75.0F));
        } else if (entity.hasPose(Pose.SLEEPING)) {
            Direction direction = entity.getBedOrientation();
            float f1 = direction != null ? sleepDirectionToRotation(direction) : p_115320_;
            p_115318_.mulPose(Axis.YP.rotationDegrees(f1));
            p_115318_.mulPose(Axis.ZP.rotationDegrees(this.getFlipDegrees(entity)));
            p_115318_.mulPose(Axis.YP.rotationDegrees(270.0F));
        } else if (isEntityUpsideDown(entity)) {
            p_115318_.translate(0.0F, entity.getBbHeight() + 0.1F, 0.0F);
            p_115318_.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }
    private static float sleepDirectionToRotation(Direction p_115329_) {
        switch (p_115329_) {
            case SOUTH:
                return 90.0F;
            case WEST:
                return 0.0F;
            case NORTH:
                return 270.0F;
            case EAST:
                return 180.0F;
            default:
                return 0.0F;
        }
    }

    @Override
    protected float getFlipDegrees(EagleBowEntity entity) {
        return 0.0f;
    }


    @Override
    public ResourceLocation getTextureLocation(EagleBowEntity entity) {
        return LOCATION;
    }
}