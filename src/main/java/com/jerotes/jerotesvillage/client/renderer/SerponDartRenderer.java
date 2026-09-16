package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.SerponDartEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SerponDartRenderer extends ArrowRenderer<SerponDartEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_dart.png");

    public SerponDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SerponDartEntity spectralArrow) {
        return ARROW_LOCATION;
    }
}

