package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelflowing_dust;
import com.jerotes.jerotesvillage.entity.Monster.FlowingDustEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlowingDustRenderer extends MobRenderer<FlowingDustEntity, Modelflowing_dust<FlowingDustEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flowing_dust.png");
	public FlowingDustRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelflowing_dust(context.bakeLayer(Modelflowing_dust.LAYER_LOCATION)), 0.4f);
	}

	@Override
	public ResourceLocation getTextureLocation(FlowingDustEntity entity) {
		return LOCATION;
	}

}

