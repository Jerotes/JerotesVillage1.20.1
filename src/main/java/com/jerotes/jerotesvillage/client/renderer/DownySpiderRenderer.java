package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvirtual_cave_spider;
import com.jerotes.jerotesvillage.entity.Animal.DownySpiderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DownySpiderRenderer extends MobRenderer<DownySpiderEntity, Modelvirtual_cave_spider<DownySpiderEntity>> {
	private static final ResourceLocation SPIDER_LOCATION_BROWN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/downy_spider_brown.png");
	private static final ResourceLocation SPIDER_LOCATION_PURPLE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/downy_spider_purple.png");
	private static final ResourceLocation SPIDER_LOCATION_GREEN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/downy_spider_green.png");
	private static final ResourceLocation SPIDER_LOCATION_BULE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/downy_spider_blue.png");
	public DownySpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelvirtual_cave_spider(context.bakeLayer(Modelvirtual_cave_spider.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected float getFlipDegrees(DownySpiderEntity entity) {
		return 180.0f;
	}

	@Override
	protected void scale(DownySpiderEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.25f, 0.25f, 0.25f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(DownySpiderEntity entity) {
		if (entity.isColorA() && entity.isColorB()) {
			return SPIDER_LOCATION_PURPLE;
		}
		if (entity.isColorA()) {
			return SPIDER_LOCATION_GREEN;
		}
		if (entity.isColorB()) {
			return SPIDER_LOCATION_BULE;
		}
		return SPIDER_LOCATION_BROWN;
	}
}

