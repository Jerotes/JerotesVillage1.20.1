package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CorrosiverLayer;
import com.jerotes.jerotesvillage.client.layer.DracognathusArmorLayer;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modeldracognathus;
import com.jerotes.jerotesvillage.entity.Animal.DracognathusEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class DracognathusRenderer extends MobRenderer<DracognathusEntity, Modeldracognathus<DracognathusEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dracognathus.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dracognathus_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dracognathus_saddle.png");
	private static final ResourceLocation CORROSIVER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/dracognathus_corrosiver.png");
	public DracognathusRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldracognathus(context.bakeLayer(Modeldracognathus.LAYER_LOCATION)), 1.05f);
		this.addLayer(new TameLayer(this, new Modeldracognathus(context.bakeLayer(Modeldracognathus.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new CorrosiverLayer(this, new Modeldracognathus(context.bakeLayer(Modeldracognathus.LAYER_LOCATION)), CORROSIVER_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modeldracognathus(context.bakeLayer(Modeldracognathus.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new DracognathusArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(DracognathusEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.4F, 1.4F, 1.4F);
	}

	@Override
	public ResourceLocation getTextureLocation(DracognathusEntity entity) {
		return LOCATION;
	}
}

