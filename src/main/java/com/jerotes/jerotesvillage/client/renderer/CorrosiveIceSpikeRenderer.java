package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelice_spike;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.CorrosiveIceSpikeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CorrosiveIceSpikeRenderer extends EntityRenderer<CorrosiveIceSpikeEntity> {
	private final Modelice_spike model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosive_ice_spike.png");

	public CorrosiveIceSpikeRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelice_spike(context.bakeLayer(Modelice_spike.LAYER_LOCATION));
	}

	@Override
	public void render(CorrosiveIceSpikeEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.glow(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.scale(0.8f, 0.8f, 0.8f);
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(CorrosiveIceSpikeEntity entity) {
		return LOCATION;
	}
}
