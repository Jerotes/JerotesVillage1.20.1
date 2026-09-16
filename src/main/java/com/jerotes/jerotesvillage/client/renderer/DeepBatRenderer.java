package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeldeep_bat;
import com.jerotes.jerotesvillage.entity.Animal.DeepBatEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DeepBatRenderer extends MobRenderer<DeepBatEntity, Modeldeep_bat> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/deep_bat.png");
    public DeepBatRenderer(EntityRendererProvider.Context context) {
        super(context, new Modeldeep_bat(context.bakeLayer(Modeldeep_bat.LAYER_LOCATION)), 0.25f);
    }
    @Override
    public ResourceLocation getTextureLocation(DeepBatEntity entity) {
        return LOCATION;
    }
}

