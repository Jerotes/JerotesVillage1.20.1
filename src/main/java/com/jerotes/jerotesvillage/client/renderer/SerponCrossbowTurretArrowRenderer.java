package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelserpon_crossbow_turret_arrow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.SerponCrossbowTurretArrowEntity;
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

public class SerponCrossbowTurretArrowRenderer extends EntityRenderer<SerponCrossbowTurretArrowEntity> {
	private static final ResourceLocation texture = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/serpon_crossbow_turret_arrow.png");
	private final Modelserpon_crossbow_turret_arrow model;

	public SerponCrossbowTurretArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelserpon_crossbow_turret_arrow(context.bakeLayer(Modelserpon_crossbow_turret_arrow.LAYER_LOCATION));
	}

	@Override
	public void render(SerponCrossbowTurretArrowEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, true);
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.translate(0.25F, 0, 0.0F);
		poseStack.scale(1.5F, 1.5F, 1.5F);
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(SerponCrossbowTurretArrowEntity entity) {
		return texture;
	}
}
