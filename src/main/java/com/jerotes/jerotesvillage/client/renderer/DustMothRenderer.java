package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeldust_moth;
import com.jerotes.jerotesvillage.entity.Animal.DustMothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DustMothRenderer extends MobRenderer<DustMothEntity, Modeldust_moth<DustMothEntity>> {
	private static final ResourceLocation LOCATION_BROWN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_brown.png");
	private static final ResourceLocation LOCATION_PINK = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_pink.png");
	private static final ResourceLocation LOCATION_PUPA = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_pupa.png");
	private static final ResourceLocation LOCATION_WHITE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_white.png");
	private static final ResourceLocation LOCATION_BROWN_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_brown_baby.png");
	private static final ResourceLocation LOCATION_PINK_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_pink_baby.png");
	private static final ResourceLocation LOCATION_WHITE_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dust_moth_white_baby.png");
	public DustMothRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldust_moth(context.bakeLayer(Modeldust_moth.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected float getFlipDegrees(DustMothEntity entity) {
		return 180.0f;
	}

	@Override
	protected void scale(DustMothEntity entity, PoseStack poseStack, float f) {
		if (entity.isQueen()) {
			poseStack.scale(1.2f, 1.2f, 1.2f);
		}
		else {
			poseStack.scale(0.8f, 0.8f, 0.8f);
		}
		super.scale(entity, poseStack, f);
	}
	@Override
	public void render(DustMothEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (entity.isQueen()) {
			this.shadowRadius = 0.3f;
		}
		else {
			this.shadowRadius = 0.2f;
		}
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	public ResourceLocation getTextureLocation(DustMothEntity entity) {
		if (entity.isBaby()) {
			if (entity.isPupa()) {
				return LOCATION_PUPA;
			}
			if (entity.isColorA()) {
				return LOCATION_BROWN_BABY;
			}
			if (entity.isColorB()) {
				return LOCATION_PINK_BABY;
			}
			return LOCATION_WHITE_BABY;
		}
		if (entity.isColorA()) {
			return LOCATION_BROWN;
		}
		if (entity.isColorB()) {
			return LOCATION_PINK;
		}
		return LOCATION_WHITE;
	}
}

