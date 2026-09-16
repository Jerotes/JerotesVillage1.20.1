package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelserpon_chariot;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SerponChariotRenderer extends MobRenderer<SerponChariotEntity, Modelserpon_chariot<SerponChariotEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_chariot.png");
	public SerponChariotRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_chariot(context.bakeLayer(Modelserpon_chariot.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected int getBlockLightLevel(SerponChariotEntity entity, BlockPos blockPos) {
		return 6;
	}

	@Override
	protected float getFlipDegrees(SerponChariotEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SerponChariotEntity entity) {
		return LOCATION;
	}
}