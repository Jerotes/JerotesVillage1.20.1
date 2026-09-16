package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelsecond_rounder_golem_show;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntitySecondRounderGolemRenderer extends LivingEntityRenderer<BossShowEntity, Modelsecond_rounder_golem_show<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_show.png");
	public BossShowEntitySecondRounderGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsecond_rounder_golem_show(context.bakeLayer(Modelsecond_rounder_golem_show.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.45f, 0.45f, 0.45f);
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