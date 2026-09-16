package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelduneshockler_body;
import com.jerotes.jerotesvillage.client.model.Modellightning_worm_body;
import com.jerotes.jerotesvillage.entity.Part.DuneshocklerPart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DuneshocklerPartRenderer extends LivingEntityRenderer<DuneshocklerPart, Modelduneshockler_body<DuneshocklerPart>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/duneshockler_part.png");
	public DuneshocklerPartRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelduneshockler_body(context.bakeLayer(Modelduneshockler_body.LAYER_LOCATION)), 1.5f);
	}

	@Override
	protected void scale(DuneshocklerPart entity, PoseStack poseStack, float f) {
		super.scale(entity, poseStack, f);
	}

	protected boolean shouldShowName(DuneshocklerPart entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	protected int getBlockLightLevel(DuneshocklerPart entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(DuneshocklerPart entity) {
		return LOCATION;
	}
}