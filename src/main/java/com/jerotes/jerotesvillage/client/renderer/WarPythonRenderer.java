package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwar_python;
import com.jerotes.jerotesvillage.entity.Animal.WarPythonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WarPythonRenderer extends MobRenderer<WarPythonEntity, Modelwar_python<WarPythonEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/war_python.png");
	public WarPythonRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwar_python(context.bakeLayer(Modelwar_python.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected float getFlipDegrees(WarPythonEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(WarPythonEntity entity, BlockPos blockPos) {
		return 8;
	}

	@Override
	public ResourceLocation getTextureLocation(WarPythonEntity entity) {
		return LOCATION;
	}
}