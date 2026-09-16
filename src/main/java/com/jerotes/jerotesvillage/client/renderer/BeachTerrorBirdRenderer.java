package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelbeach_terror_bird;
import com.jerotes.jerotesvillage.entity.Animal.BeachTerrorBirdEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeachTerrorBirdRenderer extends MobRenderer<BeachTerrorBirdEntity, Modelbeach_terror_bird<BeachTerrorBirdEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/beach_terror_bird.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/beach_terror_bird_tame.png");
	public BeachTerrorBirdRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbeach_terror_bird(context.bakeLayer(Modelbeach_terror_bird.LAYER_LOCATION)), 0.7f);
		this.addLayer(new TameLayer(this, new Modelbeach_terror_bird(context.bakeLayer(Modelbeach_terror_bird.LAYER_LOCATION)), TAME_LOCATION));
	}

	@Override
	public ResourceLocation getTextureLocation(BeachTerrorBirdEntity entity) {
		return LOCATION;
	}
}

