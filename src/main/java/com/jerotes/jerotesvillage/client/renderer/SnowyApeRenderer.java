package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SnowyApeHeadLayer;
import com.jerotes.jerotesvillage.client.layer.SnowyApeItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelfrost_yeti;
import com.jerotes.jerotesvillage.entity.Animal.SnowyApeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SnowyApeRenderer extends MobRenderer<SnowyApeEntity, Modelfrost_yeti<SnowyApeEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/snowy_ape.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/snowy_ape_tame.png");
	public SnowyApeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfrost_yeti(context.bakeLayer(Modelfrost_yeti.LAYER_LOCATION)), 0.9f);
		this.addLayer(new TameLayer<>(this, new Modelfrost_yeti(context.bakeLayer(Modelfrost_yeti.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SnowyApeHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new SnowyApeItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(SnowyApeEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(SnowyApeEntity entity) {
		return LOCATION;
	}
}

