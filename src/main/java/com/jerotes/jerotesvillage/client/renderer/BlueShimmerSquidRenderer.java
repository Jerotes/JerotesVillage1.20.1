package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BlueShimmerSquidBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelblue_shimmer_squid;
import com.jerotes.jerotesvillage.entity.Animal.BlueShimmerSquidEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Squid;

public class BlueShimmerSquidRenderer<T extends BlueShimmerSquidEntity> extends MobRenderer<T, Modelblue_shimmer_squid<T>> {
    private static final ResourceLocation SQUID_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/blue_shimmer_squid.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/blue_shimmer_squid_glow.png");

    public BlueShimmerSquidRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelblue_shimmer_squid<>(context.bakeLayer(Modelblue_shimmer_squid.LAYER_LOCATION)), 0.4f);
        this.addLayer(new BlueShimmerSquidBodyLayer<>(this, new Modelblue_shimmer_squid(context.bakeLayer(Modelblue_shimmer_squid.LAYER_LOCATION)), GLOW_LOCATION));
    }

    @Override
    public void render(T entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (Minecraft.getInstance().player.hasEffect(MobEffects.CONFUSION)){
            poseStack.pushPose();
            poseStack.translate(
                    0.15f * 0.5f * (entity.getRandom().nextFloat() - 0.5f),
                    0.15f * 0.5f * (entity.getRandom().nextFloat() - 0.5f),
                    0.15f * 0.5f * (entity.getRandom().nextFloat() - 0.5f)
            );
            super.render(entity, f, f2, poseStack, multiBufferSource, n);
            poseStack.popPose();
        } else {
            super.render(entity, f, f2, poseStack, multiBufferSource, n);
        }
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float f) {
        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected int getBlockLightLevel(T glowSquid, BlockPos blockPos) {
        int n = (int)Mth.clampedLerp(0.0f, 15.0f, 1.0f - (float)glowSquid.getDarkTicksRemaining() / 10.0f);
        if (n == 15) {
            return 15;
        }
        return Math.max(n, super.getBlockLightLevel(glowSquid, blockPos));
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return SQUID_LOCATION;
    }

    @Override
    protected void setupRotations(T t, PoseStack poseStack, float f, float f2, float f3) {
        float f4 = Mth.lerp(f3, ((Squid)t).xBodyRotO, ((Squid)t).xBodyRot);
        float f5 = Mth.lerp(f3, ((Squid)t).zBodyRotO, ((Squid)t).zBodyRot);
        poseStack.translate(0.0f, 0.5f, 0.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - f2));
        poseStack.mulPose(Axis.XP.rotationDegrees(f4));
        poseStack.mulPose(Axis.YP.rotationDegrees(f5));
        poseStack.translate(0.0f, -1.2f, 0.0f);
    }

    @Override
    protected float getBob(T t, float f) {
        return Mth.lerp(f, ((Squid)t).oldTentacleAngle, ((Squid)t).tentacleAngle);
    }
}

