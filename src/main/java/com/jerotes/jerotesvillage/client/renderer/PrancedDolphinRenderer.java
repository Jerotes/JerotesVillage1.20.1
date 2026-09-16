package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.PrancedDolphinArmorLayer;
import com.jerotes.jerotesvillage.client.layer.PrancedDolphinCarryingItemLayer;
import com.jerotes.jerotesvillage.client.model.Modelpranced_dolphin;
import com.jerotes.jerotesvillage.entity.Animal.PrancedDolphinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PrancedDolphinRenderer extends MobRenderer<PrancedDolphinEntity, Modelpranced_dolphin<PrancedDolphinEntity>> {
    private static final ResourceLocation DOLPHIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/pranced_dolphin.png");
    private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/pranced_dolphin_tame.png");
    private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/pranced_dolphin_saddle.png");

    public PrancedDolphinRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelpranced_dolphin(context.bakeLayer(Modelpranced_dolphin.LAYER_LOCATION)), 1.4f);
        this.addLayer(new TameLayer(this, new Modelpranced_dolphin(context.bakeLayer(Modelpranced_dolphin.LAYER_LOCATION)), TAME_LOCATION));
        this.addLayer(new SaddleLayer(this, new Modelpranced_dolphin(context.bakeLayer(Modelpranced_dolphin.LAYER_LOCATION)), SADDLE_LOCATION));
        this.addLayer(new PrancedDolphinCarryingItemLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new PrancedDolphinArmorLayer(this, context.getModelSet()));
    }

    @Override
    public void render(PrancedDolphinEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        t.thisTickRenderTime += 1;
        float waterAnim = t.getWaterAnim();
        float fs = t.lastTickRenderTime + 1;
        float smoothFactor = 1f / fs;
        t.waterAnimProgress = Mth.lerp(smoothFactor, t.waterAnimProgress, waterAnim);
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    protected void scale(PrancedDolphinEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(2f, 2f, 2f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(PrancedDolphinEntity dolphin) {
        return DOLPHIN_LOCATION;
    }
}

