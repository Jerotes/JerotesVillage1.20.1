package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrambler;
import com.jerotes.jerotesvillage.entity.Animal.RamblerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RamblerRenderer extends MobRenderer<RamblerEntity, Modelrambler<RamblerEntity>> {
	private static final ResourceLocation RAMBLER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rambler.png");
	public RamblerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelrambler(context.bakeLayer(Modelrambler.LAYER_LOCATION)), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(RamblerEntity entity) {
		return RAMBLER_LOCATION;
	}
}

