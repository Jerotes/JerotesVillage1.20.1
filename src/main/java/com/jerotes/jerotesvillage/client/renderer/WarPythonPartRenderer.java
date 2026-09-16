package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwar_python_body;
import com.jerotes.jerotesvillage.entity.Part.WarPythonBasePartLivingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WarPythonPartRenderer extends LivingEntityRenderer<WarPythonBasePartLivingEntity, Modelwar_python_body<WarPythonBasePartLivingEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/war_python.png");
	public WarPythonPartRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwar_python_body(context.bakeLayer(Modelwar_python_body.LAYER_LOCATION)), 0.5f);
	}

	protected boolean shouldShowName(WarPythonBasePartLivingEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	protected int getBlockLightLevel(WarPythonBasePartLivingEntity entity, BlockPos blockPos) {
		return 8;
	}

	@Override
	public ResourceLocation getTextureLocation(WarPythonBasePartLivingEntity entity) {
		return LOCATION;
	}
}