package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbenthict;
import com.jerotes.jerotesvillage.entity.Animal.BenthictEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BenthictRenderer extends MobRenderer<BenthictEntity, Modelbenthict<BenthictEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/benthict.png");
	public BenthictRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbenthict(context.bakeLayer(Modelbenthict.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected float getFlipDegrees(BenthictEntity entity) {
		return 180.0f;
	}

	@Override
	protected void scale(BenthictEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.8f, 0.8f, 0.8f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BenthictEntity entity) {
		return LOCATION;
	}
}

