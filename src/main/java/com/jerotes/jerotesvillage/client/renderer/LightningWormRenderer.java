package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modellightning_worm;
import com.jerotes.jerotesvillage.entity.Monster.Elite.LightningWormEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class LightningWormRenderer extends MobRenderer<LightningWormEntity, Modellightning_worm<LightningWormEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/lightning_worm.png");
	public LightningWormRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellightning_worm(context.bakeLayer(Modellightning_worm.LAYER_LOCATION)), 1.0f);
	}

	@Override
	protected void scale(LightningWormEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(2f, 2f, 2f);
		super.scale(entity, poseStack, f);
	}
	@Override
	protected float getFlipDegrees(LightningWormEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(LightningWormEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(LightningWormEntity entity) {
		return LOCATION;
	}
}