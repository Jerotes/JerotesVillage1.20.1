package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.entity.Other.Other.PrimedSerponClassicalBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;

public class PrimedSerponClassicalBombRenderer extends EntityRenderer<PrimedSerponClassicalBombEntity> {
	private final BlockRenderDispatcher blockRenderer;

	public PrimedSerponClassicalBombRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	public void render(PrimedSerponClassicalBombEntity primedSerponClassicalBomb, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		poseStack.pushPose();
		renderExplosionBeams(primedSerponClassicalBomb,f2, poseStack, multiBufferSource);
		poseStack.popPose();
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.5F, 0.0F);
		int i = primedSerponClassicalBomb.getFuse();
		if ((float)i - f2 + 1.0F < 10.0F) {
			float f3 = 1.0F - ((float)i - f2 + 1.0F) / 10.0F;
			f3 = Mth.clamp(f3, 0.0F, 1.0F);
			f3 *= f3;
			f3 *= f3;
			float f4 = 1.0F + f3 * 0.3F;
			poseStack.scale(f4, f4, f4);
		}
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		poseStack.translate(-0.5F, -0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
		TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, primedSerponClassicalBomb.getBlockState(), poseStack, multiBufferSource, n, i / 5 % 2 == 0);
		poseStack.popPose();
		super.render(primedSerponClassicalBomb, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected int getBlockLightLevel(PrimedSerponClassicalBombEntity entity, BlockPos blockPos) {
		return 15;
	}

	public ResourceLocation getTextureLocation(PrimedSerponClassicalBombEntity primedSerponClassicalBomb) {
		return TextureAtlas.LOCATION_BLOCKS;
	}

	public void renderExplosionBeams(PrimedSerponClassicalBombEntity primedSerponClassicalBomb, float f, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        int tickAbout = Math.min(primedSerponClassicalBomb.tickCount, 240);
		if (tickAbout < 40) {
			return;
		}
		float f6 = (tickAbout + f) / 200.0f;
		float f7 = Math.min(f6 > 0.8f ? (f6 - 0.8f) / 0.2f : 0.0f, 0.5f);
		RandomSource randomSource = RandomSource.create(432L);
		VertexConsumer vertexConsumer3 = multiBufferSource.getBuffer(RenderType.lightning());
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.5F, 0.0F);
		int n2 = 0;
		while ((float)n2 < (f6 + f6 * f6) / 2.0f * 60.0f) {
			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f + f6 * 90.0f));
			float f8 = randomSource.nextFloat() * 10.0f + 2.5f + f7 * 5.0f;
			float f9 = randomSource.nextFloat() * 1.2f + 0.5f + f7 * 1.2f;
			Matrix4f matrix4f = poseStack.last().pose();
			int n3 = (int)(255.0f * (1.0f - f7));
			PrimedSerponClassicalBombRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			PrimedSerponClassicalBombRenderer.vertex2(vertexConsumer3, matrix4f, f8, f9);
			PrimedSerponClassicalBombRenderer.vertex3(vertexConsumer3, matrix4f, f8, f9);
			PrimedSerponClassicalBombRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			PrimedSerponClassicalBombRenderer.vertex3(vertexConsumer3, matrix4f, f8, f9);
			PrimedSerponClassicalBombRenderer.vertex4(vertexConsumer3, matrix4f, f8, f9);
			PrimedSerponClassicalBombRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			PrimedSerponClassicalBombRenderer.vertex4(vertexConsumer3, matrix4f, f8, f9);
			PrimedSerponClassicalBombRenderer.vertex2(vertexConsumer3, matrix4f, f8, f9);
			++n2;
		}
		poseStack.popPose();
	}
	
	private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);
	private static void vertex1(VertexConsumer vertexConsumer, Matrix4f matrix4f, int n) {
		vertexConsumer.vertex(matrix4f, 0.0f, 0.0f, 0.0f).color(236, 227, 140, n).endVertex();
	}
	private static void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * f2, f, -0.5f * f2).color(255, 161, 0, 0).endVertex();
	}
	private static void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * f2, f, -0.5f * f2).color(193, 122, 110, 0).endVertex();
	}
	private static void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, 0.0f, f, 1.0f * f2).color(94, 40, 50, 0).endVertex();
	}
}