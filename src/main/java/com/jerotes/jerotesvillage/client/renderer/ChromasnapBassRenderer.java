package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.ChromasnapBassRandomColorLayer;
import com.jerotes.jerotesvillage.client.model.Modelchromasnap_bass;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.ChromasnapBassEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChromasnapBassRenderer extends MobRenderer<ChromasnapBassEntity, Modelchromasnap_bass<ChromasnapBassEntity>> {
	private static final ResourceLocation SILVER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_silver.png");
	private static final ResourceLocation BLUE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_blue.png");
	private static final ResourceLocation YELLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_yellow.png");
	private static final ResourceLocation GREEN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_green.png");
	public ChromasnapBassRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchromasnap_bass<>(context.bakeLayer(Modelchromasnap_bass.LAYER_LOCATION)), 0.4f);
		this.addLayer(new ChromasnapBassRandomColorLayer<>(this, new Modelchromasnap_bass<>(context.bakeLayer(Modelchromasnap_bass.LAYER_LOCATION))));
	}

	@Override
	protected void scale(ChromasnapBassEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.0f, 1.0f, 1.0f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(ChromasnapBassEntity entity) {
		if (entity.getBaseColor() == 1)
			return YELLOW_LOCATION;
		if (entity.getBaseColor() == 2)
			return BLUE_LOCATION;
		if (entity.getBaseColor() == 3)
			return GREEN_LOCATION;
		return SILVER_LOCATION;
	}

	@Override
	protected void setupRotations(ChromasnapBassEntity entity, PoseStack poseStack, float f, float f2, float f3) {
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

