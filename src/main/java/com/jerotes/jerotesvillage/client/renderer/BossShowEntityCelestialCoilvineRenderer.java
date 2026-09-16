package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcelestial_coilvine_show;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityCelestialCoilvineRenderer extends LivingEntityRenderer<BossShowEntity, Modelcelestial_coilvine_show<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine.png");
	public BossShowEntityCelestialCoilvineRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcelestial_coilvine_show(context.bakeLayer(Modelcelestial_coilvine_show.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.65f, 0.65f, 0.65f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected int getBlockLightLevel(BossShowEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	public ResourceLocation getTextureLocation(BossShowEntity entity) {
		return LOCATION;
	}
}
