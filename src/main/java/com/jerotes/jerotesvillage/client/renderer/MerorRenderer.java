package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TruesightLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SkinEntityBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class MerorRenderer extends HumanoidMobRenderer<MerorEntity, Modelhumanoid_wide_or_slim<MerorEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	public MerorRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid_wide_or_slim<>(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SkinEntityBodyLayer(this, new Modelhumanoid_wide_or_slim(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), "second_rounder"));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new TruesightLayer<>(this));
	}

	@Override
	protected float getFlipDegrees(MerorEntity entity) {
		return 0.0f;
	}

	@Override
	protected void scale(MerorEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(MerorEntity entity) {
		return OtherMainConfig.RandomSkinMobHasUnderTexture ? LOCATION : NULL_LOCATION;
	}
}
