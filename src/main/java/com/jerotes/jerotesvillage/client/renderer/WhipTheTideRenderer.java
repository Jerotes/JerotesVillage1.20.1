package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelblock;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.WhipTheTideEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
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

public class WhipTheTideRenderer extends EntityRenderer<WhipTheTideEntity> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/whip_the_tide.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
	private final Modelblock model;

	public WhipTheTideRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new Modelblock(context.bakeLayer(Modelblock.LAYER_LOCATION));
	}

	@Override
	protected int getBlockLightLevel(WhipTheTideEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public void render(WhipTheTideEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		poseStack.pushPose();
		VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(this.getTextureLocation(entityIn)));
		poseStack.scale(-0.25f, -0.25f, 0.25f);
		poseStack.translate(0f, -0.125f * 10, 0f);
		this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
		poseStack.popPose();

		poseStack.pushPose();
		List<WhipTheTideEntity.TrailPoint> points = this.renderTrail(entityIn, partialTicks, poseStack, bufferIn);
		poseStack.popPose();

		poseStack.pushPose();
		if (points != null && !points.isEmpty()) {
			float size = Mth.clamp((entityIn.life + partialTicks) / 12f, 0, 1);
			renderTailBlock(entityIn, points.get(0).getPosition(partialTicks), partialTicks, size, poseStack, bufferIn, packedLightIn);
		}
		poseStack.popPose();

		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	private void renderTailBlock(WhipTheTideEntity entity, Vec3 pos, float partialTick, float size, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		Vec3 entityPos = new Vec3(
				Mth.lerp(partialTick, entity.xOld, entity.getX()),
				Mth.lerp(partialTick, entity.yOld, entity.getY()),
				Mth.lerp(partialTick, entity.zOld, entity.getZ())
		);
		Vec3 localPos = pos.subtract(entityPos);
		poseStack.pushPose();
		poseStack.translate(localPos.x, localPos.y, localPos.z);
		poseStack.scale(size, size, size);
		poseStack.scale(-0.25f, -0.25f, 0.25f);
		poseStack.translate(0f, -0.5f, 0f);
		VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(this.getTextureLocation(entity)));
		this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
		poseStack.popPose();
	}

	private List<WhipTheTideEntity.TrailPoint> renderTrail(WhipTheTideEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		if (!entity.showBeam()) return null;
		List<WhipTheTideEntity.TrailPoint> points = entity.getTrailPoints();
		if (points.size() < 2) return null;

		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSided(GLOW_LOCATION));
		Matrix4f matrix = poseStack.last().pose();
		Matrix3f normal = poseStack.last().normal();

		int currentLife = entity.life;
		int maxLife = 120;

		int n = points.size();
		float[] widths = new float[n];
		float[] alphas = new float[n];
		for (int i = 0; i < n; i++) {
			WhipTheTideEntity.TrailPoint tp = points.get(i);
			int age = currentLife - tp.createdAt;
			float ageFade = 1.0f - Math.min(1.0f, age / 20.0f);
			float overallFade = 1.0f - (float) currentLife / maxLife;
			float alpha = Math.min(0.6f, 0.8f * ageFade * (1 - overallFade * 0.7f));
			alphas[i] = Math.max(0, alpha);
			float positionFade = (float) i / (n - 1);
			widths[i] = 0.12f * ageFade * (0.3f + 0.7f * positionFade);
		}

		Vec3 currentPos = new Vec3(
				Mth.lerp(partialTick, entity.xOld, entity.getX()),
				Mth.lerp(partialTick, entity.yOld, entity.getY()),
				Mth.lerp(partialTick, entity.zOld, entity.getZ())
		);

		int colorI = entity.beamLightI();
		int colorII = entity.beamLightII();
		int colorIII = entity.beamLightIII();
		int ri = (colorI >> 16) & 0xFF;
		int gi = (colorI >> 8) & 0xFF;
		int bi = (colorI) & 0xFF;
		int rii = (colorII >> 16) & 0xFF;
		int gii = (colorII >> 8) & 0xFF;
		int bii = (colorII) & 0xFF;
		int riii = (colorIII >> 16) & 0xFF;
		int giii = (colorIII >> 8) & 0xFF;
		int biii = (colorIII) & 0xFF;

		for (int i = 0; i < n - 1; i++) {
			WhipTheTideEntity.TrailPoint tp0 = points.get(i);
			WhipTheTideEntity.TrailPoint tp1 = points.get(i + 1);
			Vec3 point0 = tp0.getPosition(partialTick);
			Vec3 point1 = tp1.getPosition(partialTick);
			Vec3 local0 = point0.subtract(currentPos);
			Vec3 local1 = point1.subtract(currentPos);

			Vec3 dirVec = local1.subtract(local0);
			double lenSq = dirVec.lengthSqr();
			if (lenSq < 1e-8) continue;
			Vec3 dir = dirVec.normalize();

			Vec3 refUp = (Math.abs(dir.y) < 0.9) ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
			Vec3 right = dir.cross(refUp).normalize();
			Vec3 up = right.cross(dir).normalize();

			float w0 = widths[i];
			float w1 = widths[i + 1];
			float a0 = alphas[i];
			float a1 = alphas[i + 1];

			float t0 = (float) i / (n - 1);
			float t1 = (float) (i + 1) / (n - 1);

			int r0 = getInterpolatedColor(ri, rii, riii, t0);
			int g0 = getInterpolatedColor(gi, gii, giii, t0);
			int b0 = getInterpolatedColor(bi, bii, biii, t0);
			int r1 = getInterpolatedColor(ri, rii, riii, t1);
			int g1 = getInterpolatedColor(gi, gii, giii, t1);
			int b1 = getInterpolatedColor(bi, bii, biii, t1);

			Vec3 p0l = local0.add(right.scale(-w0));
			Vec3 p0r = local0.add(right.scale(w0));
			Vec3 p1l = local1.add(right.scale(-w1));
			Vec3 p1r = local1.add(right.scale(w1));
			Vec3 p0u = local0.add(up.scale(-w0));
			Vec3 p0d = local0.add(up.scale(w0));
			Vec3 p1u = local1.add(up.scale(-w1));
			Vec3 p1d = local1.add(up.scale(w1));

			addVertex(consumer, matrix, normal, p0l, r0, g0, b0, (int) (a0 * 200), 0, 0);
			addVertex(consumer, matrix, normal, p0r, r0, g0, b0, (int) (a0 * 200), 1, 0);
			addVertex(consumer, matrix, normal, p1r, r1, g1, b1, (int) (a1 * 200), 1, 1);
			addVertex(consumer, matrix, normal, p1l, r1, g1, b1, (int) (a1 * 200), 0, 1);

			addVertex(consumer, matrix, normal, p0u, r0, g0, b0, (int) (a0 * 200), 0, 0);
			addVertex(consumer, matrix, normal, p0d, r0, g0, b0, (int) (a0 * 200), 1, 0);
			addVertex(consumer, matrix, normal, p1d, r1, g1, b1, (int) (a1 * 200), 1, 1);
			addVertex(consumer, matrix, normal, p1u, r1, g1, b1, (int) (a1 * 200), 0, 1);
		}

		return points;
	}

	private int getInterpolatedColor(int c1, int c2, int c3, float t) {
		if (t < 0.5f) {
			float u = t / 0.5f;
			return (int) (c1 + (c2 - c1) * u);
		} else {
			float u = (t - 0.5f) / 0.5f;
			return (int) (c2 + (c3 - c2) * u);
		}
	}

	private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
	                       int r, int g, int b, int a, float u, float v) {
		consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
				.color(r, g, b, a)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880)
				.normal(normal, 0, 1, 0)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(WhipTheTideEntity entity) {
		return LOCATION;
	}
}
