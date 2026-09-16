package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BitterColdAltarOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelbitter_cold_altar;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BitterColdAltarRenderer extends MobRenderer<BitterColdAltarEntity, Modelbitter_cold_altar<BitterColdAltarEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_altar.png");
    public BitterColdAltarRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelbitter_cold_altar(context.bakeLayer(Modelbitter_cold_altar.LAYER_LOCATION)), 0f);
        this.addLayer(new BitterColdAltarOtherBodyLayer(this, new Modelbitter_cold_altar(context.bakeLayer(Modelbitter_cold_altar.LAYER_LOCATION))));
    }

    @Override
    protected boolean isShaking(BitterColdAltarEntity entity) {
        return !entity.isAlive() || super.isShaking(entity);
    }

    @Override
    protected float getFlipDegrees(BitterColdAltarEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(BitterColdAltarEntity entity) {
        return LOCATION;
    }

}