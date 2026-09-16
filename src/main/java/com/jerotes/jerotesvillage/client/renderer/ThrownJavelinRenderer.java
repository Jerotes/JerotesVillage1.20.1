package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.model.Modeljavelin;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownJavelinVillagerMetalEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownMerorMetalJavelinEntity;
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

public class ThrownJavelinRenderer extends EntityRenderer<BaseJavelinEntity> {
	private static final ResourceLocation MEROR_METAL_JAVELIN = new ResourceLocation(JerotesVillage.MODID, "textures/item/meror_metal_javelin_model.png");
	private static final ResourceLocation VILLAGER_METAL_JAVELIN = new ResourceLocation(JerotesVillage.MODID, "textures/item/villager_metal_javelin_model.png");
	private final Modeljavelin model;

	public ThrownJavelinRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modeljavelin(context.bakeLayer(Modeljavelin.LAYER_LOCATION));
	}

	@Override
	public void render(BaseJavelinEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseJavelinEntity entity) {
		if (entity instanceof ThrownMerorMetalJavelinEntity)
			return MEROR_METAL_JAVELIN;
		if (entity instanceof ThrownJavelinVillagerMetalEntity)
			return VILLAGER_METAL_JAVELIN;
		return VILLAGER_METAL_JAVELIN;
	}
}
