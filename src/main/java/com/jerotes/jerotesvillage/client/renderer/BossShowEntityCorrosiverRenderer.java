package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcorrosiver;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityCorrosiverRenderer extends LivingEntityRenderer<BossShowEntity, Modelcorrosiver<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosiver/corrosiver.png");
	public BossShowEntityCorrosiverRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcorrosiver(context.bakeLayer(Modelcorrosiver.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.7f, 0.7f, 0.7f);
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