package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.JewelroamerRandomColorLayer;
import com.jerotes.jerotesvillage.client.model.Modeljewelroamer;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.JewelroamerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JewelroamerRenderer extends MobRenderer<JewelroamerEntity, Modeljewelroamer<JewelroamerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/jewelroamer.png");
	public JewelroamerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeljewelroamer<>(context.bakeLayer(Modeljewelroamer.LAYER_LOCATION)), 0.35f);
		this.addLayer(new JewelroamerRandomColorLayer<>(this, new Modeljewelroamer<>(context.bakeLayer(Modeljewelroamer.LAYER_LOCATION))));
	}

	@Override
	protected void scale(JewelroamerEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.0f, 1.0f, 1.0f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(JewelroamerEntity entity) {
		return LOCATION;
	}

	@Override
	protected void setupRotations(JewelroamerEntity entity, PoseStack poseStack, float f, float f2, float f3) {
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

