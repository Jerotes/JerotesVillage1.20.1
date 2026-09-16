package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TruesightLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SkinEntityBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SpirveRenderer extends HumanoidMobRenderer<SpirveEntity, Modelhumanoid_wide_or_slim<SpirveEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/spirve.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	public SpirveRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid_wide_or_slim<>(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SkinEntityBodyLayer(this, new Modelhumanoid_wide_or_slim(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), "spirve"));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new TruesightLayer<>(this));
	}

	@Override
	protected void scale(SpirveEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.9375f, 0.9375f, 0.9375f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(SpirveEntity entity) {
		return OtherMainConfig.RandomSkinMobHasUnderTexture ? LOCATION : NULL_LOCATION;
	}
}
