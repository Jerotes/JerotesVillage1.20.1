package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.RuinsBreezeEyesLayer;
import com.jerotes.jerotesvillage.client.layer.RuinsBreezeWindLayer;
import com.jerotes.jerotesvillage.client.model.Modelruins_breeze;
import com.jerotes.jerotesvillage.entity.Monster.RuinsBreeze;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RuinsBreezeRenderer extends MobRenderer<RuinsBreeze, Modelruins_breeze<RuinsBreeze>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_breeze.png");
    private static final ResourceLocation EYES_TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_breeze_eyes.png");
    private static final ResourceLocation WIND_TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_breeze_wind.png");

    public RuinsBreezeRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelruins_breeze(context.bakeLayer(Modelruins_breeze.LAYER_LOCATION)), 0.8f);
        this.addLayer(new RuinsBreezeWindLayer(this, context.getModelSet(), WIND_TEXTURE_LOCATION));
        this.addLayer(new RuinsBreezeEyesLayer(this, context.getModelSet(), EYES_TEXTURE_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(RuinsBreeze breeze) {
        return TEXTURE_LOCATION;
    }
}

