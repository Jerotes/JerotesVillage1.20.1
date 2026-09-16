package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.SerponSiroccoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Random;

public class SerponSiroccoRenderer extends EntityRenderer<SerponSiroccoEntity> {
	public SerponSiroccoRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(SerponSiroccoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLightIn) {
		poseStack.pushPose();float height = entity.getBbHeight();
		float centerY = height / 2.0f;

		poseStack.translate(0, centerY, 0);
//		poseStack.mulPose(Axis.YP.rotationDegrees(-entityYaw / 2));
//		poseStack.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));
		poseStack.translate(0, -centerY, 0);
		renderBeam(entity, partialTicks * 2, poseStack, multiBufferSource);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLightIn);
	}

	private void renderBeam(SerponSiroccoEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		double width = 2f;
		double height = 4f;
		int life = entity.life;
		float totalAlpha = 1.0f;
		if (life < 5)
			totalAlpha = life / 5.0f;
		else if (life > 30)
			totalAlpha = 1.0f - (life - 30) / 10.0f;
		totalAlpha = Mth.clamp(totalAlpha, 0.0f, 1.0f);
		if (totalAlpha < 0.01f) return;


		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(getTextureLocation(entity)));
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normal = pose.normal();

		int numSegments = 20;
		int pointsPerSegment = 20;
		float maxRadius = (float) width * 0.5f;
		float maxHeight = (float) height;
		float baseWidth = 0.08f;

		float riseOffset = (life + partialTick) * 0.025f;
		float baseRotation = (life + partialTick) * 0.12f;

		int color;
		int beginColor = 0x5c311f;
		float beginR = ((beginColor >> 16) & 0xFF) / 255.0f;
		float beginG = ((beginColor >> 8) & 0xFF) / 255.0f;
		float beginB = (beginColor & 0xFF) / 255.0f;
		int targetColor = 0xa17541;
		float targetR = ((targetColor >> 16) & 0xFF) / 255.0f;
		float targetG = ((targetColor >> 8) & 0xFF) / 255.0f;
		float targetB = (targetColor & 0xFF) / 255.0f;

		Vec3 up = new Vec3(0, 1, 0);

		for (int s = 0; s < numSegments; s++) {
			Random random = new Random(entity.getId() * 31L + 17L + s * 103L);
			float fade = (random.nextFloat() + totalAlpha) / 2;
			float r = fade * beginR + targetR * (1.0f - fade);
			float g = fade * beginG + targetG * (1.0f - fade);
			float b = fade * beginB + targetB * (1.0f - fade);
			color = ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);

			int cr = color >> 16 & 255;
			int cg = color >> 8 & 255;
			int cb = color & 255;
			float radiusBase = (0.5f + 1.8f * random.nextFloat()) * maxRadius;
			float segHeight = (0.5f + 0.5f * random.nextFloat()) * maxHeight;
			float angleOffset = random.nextFloat() * 2.0f * (float) Math.PI;
			float turns = 1.5f + 2.5f * random.nextFloat();
			float speedFactor = 5f + 4f * random.nextFloat();

			Vec3[] points = new Vec3[pointsPerSegment + 1];
			for (int i = 0; i <= pointsPerSegment; i++) {
				float progress = (float) i / pointsPerSegment;
				float radius = radiusBase * progress;
				float y = progress * segHeight + riseOffset;
				float angle = angleOffset + progress * turns * 2.0f * (float) Math.PI
						+ baseRotation * speedFactor;
				float x = radius * Mth.cos(angle);
				float z = radius * Mth.sin(angle);
				points[i] = new Vec3(x, y, z);
			}

			for (int i = 0; i < points.length - 1; i++) {
				Vec3 p0 = points[i];
				Vec3 p1 = points[i + 1];
				double y0 = p0.y, y1 = p1.y;

				if (y0 < 0 && y1 < 0) continue;
				if (y0 > maxHeight && y1 > maxHeight) continue;

				float h0 = (float) Mth.clamp(y0 / maxHeight, 0.0, 1.0);
				float h1 = (float) Mth.clamp(y1 / maxHeight, 0.0, 1.0);

				float edgeFade0 = 1.0f;
				if (y0 < 0.1 * maxHeight) edgeFade0 = (float) (y0 / (0.1 * maxHeight));
				if (y0 > 0.9 * maxHeight) edgeFade0 = (float) ((maxHeight - y0) / (0.1 * maxHeight));
				float edgeFade1 = 1.0f;
				if (y1 < 0.1 * maxHeight) edgeFade1 = (float) (y1 / (0.1 * maxHeight));
				if (y1 > 0.9 * maxHeight) edgeFade1 = (float) ((maxHeight - y1) / (0.1 * maxHeight));

				float alpha0 = totalAlpha * h0 * edgeFade0;
				float alpha1 = totalAlpha * h1 * edgeFade1;
				if (alpha0 < 0.005f && alpha1 < 0.005f) continue;

				float w0 = baseWidth * (0.2f + 0.8f * h0);
				float w1 = baseWidth * (0.2f + 0.8f * h1);

				Vec3 dir = p1.subtract(p0);
				if (dir.lengthSqr() < 1e-8) continue;
				Vec3 offset0 = new Vec3(0, w0, 0);
				Vec3 offset1 = new Vec3(0, w1, 0);
				Vec3 p0l = p0.subtract(offset0);
				Vec3 p0r = p0.add(offset0);
				Vec3 p1l = p1.subtract(offset1);
				Vec3 p1r = p1.add(offset1);

				float u0 = 0, u1 = 1;
				float v0 = (float) i / pointsPerSegment;
				float v1 = (float) (i + 1) / pointsPerSegment;

				int a0 = (int) (255 * alpha0);
				int a1 = (int) (255 * alpha1);

				addVertex(consumer, matrix, normal, p0l, cr, cg, cb, a0, u0, v0);
				addVertex(consumer, matrix, normal, p0r, cr, cg, cb, a0, u1, v0);
				addVertex(consumer, matrix, normal, p1r, cr, cg, cb, a1, u1, v1);
				addVertex(consumer, matrix, normal, p1l, cr, cg, cb, a1, u0, v1);
			}
		}
	}

	private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
						   Vec3 pos, int r, int g, int b, int a,
						   float u, float v) {
		consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
				.color(r, g, b, a)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880) // FULL_BRIGHT
				.normal(normal, 0, 1, 0)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(SerponSiroccoEntity entity) {
		return new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
	}
}