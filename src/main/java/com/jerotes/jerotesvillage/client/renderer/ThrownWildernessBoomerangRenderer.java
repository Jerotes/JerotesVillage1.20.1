package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwilderness_boomerang;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownWildernessBoomerangEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThrownWildernessBoomerangRenderer extends EntityRenderer<ThrownWildernessBoomerangEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/item/wilderness_boomerang_model.png");
	private final Modelwilderness_boomerang<ThrownWildernessBoomerangEntity> model;

	public ThrownWildernessBoomerangRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelwilderness_boomerang(context.bakeLayer(Modelwilderness_boomerang.LAYER_LOCATION));
	}

	@Override
	public void render(ThrownWildernessBoomerangEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
		this.model.setupAnim(entity, 0.0f, 0.0f, entity.tickCount + partialTicks, entity.getYRot(), entity.getXRot());
		this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownWildernessBoomerangEntity entity) {
		return texture;
	}
}

