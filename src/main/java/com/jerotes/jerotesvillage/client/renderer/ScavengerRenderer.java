package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelscavenger;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ScavengerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScavengerRenderer extends MobRenderer<ScavengerEntity, Modelscavenger<ScavengerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scavenger.png");
	public ScavengerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelscavenger(context.bakeLayer(Modelscavenger.LAYER_LOCATION)), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(ScavengerEntity entity) {
		return LOCATION;
	}
}

