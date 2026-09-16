package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwilderness_throwing_spear;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownWildernessThrowingSpearEntity;
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

public class ThrownWildernessThrowingSpearRenderer extends EntityRenderer<ThrownWildernessThrowingSpearEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/item/wilderness_throwing_spear_model.png");
	private final Modelwilderness_throwing_spear model;

	public ThrownWildernessThrowingSpearRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelwilderness_throwing_spear(context.bakeLayer(Modelwilderness_throwing_spear.LAYER_LOCATION));
	}

	@Override
	public void render(ThrownWildernessThrowingSpearEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownWildernessThrowingSpearEntity entity) {
		return texture;
	}
}

