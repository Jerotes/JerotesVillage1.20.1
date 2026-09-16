package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelthorn_spike;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.ThornSpikeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ThornSpikeRenderer extends MobRenderer<ThornSpikeEntity, Modelthorn_spike<ThornSpikeEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/thorn_spike.png");
    public ThornSpikeRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelthorn_spike<>(context.bakeLayer(Modelthorn_spike.LAYER_LOCATION)), 0f);
        this.addLayer(new ClothesLayer<>(this, new Modelthorn_spike(context.bakeLayer(Modelthorn_spike.LAYER_LOCATION)), LOCATION));
    }

    @Override
    protected float getFlipDegrees(ThornSpikeEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(ThornSpikeEntity entity) {
        return LOCATION;
    }

}