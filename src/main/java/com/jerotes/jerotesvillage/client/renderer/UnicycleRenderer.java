package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelunicycle;
import com.jerotes.jerotesvillage.entity.Neutral.UnicycleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class UnicycleRenderer extends MobRenderer<UnicycleEntity, Modelunicycle<UnicycleEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/unicycle.png");
	public UnicycleRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelunicycle(context.bakeLayer(Modelunicycle.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected float getFlipDegrees(UnicycleEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(UnicycleEntity entity) {
		return LOCATION;
	}
}
