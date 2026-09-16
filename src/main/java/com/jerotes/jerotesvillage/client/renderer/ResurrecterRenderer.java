package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.ResurrecterEyesLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.HeavyArmoredSedimentGuardEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.SedimentGuardEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.SedimentSoulStabilizerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ResurrecterRenderer extends HumanoidMobRenderer<ResurrecterEntity, Modelhumanoid<ResurrecterEntity>> {
	private static final ResourceLocation RESURRECTER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/resurrecter.png");
	private static final ResourceLocation SEDIMENT_GUARD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_guard.png");
	private static final ResourceLocation SEDIMENT_SOUL_STABILIZER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sediment_soul_stabilizer.png");
	private static final ResourceLocation HEAVY_ARMORED_SEDIMENT_GUARD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/heavy_armored_sediment_guard.png");
	private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/resurrecter_eye.png");
	public ResurrecterRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION)), 0.55f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new ResurrecterEyesLayer(this, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION)), EYE_LOCATION));
	}

	@Override
	protected float getFlipDegrees(ResurrecterEntity entity) {
		return entity instanceof EliteEntity ? 0.0f : super.getFlipDegrees(entity);
	}

	@Override
	protected boolean isShaking(ResurrecterEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity);
	}

	@Override
	protected void scale(ResurrecterEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(ResurrecterEntity entity) {
		if (entity instanceof SedimentGuardEntity) {
			return SEDIMENT_GUARD_LOCATION;
		}
		else if (entity instanceof SedimentSoulStabilizerEntity) {
			return SEDIMENT_SOUL_STABILIZER_LOCATION;
		}
		else if (entity instanceof HeavyArmoredSedimentGuardEntity) {
			return HEAVY_ARMORED_SEDIMENT_GUARD_LOCATION;
		}
		return RESURRECTER_LOCATION;
	}

	@Override
	protected void setupRotations(ResurrecterEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if ((double)entity.walkAnimation.speed() < 0.01) {
			return;
		}
		float f5 = entity.walkAnimation.position(f3) + 6.0f;
		float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
		poseStack.mulPose(Axis.ZP.rotationDegrees(3.5f * f6));
	}
}
