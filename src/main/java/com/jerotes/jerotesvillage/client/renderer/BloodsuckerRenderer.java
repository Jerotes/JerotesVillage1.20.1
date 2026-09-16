package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbloodsucker;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.BloodsuckerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BloodsuckerRenderer extends MobRenderer<BloodsuckerEntity, Modelbloodsucker<BloodsuckerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bloodsucker.png");
	public BloodsuckerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbloodsucker(context.bakeLayer(Modelbloodsucker.LAYER_LOCATION)), 1.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(BloodsuckerEntity entity) {
		return LOCATION;
	}
}

