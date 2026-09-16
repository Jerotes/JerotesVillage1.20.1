package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.HornLayer;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcloud_fur_deer;
import com.jerotes.jerotesvillage.entity.Animal.CloudFurDeerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class CloudFurDeerRenderer extends MobRenderer<CloudFurDeerEntity, Modelcloud_fur_deer<CloudFurDeerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_baby.png");
	private static final ResourceLocation SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_snow.png");
	private static final ResourceLocation SNOW_BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_snow_baby.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_horn.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_tame.png");
	public CloudFurDeerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcloud_fur_deer(context.bakeLayer(Modelcloud_fur_deer.LAYER_LOCATION)), 0.7F);
		this.addLayer(new TameLayer(this, new Modelcloud_fur_deer(context.bakeLayer(Modelcloud_fur_deer.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new HornLayer(this, new Modelcloud_fur_deer(context.bakeLayer(Modelcloud_fur_deer.LAYER_LOCATION)), HORN_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelcloud_fur_deer(context.bakeLayer(Modelcloud_fur_deer.LAYER_LOCATION)), new ResourceLocation(JerotesVillage.MODID, "textures/entity/cloud_fur_deer/cloud_fur_deer_saddle.png")));
	}

	@Override
	protected void scale(CloudFurDeerEntity entity, PoseStack poseStack, float f) {
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(CloudFurDeerEntity entity) {
		if (entity.isBaby()) {
			if (entity.isSnow())
				return SNOW_BABY_LOCATION;
			return BABY_LOCATION;
		}
		if (entity.isSnow())
			return SNOW_LOCATION;
		return LOCATION;
	}
}

