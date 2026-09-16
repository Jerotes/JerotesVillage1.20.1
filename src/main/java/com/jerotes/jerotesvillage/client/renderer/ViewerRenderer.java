package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.util.PlayerSkin;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.ViewerOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim_mask;
import com.jerotes.jerotesvillage.entity.Other.Other.ViewerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ViewerRenderer extends HumanoidMobRenderer<ViewerEntity, Modelhumanoid_wide_or_slim<ViewerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/viewer.png");
	public ViewerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid_wide_or_slim(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), 0.5f);
		this.addLayer(new ViewerOtherBodyLayer(this, new Modelhumanoid_wide_or_slim_mask(context.bakeLayer(Modelhumanoid_wide_or_slim_mask.LAYER_LOCATION))));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(ViewerEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.9375f, 0.9375f, 0.9375f);
		super.scale(entity, poseStack, f);
	}

	public ResourceLocation getTextureLocation(ViewerEntity entity) {
		if (entity.getUsername() == null) {
			return LOCATION;
		}
		return PlayerSkin.getPlayerSkin(entity);
	}

}
