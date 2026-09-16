package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modellake_loach;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.LakeLoachEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LakeLoachRenderer extends MobRenderer<LakeLoachEntity, Modellake_loach<LakeLoachEntity>> {
	private static final ResourceLocation BROWN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/lake_loach_brown.png");
	private static final ResourceLocation BLUE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/lake_loach_blue.png");
	private static final ResourceLocation YELLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/lake_loach_yellow.png");
	private static final ResourceLocation GREEN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/lake_loach_green.png");
	public LakeLoachRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellake_loach<>(context.bakeLayer(Modellake_loach.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected void scale(LakeLoachEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.8f, 0.8f, 0.8f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(LakeLoachEntity entity) {
		if (entity.getColor() == 1)
			return BROWN_LOCATION;
		if (entity.getColor() == 2)
			return BLUE_LOCATION;
		if (entity.getColor() == 3)
			return GREEN_LOCATION;
		return YELLOW_LOCATION;
	}

	@Override
	protected void setupRotations(LakeLoachEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if (entity.getAirTick() >= 100) {
			float scale = Math.min(entity.getAirTick() - 100, 50) / 50f;
			poseStack.translate(0.1f * scale, 0.1f * scale, -0.1f * scale);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f * scale));
		}
	}
}

