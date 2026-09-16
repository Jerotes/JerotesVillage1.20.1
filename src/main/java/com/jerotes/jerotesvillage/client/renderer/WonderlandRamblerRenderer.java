package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrambler;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandRamblerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WonderlandRamblerRenderer extends MobRenderer<WonderlandRamblerEntity, Modelrambler<WonderlandRamblerEntity>> {
	private static final ResourceLocation RAMBLER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_rambler.png");
	public WonderlandRamblerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelrambler(context.bakeLayer(Modelrambler.LAYER_LOCATION)), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(WonderlandRamblerEntity entity) {
		return RAMBLER_LOCATION;
	}
}

