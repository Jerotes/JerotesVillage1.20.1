package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TruesightLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SkinEntityBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SecondRounderRenderer extends HumanoidMobRenderer<SecondRounderEntity, Modelhumanoid_wide_or_slim<SecondRounderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	public SecondRounderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid_wide_or_slim<>(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), 0.55f);
		this.addLayer(new SkinEntityBodyLayer(this, new Modelhumanoid_wide_or_slim(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), "second_rounder"));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new TruesightLayer<>(this));
	}

	@Override
	protected void scale(SecondRounderEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.1f, 1.1f, 1.1f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(SecondRounderEntity entity) {
		return OtherMainConfig.RandomSkinMobHasUnderTexture ? LOCATION : NULL_LOCATION;
	}
}
