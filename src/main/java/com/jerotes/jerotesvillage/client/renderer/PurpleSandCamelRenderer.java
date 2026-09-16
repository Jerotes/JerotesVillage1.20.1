package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelpurple_sand_camel;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandCamelEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class PurpleSandCamelRenderer extends MobRenderer<PurpleSandCamelEntity, Modelpurple_sand_camel<PurpleSandCamelEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_camel.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_camel_tame.png");

	public PurpleSandCamelRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpurple_sand_camel(context.bakeLayer(Modelpurple_sand_camel.LAYER_LOCATION)), 1.0f);
		this.addLayer(new TameLayer(this, new Modelpurple_sand_camel(context.bakeLayer(Modelpurple_sand_camel.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelpurple_sand_camel(context.bakeLayer(Modelpurple_sand_camel.LAYER_LOCATION)), new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_camel_saddle.png")));
	}

	@Override
	protected void scale(PurpleSandCamelEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(PurpleSandCamelEntity entity) {
		return LOCATION;
	}

}

