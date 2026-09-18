package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.WoodlandHeartGuardianGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.WoodlandHeartGuardianSweepLayer;
import com.jerotes.jerotesvillage.client.model.Modelwoodland_heart_guardian;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WoodlandHeartGuardianEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WoodlandHeartGuardianRenderer extends MobRenderer<WoodlandHeartGuardianEntity, Modelwoodland_heart_guardian<WoodlandHeartGuardianEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/woodland_heart_guardian/woodland_heart_guardian.png");
   public WoodlandHeartGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelwoodland_heart_guardian(context.bakeLayer(Modelwoodland_heart_guardian.LAYER_LOCATION)), 0.90f);
       this.addLayer(new WoodlandHeartGuardianGlowOtherBodyLayer<>(this, new Modelwoodland_heart_guardian(context.bakeLayer(Modelwoodland_heart_guardian.LAYER_LOCATION))));
       this.addLayer(new WoodlandHeartGuardianSweepLayer<>(this, new Modelwoodland_heart_guardian(context.bakeLayer(Modelwoodland_heart_guardian.LAYER_LOCATION))));
       this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
       this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getModelManager()));
   }

    @Override
    protected void scale(WoodlandHeartGuardianEntity entity, PoseStack poseStack, float f) {
        if (!entity.isSmall()) {
            poseStack.scale(1.75f, 1.75f, 1.75f);
        }
        else {
            poseStack.scale(0.9375f, 0.9375f, 0.9375f);
        }
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(WoodlandHeartGuardianEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (!t.isSmall()) {
            this.shadowRadius = 0.90f;
        }
        else {
            this.shadowRadius = 0.5f;
        }
        t.thisTickRenderTime += 1;
        float attackAnim = t.getAttackAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    protected float getFlipDegrees(WoodlandHeartGuardianEntity entity) {
        return 0.0f;
    }


    @Override
    public ResourceLocation getTextureLocation(WoodlandHeartGuardianEntity entity) {
        return LOCATION;
    }
}