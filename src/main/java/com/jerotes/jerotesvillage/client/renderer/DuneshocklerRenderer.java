package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelduneshockler;
import com.jerotes.jerotesvillage.entity.Monster.Elite.DuneshocklerEntity;
import com.jerotes.jerotesvillage.entity.Part.DuneshocklerPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DuneshocklerRenderer extends MobRenderer<DuneshocklerEntity, Modelduneshockler<DuneshocklerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/duneshockler.png");
	public DuneshocklerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelduneshockler<>(context.bakeLayer(Modelduneshockler.LAYER_LOCATION)), 3.25f);
	}

	@Override
	protected float getFlipDegrees(DuneshocklerEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(DuneshocklerEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(DuneshocklerEntity entity) {
		return LOCATION;
	}
}

