package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.ResurrecterEyesLayer;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelsediment_lord;
import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SedimentLordRenderer extends MobRenderer<SedimentLordEntity, Modelsediment_lord<SedimentLordEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_lord.png");
	private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_lord_eye.png");

	public SedimentLordRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsediment_lord(context.bakeLayer(Modelsediment_lord.LAYER_LOCATION)), 0.5f);
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new ResurrecterEyesLayer(this, new Modelsediment_lord(context.bakeLayer(Modelsediment_lord.LAYER_LOCATION)), EYE_LOCATION));
	}

	@Override
	protected void scale(SedimentLordEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(SedimentLordEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SedimentLordEntity entity) {
		return LOCATION;
	}

	@Override
	protected void setupRotations(SedimentLordEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if ((double)entity.walkAnimation.speed() < 0.01) {
			return;
		}
		float f5 = entity.walkAnimation.position(f3) + 6.0f;
		float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
		poseStack.mulPose(Axis.ZP.rotationDegrees(1.5f * f6));
	}
}
