package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam.ElectroflashEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
public class ElectroflashRenderer<T extends ElectroflashEntity> extends EntityRenderer<T> {

	private static final ResourceLocation BEAM_TEX = new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/electroflash.png");

	public ElectroflashRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	@Override
	public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		float targetX = entity.getTargetX();
		float targetY = entity.getTargetY();
		float targetZ = entity.getTargetZ();

		float selfX = (float) entity.getX();
		float selfY = (float) entity.getY();
		float selfZ = (float) entity.getZ();

		if (!entity.renderInitialized || entity.life < 10) {
			entity.selfXProgress = selfX;
			entity.selfYProgress = selfY;
			entity.selfZProgress = selfZ;

			entity.targetXProgress = targetX;
			entity.targetYProgress = targetY;
			entity.targetZProgress = targetZ;

			entity.renderInitialized = true;
		} else {
			entity.selfXProgress = selfX;
			entity.selfYProgress = selfY;
			entity.selfZProgress = selfZ;

			float targetSmooth = 0.35f;

			entity.targetXProgress = Mth.lerp(targetSmooth, entity.targetXProgress, targetX);
			entity.targetYProgress = Mth.lerp(targetSmooth, entity.targetYProgress, targetY);
			entity.targetZProgress = Mth.lerp(targetSmooth, entity.targetZProgress, targetZ);
		}

		poseStack.pushPose();

		float f7 = entity.targetXProgress;
		float f8 = entity.targetYProgress;
		float f9 = entity.targetZProgress;

		float f10 = entity.selfXProgress;
		float f11 = entity.selfYProgress;
		float f12 = entity.selfZProgress;

		if (entity.tickCount <= 3) {
			poseStack.popPose();
			super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
			return;
		}

		float dx = (float) (f7 - Mth.lerp(partialTick, entity.xo, f10));
		float dy = (float) (f8 - Mth.lerp(partialTick, entity.yo, f11));
		float dz = (float) (f9 - Mth.lerp(partialTick, entity.zo, f12));

		poseStack.translate(0.0f, entity.getBbHeight() / 2.0f, 0.0f);
		renderBeam(entity, dx, dy, dz, partialTick, entity.tickCount, poseStack, buffer, packedLight);

		poseStack.popPose();

		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
	}

	private void renderBeam(T entity, float dx, float dy, float dz, float partialTick, int age, PoseStack poseStack, MultiBufferSource buffer, int light) {
		float totalDist = Mth.sqrt(dx * dx + dy * dy + dz * dz);
		if (totalDist < 0.01f) return;

		poseStack.pushPose();
		Vector3f dir = new Vector3f(dx, dy, dz);
		if (dir.lengthSquared() > 1e-6f) {
			dir.normalize();
			Vector3f zAxis = new Vector3f(0, 0, 1);
			Quaternionf rot = rotationBetween(zAxis, dir);
			poseStack.mulPose(rot);
		}

		float widthMul = entity.getBeamScale();
		float beamWidth = 0.18f * widthMul;
		float halfWidth = beamWidth / 2f;
		float len = totalDist;

		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(entity.getBeamLocation()));
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normal = pose.normal();

		float time = age + partialTick;
		int seed = entity.getId();

		int color1 = 0x62b301;
		int color2 = 0x9cf801;
		int color3 = 0xfefb53;

		int r1 = (color1 >> 16) & 0xFF, g1 = (color1 >> 8) & 0xFF, b1 = color1 & 0xFF;
		int r2 = (color2 >> 16) & 0xFF, g2 = (color2 >> 8) & 0xFF, b2 = color2 & 0xFF;
		int r3 = (color3 >> 16) & 0xFF, g3 = (color3 >> 8) & 0xFF, b3 = color3 & 0xFF;

		drawCore(consumer, matrix, normal, light, len, halfWidth, age, r1, g1, b1, r2, g2, b2);
		drawLightningBranches(consumer, matrix, normal, light, len, widthMul, time, seed, r2, g2, b2, r3, g3, b3);
		drawGlowRings(consumer, matrix, normal, light, len, widthMul, time, seed, r2, g2, b2);

		poseStack.popPose();
	}

	private void drawCore(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
						  float len, float halfWidth, int age,
						  int r1, int g1, int b1, int r2, int g2, int b2) {
		float uvBottom = -age * 0.01f;
		float uvTop = len / 32f - age * 0.01f;
		int a = 220;

		float[] rArr = new float[6];
		float[] gArr = new float[6];
		float[] bArr = new float[6];
		for (int i = 0; i < 6; i++) {
			float t = i / 5f;
			rArr[i] = Mth.lerp(t, r1, r2);
			gArr[i] = Mth.lerp(t, g1, g2);
			bArr[i] = Mth.lerp(t, b1, b2);
		}

		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, halfWidth, 0), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvBottom,
				new Vec3( halfWidth, halfWidth, 0), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvBottom,
				new Vec3( halfWidth, halfWidth, len), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvTop,
				new Vec3(-halfWidth, halfWidth, len), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvTop);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, len), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvTop,
				new Vec3( halfWidth, -halfWidth, len), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvTop,
				new Vec3( halfWidth, -halfWidth, 0), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvBottom,
				new Vec3(-halfWidth, -halfWidth, 0), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvBottom);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, len), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvTop,
				new Vec3(-halfWidth, -halfWidth, 0), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvBottom,
				new Vec3(-halfWidth,  halfWidth, 0), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvBottom,
				new Vec3(-halfWidth,  halfWidth, len), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvTop);
		addQuad(consumer, matrix, normal, light,
				new Vec3( halfWidth, -halfWidth, 0), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvBottom,
				new Vec3( halfWidth, -halfWidth, len), (int)rArr[0], (int)gArr[0], (int)bArr[0], a, 0, uvTop,
				new Vec3( halfWidth,  halfWidth, len), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvTop,
				new Vec3( halfWidth,  halfWidth, 0), (int)rArr[3], (int)gArr[3], (int)bArr[3], a, 1, uvBottom);
	}

	private void addQuad(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
						 Vec3 p0, int r0, int g0, int b0, int a0, float u0, float v0,
						 Vec3 p1, int r1, int g1, int b1, int a1, float u1, float v1,
						 Vec3 p2, int r2, int g2, int b2, int a2, float u2, float v2,
						 Vec3 p3, int r3, int g3, int b3, int a3, float u3, float v3) {
		addVertex(consumer, matrix, normal, p0, r0, g0, b0, a0, u0, v0, light);
		addVertex(consumer, matrix, normal, p1, r1, g1, b1, a1, u1, v1, light);
		addVertex(consumer, matrix, normal, p2, r2, g2, b2, a2, u2, v2, light);
		addVertex(consumer, matrix, normal, p3, r3, g3, b3, a3, u3, v3, light);

		addVertex(consumer, matrix, normal, p0, r0, g0, b0, a0, u0, v0, light);
		addVertex(consumer, matrix, normal, p3, r3, g3, b3, a3, u3, v3, light);
		addVertex(consumer, matrix, normal, p2, r2, g2, b2, a2, u2, v2, light);
		addVertex(consumer, matrix, normal, p1, r1, g1, b1, a1, u1, v1, light);
	}

	private void drawLightningBranches(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
									   float len, float widthMul, float time, int seed,
									   int r1, int g1, int b1, int r2, int g2, int b2) {
		int branchCount = Math.min(6, (int)(len / 4f) + 2);
		float amplitude = 0.6f * widthMul;
		int segments = 12 + seed % 6;
		RandomSource rand = RandomSource.create(seed + 31L);

		for (int b = 0; b < branchCount; b++) {
			float startZ = (b + 0.3f) / branchCount * len;
			if (b == 0) startZ = len * 0.1f;
			float angleOffset = (float) (b * 2.3 + seed * 0.1 + time * 0.2);
			float baseX = Mth.cos(angleOffset) * 0.15f * widthMul;
			float baseY = Mth.sin(angleOffset) * 0.15f * widthMul;

			List<Vec3> points = new ArrayList<>();
			points.add(new Vec3(baseX, baseY, startZ));
			float branchLen = Math.min(len * 0.35f, 3.0f);
			for (int i = 1; i < segments; i++) {
				float t = (float) i / segments;
				float z = startZ + t * branchLen;
				float envelope = Mth.sin((float) Math.PI * t);
				float noiseX = (rand.nextFloat() - 0.5f) * 2f * amplitude * envelope;
				float noiseY = (rand.nextFloat() - 0.5f) * 2f * amplitude * envelope;
				float waveX = Mth.sin(t * 15f + b * 1.7f + time * 0.3f) * 0.3f * amplitude * envelope;
				float waveY = Mth.cos(t * 12f + b * 2.1f + time * 0.4f) * 0.3f * amplitude * envelope;
				points.add(new Vec3(baseX + noiseX + waveX, baseY + noiseY + waveY, z));
			}
			Vec3 endPoint = points.get(points.size() - 1);
			float distToEnd = (float) endPoint.distanceTo(new Vec3(0, 0, len));
			if (distToEnd < 0.5f) {
				points.set(points.size() - 1, new Vec3(endPoint.x * 0.8f, endPoint.y * 0.8f, len - 0.2f));
			}

			for (int i = 0; i < points.size() - 1; i++) {
				Vec3 p1 = points.get(i);
				Vec3 p2 = points.get(i + 1);
				Vec3 dirSeg = p2.subtract(p1);
				float segLen = (float) dirSeg.length();
				if (segLen < 0.01f) continue;
				Vec3 perp = new Vec3(-dirSeg.y, dirSeg.x, 0).normalize();
				float halfW = 0.02f * widthMul * (1f - i / (float) points.size() * 0.7f);
				float t = i / (float) points.size();
				int r = (int) Mth.lerp(t, r1, r2);
				int g = (int) Mth.lerp(t, g1, g2);
				int bCol = (int) Mth.lerp(t, b1, b2);
				int alpha = (int) (200 * (0.5f + 0.5f * Mth.sin(time * 0.5f + i + b)));

				Vec3 v0 = p1.add(perp.scale(-halfW));
				Vec3 v1 = p1.add(perp.scale(halfW));
				Vec3 v2 = p2.add(perp.scale(halfW));
				Vec3 v3 = p2.add(perp.scale(-halfW));
				addVertex(consumer, matrix, normal, v0, r, g, bCol, alpha, 0, 0, light);
				addVertex(consumer, matrix, normal, v1, r, g, bCol, alpha, 1, 0, light);
				addVertex(consumer, matrix, normal, v2, r, g, bCol, alpha, 1, 1, light);
				addVertex(consumer, matrix, normal, v3, r, g, bCol, alpha, 0, 1, light);
			}
		}
	}

	private void drawGlowRings(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
							   float len, float widthMul, float time, int seed,
							   int r, int g, int b) {
		int ringCount = (int)(len / 0.8f) + 1;
		ringCount = Math.min(ringCount, 12);
		float radius = 0.14f * widthMul;
		for (int i = 0; i < ringCount; i++) {
			float z = (i + 0.5f) / ringCount * len;
			float angle = time * 0.5f + seed * 0.3f + i * 1.2f;
			int alpha = (int) (180 * (0.6f + 0.4f * Mth.sin(time * 0.2f + i * 0.9f)));
			for (int j = 0; j < 6; j++) {
				float a0 = angle + j * (float) Math.PI / 3;
				float a1 = angle + (j + 1) * (float) Math.PI / 3;
				float x0 = Mth.cos(a0) * radius;
				float y0 = Mth.sin(a0) * radius;
				float x1 = Mth.cos(a1) * radius;
				float y1 = Mth.sin(a1) * radius;
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, z - 0.01f), r, g, b, alpha, 0, 0, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, z - 0.01f), r, g, b, alpha, 1, 0, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, z + 0.01f), r, g, b, alpha, 1, 1, light);
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, z + 0.01f), r, g, b, alpha, 0, 1, light);
			}
		}
	}

	private static Quaternionf rotationBetween(Vector3f from, Vector3f to) {
		Vector3f f = new Vector3f(from).normalize();
		Vector3f t = new Vector3f(to).normalize();
		float dot = f.dot(t);
		if (dot > 0.999999f) return new Quaternionf();
		if (dot < -0.999999f) {
			Vector3f axis = new Vector3f(1, 0, 0).cross(f);
			if (axis.lengthSquared() < 1e-6f) axis = new Vector3f(0, 1, 0).cross(f);
			axis.normalize();
			return new Quaternionf().fromAxisAngleRad(axis.x(), axis.y(), axis.z(), (float) Math.PI);
		}
		return new Quaternionf().rotationTo(f, t);
	}

	private static void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
								  int r, int g, int b, int a, float u, float v, int light) {
		consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
				.color(r, g, b, a)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(light)
				.normal(normal, 0, 0, 1)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return BEAM_TEX;
	}
}