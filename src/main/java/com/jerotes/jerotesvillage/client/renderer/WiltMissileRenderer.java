package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicMissile.BaseMagicMissileEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.WiltMissileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class WiltMissileRenderer extends EntityRenderer<WiltMissileEntity> {
	private final Modelblock model;
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilt_missile.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");

	private static final float[] LIFE_COLOR = {0.78f, 0.85f, 0.25f};
	private static final float[] ROT_COLOR  = {0.12f, 0.07f, 0.04f};

	private static final float CROSS_OVERLAP_COMPENSATION = 0.7f;
	private static final float HALO_OVERLAP_COMPENSATION = 0.55f;

	public WiltMissileRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	protected int getBlockLightLevel(WiltMissileEntity t, BlockPos blockPos) {
		return 15;
	}

	@Override
	public void render(WiltMissileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.eyes(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.scale(0.45f, 0.45f, 0.45f);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.5f);
		poseStack.popPose();

		poseStack.pushPose();
		renderTrail(entityIn, partialTicks, poseStack, bufferIn);
		poseStack.popPose();

		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	private void renderTrail(WiltMissileEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		List<BaseMagicMissileEntity.TrailPoint> points = entity.getTrailPoints();
		if (points.size() < 2) return;

		poseStack.pushPose();
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSided(GLOW_LOCATION));
		Matrix4f matrix = poseStack.last().pose();
		Matrix3f normal = poseStack.last().normal();

		int currentLife = entity.life;
		int maxLife = 120;
		int n = points.size();

		float[] ageFades = new float[n];
		float[] flicker = new float[n];
		for (int i = 0; i < n; i++) {
			BaseMagicMissileEntity.TrailPoint tp = points.get(i);
			int age = currentLife - tp.createdAt;
			ageFades[i] = 1.0f - Math.min(1.0f, age / 20.0f);
			flicker[i] = 0.85f + 0.15f * Mth.sin(currentLife * 0.35f + i * 1.7f);
		}

		Vec3 currentPos = new Vec3(
				Mth.lerp(partialTick, entity.xOld, entity.getX()),
				Mth.lerp(partialTick, entity.yOld, entity.getY()),
				Mth.lerp(partialTick, entity.zOld, entity.getZ())
		);

		int colorI = entity.beamLightI();
		float srcR = ((colorI >> 16) & 0xFF) / 255f;
		float srcG = ((colorI >> 8) & 0xFF) / 255f;
		float srcB = (colorI & 0xFF) / 255f;

		for (int i = 0; i < n - 1; i++) {
			renderCrossSegment(consumer, matrix, normal, points, i, currentPos, partialTick,
					ageFades, flicker, srcR, srcG, srcB,
					0.12f, 0.9f * CROSS_OVERLAP_COMPENSATION);
		}

		for (int i = 0; i < n - 1; i++) {
			renderCrossSegment(consumer, matrix, normal, points, i, currentPos, partialTick,
					ageFades, flicker, ROT_COLOR[0], ROT_COLOR[1], ROT_COLOR[2],
					0.26f, 0.28f * HALO_OVERLAP_COMPENSATION);
		}

		for (int strand = 0; strand < 2; strand++) {
			float phase = strand == 0 ? 0f : (float) Math.PI;
			for (int i = 0; i < n - 1; i++) {
				renderTendrilSegment(consumer, matrix, normal, points, i, currentPos, partialTick,
						ageFades, flicker, srcR, srcG, srcB, currentLife, phase);
			}
		}

		poseStack.popPose();
	}

	private void renderCrossSegment(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
									List<BaseMagicMissileEntity.TrailPoint> points, int i, Vec3 currentPos, float partialTick,
									float[] ageFades, float[] flicker,
									float baseR, float baseG, float baseB,
									float baseWidth, float maxAlpha) {
		BaseMagicMissileEntity.TrailPoint tp0 = points.get(i);
		BaseMagicMissileEntity.TrailPoint tp1 = points.get(i + 1);
		Vec3 local0 = tp0.getPosition(partialTick).subtract(currentPos);
		Vec3 local1 = tp1.getPosition(partialTick).subtract(currentPos);

		Vec3 dirVec = local1.subtract(local0);
		if (dirVec.lengthSqr() < 1e-8) return;
		Vec3 dir = dirVec.normalize();

		Vec3 refUp = (Math.abs(dir.y) < 0.9) ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
		Vec3 right = dir.cross(refUp).normalize();
		Vec3 up = right.cross(dir).normalize();

		int n = points.size();
		float t0 = 1.0f - (float) i / (n - 1);
		float t1 = 1.0f - (float) (i + 1) / (n - 1);

		float w0 = baseWidth * ageFades[i];
		float w1 = baseWidth * ageFades[i + 1];
		float a0 = Math.max(0, Math.min(maxAlpha, maxAlpha * ageFades[i] * (1 - t0 * 0.3f)) * flicker[i]);
		float a1 = Math.max(0, Math.min(maxAlpha, maxAlpha * ageFades[i + 1] * (1 - t1 * 0.3f)) * flicker[i + 1]);

		float r0 = Mth.lerp(t0, ROT_COLOR[0], baseR), g0 = Mth.lerp(t0, ROT_COLOR[1], baseG), b0 = Mth.lerp(t0, ROT_COLOR[2], baseB);
		float r1 = Mth.lerp(t1, ROT_COLOR[0], baseR), g1 = Mth.lerp(t1, ROT_COLOR[1], baseG), b1 = Mth.lerp(t1, ROT_COLOR[2], baseB);

		Vec3 p0l = local0.add(right.scale(-w0));
		Vec3 p0r = local0.add(right.scale(w0));
		Vec3 p1l = local1.add(right.scale(-w1));
		Vec3 p1r = local1.add(right.scale(w1));
		Vec3 p0u = local0.add(up.scale(-w0));
		Vec3 p0d = local0.add(up.scale(w0));
		Vec3 p1u = local1.add(up.scale(-w1));
		Vec3 p1d = local1.add(up.scale(w1));

		addVertex(consumer, matrix, normal, p0l, r0, g0, b0, a0, 0, 0);
		addVertex(consumer, matrix, normal, p0r, r0, g0, b0, a0, 1, 0);
		addVertex(consumer, matrix, normal, p1r, r1, g1, b1, a1, 1, 1);
		addVertex(consumer, matrix, normal, p1l, r1, g1, b1, a1, 0, 1);

		addVertex(consumer, matrix, normal, p0u, r0, g0, b0, a0, 0, 0);
		addVertex(consumer, matrix, normal, p0d, r0, g0, b0, a0, 1, 0);
		addVertex(consumer, matrix, normal, p1d, r1, g1, b1, a1, 1, 1);
		addVertex(consumer, matrix, normal, p1u, r1, g1, b1, a1, 0, 1);
	}

	private void renderTendrilSegment(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
									  List<BaseMagicMissileEntity.TrailPoint> points, int i, Vec3 currentPos, float partialTick,
									  float[] ageFades, float[] flicker,
									  float lifeR, float lifeG, float lifeB,
									  int currentLife, float phase) {
		BaseMagicMissileEntity.TrailPoint tp0 = points.get(i);
		BaseMagicMissileEntity.TrailPoint tp1 = points.get(i + 1);
		Vec3 local0 = tp0.getPosition(partialTick).subtract(currentPos);
		Vec3 local1 = tp1.getPosition(partialTick).subtract(currentPos);

		Vec3 dirVec = local1.subtract(local0);
		if (dirVec.lengthSqr() < 1e-8) return;
		Vec3 dir = dirVec.normalize();

		Vec3 refUp = (Math.abs(dir.y) < 0.9) ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
		Vec3 right = dir.cross(refUp).normalize();
		Vec3 up = right.cross(dir).normalize();

		float spiralFreq = 0.9f;
		float amp0 = 0.10f * ageFades[i];
		float amp1 = 0.10f * ageFades[i + 1];
		float ang0 = i * spiralFreq + currentLife * 0.15f + phase;
		float ang1 = (i + 1) * spiralFreq + currentLife * 0.15f + phase;

		Vec3 offset0 = right.scale(Mth.cos(ang0) * amp0).add(up.scale(Mth.sin(ang0) * amp0));
		Vec3 offset1 = right.scale(Mth.cos(ang1) * amp1).add(up.scale(Mth.sin(ang1) * amp1));

		Vec3 center0 = local0.add(offset0);
		Vec3 center1 = local1.add(offset1);

		float w = 0.035f;
		Vec3 p0l = center0.add(right.scale(-w));
		Vec3 p0r = center0.add(right.scale(w));
		Vec3 p1l = center1.add(right.scale(-w));
		Vec3 p1r = center1.add(right.scale(w));

		float a0 = 0.45f * ageFades[i] * flicker[i];
		float a1 = 0.45f * ageFades[i + 1] * flicker[i + 1];

		addVertex(consumer, matrix, normal, p0l, ROT_COLOR[0], ROT_COLOR[1], ROT_COLOR[2], a0, 0, 0);
		addVertex(consumer, matrix, normal, p0r, lifeR * 0.4f + ROT_COLOR[0] * 0.6f, lifeG * 0.4f + ROT_COLOR[1] * 0.6f, lifeB * 0.4f + ROT_COLOR[2] * 0.6f, a0, 1, 0);
		addVertex(consumer, matrix, normal, p1r, lifeR * 0.4f + ROT_COLOR[0] * 0.6f, lifeG * 0.4f + ROT_COLOR[1] * 0.6f, lifeB * 0.4f + ROT_COLOR[2] * 0.6f, a1, 1, 1);
		addVertex(consumer, matrix, normal, p1l, ROT_COLOR[0], ROT_COLOR[1], ROT_COLOR[2], a1, 0, 1);
	}

	private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
						   float r, float g, float b, float a, float u, float v) {
		consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
				.color((int) (Mth.clamp(r, 0, 1) * 255), (int) (Mth.clamp(g, 0, 1) * 255), (int) (Mth.clamp(b, 0, 1) * 255), (int) (Mth.clamp(a, 0, 1) * 255))
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880)
				.normal(normal, 0, 1, 0)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(WiltMissileEntity entity) {
		return LOCATION;
	}
}