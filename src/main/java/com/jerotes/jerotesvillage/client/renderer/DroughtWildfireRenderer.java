package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modeldrought_wildfire;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtWildfireEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Enemy;

public class DroughtWildfireRenderer extends MobRenderer<DroughtWildfireEntity, Modeldrought_wildfire<DroughtWildfireEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/drought_wildfire.png");
    private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/fury_drought_wildfire.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/drought_wildfire_glow.png");
    public DroughtWildfireRenderer(EntityRendererProvider.Context context) {
        super(context, new Modeldrought_wildfire(context.bakeLayer(Modeldrought_wildfire.LAYER_LOCATION)), 0.6f);
        this.addLayer(new GlowOtherBodyLayer<>(this, new Modeldrought_wildfire(context.bakeLayer(Modeldrought_wildfire.LAYER_LOCATION)), GLOW_LOCATION));
    }
    @Override
    protected int getBlockLightLevel(DroughtWildfireEntity entity, BlockPos blockPos) {
        return 15;
    }
    @Override
    protected float getFlipDegrees(DroughtWildfireEntity entity) {
        return 0f;
    }

    @Override
    protected void scale(DroughtWildfireEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.25f, 1.25f, 1.25f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(DroughtWildfireEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float idleAnim = t.getIdleAnim();
        float rollAnim = t.getRollAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.idleAnimProgress = Math.min(10, Mth.lerp(smoothFactor, t.idleAnimProgress, idleAnim));
        if (!t.specialAction() && t.getAttackTick() <= 0 && t.isAlive()) {
            t.rollAnimProgress = Mth.lerp(smoothFactor, t.rollAnimProgress, rollAnim);
        }
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(DroughtWildfireEntity entity) {
        if (entity instanceof Enemy) {
            return FURY_LOCATION;
        }
        return LOCATION;
    }
}

