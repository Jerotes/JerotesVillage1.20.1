package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelmeror_pilum;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownMerorPilumEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThrownMerorPilumRenderer extends EntityRenderer<ThrownMerorPilumEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/item/meror_pilum_use_model.png");
	private final Modelmeror_pilum model;

	public ThrownMerorPilumRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelmeror_pilum(context.bakeLayer(Modelmeror_pilum.LAYER_LOCATION));
	}

	@Override
	public void render(ThrownMerorPilumEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		poseStack.scale(1.25f, 1.25f, 1.25f);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownMerorPilumEntity entity) {
		return texture;
	}
}
