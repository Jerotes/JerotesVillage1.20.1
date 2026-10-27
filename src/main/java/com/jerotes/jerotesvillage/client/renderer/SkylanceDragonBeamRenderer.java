package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam.SkylanceDragonBeamEntity;
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

import java.util.List;
import java.util.ArrayList;

public class SkylanceDragonBeamRenderer<T extends SkylanceDragonBeamEntity> extends EntityRenderer<T> {

	private static final ResourceLocation BEAM_TEX = new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/skylance_dragon_beam.png");
	private static final ResourceLocation RAY_TEX = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");

	private static final int SEGMENTS = 24;
	private static final int STRAND_COUNT = 8;
	private static final int BOLT_COUNT = 18;
	private static final int RING_SEGMENTS = 20;
	private static final int MUZZLE_PETALS = 6;
	private static final int TAIL_SPIKES = 8;
	private static final int SHOCKWAVE_RINGS = 3;
	private static final float RING_SPEED = 0.7f;
	private static final float RING_SPACING = 0.95f;
	private static final float RING_RADIUS_MUL = 1.95f;
	private static final float CORE_HALF_WIDTH = 0.045f;
	private static final float INNER_HALF_WIDTH = 0.085f;
	private static final float GLOW_HALF_WIDTH = 0.145f;
	private static final float STRAND_HALF_WIDTH = 0.026f;
	private static final float BOLT_HALF_WIDTH = 0.014f;
	private static final float MUZZLE_SCALE = 0.17f;
	private static final float TAIL_SCALE = 0.15f;

	private static final int CORE_COLOR = 0xffffff;
	private static final int INNER_COLOR = 0xd8f2ff;
	private static final int OUTER_COLOR = 0x48a8ff;
	private static final int DEEP_COLOR = 0x2447ff;
	private static final int RING_COLOR = 0xeaffff;

	public SkylanceDragonBeamRenderer(EntityRendererProvider.Context context) {
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
		float len = Mth.sqrt(dx * dx + dy * dy + dz * dz);
		if (len < 0.05f) return;

		float maxTick = Math.max(1, entity.getMaxTick());
		float fadeIn = Mth.clamp((age + partialTick) / 8.0f, 0.0f, 1.0f);
		float fadeOut = Mth.clamp((maxTick - age - partialTick) / 18.0f, 0.0f, 1.0f);
		float fade = fadeIn * fadeOut;
		if (fade <= 0.01f) return;

		poseStack.pushPose();
		Vector3f dir = new Vector3f(dx, dy, dz);
		if (dir.lengthSquared() > 1e-6f) {
			dir.normalize();
			Vector3f zAxis = new Vector3f(0, 0, 1);
			Quaternionf rot = rotationBetween(zAxis, dir);
			poseStack.mulPose(rot);
		}

		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normal = pose.normal();

		float widthMul = entity.getBeamScale();
		float time = age + partialTick;
		int seed = entity.getId();

		drawGlowBody(matrix, normal, buffer, light, len, widthMul, age, fade);
		drawStrands(matrix, normal, buffer, light, len, widthMul, time, seed, fade);
		drawBolts(matrix, normal, buffer, light, len, widthMul, time, seed, fade);
		drawRings(matrix, normal, buffer, light, len, widthMul, time, seed, fade);
		drawMuzzle(matrix, normal, buffer, light, widthMul, time, seed, fade);
		drawTail(matrix, normal, buffer, light, len, widthMul, time, seed, fade);

		poseStack.popPose();
	}

	private void drawGlowBody(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
							  float len, float widthMul, int age, float fade) {
		VertexConsumer glow = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(RAY_TEX));
		drawBox(glow, matrix, normal, light, len, age, GLOW_HALF_WIDTH * widthMul,
				(OUTER_COLOR >> 16) & 0xFF, (OUTER_COLOR >> 8) & 0xFF, OUTER_COLOR & 0xFF, (int) (60 * fade));
		drawBox(glow, matrix, normal, light, len, age, INNER_HALF_WIDTH * widthMul,
				(INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (110 * fade));

		VertexConsumer core = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(BEAM_TEX));
		drawBox(core, matrix, normal, light, len, age, CORE_HALF_WIDTH * widthMul,
				(CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, (int) (235 * fade));
	}

	private void drawStrands(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
							 float len, float widthMul, float time, int seed, float fade) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(BEAM_TEX));
		float baseRadius = 0.085f * widthMul;

		for (int s = 0; s < STRAND_COUNT; s++) {
			float angle0 = s * Mth.TWO_PI / STRAND_COUNT + seed * 0.13f;
			float radiusMul = 0.55f + 0.45f * Mth.sin(time * 0.11f + s * 1.7f + seed * 0.07f);
			float spin = time * 0.055f + seed * 0.02f;

			Vec3[] points = new Vec3[SEGMENTS + 1];
			for (int i = 0; i <= SEGMENTS; i++) {
				float t = (float) i / SEGMENTS;
				float radius = baseRadius * radiusMul * (1.0f - t * 0.62f);
				float angle = angle0 + spin + t * 3.6f;
				float wob = 1.0f + 0.22f * Mth.sin(t * 9.0f + time * 0.35f + s * 2.1f);
				float jitterX = Mth.sin(time * 0.7f + t * 21.0f + s * 3.1f) * 0.014f * widthMul;
				float jitterY = Mth.cos(time * 0.6f + t * 18.0f + s * 2.3f) * 0.014f * widthMul;
				points[i] = new Vec3(
						Mth.cos(angle) * radius * wob + jitterX,
						Mth.sin(angle) * radius * wob + jitterY,
						t * len);
			}

			for (int i = 0; i < SEGMENTS; i++) {
				float t = (float) i / SEGMENTS;
				float mix = Mth.clamp(t * 1.4f, 0.0f, 1.0f);
				int r = (int) Mth.lerp(mix, (CORE_COLOR >> 16) & 0xFF, (DEEP_COLOR >> 16) & 0xFF);
				int g = (int) Mth.lerp(mix, (CORE_COLOR >> 8) & 0xFF, (DEEP_COLOR >> 8) & 0xFF);
				int b = (int) Mth.lerp(mix, CORE_COLOR & 0xFF, DEEP_COLOR & 0xFF);
				int alpha = (int) ((190 - 55 * t) * fade * (0.75f + 0.25f * Mth.sin(time * 0.5f + i * 0.8f + s)));
				drawCrossRibbon(consumer, matrix, normal, light, points[i], points[i + 1],
						STRAND_HALF_WIDTH * widthMul, r, g, b, alpha);
			}
		}
	}

	private void drawBolts(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
						   float len, float widthMul, float time, int seed, float fade) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(RAY_TEX));
		RandomSource rand = RandomSource.create(seed * 31L + 7L);
		float amplitude = 0.17f * widthMul;

		for (int b = 0; b < BOLT_COUNT; b++) {
			float angle = (float) (b * Mth.TWO_PI / BOLT_COUNT) + seed * 0.05f + (float) rand.nextDouble() * 0.9f;
			float radius = (0.11f + (float) rand.nextDouble() * 0.2f) * widthMul;
			float zStart = (float) rand.nextDouble() * len * 0.3f;
			float zEnd = Math.min(len, zStart + len * (0.4f + (float) rand.nextDouble() * 0.6f));
			float spin = time * (0.05f + (float) rand.nextDouble() * 0.09f);
			float baseX = Mth.cos(angle) * radius;
			float baseY = Mth.sin(angle) * radius;
			float lengthFactor = Math.max(0.35f, (zEnd - zStart) / Math.max(1.0f, len));

			List<Vec3> points = new ArrayList<>();
			for (int i = 0; i <= SEGMENTS; i++) {
				float t = (float) i / SEGMENTS;
				float z = Mth.lerp(t, zStart, zEnd);
				float envelope = Mth.sin((float) Math.PI * Mth.clamp(t, 0.02f, 0.98f));
				float drift = Mth.cos(t * 13.0f + time * 0.25f + b) * 0.55f * radius;
				float noiseX = (rand.nextFloat() - 0.5f) * 2.0f * amplitude * envelope;
				float noiseY = (rand.nextFloat() - 0.5f) * 2.0f * amplitude * envelope;
				float waveX = Mth.sin(t * 19.0f + b * 1.7f + time * 0.4f) * 0.5f * amplitude * envelope;
				float waveY = Mth.cos(t * 16.0f + b * 2.3f + time * 0.33f) * 0.5f * amplitude * envelope;
				float spinX = Mth.cos(angle + spin + t * 1.6f) * drift;
				float spinY = Mth.sin(angle + spin + t * 1.6f) * drift;
				points.add(new Vec3(baseX + spinX + noiseX + waveX, baseY + spinY + noiseY + waveY, z));
			}

			for (int i = 0; i < SEGMENTS; i++) {
				float t = (float) i / SEGMENTS;
				int c = t < 0.5f ? CORE_COLOR : INNER_COLOR;
				int alpha = (int) ((55 + 95 * (0.5f + 0.5f * Mth.sin(time * 0.8f + i * 1.1f + b * 0.7f))) * fade * lengthFactor);
				drawCrossRibbon(consumer, matrix, normal, light, points.get(i), points.get(i + 1),
						BOLT_HALF_WIDTH * widthMul, (c >> 16) & 0xFF, (c >> 8) & 0xFF, c & 0xFF, alpha);
			}

			if (b % 3 == 0) {
				int branchStart = Mth.clamp(SEGMENTS / 3 + b % 6, 0, SEGMENTS);
				Vec3 from = points.get(branchStart);
				float branchAngle = angle + (float) rand.nextDouble() * Mth.TWO_PI;
				float branchReach = (0.4f + (float) rand.nextDouble() * 0.85f) * widthMul;
				int branchSegments = 5;
				Vec3 prev = from;
				for (int i = 1; i <= branchSegments; i++) {
					float t = (float) i / branchSegments;
					float jitter = 0.14f * widthMul;
					Vec3 next = from.add(
							Mth.cos(branchAngle) * branchReach * t + (rand.nextFloat() - 0.5f) * jitter,
							Mth.sin(branchAngle) * branchReach * t + (rand.nextFloat() - 0.5f) * jitter,
							(rand.nextFloat() - 0.5f) * jitter * 2.0f);
					int alpha = (int) ((160 - 110 * t) * fade);
					drawCrossRibbon(consumer, matrix, normal, light, prev, next,
							BOLT_HALF_WIDTH * widthMul * (1.0f - t * 0.5f),
							(CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, alpha);
					prev = next;
				}
			}
		}
	}

	private void drawRings(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
						   float len, float widthMul, float time, int seed, float fade) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightning(BEAM_TEX));
		float baseRadius = GLOW_HALF_WIDTH * widthMul * RING_RADIUS_MUL;
		float span = len + RING_SPACING;
		int ringCount = Math.max(4, (int) (span / RING_SPACING));

		for (int i = 0; i < ringCount; i++) {
			float z = (time * RING_SPEED + i * RING_SPACING + seed * 0.37f) % span;
			if (z > len) continue;
			float t = z / len;
			float radius = baseRadius * (1.15f - 0.25f * t) * (1.0f + 0.14f * Mth.sin(time * 0.3f + i * 1.3f));
			float thickness = 0.024f * widthMul * (1.0f + 0.4f * Mth.sin(time * 0.45f + i));
			int alpha = (int) (Mth.clamp(100 + 130 * Mth.sin(time * 0.55f + i * 1.7f), 45.0f, 230.0f) * fade);
			int rr = (RING_COLOR >> 16) & 0xFF, gg = (RING_COLOR >> 8) & 0xFF, bb = RING_COLOR & 0xFF;

			for (int j = 0; j < RING_SEGMENTS; j++) {
				float a0 = (float) (j * Mth.TWO_PI / RING_SEGMENTS) + time * 0.09f;
				float a1 = (float) ((j + 1) * Mth.TWO_PI / RING_SEGMENTS) + time * 0.09f;
				float x0 = Mth.cos(a0) * radius;
				float y0 = Mth.sin(a0) * radius;
				float x1 = Mth.cos(a1) * radius;
				float y1 = Mth.sin(a1) * radius;
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, z - thickness), rr, gg, bb, alpha, 0.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, z - thickness), rr, gg, bb, alpha, 1.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, z + thickness), rr, gg, bb, alpha, 1.0f, 1.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, z + thickness), rr, gg, bb, alpha, 0.0f, 1.0f, light);

				float lead = 0.5f + 0.5f * Mth.sin(time * 0.9f + j * 0.7f + i);
				int leadAlpha = (int) (alpha * lead * 0.6f);
				addVertex(consumer, matrix, normal, new Vec3(x0 * 1.35f, y0 * 1.35f, z), rr, gg, bb, leadAlpha, 0.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1 * 1.35f, y1 * 1.35f, z), rr, gg, bb, leadAlpha, 1.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1 * 1.35f, y1 * 1.35f, z + thickness * 0.5f), rr, gg, bb, leadAlpha, 1.0f, 1.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x0 * 1.35f, y0 * 1.35f, z + thickness * 0.5f), rr, gg, bb, leadAlpha, 0.0f, 1.0f, light);
			}
		}
	}

	private void drawMuzzle(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
							float widthMul, float time, int seed, float fade) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningOverlay(BEAM_TEX));
		float scale = MUZZLE_SCALE * widthMul;
		float pulse = 1.0f + 0.13f * Mth.sin(time * 0.45f + seed);

		for (int layer = 0; layer < 2; layer++) {
			float spin = time * (layer == 0 ? 0.12f : -0.085f) + seed * 0.05f;
			float lengthMul = layer == 0 ? 1.0f : 0.6f;
			float widthMulLayer = layer == 0 ? 0.36f : 0.24f;
			for (int i = 0; i < MUZZLE_PETALS; i++) {
				float angle = i * Mth.TWO_PI / MUZZLE_PETALS + spin + layer * 0.52f;
				float flick = 1.0f + 0.28f * Mth.sin(time * 0.62f + i * 1.9f + layer * 2.2f);
				float petalLength = scale * 2.1f * lengthMul * flick * pulse;
				float petalHalfWidth = scale * widthMulLayer;
				Vec3 base = new Vec3(0.0, 0.0, 0.04f);
				Vec3 out = new Vec3(Mth.cos(angle), Mth.sin(angle), -0.6).normalize();
				Vec3 tip = base.add(out.scale(petalLength));
				Vec3 perp = new Vec3(-Mth.sin(angle), Mth.cos(angle), 0.0).scale(petalHalfWidth);
				int alphaBase = (int) ((layer == 0 ? 210 : 150) * fade * (0.8f + 0.2f * flick));
				int alphaTip = (int) (28 * fade);
				addVertex(consumer, matrix, normal, base.subtract(perp), (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, alphaBase, 0.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, base.add(perp), (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, alphaBase, 1.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, tip, (OUTER_COLOR >> 16) & 0xFF, (OUTER_COLOR >> 8) & 0xFF, OUTER_COLOR & 0xFF, alphaTip, 0.5f, 1.0f, light);
				addVertex(consumer, matrix, normal, tip, (OUTER_COLOR >> 16) & 0xFF, (OUTER_COLOR >> 8) & 0xFF, OUTER_COLOR & 0xFF, alphaTip, 0.5f, 1.0f, light);
			}
		}

		for (int i = 0; i < 6; i++) {
			float angle = i * Mth.TWO_PI / 6.0f + time * 0.16f;
			float reach = scale * (0.9f + 0.35f * Mth.sin(time * 0.5f + i * 1.4f));
			Vec3 from = new Vec3(0.0, 0.0, -0.02f);
			Vec3 to = from.add(Mth.cos(angle) * reach * 0.35f, Mth.sin(angle) * reach * 0.35f, -reach * 1.6f);
			drawCrossRibbon(consumer, matrix, normal, light, from, to, 0.012f * widthMul,
					(INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (110 * fade));
		}

		addBoxAt(matrix, normal, buffer, light, 0.0f, scale * 0.6f, scale * 0.85f, OUTER_COLOR, (int) (85 * fade));
		addBoxAt(matrix, normal, buffer, light, 0.0f, scale * 0.35f, scale * 0.5f, CORE_COLOR, (int) (210 * fade));
	}

	private void drawTail(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
						  float len, float widthMul, float time, int seed, float fade) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningOverlay(BEAM_TEX));
		float scale = TAIL_SCALE * widthMul;
		float pulse = 1.0f + 0.16f * Mth.sin(time * 0.52f + seed);
		Vec3 c = new Vec3(0.0, 0.0, len);

		addBoxAt(matrix, normal, buffer, light, len, scale * 0.7f, scale * 0.95f, OUTER_COLOR, (int) (90 * fade));
		addBoxAt(matrix, normal, buffer, light, len, scale * 0.4f, scale * 0.55f, CORE_COLOR, (int) (225 * fade));

		for (int k = 0; k < SHOCKWAVE_RINGS; k++) {
			float phase = (time * 0.052f + k / (float) SHOCKWAVE_RINGS) % 1.0f;
			float radius = (0.22f + phase * 1.6f) * widthMul;
			float thickness = 0.022f * widthMul * (1.0f - phase * 0.55f);
			int alpha = (int) ((1.0f - phase) * (1.0f - phase) * 165 * fade);
			if (alpha < 4) continue;
			for (int j = 0; j < RING_SEGMENTS; j++) {
				float a0 = (float) (j * Mth.TWO_PI / RING_SEGMENTS);
				float a1 = (float) ((j + 1) * Mth.TWO_PI / RING_SEGMENTS);
				float x0 = Mth.cos(a0) * radius;
				float y0 = Mth.sin(a0) * radius;
				float x1 = Mth.cos(a1) * radius;
				float y1 = Mth.sin(a1) * radius;
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, len - thickness), (RING_COLOR >> 16) & 0xFF, (RING_COLOR >> 8) & 0xFF, RING_COLOR & 0xFF, alpha, 0.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, len - thickness), (RING_COLOR >> 16) & 0xFF, (RING_COLOR >> 8) & 0xFF, RING_COLOR & 0xFF, alpha, 1.0f, 0.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x1, y1, len + thickness), (RING_COLOR >> 16) & 0xFF, (RING_COLOR >> 8) & 0xFF, RING_COLOR & 0xFF, alpha, 1.0f, 1.0f, light);
				addVertex(consumer, matrix, normal, new Vec3(x0, y0, len + thickness), (RING_COLOR >> 16) & 0xFF, (RING_COLOR >> 8) & 0xFF, RING_COLOR & 0xFF, alpha, 0.0f, 1.0f, light);
			}
		}

		for (int i = 0; i < TAIL_SPIKES; i++) {
			float angle = i * Mth.TWO_PI / TAIL_SPIKES + time * 0.07f + seed * 0.03f;
			float flick = 0.75f + 0.35f * Mth.sin(time * 0.7f + i * 1.6f);
			float reach = scale * 2.3f * flick;
			Vec3 dirOut = new Vec3(Mth.cos(angle), Mth.sin(angle), 0.55).normalize();
			Vec3 tip = c.add(dirOut.scale(reach));
			Vec3 perp = new Vec3(-Mth.sin(angle), Mth.cos(angle), 0.0).scale(scale * 0.24f);
			int alphaBase = (int) (170 * fade);
			addVertex(consumer, matrix, normal, c.subtract(perp), (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, alphaBase, 0.0f, 0.0f, light);
			addVertex(consumer, matrix, normal, c.add(perp), (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, alphaBase, 1.0f, 0.0f, light);
			addVertex(consumer, matrix, normal, tip, (INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (30 * fade), 0.5f, 1.0f, light);
			addVertex(consumer, matrix, normal, tip, (INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (30 * fade), 0.5f, 1.0f, light);
		}

		for (int k = 0; k < 3; k++) {
			float spin = time * (k == 1 ? -0.14f : 0.11f) + k * 2.1f;
			float len2 = scale * (2.6f + 0.9f * k) * pulse;
			float width2 = scale * (0.30f - 0.05f * k);
			float angle = spin + seed * 0.04f;
			Vec3 out = new Vec3(Mth.cos(angle), Mth.sin(angle), 0.9).normalize();
			Vec3 from = c.subtract(out.scale(scale * 0.8f));
			Vec3 to = c.add(out.scale(len2));
			Vec3 perp = new Vec3(-Mth.sin(angle), Mth.cos(angle), 0.0).scale(width2);
			addVertex(consumer, matrix, normal, from.subtract(perp), (INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (170 * fade), 0.0f, 0.0f, light);
			addVertex(consumer, matrix, normal, from.add(perp), (INNER_COLOR >> 16) & 0xFF, (INNER_COLOR >> 8) & 0xFF, INNER_COLOR & 0xFF, (int) (170 * fade), 1.0f, 0.0f, light);
			addVertex(consumer, matrix, normal, to, (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, (int) (40 * fade), 0.5f, 1.0f, light);
			addVertex(consumer, matrix, normal, to, (CORE_COLOR >> 16) & 0xFF, (CORE_COLOR >> 8) & 0xFF, CORE_COLOR & 0xFF, (int) (40 * fade), 0.5f, 1.0f, light);
		}
	}

	private void addBoxAt(Matrix4f matrix, Matrix3f normal, MultiBufferSource buffer, int light,
						  float zCenter, float halfLen, float halfWidth, int color, int alpha) {
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedLightningAdd(RAY_TEX));
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		float z0 = zCenter - halfLen;
		float z1 = zCenter + halfLen;
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, halfWidth, z0), r, g, b, alpha, 0.0f, 0.0f,
				new Vec3(halfWidth, halfWidth, z0), r, g, b, alpha, 1.0f, 0.0f,
				new Vec3(halfWidth, halfWidth, z1), r, g, b, alpha, 1.0f, 1.0f,
				new Vec3(-halfWidth, halfWidth, z1), r, g, b, alpha, 0.0f, 1.0f);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, z1), r, g, b, alpha, 0.0f, 1.0f,
				new Vec3(halfWidth, -halfWidth, z1), r, g, b, alpha, 1.0f, 1.0f,
				new Vec3(halfWidth, -halfWidth, z0), r, g, b, alpha, 1.0f, 0.0f,
				new Vec3(-halfWidth, -halfWidth, z0), r, g, b, alpha, 0.0f, 0.0f);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, z0), r, g, b, alpha, 0.0f, 0.0f,
				new Vec3(-halfWidth, -halfWidth, z1), r, g, b, alpha, 0.0f, 1.0f,
				new Vec3(-halfWidth, halfWidth, z1), r, g, b, alpha, 1.0f, 1.0f,
				new Vec3(-halfWidth, halfWidth, z0), r, g, b, alpha, 1.0f, 0.0f);
		addQuad(consumer, matrix, normal, light,
				new Vec3(halfWidth, -halfWidth, z1), r, g, b, alpha, 0.0f, 1.0f,
				new Vec3(halfWidth, -halfWidth, z0), r, g, b, alpha, 0.0f, 0.0f,
				new Vec3(halfWidth, halfWidth, z0), r, g, b, alpha, 1.0f, 0.0f,
				new Vec3(halfWidth, halfWidth, z1), r, g, b, alpha, 1.0f, 1.0f);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, z0), r, g, b, alpha, 0.0f, 0.0f,
				new Vec3(halfWidth, -halfWidth, z0), r, g, b, alpha, 1.0f, 0.0f,
				new Vec3(halfWidth, halfWidth, z0), r, g, b, alpha, 1.0f, 1.0f,
				new Vec3(-halfWidth, halfWidth, z0), r, g, b, alpha, 0.0f, 1.0f);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, halfWidth, z1), r, g, b, alpha, 0.0f, 1.0f,
				new Vec3(halfWidth, halfWidth, z1), r, g, b, alpha, 1.0f, 1.0f,
				new Vec3(halfWidth, -halfWidth, z1), r, g, b, alpha, 1.0f, 0.0f,
				new Vec3(-halfWidth, -halfWidth, z1), r, g, b, alpha, 0.0f, 0.0f);
	}

	private void drawBox(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
						 float len, int age, float halfWidth, int r, int g, int b, int a) {
		float uvBottom = -age * 0.01f;
		float uvTop = len / 32.0f - age * 0.01f;
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, halfWidth, 0), r, g, b, a, 0.0f, uvBottom,
				new Vec3(halfWidth, halfWidth, 0), r, g, b, a, 1.0f, uvBottom,
				new Vec3(halfWidth, halfWidth, len), r, g, b, a, 1.0f, uvTop,
				new Vec3(-halfWidth, halfWidth, len), r, g, b, a, 0.0f, uvTop);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, len), r, g, b, a, 0.0f, uvTop,
				new Vec3(halfWidth, -halfWidth, len), r, g, b, a, 1.0f, uvTop,
				new Vec3(halfWidth, -halfWidth, 0), r, g, b, a, 1.0f, uvBottom,
				new Vec3(-halfWidth, -halfWidth, 0), r, g, b, a, 0.0f, uvBottom);
		addQuad(consumer, matrix, normal, light,
				new Vec3(-halfWidth, -halfWidth, len), r, g, b, a, 0.0f, uvTop,
				new Vec3(-halfWidth, -halfWidth, 0), r, g, b, a, 0.0f, uvBottom,
				new Vec3(-halfWidth, halfWidth, 0), r, g, b, a, 1.0f, uvBottom,
				new Vec3(-halfWidth, halfWidth, len), r, g, b, a, 1.0f, uvTop);
		addQuad(consumer, matrix, normal, light,
				new Vec3(halfWidth, -halfWidth, 0), r, g, b, a, 0.0f, uvBottom,
				new Vec3(halfWidth, -halfWidth, len), r, g, b, a, 0.0f, uvTop,
				new Vec3(halfWidth, halfWidth, len), r, g, b, a, 1.0f, uvTop,
				new Vec3(halfWidth, halfWidth, 0), r, g, b, a, 1.0f, uvBottom);
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
	}

	private void drawCrossRibbon(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
								 Vec3 p0, Vec3 p1, float halfWidth, int r, int g, int b, int a) {
		Vec3 dir = p1.subtract(p0);
		if (dir.lengthSqr() < 1.0E-8) return;
		dir = dir.normalize();
		Vec3 sideRef = Math.abs(dir.y) < 0.9 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
		Vec3 axisA = dir.cross(sideRef);
		if (axisA.lengthSqr() < 1.0E-8) {
			axisA = new Vec3(1.0, 0.0, 0.0);
		}
		axisA = axisA.normalize();
		Vec3 axisB = dir.cross(axisA).normalize();
		drawRibbon(consumer, matrix, normal, light, p0, p1, axisA, halfWidth, r, g, b, a);
		drawRibbon(consumer, matrix, normal, light, p0, p1, axisB, halfWidth, r, g, b, a);
	}

	private void drawRibbon(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, int light,
							Vec3 p0, Vec3 p1, Vec3 axis, float halfWidth, int r, int g, int b, int a) {
		Vec3 offset = axis.scale(halfWidth);
		addVertex(consumer, matrix, normal, p0.subtract(offset), r, g, b, a, 0.0f, 0.0f, light);
		addVertex(consumer, matrix, normal, p0.add(offset), r, g, b, a, 1.0f, 0.0f, light);
		addVertex(consumer, matrix, normal, p1.add(offset), r, g, b, a, 1.0f, 1.0f, light);
		addVertex(consumer, matrix, normal, p1.subtract(offset), r, g, b, a, 0.0f, 1.0f, light);
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
