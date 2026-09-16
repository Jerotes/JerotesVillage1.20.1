package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbright_grass_carp;
import com.jerotes.jerotesvillage.client.model.Modelbright_grass_carp_walker;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.BrightGrassCarpEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BrightGrassCarpRenderer extends MobRenderer<BrightGrassCarpEntity, EntityModel<BrightGrassCarpEntity>> {
	private final EntityModel<BrightGrassCarpEntity> walk;
	private final EntityModel<BrightGrassCarpEntity> swim = this.getModel();
	private boolean walker = false;

	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/bright_grass_carp.png");
	private static final ResourceLocation KOI_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/bright_grass_carp_koi.png");
	public BrightGrassCarpRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbright_grass_carp(context.bakeLayer(Modelbright_grass_carp.LAYER_LOCATION)), 0.4f);
		this.walk = new Modelbright_grass_carp_walker<>(context.bakeLayer(Modelbright_grass_carp_walker.LAYER_LOCATION));
	}

	@Override
	public void render(BrightGrassCarpEntity brightGrassCarpEntity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (brightGrassCarpEntity.isWalker() != walker) {
			this.model = brightGrassCarpEntity.isWalker() ? walk : swim;
		}
		this.walker = brightGrassCarpEntity.isWalker();
		super.render(brightGrassCarpEntity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(BrightGrassCarpEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.0f, 1.0f, 1.0f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BrightGrassCarpEntity entity) {
		if (WeatherEvent.isNewYearsDay() && OtherMainConfig.SpecialDay) {
			return KOI_LOCATION;
		}
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Koi".equals(string) || "锦鲤".equals(string)) {
			return KOI_LOCATION;
		}
		return LOCATION;
	}

	@Override
	protected void setupRotations(BrightGrassCarpEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if (entity.isWalker())
			return;
		float f4 = 4.3f * Mth.sin(0.6f * f);
		poseStack.mulPose(Axis.YP.rotationDegrees(f4));
		if (entity.getAirTick() >= 100 && !entity.isWalker()) {
			float scale = Math.min(entity.getAirTick() - 100, 50) / 50f;
			poseStack.translate(0.1f * scale, 0.1f * scale, -0.1f * scale);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f * scale));
		}
	}
}

