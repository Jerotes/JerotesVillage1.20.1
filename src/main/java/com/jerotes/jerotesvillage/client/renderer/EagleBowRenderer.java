package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modeleagle_bow;
import com.jerotes.jerotesvillage.entity.Boss.EagleBowEntity;
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
    public void render(EagleBowEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float attackAnim = t.getAttackAnim();
        float shootAnim = t.getShootAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
        t.shootAnimProgress = Mth.lerp(smoothFactor, t.shootAnimProgress, shootAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
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