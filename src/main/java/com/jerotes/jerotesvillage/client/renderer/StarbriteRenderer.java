package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelstarbrite;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.StarbriteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class StarbriteRenderer extends MobRenderer<StarbriteEntity, Modelstarbrite<StarbriteEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/starbrite.png");
	public StarbriteRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelstarbrite<>(context.bakeLayer(Modelstarbrite.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected void scale(StarbriteEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.0f, 1.0f, 1.0f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(StarbriteEntity entity) {
		return LOCATION;
	}

	@Override
	protected void setupRotations(StarbriteEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		float f4 = 4.3f * Mth.sin(0.6f * f);
		poseStack.mulPose(Axis.YP.rotationDegrees(f4));
		if (entity.getAirTick() >= 100) {
			float scale = Math.min(entity.getAirTick() - 100, 50) / 50f;
			poseStack.translate(0.1f * scale, 0.1f * scale, -0.1f * scale);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f * scale));
		}
	}
}

