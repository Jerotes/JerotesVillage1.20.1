package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.OminousBannerProjectionGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelominous_banner_projection;
import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OminousBannerProjectionRenderer extends MobRenderer<OminousBannerProjectionEntity, Modelominous_banner_projection<OminousBannerProjectionEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ominous_banner_projection.png");
    private static final ResourceLocation BANNER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ominous_banner_projection_banner.png");
    public OminousBannerProjectionRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelominous_banner_projection(context.bakeLayer(Modelominous_banner_projection.LAYER_LOCATION)), 0f);
        this.addLayer(new OminousBannerProjectionGlowOtherBodyLayer<>(this, new Modelominous_banner_projection(context.bakeLayer(Modelominous_banner_projection.LAYER_LOCATION)), BANNER_LOCATION));
    }

    @Override
    protected float getFlipDegrees(OminousBannerProjectionEntity entity) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(OminousBannerProjectionEntity entity) {
        return LOCATION;
    }

}