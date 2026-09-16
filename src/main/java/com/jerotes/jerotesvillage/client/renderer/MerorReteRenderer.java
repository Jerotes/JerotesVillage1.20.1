package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvirtual_cave_fishing_net;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorReteEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class MerorReteRenderer extends EntityRenderer<MerorReteEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_rete.png");
	private static final ResourceLocation ADD_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_rete_add.png");
	private final Modelvirtual_cave_fishing_net model;

	public MerorReteRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelvirtual_cave_fishing_net(context.bakeLayer(Modelvirtual_cave_fishing_net.LAYER_LOCATION));
	}

	@Override
	public void render(MerorReteEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		poseStack.translate(0, -2, 0);
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
		Entity entity = entityIn.getOwner();
		if (entity == null) {
			return;
		}
		this.renderLeash(entityIn, partialTicks, poseStack, bufferIn, entity);
	}
	private <E extends Entity> void renderLeash(MerorReteEntity t, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, E e) {
		int n;
		poseStack.pushPose();
		Vec3 vec3 = ((Entity)e).getRopeHoldPosition(f);
		double d = (double)(Mth.lerp(f, t.getYRot(), t.getYRot()) * 0.017453292f) + 1.5707963267948966;
		Vec3 vec32 = ((Entity)t).getLeashOffset(f);
		double d2 = Math.cos(d) * vec32.z + Math.sin(d) * vec32.x;
		double d3 = Math.sin(d) * vec32.z - Math.cos(d) * vec32.x;
		double d4 = Mth.lerp((double)f, t.xo, ((Entity)t).getX()) + d2;
		double d5 = Mth.lerp((double)f, t.yo, ((Entity)t).getY()) + vec32.y;
		double d6 = Mth.lerp((double)f, t.zo, ((Entity)t).getZ()) + d3;
		poseStack.translate(d2, vec32.y, d3);
		float f2 = (float)(vec3.x - d4);
		float f3 = (float)(vec3.y - d5);
		float f4 = (float)(vec3.z - d6);
		float f5 = 0.025f;
		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesVillageRenderType.meror_rete());
		Matrix4f matrix4f = poseStack.last().pose();
		float f6 = Mth.invSqrt(f2 * f2 + f4 * f4) * 0.025f / 2.0f;
		float f7 = f4 * f6;
		float f8 = f2 * f6;
		BlockPos blockPos = BlockPos.containing(((Entity)t).getEyePosition(f));
		BlockPos blockPos2 = BlockPos.containing(((Entity)e).getEyePosition(f));
		int n2 = this.getBlockLightLevel(t, blockPos);
		int n3 = 15;
		int n4 = ((Entity)t).level().getBrightness(LightLayer.SKY, blockPos);
		int n5 = ((Entity)t).level().getBrightness(LightLayer.SKY, blockPos2);
		for (n = 0; n <= 24; ++n) {
			addVertexPair(vertexConsumer, matrix4f, f2, f3, f4, n2, n3, n4, n5, 0.025f, 0.025f, f7, f8, n, false);
		}
		for (n = 24; n >= 0; --n) {
			addVertexPair(vertexConsumer, matrix4f, f2, f3, f4, n2, n3, n4, n5, 0.025f, 0.0f, f7, f8, n, true);
		}
		poseStack.popPose();
	}


	private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
		float $$15 = (float)p_174321_ / 24.0F;
		int $$16 = (int)Mth.lerp($$15, (float)p_174313_, (float)p_174314_);
		int $$17 = (int)Mth.lerp($$15, (float)p_174315_, (float)p_174316_);
		int $$18 = LightTexture.pack($$16, $$17);
		float $$19 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
		float $$20 = 0.5F * $$19;
		float $$21 = 0.4F * $$19;
		float $$22 = 0.3F * $$19;
		float $$23 = p_174310_ * $$15;
		float $$24 = p_174311_ > 0.0F ? p_174311_ * $$15 * $$15 : p_174311_ - p_174311_ * (1.0F - $$15) * (1.0F - $$15);
		float $$25 = p_174312_ * $$15;
		p_174308_.vertex(p_254405_, $$23 - p_174319_, $$24 + p_174318_, $$25 + p_174320_).color($$20, $$21, $$22, 1.0F).uv2($$18).endVertex();
		p_174308_.vertex(p_254405_, $$23 + p_174319_, $$24 + p_174317_ - p_174318_, $$25 - p_174320_).color($$20, $$21, $$22, 1.0F).uv2($$18).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(MerorReteEntity entity) {
		return TEXTURE;
	}


	@Override
	public boolean shouldRender(MerorReteEntity entity, Frustum frustum, double d, double d2, double d3) {
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
