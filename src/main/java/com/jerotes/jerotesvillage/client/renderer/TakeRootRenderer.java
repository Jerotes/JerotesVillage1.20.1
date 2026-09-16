package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Other.OtherSpell.MagicBonfireEntity;
import com.jerotes.jerotes.entity.Other.ShootTargetSpell.FireballEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeltake_root;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.TakeRootEntity;
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

import java.util.List;

public class TakeRootRenderer extends EntityRenderer<TakeRootEntity> {
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
	private final Modeltake_root<MagicBonfireEntity> model;

	public TakeRootRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modeltake_root<>(context.bakeLayer(Modeltake_root.LAYER_LOCATION));
	}

	@Override
	public void render(TakeRootEntity t, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		poseStack.pushPose();
		if (t.life >= 5 && t.life <= 25) {
			renderRangeIndicator(t, partialTicks, poseStack, bufferIn);
		}
		renderTrail(t, partialTicks, poseStack, bufferIn);
		poseStack.popPose();


		if (t.getLifeBomb() > 0) {
			poseStack.pushPose();
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -1.5F, 0.0F);
			this.model.scale(t, t.getLifeBomb() + partialTicks);
			VertexConsumer vb = bufferIn.getBuffer(JerotesRenderType.entityTranslucent(getTextureLocation(t)));
			model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
			poseStack.popPose();

		}

		super.render(t, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	public ResourceLocation getTextureLocation(TakeRootEntity takeRootEntity) {
		return new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/take_root.png");
	}

	private void renderRangeIndicator(TakeRootEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		float radius = 6.0f;
		float innerRadius = 5.5f;
		int segments = 32;
		float y = 0.0f;

		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(GLOW_LOCATION));
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		Matrix3f normalMat = pose.normal();

		int alpha = (int) (80 * (entity.life >= 15 ? (1 - (entity.life - 15)/10f) : (entity.life - 5)/10f));
		int r = 143, g = 225, b = 50;

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

	private void renderTrail(TakeRootEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer) {
		List<TakeRootEntity.TrailPoint> points = entity.getTrailPoints();
		if (points.size() < 2) return;

		poseStack.pushPose();
		VertexConsumer consumer = buffer.getBuffer(JerotesRenderType.glowDoubleSided(GLOW_LOCATION));
		Matrix4f matrix = poseStack.last().pose();
		Matrix3f normal = poseStack.last().normal();

		int currentLife = entity.life;
		int maxLife = 120;

		int n = points.size();
		float[] widths = new float[n];
		float[] alphas = new float[n];
		for (int i = 0; i < n; i++) {
			FireballEntity.TrailPoint tp = points.get(i);
			int age = currentLife - tp.createdAt;
			float ageFade = 1.0f - Math.min(1.0f, age / 20.0f);
			float overallFade = 1.0f - (float) currentLife / maxLife;
			float alpha = Math.min(0.6f, 0.8f * ageFade * (1 - overallFade * 0.7f));
			alphas[i] = Math.max(0, alpha);
			widths[i] = 0.12f * ageFade * (0.2f + 0.8f * (float)i / (float)n);
		}
		Vec3 currentPos = new Vec3(
				Mth.lerp(partialTick, entity.xOld, entity.getX()),
				Mth.lerp(partialTick, entity.yOld, entity.getY()),
				Mth.lerp(partialTick, entity.zOld, entity.getZ())
		);
		Vec3 cameraRight = new Vec3(1, 0, 0);
		Vec3 cameraUp = new Vec3(0, 1, 0);

		for (int i = 0; i < n - 1; i++) {
			FireballEntity.TrailPoint tp0 = points.get(i);
			FireballEntity.TrailPoint tp1 = points.get(i + 1);
			Vec3 point0 = tp0.getPosition(partialTick);
			Vec3 point1 = tp1.getPosition(partialTick);
			Vec3 local0 = point0.subtract(currentPos);
			Vec3 local1 = point1.subtract(currentPos);

			Vec3 dirVec = local1.subtract(local0);
			double lenSq = dirVec.lengthSqr();
			if (lenSq < 1e-8) continue;
			Vec3 dir = dirVec.normalize();

			int colorI = entity.beamLightI();
			int innerRI = (colorI >> 16) & 0xFF;
			int innerGI = (colorI >> 8) & 0xFF;
			int innerBI = colorI & 0xFF;
			int colorII = entity.beamLightII();
			int innerRII = (colorII >> 16) & 0xFF;
			int innerGII = (colorII >> 8) & 0xFF;
			int innerBII = colorII & 0xFF;
			float scaleColor = 1f - (float) i / (float) n;
			int r = (int) (innerRI * scaleColor + innerRII * (1 - scaleColor));
			int g = (int) (innerGI * scaleColor + innerGII * (1 - scaleColor));
			int b = (int) (innerBI * scaleColor + innerBII * (1 - scaleColor));

			Vec3 right1 = cameraUp.cross(dir);
			if (right1.lengthSqr() < 1e-4) right1 = cameraRight.cross(dir);
			right1 = right1.normalize();

			Vec3 right2 = right1.cross(dir).normalize();
			if (right2.lengthSqr() < 1e-4) {
				right2 = cameraRight.cross(dir).normalize();
			}

			drawQuad(consumer, matrix, normal, local0, local1, right1, widths[i], widths[i+1], alphas[i], alphas[i+1], r, g, b);
			drawQuad(consumer, matrix, normal, local0, local1, right2, widths[i], widths[i+1], alphas[i], alphas[i+1], r, g, b);
		}
		if (n > 0) {
			Vec3 headPos = points.get(n - 1).getPosition(partialTick);
			Vec3 localHead = headPos.subtract(currentPos);
			float glowSize = 0.3f * (1.0f - (float)currentLife / maxLife * 0.5f);
			drawQuad(consumer, matrix, normal,
					localHead, localHead, cameraRight, glowSize, glowSize, 1.0f, 1.0f, 255, 255, 255);
			drawQuad(consumer, matrix, normal,
					localHead, localHead, cameraUp, glowSize, glowSize, 1.0f, 1.0f, 255, 255, 255);
		}
		poseStack.popPose();
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
	public boolean shouldRender(TakeRootEntity entity, Frustum frustum, double d, double d2, double d3) {
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