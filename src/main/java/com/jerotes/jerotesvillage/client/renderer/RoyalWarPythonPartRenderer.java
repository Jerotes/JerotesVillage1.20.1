package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.entity.Part.WarPythonBasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwar_python_body;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RoyalWarPythonPartRenderer extends LivingEntityRenderer<WarPythonBasePartLivingEntity, Modelwar_python_body<WarPythonBasePartLivingEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/royal_war_python.png");
	public RoyalWarPythonPartRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwar_python_body(context.bakeLayer(Modelwar_python_body.LAYER_LOCATION)), 0.6f);
	}

	@Override
	protected void scale(WarPythonBasePartLivingEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	protected boolean shouldShowName(WarPythonBasePartLivingEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	protected int getBlockLightLevel(WarPythonBasePartLivingEntity entity, BlockPos blockPos) {
		return 8;
	}

	@Override
	public ResourceLocation getTextureLocation(WarPythonBasePartLivingEntity entity) {
		return LOCATION;
	}
}