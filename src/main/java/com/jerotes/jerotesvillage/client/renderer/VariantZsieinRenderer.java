package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.VariantZsieinBodyLayer;
import com.jerotes.jerotesvillage.client.layer.VariantZsieinBodyOtherLayer;
import com.jerotes.jerotesvillage.client.layer.VariantZsieinSonicLayer;
import com.jerotes.jerotesvillage.client.model.Modelvariant_zsiein;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;

public class VariantZsieinRenderer extends MobRenderer<OldVariantZsieinEntity, Modelvariant_zsiein<OldVariantZsieinEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/variant_zsiein.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	private static final ResourceLocation EXPLODING_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/variant_zsiein_exploding.png");
	private static final ResourceLocation S0NIC_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/variant_zsiein_sonic.png");

	public VariantZsieinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelvariant_zsiein(context.bakeLayer(Modelvariant_zsiein.LAYER_LOCATION)), 3f);
		this.addLayer(new VariantZsieinBodyLayer(this, new Modelvariant_zsiein(context.bakeLayer(Modelvariant_zsiein.LAYER_LOCATION)), EXPLODING_LOCATION));
		this.addLayer(new VariantZsieinBodyOtherLayer(this, new Modelvariant_zsiein(context.bakeLayer(Modelvariant_zsiein.LAYER_LOCATION)), EXPLODING_LOCATION));
		this.addLayer(new VariantZsieinSonicLayer(this, new Modelvariant_zsiein(context.bakeLayer(Modelvariant_zsiein.LAYER_LOCATION)), S0NIC_LOCATION));
	}

	@Override
	protected void scale(OldVariantZsieinEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected boolean isShaking(OldVariantZsieinEntity zsiein) {
		return zsiein.deathTime > 100;
	}

	@Override
	public void render(OldVariantZsieinEntity zsiein, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (zsiein.deathTime > 0 && zsiein.shouldExaltedAway()) {
			poseStack.pushPose();
			renderExplosionBeams(zsiein,f2, poseStack, multiBufferSource);
			poseStack.popPose();

			float f6 = ((float)zsiein.deathTime + f) / 200.0f;
			RandomSource randomSource = RandomSource.create(432L);
			poseStack.pushPose();
			int n2 = 0;
			while ((float)n2 < (f6 + f6 * f6) / 2.0f * 60.0f) {
				poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
				poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
				poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f));
				poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
				poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
				poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f + f6 * 90.0f));
				++n2;
			}
			renderSpace(zsiein,f2, poseStack, multiBufferSource);
			poseStack.popPose();
		}
		super.render(zsiein, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected float getFlipDegrees(OldVariantZsieinEntity zsiein) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(OldVariantZsieinEntity zsiein) {
		if (zsiein.deathTime > 80 && zsiein.shouldExaltedAway()) {
			return NULL_LOCATION;
		}
		return LOCATION;
	}

	public void renderExplosionBeams(OldVariantZsieinEntity zsiein, float f, PoseStack poseStack, MultiBufferSource multiBufferSource) {
		float f6 = ((float)zsiein.deathTime + f) / 200.0f;
		float f7 = Math.min(f6 > 0.8f ? (f6 - 0.8f) / 0.2f : 0.0f, 0.5f);
		RandomSource randomSource = RandomSource.create(432L);
		VertexConsumer vertexConsumer3 = multiBufferSource.getBuffer(RenderType.lightning());
		poseStack.pushPose();
		poseStack.translate(0.0f, 3.0f, 1.5f);
		int n2 = 0;
		while ((float)n2 < (f6 + f6 * f6) / 2.0f * 60.0f) {
			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f + f6 * 90.0f));
			float f8 = randomSource.nextFloat() * 20.0f + 5.0f + f7 * 10.0f;
			float f9 = randomSource.nextFloat() * 2.0f + 1.0f + f7 * 2.0f;
			Matrix4f matrix4f = poseStack.last().pose();
			int n3 = (int)(255.0f * (1.0f - f7));
			VariantZsieinRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex2(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex3(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex3(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex4(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex1(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex4(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex2(vertexConsumer3, matrix4f, f8, f9);
			++n2;
		}
		poseStack.popPose();
	}

	public void renderSpace(OldVariantZsieinEntity zsiein, float f, PoseStack poseStack, MultiBufferSource multiBufferSource) {
		float f6 = ((float)zsiein.deathTime + f) / 200.0f;
		float f7 = Math.min(f6 > 0.8f ? (f6 - 0.8f) / 0.2f : 0.0f, 1.0f);
		RandomSource randomSource = RandomSource.create(432L);
		VertexConsumer vertexConsumer3 = multiBufferSource.getBuffer(RenderType.lightning());

		poseStack.pushPose();

		int n2 = 0;
		while ((float)n2 < (f6 + f6 * f6) / 2.0f * 60.0f) {

			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360.0f));
			poseStack.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360.0f + f6 * 90.0f));

			float f8 = randomSource.nextFloat() * 20.0f + 5.0f + f7 * 10.0f;
			float f9 = randomSource.nextFloat() * 2.0f + 1.0f + f7 * 2.0f;
			Matrix4f matrix4f = poseStack.last().pose();
			int n3 = (int)(255.0f * (1.0f - f7));

			poseStack.translate(0.0f, 5.0f, 0.0f);
			VariantZsieinRenderer.vertex5(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex6(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex7(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex5(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex7(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex8(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex5(vertexConsumer3, matrix4f, n3);
			VariantZsieinRenderer.vertex8(vertexConsumer3, matrix4f, f8, f9);
			VariantZsieinRenderer.vertex6(vertexConsumer3, matrix4f, f8, f9);

			++n2;
		}

		poseStack.popPose();
	}


	private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);
	private static void vertex1(VertexConsumer vertexConsumer, Matrix4f matrix4f, int n) {
		vertexConsumer.vertex(matrix4f, 0.0f, 0.0f, 0.0f).color(156, 146, 255, n).endVertex();
	}
	private static void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * f2, f, -0.5f * f2).color(54, 40, 142, 0).endVertex();
	}
	private static void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * f2, f, -0.5f * f2).color(16, 238, 233, 0).endVertex();
	}
	private static void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, 0.0f, f, 1.0f * f2).color(77, 68, 172, 0).endVertex();
	}

	private static void vertex5(VertexConsumer vertexConsumer, Matrix4f matrix4f, int n) {
		vertexConsumer.vertex(matrix4f, 0.0f, 0.0f, 0.0f).color(10, 8, 22, n).endVertex();
	}
	private static void vertex6(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * f2, f, -0.5f * f2).color(46, 23, 192, 0).endVertex();
	}
	private static void vertex7(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * f2, f, -0.5f * f2).color(146, 128, 255, 0).endVertex();
	}
	private static void vertex8(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2) {
		vertexConsumer.vertex(matrix4f, 0.0f, f, 1.0f * f2).color(46, 41, 93, 0).endVertex();
	}
}
