package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelmeganose_snow_lizard;
import com.jerotes.jerotesvillage.entity.Animal.MeganoseSnowLizardEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MeganoseSnowLizardRenderer extends MobRenderer<MeganoseSnowLizardEntity, Modelmeganose_snow_lizard<MeganoseSnowLizardEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meganose_snow_lizard.png");
	public MeganoseSnowLizardRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmeganose_snow_lizard(context.bakeLayer(Modelmeganose_snow_lizard.LAYER_LOCATION)), 1.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(MeganoseSnowLizardEntity entity) {
		return LOCATION;
	}
}

