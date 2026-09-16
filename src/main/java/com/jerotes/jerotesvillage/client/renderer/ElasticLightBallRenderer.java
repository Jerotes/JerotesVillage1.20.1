package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.ElasticLightBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class ElasticLightBallRenderer extends EntityRenderer<ElasticLightBallEntity> {
	private final Modelblock model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elastic_light_ball.png");

	public ElasticLightBallRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	protected int getBlockLightLevel(ElasticLightBallEntity t, BlockPos blockPos) {
		return 15;
	}
	@Override
	public void render(ElasticLightBallEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.glow(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.scale(0.5f, 0.5f, 0.5f);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.8f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ElasticLightBallEntity entity) {
		return LOCATION;
	}
}
