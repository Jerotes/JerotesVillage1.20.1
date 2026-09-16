package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.VirosaansForceBurstBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class VirosaansForceBurstBallRenderer extends EntityRenderer<VirosaansForceBurstBallEntity> {
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");

	private static final float PHI = 1.618034f;
	private static final float ICOSA_NORM = (float) Math.sqrt(1f + PHI * PHI);
	private static final Vec3[] ICOSA_VERTS = {
			v(-1, PHI, 0), v(1, PHI, 0), v(-1, -PHI, 0), v(1, -PHI, 0),
			v(0, -1, PHI), v(0, 1, PHI), v(0, -1, -PHI), v(0, 1, -PHI),
			v(PHI, 0, -1), v(PHI, 0, 1), v(-PHI, 0, -1), v(-PHI, 0, 1)
	};
	private static final int[][] ICOSA_EDGES = {
			{0,1},{0,5},{0,7},{0,10},{0,11},
			{1,5},{1,7},{1,8},{1,9},
			{2,3},{2,4},{2,6},{2,10},{2,11},
			{3,4},{3,6},{3,8},{3,9},
			{4,5},{4,9},{4,11},
			{5,9},{5,11},
			{6,7},{6,8},{6,10},
			{7,8},{7,10},
			{8,9},
			{10,11}
	};

	private static Vec3 v(float x, float y, float z) {
		return new Vec3(x / ICOSA_NORM, y / ICOSA_NORM, z / ICOSA_NORM);
	}

	public VirosaansForceBurstBallRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(VirosaansForceBurstBallEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		poseStack.pushPose();

		boolean exploded = entityIn.getLifeBomb() > 0;
		if (entityIn.life >= 5 && entityIn.life <= 25) {
			renderRangeIndicator(entityIn, partialTicks, poseStack, bufferIn);
		}
		renderTrail(entityIn, partialTicks, poseStack, bufferIn);
		if (exploded) {
			renderForceBurstExplosion(entityIn, partialTicks, poseStack, bufferIn);
		}

		poseStack.popPose();

		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}


	private void renderRangeIndicator(VirosaansForceBurstBallEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		float radius = 6.0f;
		float innerRadius = 5.5f;
		int segments = 32;
		float y = 0.0f;

		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(GLOW_LOCATION));
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normalMat = pose.normal();

		int alpha = (int) (80 * (entity.life >= 15 ? (1 - (entity.life - 15)/10f) : (entity.life - 5)/10f));
		int r = 255, g = 255, b = 255;

		for (int i = 0; i < segments; i++) {
			float angle1 = (float) i / segments * 2.0f * (float) Math.PI;
			float angle2 = (float) (i + 1) / segments * 2.0f * (float) Math.PI;

			float x1o = radius * (float) Math.cos(angle1);
			float z1o = radius * (float) Math.sin(angle1);
			float x2o = radius * (float) Math.cos(angle2);
			float z2o = radius * (float) Math.sin(angle2);

			float x1i = innerRadius * (float) Math.cos(angle1);
			float z1i = innerRadius * (float) Math.sin(angle1);
			float x2i = innerRadius * (float) Math.cos(angle2);
			float z2i = innerRadius * (float) Math.sin(angle2);

			consumer.vertex(matrix, x1o, y, z1o).color(r, g, b, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x2o, y, z2o).color(r, g, b, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x2i, y, z2i).color(r, g, b, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x1i, y, z1i).color(r, g, b, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
		}
	}

	private void renderTrail(VirosaansForceBurstBallEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		List<VirosaansForceBurstBallEntity.TrailPoint> points = entity.getTrailPoints();
		if (points.size() < 2) return;

		poseStack.pushPose();
		VertexConsumer bodyConsumer = buffer.getBuffer(JerotesRenderType.glowDoubleSided(GLOW_LOCATION));
		Matrix4f matrix = poseStack.last().pose();
		Matrix3f normal = poseStack.last().normal();

		int currentLife = entity.life;
		int maxLife = 120;

		int n = points.size();
		float[] bodyWidths = new float[n];
		float[] alphas = new float[n];
		for (int i = 0; i < n; i++) {
			VirosaansForceBurstBallEntity.TrailPoint tp = points.get(i);
			int age = currentLife - tp.createdAt;
			float ageFade = 1.0f - Math.min(1.0f, age / 20.0f);
			float overallFade = 1.0f - (float) currentLife / maxLife;
			float alpha = Math.min(0.6f, 0.8f * ageFade * (1 - overallFade * 0.7f));
			alphas[i] = Math.max(0, alpha);
			bodyWidths[i] = 0.28f * ageFade * (0.15f + 0.85f * (float) i / (float) n);
		}
		Vec3 currentPos = new Vec3(
				Mth.lerp(partialTick, entity.xOld, entity.getX()),
				Mth.lerp(partialTick, entity.yOld, entity.getY()),
				Mth.lerp(partialTick, entity.zOld, entity.getZ())
		);
		Vec3 cameraRight = new Vec3(1, 0, 0);
		Vec3 cameraUp = new Vec3(0, 1, 0);

		int colorI = entity.beamLightI();
		int bodyRI = (colorI >> 16) & 0xFF, bodyGI = (colorI >> 8) & 0xFF, bodyBI = colorI & 0xFF;
		int colorII = entity.beamLightII();
		int bodyRII = (colorII >> 16) & 0xFF, bodyGII = (colorII >> 8) & 0xFF, bodyBII = colorII & 0xFF;

		for (int i = 0; i < n - 1; i++) {
			VirosaansForceBurstBallEntity.TrailPoint tp0 = points.get(i);
			VirosaansForceBurstBallEntity.TrailPoint tp1 = points.get(i + 1);
			Vec3 point0 = tp0.getPosition(partialTick);
			Vec3 point1 = tp1.getPosition(partialTick);
			Vec3 local0 = point0.subtract(currentPos);
			Vec3 local1 = point1.subtract(currentPos);

			Vec3 dirVec = local1.subtract(local0);
			double lenSq = dirVec.lengthSqr();
			if (lenSq < 1e-8) continue;
			Vec3 dir = dirVec.normalize();

			float scaleColor = 1f - (float) i / (float) n;
			int r = (int) (bodyRI * scaleColor + bodyRII * (1 - scaleColor));
			int g = (int) (bodyGI * scaleColor + bodyGII * (1 - scaleColor));
			int b = (int) (bodyBI * scaleColor + bodyBII * (1 - scaleColor));

			Vec3 right1 = cameraUp.cross(dir);
			if (right1.lengthSqr() < 1e-4) right1 = cameraRight.cross(dir);
			right1 = right1.normalize();
			Vec3 right2 = right1.cross(dir).normalize();
			if (right2.lengthSqr() < 1e-4) right2 = cameraRight.cross(dir).normalize();

			drawQuad(bodyConsumer, matrix, normal, local0, local1, right1, bodyWidths[i], bodyWidths[i + 1], alphas[i], alphas[i + 1], r, g, b);
			drawQuad(bodyConsumer, matrix, normal, local0, local1, right2, bodyWidths[i], bodyWidths[i + 1], alphas[i], alphas[i + 1], r, g, b);

			float flicker = pseudoRandom(i, entity.life);
			if (flicker > 0.35f) {
				Vector3f neon = VirosaansForceBurstBallEntity.COLOR_NEON_PINK;
				int nr = (int) (neon.x() * 255), ng = (int) (neon.y() * 255), nb = (int) (neon.z() * 255);
				float arcW0 = bodyWidths[i] * 0.28f, arcW1 = bodyWidths[i + 1] * 0.28f;
				float arcA = Math.min(1.0f, flicker) * Math.max(alphas[i], alphas[i + 1]) * 1.4f;
				drawQuad(bodyConsumer, matrix, normal, local0, local1, right1, arcW0, arcW1, arcA, arcA, nr, ng, nb);
			}

			if (i % 2 == 0) {
				int age = currentLife - tp0.createdAt;
				float rippleT = Mth.clamp(age / 10.0f, 0f, 1f);
				if (rippleT < 1f) {
					float rippleRadius = bodyWidths[i] * 1.2f + rippleT * 0.5f;
					int rippleAlpha = (int) (60 * (1f - rippleT));
					drawFlatRingAt(bodyConsumer, matrix, normal, local0, dir, rippleRadius, r, g, b, rippleAlpha);
				}
			}
		}

		if (n > 0) {
			Vec3 headPos = points.get(n - 1).getPosition(partialTick);
			Vec3 localHead = headPos.subtract(currentPos);
			float glowSize = 0.35f * (1.0f - (float) currentLife / maxLife * 0.5f);
			drawQuad(bodyConsumer, matrix, normal, localHead, localHead, cameraRight, glowSize, glowSize, 1.0f, 1.0f, 255, 220, 240);
			drawQuad(bodyConsumer, matrix, normal, localHead, localHead, cameraUp, glowSize, glowSize, 1.0f, 1.0f, 255, 220, 240);
		}
		poseStack.popPose();
	}
	private void drawFlatRingAt(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 center, Vec3 dir, float radius, int r, int g, int b, int alpha) {
		if (alpha <= 0) return;
		Vec3 up = new Vec3(0, 1, 0);
		Vec3 right = up.cross(dir);
		if (right.lengthSqr() < 1e-4) right = new Vec3(1, 0, 0).cross(dir);
		right = right.normalize();
		Vec3 up2 = right.cross(dir).normalize();

		int segments = 10;
		for (int i = 0; i < segments; i++) {
			float a1 = (float) i / segments * 2.0f * (float) Math.PI;
			float a2 = (float) (i + 1) / segments * 2.0f * (float) Math.PI;
			Vec3 p1 = center.add(right.scale(radius * Mth.cos(a1))).add(up2.scale(radius * Mth.sin(a1)));
			Vec3 p2 = center.add(right.scale(radius * Mth.cos(a2))).add(up2.scale(radius * Mth.sin(a2)));
			Vec3 p1In = center.add(right.scale(radius * 0.85f * Mth.cos(a1))).add(up2.scale(radius * 0.85f * Mth.sin(a1)));
			Vec3 p2In = center.add(right.scale(radius * 0.85f * Mth.cos(a2))).add(up2.scale(radius * 0.85f * Mth.sin(a2)));
			addVertex(consumer, matrix, normal, p1, r, g, b, alpha, 0, 0);
			addVertex(consumer, matrix, normal, p2, r, g, b, alpha, 1, 0);
			addVertex(consumer, matrix, normal, p2In, r, g, b, 0, 1, 1);
			addVertex(consumer, matrix, normal, p1In, r, g, b, 0, 0, 1);
		}
	}

	private static float pseudoRandom(int i, int life) {
		int h = i * 928371 + life * 137;
		h = (h ^ (h >>> 13)) * 0x5bd1e995;
		h ^= (h >>> 15);
		return (h & 0xFFFF) / 65535.0f;
	}


	private static final float[] T_BREAK = {0f, 0.1f, 0.3f, 0.6f};

	private static float inverseLerp(float value, float start, float end) {
		return (value - start) / (end - start);
	}

	private static Vector3f lerpColor(Vector3f a, Vector3f b, float f) {
		f = Mth.clamp(f, 0f, 1f);
		return new Vector3f(Mth.lerp(f, a.x(), b.x()), Mth.lerp(f, a.y(), b.y()), Mth.lerp(f, a.z(), b.z()));
	}

	private static Vector3f sampleTimeline(float t, Vector3f c0, Vector3f c1, Vector3f c2, Vector3f c3) {
		if (t <= T_BREAK[0]) return c0;
		if (t >= T_BREAK[3]) return c3;
		if (t < T_BREAK[1]) return lerpColor(c0, c1, inverseLerp(t, T_BREAK[0], T_BREAK[1]));
		if (t < T_BREAK[2]) return lerpColor(c1, c2, inverseLerp(t, T_BREAK[1], T_BREAK[2]));
		return lerpColor(c2, c3, inverseLerp(t, T_BREAK[2], T_BREAK[3]));
	}

	private void renderForceBurstExplosion(VirosaansForceBurstBallEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		int lifeBomb = entity.getLifeBomb();
		if (lifeBomb < 1 || lifeBomb > 12) return;

		float smoothTick = (lifeBomb - 1) + partialTick;
		float t = smoothTick / 20f;

		poseStack.pushPose();
		renderLiquidCore(entity, t, poseStack, buffer);
		renderShockwaveRing(entity, t, poseStack, buffer);
		poseStack.popPose();
	}

	private void renderLiquidCore(VirosaansForceBurstBallEntity entity, float t, PoseStack poseStack, MultiBufferSource buffer) {
		float growProgress;
		float fadeAlpha;

		if (t <= 0.1f) {
			growProgress = Mth.clamp(t / 0.1f, 0f, 1f);
			fadeAlpha = 1.0f;
		} else if (t <= 0.3f) {
			growProgress = 1.0f;
			fadeAlpha = 1.0f;
		} else {
			float fade = Mth.clamp((t - 0.3f) / 0.3f, 0f, 1f);
			growProgress = 1.0f - fade;
			fadeAlpha = 1.0f - fade;
		}

		if (growProgress <= 0.01f || fadeAlpha <= 0.01f) return;

		float radius = 6.0f * growProgress;

		Vector3f color = sampleTimeline(t,
				VirosaansForceBurstBallEntity.COLOR_CORE_FLASH,
				VirosaansForceBurstBallEntity.COLOR_BRIGHT_PINK,
				VirosaansForceBurstBallEntity.COLOR_PINK_PURPLE,
				VirosaansForceBurstBallEntity.COLOR_PALE_VIOLET);

		drawSphere(buffer, poseStack, radius, color, fadeAlpha, 24, 16);
		drawSphere(buffer, poseStack, radius * 0.75f, VirosaansForceBurstBallEntity.COLOR_CORE_FLASH, fadeAlpha, 12, 8);
	}

	private void renderShockwaveRing(VirosaansForceBurstBallEntity entity, float t, PoseStack poseStack, MultiBufferSource buffer) {
		float progress = Mth.clamp(t / 0.6f, 0f, 1f);
		float outerRadius = Mth.lerp(progress, 0f, 6.0f);
		float bandWidth = Mth.lerp(progress, 1.2f, 0.4f);
		float innerRadius = Math.max(0f, outerRadius - bandWidth);
		float fade = 1f - Mth.clamp(inverseLerp(t, 0.4f, 0.6f), 0f, 1f);
		if (fade <= 0.01f || outerRadius <= 0.05f) return;

		Vector3f innerColor = sampleTimeline(t,
				VirosaansForceBurstBallEntity.COLOR_BRIGHT_PINK,
				VirosaansForceBurstBallEntity.COLOR_BRIGHT_PINK,
				VirosaansForceBurstBallEntity.COLOR_PINK_PURPLE,
				VirosaansForceBurstBallEntity.COLOR_PALE_VIOLET);

		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(GLOW_LOCATION));
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normalMat = pose.normal();

		int segments = 40;
		int ir = (int) (innerColor.x() * 255), ig = (int) (innerColor.y() * 255), ib = (int) (innerColor.z() * 255);
		int innerAlpha = (int) (200 * fade);

		for (int i = 0; i < segments; i++) {
			float a1 = (float) i / segments * 2.0f * (float) Math.PI;
			float a2 = (float) (i + 1) / segments * 2.0f * (float) Math.PI;

			float x1o = outerRadius * Mth.cos(a1), z1o = outerRadius * Mth.sin(a1);
			float x2o = outerRadius * Mth.cos(a2), z2o = outerRadius * Mth.sin(a2);
			float x1i = innerRadius * Mth.cos(a1), z1i = innerRadius * Mth.sin(a1);
			float x2i = innerRadius * Mth.cos(a2), z2i = innerRadius * Mth.sin(a2);

			consumer.vertex(matrix, x1o, 0, z1o).color(ir, ig, ib, 0).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x2o, 0, z2o).color(ir, ig, ib, 0).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x2i, 0, z2i).color(ir, ig, ib, innerAlpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
			consumer.vertex(matrix, x1i, 0, z1i).color(ir, ig, ib, innerAlpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMat, 0, 1, 0).endVertex();
		}
	}

	private void drawSphere(MultiBufferSource buffer, PoseStack poseStack, float radius, Vector3f color, float fade, int segments, int stacks) {
		if (radius <= 0.01f) return;
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.lightning());
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normalMat = pose.normal();

		int r = (int) (color.x() * 255), g = (int) (color.y() * 255), b = (int) (color.z() * 255);
		int alpha = (int) (255 * fade);

		for (int i = 0; i < stacks; i++) {
			float phi1 = (float) i / stacks * (float) Math.PI;
			float phi2 = (float) (i + 1) / stacks * (float) Math.PI;
			for (int j = 0; j < segments; j++) {
				float theta1 = (float) j / segments * 2.0f * (float) Math.PI;
				float theta2 = (float) (j + 1) / segments * 2.0f * (float) Math.PI;

				float x1 = radius * Mth.sin(phi1) * Mth.cos(theta1), y1 = radius * Mth.cos(phi1), z1 = radius * Mth.sin(phi1) * Mth.sin(theta1);
				float x2 = radius * Mth.sin(phi1) * Mth.cos(theta2), y2 = radius * Mth.cos(phi1), z2 = radius * Mth.sin(phi1) * Mth.sin(theta2);
				float x3 = radius * Mth.sin(phi2) * Mth.cos(theta2), y3 = radius * Mth.cos(phi2), z3 = radius * Mth.sin(phi2) * Mth.sin(theta2);
				float x4 = radius * Mth.sin(phi2) * Mth.cos(theta1), y4 = radius * Mth.cos(phi2), z4 = radius * Mth.sin(phi2) * Mth.sin(theta1);

				Vec3 n1 = new Vec3(x1, y1, z1).normalize();
				Vec3 n2 = new Vec3(x2, y2, z2).normalize();
				Vec3 n3 = new Vec3(x3, y3, z3).normalize();
				Vec3 n4 = new Vec3(x4, y4, z4).normalize();

				addVertex(consumer, matrix, normalMat, x1, y1, z1, r, g, b, alpha, 0, 0, n1);
				addVertex(consumer, matrix, normalMat, x2, y2, z2, r, g, b, alpha, 1, 0, n2);
				addVertex(consumer, matrix, normalMat, x3, y3, z3, r, g, b, alpha, 1, 1, n3);
				addVertex(consumer, matrix, normalMat, x4, y4, z4, r, g, b, alpha, 0, 1, n4);
			}
		}
	}

	private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMat,
						   float x, float y, float z, int r, int g, int b, int a,
						   float u, float v, Vec3 normal) {
		consumer.vertex(matrix, x, y, z)
				.color(r, g, b, a)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880)
				.normal(normalMat, (float) normal.x, (float) normal.y, (float) normal.z)
				.endVertex();
	}

	private void drawQuad(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 local0, Vec3 local1, Vec3 right, float w0, float w1, float a0, float a1, int r, int g, int b) {
		Vec3 p0l = local0.add(right.scale(-w0));
		Vec3 p0r = local0.add(right.scale(w0));
		Vec3 p1l = local1.add(right.scale(-w1));
		Vec3 p1r = local1.add(right.scale(w1));

		addVertex(consumer, matrix, normal, p0l, r, g, b, (int) (a0 * 200), 0, 0);
		addVertex(consumer, matrix, normal, p0r, r, g, b, (int) (a0 * 200), 1, 0);
		addVertex(consumer, matrix, normal, p1r, r, g, b, (int) (a1 * 200), 1, 1);
		addVertex(consumer, matrix, normal, p1l, r, g, b, (int) (a1 * 200), 0, 1);
	}

	private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos, int r, int g, int b, int a, float u, float v) {
		consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
				.color(r, g, b, a)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880)
				.normal(normal, 0, 1, 0)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(VirosaansForceBurstBallEntity entity) {
		return GLOW_LOCATION;
	}

	@Override
	public boolean shouldRender(VirosaansForceBurstBallEntity entity, Frustum frustum, double d, double d2, double d3) {
		if (!entity.shouldRender(d, d2, d3)) {
			return false;
		} else if (entity.noCulling) {
			return true;
		} else {
			AABB aabb = entity.getBoundingBoxForCulling().inflate(128.0D);
			if (aabb.hasNaN() || aabb.getSize() == 0.0D) {
				aabb = new AABB(entity.getX() - 128.0D, entity.getY() - 128.0D, entity.getZ() - 128.0D,
						entity.getX() + 128.0D, entity.getY() + 128.0D, entity.getZ() + 128.0D);
			}
			return frustum.isVisible(aabb);
		}
	}
}