package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BeastToughHurtLayer;
import com.jerotes.jerotesvillage.client.model.Modelprosperous_envoy;
import com.jerotes.jerotesvillage.entity.Boss.ProsperousEnvoyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ProsperousEnvoyRenderer extends MobRenderer<ProsperousEnvoyEntity, Modelprosperous_envoy<ProsperousEnvoyEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/prosperous_envoy.png");
	private static final ResourceLocation HURT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/prosperous_envoy_hurt.png");
	public ProsperousEnvoyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelprosperous_envoy(context.bakeLayer(Modelprosperous_envoy.LAYER_LOCATION)), 2.0f);
		this.addLayer(new BeastToughHurtLayer(this, new Modelprosperous_envoy(context.bakeLayer(Modelprosperous_envoy.LAYER_LOCATION)), HURT_LOCATION));
	}

	@Override
	protected float getFlipDegrees(ProsperousEnvoyEntity t) {
		return 0.0f;
	}


	@Override
	public ResourceLocation getTextureLocation(ProsperousEnvoyEntity entity) {
		return LOCATION;
	}
}

