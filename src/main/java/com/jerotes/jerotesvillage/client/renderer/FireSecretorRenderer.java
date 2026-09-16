package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelfire_secretor;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FireSecretorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FireSecretorRenderer extends MobRenderer<FireSecretorEntity, Modelfire_secretor<FireSecretorEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fire_secretor.png");
	private static final ResourceLocation CLOTHES_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fire_secretor_clothes.png");
	public FireSecretorRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfire_secretor(context.bakeLayer(Modelfire_secretor.LAYER_LOCATION)), 0f);
		this.addLayer(new ClothesLayer(this, new Modelfire_secretor(context.bakeLayer(Modelfire_secretor.LAYER_LOCATION)), CLOTHES_LOCATION));
	}

	@Override
	protected void scale(FireSecretorEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(FireSecretorEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(FireSecretorEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(FireSecretorEntity entity) {
		return LOCATION;
	}
}

