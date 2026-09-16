package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Other.MerorBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MerorBombRenderer extends EntityRenderer<MerorBombEntity> {
	private final Modelblock model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_bomb.png");
	private static final ResourceLocation REDSTONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_bomb_redstone.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");

	public MerorBombRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	public void render(MerorBombEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.glow(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.scale(0.85f, 0.85f, 0.85f);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.8f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(MerorBombEntity entity) {
		if (entity.getOwnerType() == 1) {
			return LOCATION;
		}
		if (entity.getOwnerType() == 2) {
			return REDSTONE_LOCATION;
		}
		return NULL_LOCATION;
	}
}
