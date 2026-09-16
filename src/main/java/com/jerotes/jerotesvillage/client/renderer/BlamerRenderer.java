package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblamer;
import com.jerotes.jerotesvillage.entity.Monster.BlamerEntity;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class BlamerRenderer extends HumanoidMobRenderer<BlamerEntity, Modelblamer<BlamerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/blamer.png");
	public BlamerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblamer(context.bakeLayer(Modelblamer.LAYER_LOCATION)), 0f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(BlamerEntity entity) {
		return LOCATION;
	}

	@Override
	protected boolean isShaking(BlamerEntity entity) {
		return true;
	}
}

