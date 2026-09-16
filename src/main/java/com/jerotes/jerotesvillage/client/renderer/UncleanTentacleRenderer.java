package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelunclean_tentacle;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.UncleanTentacleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class UncleanTentacleRenderer extends MobRenderer<UncleanTentacleEntity, Modelunclean_tentacle<UncleanTentacleEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/unclean_tentacle.png");
    public UncleanTentacleRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelunclean_tentacle(context.bakeLayer(Modelunclean_tentacle.LAYER_LOCATION)), 0f);
       }

    @Override
    protected float getFlipDegrees(UncleanTentacleEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(UncleanTentacleEntity entity) {
        return LOCATION;
    }

}