package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.IceRockThrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class IceRockThrowRenderer extends EntityRenderer<IceRockThrowEntity> {
	private final Modelblock model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/ice_rock_throw.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/ice_rock_throw_glow.png");

	public IceRockThrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	public void render(IceRockThrowEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {

		float life = entityIn.life + partialTicks;
		float rollAngle = life * -22.50F;

		poseStack.pushPose();
		poseStack.translate(0, 0.5F, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(entityIn.getYRot() + 180F));
		poseStack.mulPose(Axis.XP.rotationDegrees(rollAngle));
		poseStack.translate(0, -0.5F, 0);
		poseStack.scale(-1F, -1F, 1F);
		poseStack.translate(0.0F, -1, 0.0F);
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0, 0.5F, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(entityIn.getYRot() + 180F));
		poseStack.mulPose(Axis.XP.rotationDegrees(rollAngle));
		poseStack.scale(1.2F, 1.2F, 1.2F);
		poseStack.translate(0, -0.5F, 0);
		poseStack.scale(-1F, -1F, 1F);
		poseStack.translate(0.0F, -1, 0.0F);
		float f7 = (float)entityIn.tickCount + partialTicks;
		VertexConsumer vb2 = bufferIn.getBuffer(JerotesRenderType.flowGlow(GLOW_LOCATION, this.xOffset(f7) % 1.0f, 0));
		model.renderToBuffer(poseStack, vb2, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();

		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}
	private float xOffset(float f) {
		return f * 0.05f;
	}

	@Override
	public ResourceLocation getTextureLocation(IceRockThrowEntity entity) {
		return LOCATION;
	}
}
