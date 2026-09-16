package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelpredation_toothed_flower;
import com.jerotes.jerotesvillage.entity.Monster.PredationToothedFlowerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PredationToothedFlowerRenderer extends MobRenderer<PredationToothedFlowerEntity, Modelpredation_toothed_flower<PredationToothedFlowerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/predation_toothed_flower.png");
	public PredationToothedFlowerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpredation_toothed_flower(context.bakeLayer(Modelpredation_toothed_flower.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected void scale(PredationToothedFlowerEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.7f, 0.7f, 0.7f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(PredationToothedFlowerEntity t) {
		if (t.isUnHide()) return 0f;
		return 180.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(PredationToothedFlowerEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Chomper".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID, "textures/entity/predation_toothed_flower_chomper.png");
		}
		return LOCATION;
	}

}

