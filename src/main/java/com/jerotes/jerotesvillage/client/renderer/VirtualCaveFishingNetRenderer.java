package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvirtual_cave_fishing_net;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.VirtualCaveFishingNetEntity;
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

public class VirtualCaveFishingNetRenderer extends EntityRenderer<VirtualCaveFishingNetEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_fishing_net.png");
	private final Modelvirtual_cave_fishing_net model;

	public VirtualCaveFishingNetRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelvirtual_cave_fishing_net(context.bakeLayer(Modelvirtual_cave_fishing_net.LAYER_LOCATION));
	}

	@Override
	public void render(VirtualCaveFishingNetEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		poseStack.translate(0, -2, 0);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(VirtualCaveFishingNetEntity entity) {
		return texture;
	}
}
