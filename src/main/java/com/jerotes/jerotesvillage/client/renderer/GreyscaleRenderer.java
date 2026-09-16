package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelgreyscale;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.GreyscaleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GreyscaleRenderer extends MobRenderer<GreyscaleEntity, Modelgreyscale<GreyscaleEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/greyscale.png");
	public GreyscaleRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgreyscale<>(context.bakeLayer(Modelgreyscale.LAYER_LOCATION)), 0.4f);
	}

	@Override
	protected void scale(GreyscaleEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(entity.getSize() / 5f, entity.getSize() / 5f, entity.getSize() / 5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(GreyscaleEntity entity) {
		return LOCATION;
	}

	@Override
	protected void setupRotations(GreyscaleEntity entity, PoseStack poseStack, float f, float f2, float f3) {
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

