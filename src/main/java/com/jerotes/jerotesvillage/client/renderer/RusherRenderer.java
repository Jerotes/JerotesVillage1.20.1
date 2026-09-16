package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.RusherArmorLayer;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelrusher;
import com.jerotes.jerotesvillage.entity.Animal.RusherEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class RusherRenderer extends MobRenderer<RusherEntity, Modelrusher<RusherEntity>> {
	private static final ResourceLocation RUSHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rusher.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rusher_tame.png");
	public RusherRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelrusher(context.bakeLayer(Modelrusher.LAYER_LOCATION)), 1.2f);
		this.addLayer(new TameLayer(this, new Modelrusher(context.bakeLayer(Modelrusher.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelrusher(context.bakeLayer(Modelrusher.LAYER_LOCATION)), new ResourceLocation(JerotesVillage.MODID, "textures/entity/rusher_saddle.png")));
		this.addLayer(new RusherArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(RusherEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.4f, 1.4f, 1.4f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(RusherEntity entity) {
		return RUSHER_LOCATION;
	}
}

