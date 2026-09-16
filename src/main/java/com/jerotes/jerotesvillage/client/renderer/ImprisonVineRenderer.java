package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelimprison_vine;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.ImprisonVineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ImprisonVineRenderer extends MobRenderer<ImprisonVineEntity, Modelimprison_vine<ImprisonVineEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/imprison_vine.png");
     public ImprisonVineRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelimprison_vine<>(context.bakeLayer(Modelimprison_vine.LAYER_LOCATION)), 0f);
         this.addLayer(new ClothesLayer<>(this, new Modelimprison_vine(context.bakeLayer(Modelimprison_vine.LAYER_LOCATION)), LOCATION));
    }

    @Override
    protected float getFlipDegrees(ImprisonVineEntity entity) {
        return 0.0f;
    }
    @Override
    public ResourceLocation getTextureLocation(ImprisonVineEntity entity) {
        return LOCATION;
    }
}

