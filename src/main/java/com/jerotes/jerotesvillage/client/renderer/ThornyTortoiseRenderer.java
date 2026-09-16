package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelthorny_tortoise;
import com.jerotes.jerotesvillage.entity.Animal.ThornyTortoiseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class ThornyTortoiseRenderer extends MobRenderer<ThornyTortoiseEntity, Modelthorny_tortoise<ThornyTortoiseEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/thorny_tortoise.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/thorny_tortoise_baby.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/thorny_tortoise_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/thorny_tortoise_saddle.png");
	public ThornyTortoiseRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelthorny_tortoise(context.bakeLayer(Modelthorny_tortoise.LAYER_LOCATION)), 1.7f);
		this.addLayer(new TameLayer(this, new Modelthorny_tortoise(context.bakeLayer(Modelthorny_tortoise.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelthorny_tortoise(context.bakeLayer(Modelthorny_tortoise.LAYER_LOCATION)), SADDLE_LOCATION));

	}

	@Override
	public void render(ThornyTortoiseEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (entity.isBaby()) {
			this.shadowRadius = 1.7f/2f;
		}
		else {
			this.shadowRadius = 1.7f;
		}
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(ThornyTortoiseEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(2f, 2f, 2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(ThornyTortoiseEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Donatello".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID,
					"textures/entity/easter_egg/thorny_tortoise_donatello_by_baijin.png");
		}
		if ("Leonardo".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID,
					"textures/entity/easter_egg/thorny_tortoise_leonardo_by_baijin.png");
		}
		if ("Michelangelo".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID,
					"textures/entity/easter_egg/thorny_tortoise_michelangelo_by_baijin.png");
		}
		if ("Raphael".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID,
					"textures/entity/easter_egg/thorny_tortoise_raphael_by_baijin.png");
		}
		if (entity.isBaby())
			return BABY_LOCATION;
		return LOCATION;
	}
}

