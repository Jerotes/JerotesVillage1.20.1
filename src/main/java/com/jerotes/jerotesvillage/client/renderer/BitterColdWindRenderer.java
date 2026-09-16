package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BitterColdWindItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.BitterWindBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelbitter_cold_wind;
import com.jerotes.jerotesvillage.entity.Monster.BitterColdWindEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BitterColdWindRenderer extends MobRenderer<BitterColdWindEntity, Modelbitter_cold_wind<BitterColdWindEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_wind.png");
	private static final ResourceLocation BODY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bitter_cold_wind_body.png");
	public BitterColdWindRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbitter_cold_wind(context.bakeLayer(Modelbitter_cold_wind.LAYER_LOCATION)), 0.4f);
		this.addLayer(new BitterWindBodyLayer(this, new Modelbitter_cold_wind(context.bakeLayer(Modelbitter_cold_wind.LAYER_LOCATION)), BODY_LOCATION));
		this.addLayer(new BitterColdWindItemInHandLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	protected void scale(BitterColdWindEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.75f, 1.75f, 1.75f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(BitterColdWindEntity t) {
		return 0.0f;
	}


	@Override
	public ResourceLocation getTextureLocation(BitterColdWindEntity entity) {
		return LOCATION;
	}

}

