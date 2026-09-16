package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modellightning_worm_body;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class LightningWormPartRenderer extends LivingEntityRenderer<BasePartLivingEntity, Modellightning_worm_body<BasePartLivingEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/lightning_worm.png");
	public LightningWormPartRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellightning_worm_body(context.bakeLayer(Modellightning_worm_body.LAYER_LOCATION)), 1.0f);
	}

	@Override
	protected void scale(BasePartLivingEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(2f, 2f, 2f);
		super.scale(entity, poseStack, f);
	}

	protected boolean shouldShowName(BasePartLivingEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	protected int getBlockLightLevel(BasePartLivingEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(BasePartLivingEntity entity) {
		return LOCATION;
	}
}