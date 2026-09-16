package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.FurySecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class AnimatedArmorRenderer extends HumanoidMobRenderer<SecondRounderAnimatedArmorEntity, Modelhumanoid<SecondRounderAnimatedArmorEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_animated_armor.png");
	private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fury_second_rounder_animated_armor.png");
	private static final ResourceLocation SIT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	public AnimatedArmorRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public void render(SecondRounderAnimatedArmorEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (entity.isInSittingPose()) {
			this.shadowRadius = 0f;
		}
		else {
			this.shadowRadius = 0.5f;
		}
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	public ResourceLocation getTextureLocation(SecondRounderAnimatedArmorEntity entity) {
		if (entity.isInSittingPose()) {
			return SIT_LOCATION;
		}
		if (entity instanceof FurySecondRounderAnimatedArmorEntity) {
			return FURY_LOCATION;
		}
		return LOCATION;
	}
}
