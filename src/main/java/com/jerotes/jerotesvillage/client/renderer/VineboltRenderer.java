package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvinebolt;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.VineboltEntity;
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

public class VineboltRenderer extends EntityRenderer<VineboltEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/vinebolt.png");
	private final Modelvinebolt model;

	public VineboltRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelvinebolt(context.bakeLayer(Modelvinebolt.LAYER_LOCATION));
	}

	@Override
	public void render(VineboltEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, false);
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.translate(0.25F, 0, 0.0F);
		poseStack.scale(1F, 1F, 1F);
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}


	@Override
	public ResourceLocation getTextureLocation(VineboltEntity entity) {
		return texture;
	}
}
