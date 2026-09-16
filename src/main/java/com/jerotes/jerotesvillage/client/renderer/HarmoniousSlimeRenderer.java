package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.jerotes.jerotesvillage.entity.Animal.HarmoniousSlimeEntity;

public class HarmoniousSlimeRenderer extends MobRenderer<HarmoniousSlimeEntity, SlimeModel<HarmoniousSlimeEntity>> {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/harmonious_slime.png");

    public HarmoniousSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25f);
        this.addLayer(new SlimeOuterLayer<HarmoniousSlimeEntity>(this, context.getModelSet()));
    }

    @Override
    public void render(HarmoniousSlimeEntity slime, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        this.shadowRadius = 0.25f * (float)slime.getSize();
        super.render(slime, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    protected void scale(HarmoniousSlimeEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.999f, 0.999f, 0.999f);
        poseStack.translate(0.0f, 0.001f, 0.0f);
        float f3 = entity.getSize();
        float f4 = Mth.lerp(f, entity.oSquish, entity.squish) / (f3 * 0.5f + 1.0f);
        float f5 = 1.0f / (f4 + 1.0f);
        poseStack.scale(f5 * f3, 1.0f / f5 * f3, f5 * f3);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(HarmoniousSlimeEntity slime) {
        return SLIME_LOCATION;
    }
}

