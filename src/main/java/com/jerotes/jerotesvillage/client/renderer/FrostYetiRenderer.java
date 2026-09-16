package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SnowyApeHeadLayer;
import com.jerotes.jerotesvillage.client.layer.SnowyApeItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelfrost_yeti;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class FrostYetiRenderer extends MobRenderer<FrostYetiEntity, Modelfrost_yeti<FrostYetiEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/frost_yeti.png");
	private static final ResourceLocation ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/frost_yeti_angry.png");
	public FrostYetiRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfrost_yeti(context.bakeLayer(Modelfrost_yeti.LAYER_LOCATION)), 1.1f);
		this.addLayer(new SnowyApeHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new SnowyApeItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(FrostYetiEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(FrostYetiEntity t) {
		return 0.0f;
	}


	@Override
	public ResourceLocation getTextureLocation(FrostYetiEntity entity) {
		if (entity.isAggressive()){
			return ANGRY_LOCATION;
		}
		return LOCATION;
	}
}

