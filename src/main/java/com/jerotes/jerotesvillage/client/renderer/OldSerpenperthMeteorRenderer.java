package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.OldSerpenperthMeteorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OldSerpenperthMeteorRenderer extends EntityRenderer<OldSerpenperthMeteorEntity> {
	private final Modelblock model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/old_serpenperth_meteor.png");

	public OldSerpenperthMeteorRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	public void render(OldSerpenperthMeteorEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(OldSerpenperthMeteorEntity entity) {
		return LOCATION;
	}
}
