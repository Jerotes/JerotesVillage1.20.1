package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelmeror_locator;
import com.jerotes.jerotesvillage.entity.Neutral.MerorLocatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MerorLocatorRenderer extends MobRenderer<MerorLocatorEntity, Modelmeror_locator<MerorLocatorEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_locator.png");
	public MerorLocatorRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmeror_locator(context.bakeLayer(Modelmeror_locator.LAYER_LOCATION)), 0.2f);
	}

	@Override
	protected int getBlockLightLevel(MerorLocatorEntity entity, BlockPos blockPos) {
		return 12;
	}

	@Override
	protected float getFlipDegrees(MerorLocatorEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(MerorLocatorEntity entity) {
		return LOCATION;
	}

}

