package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelancient_second_rounder_noble_armor;
import com.jerotes.jerotesvillage.client.model.Modelsediment_lord;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntitySedimentLordRenderer extends LivingEntityRenderer<BossShowEntity, Modelsediment_lord<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_lord.png");
	private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_lord_eye.png");
	public BossShowEntitySedimentLordRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsediment_lord(context.bakeLayer(Modelsediment_lord.LAYER_LOCATION)), 0.5f);
		this.addLayer(new BossShowResurrecterEyesLayer(this, new Modelsediment_lord(context.bakeLayer(Modelsediment_lord.LAYER_LOCATION)), EYE_LOCATION));
		this.addLayer(new BossShowSedimentLordItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new BossShowSedimentLordArmorLayer(this, new Modelancient_second_rounder_noble_armor(context.bakeLayer(Modelancient_second_rounder_noble_armor.LAYER_LOCATION))));
	}

	@Override
	protected int getBlockLightLevel(BossShowEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BossShowEntity entity) {
		return LOCATION;
	}
}