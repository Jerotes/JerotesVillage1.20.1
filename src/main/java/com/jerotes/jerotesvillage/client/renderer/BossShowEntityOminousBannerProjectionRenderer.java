package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelominous_banner_projection;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityOminousBannerProjectionRenderer extends LivingEntityRenderer<BossShowEntity, Modelominous_banner_projection<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ominous_banner_projection.png");
	private static final ResourceLocation BANNER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ominous_banner_projection_banner.png");
	public BossShowEntityOminousBannerProjectionRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelominous_banner_projection(context.bakeLayer(Modelominous_banner_projection.LAYER_LOCATION)), 0f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelominous_banner_projection(context.bakeLayer(Modelominous_banner_projection.LAYER_LOCATION)), BANNER_LOCATION));
	}

	@Override
	protected int getBlockLightLevel(BossShowEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.5f, 0.5f, 0.5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BossShowEntity entity) {
		return LOCATION;
	}
}