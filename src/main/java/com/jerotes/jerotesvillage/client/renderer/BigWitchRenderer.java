package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.Elite.BigWitchEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.resources.ResourceLocation;

public class BigWitchRenderer extends MobRenderer<BigWitchEntity, WitchModel<BigWitchEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_witch.png");
    public BigWitchRenderer(EntityRendererProvider.Context context) {
        super(context, new WitchModel(context.bakeLayer(ModelLayers.WITCH)), 0.5f);
        this.addLayer(new WitchItemLayer<BigWitchEntity>(this, context.getItemInHandRenderer()));
    }

    @Override
    public void render(BigWitchEntity witch, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        this.model.setHoldingItem(!witch.getMainHandItem().isEmpty());
        super.render(witch, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(BigWitchEntity witch) {
        if (WeatherEvent.isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
            return new ResourceLocation(JerotesVillage.MODID, "textures/entity/ax_crazy_fake_3.png");
        }
        return LOCATION;
    }

    @Override
    protected float getFlipDegrees(BigWitchEntity t) {
        return 0.0f;
    }

    @Override
    protected boolean isShaking(BigWitchEntity entity) {
        return (entity.deathTime > 0 && entity.deathTime <= 20) || super.isShaking(entity);
    }

    @Override
    protected void scale(BigWitchEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375f, 0.9375f, 0.9375f);
        super.scale(entity, poseStack, f);
    }
}
