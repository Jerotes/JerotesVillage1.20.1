package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelsun_crab;
import com.jerotes.jerotesvillage.entity.Animal.SunCrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SunCrabRenderer extends MobRenderer<SunCrabEntity, Modelsun_crab<SunCrabEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sun_crab.png");
	public SunCrabRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsun_crab(context.bakeLayer(Modelsun_crab.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected float getFlipDegrees(SunCrabEntity entity) {
		return 180.0f;
	}

	@Override
	protected void scale(SunCrabEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.9f, 0.9f, 0.9f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(SunCrabEntity entity) {
		return LOCATION;
	}

}

