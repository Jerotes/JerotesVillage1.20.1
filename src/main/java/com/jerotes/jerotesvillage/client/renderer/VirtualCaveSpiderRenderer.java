package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvirtual_cave_spider;
import com.jerotes.jerotesvillage.entity.Monster.VirtualCaveSpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VirtualCaveSpiderRenderer extends MobRenderer<VirtualCaveSpiderEntity, Modelvirtual_cave_spider<VirtualCaveSpiderEntity>> {
	private static final ResourceLocation SPIDER_LOCATION_BROWN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_spider_brown.png");
	private static final ResourceLocation SPIDER_LOCATION_PURPLE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_spider_purple.png");
	private static final ResourceLocation SPIDER_LOCATION_GREEN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_spider_green.png");
	private static final ResourceLocation SPIDER_LOCATION_BULE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_spider_blue.png");
	public VirtualCaveSpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelvirtual_cave_spider(context.bakeLayer(Modelvirtual_cave_spider.LAYER_LOCATION)), 1.35f);
	}

	@Override
	protected float getFlipDegrees(VirtualCaveSpiderEntity t) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(VirtualCaveSpiderEntity entity) {
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

