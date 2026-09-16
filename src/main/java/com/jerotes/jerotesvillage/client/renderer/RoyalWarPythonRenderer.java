package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwar_python;
import com.jerotes.jerotesvillage.entity.Animal.RoyalWarPythonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RoyalWarPythonRenderer extends MobRenderer<RoyalWarPythonEntity, Modelwar_python<RoyalWarPythonEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/royal_war_python.png");
	public RoyalWarPythonRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwar_python(context.bakeLayer(Modelwar_python.LAYER_LOCATION)), 0.6f);
	}

	@Override
	protected void scale(RoyalWarPythonEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(RoyalWarPythonEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(RoyalWarPythonEntity entity, BlockPos blockPos) {
		return 8;
	}

	@Override
	public ResourceLocation getTextureLocation(RoyalWarPythonEntity entity) {
		return LOCATION;
	}
}