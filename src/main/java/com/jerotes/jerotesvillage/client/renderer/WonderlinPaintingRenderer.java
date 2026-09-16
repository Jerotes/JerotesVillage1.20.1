package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwonderlin_painting;
import com.jerotes.jerotesvillage.entity.Other.WonderlinPainting.WonderlinPaintingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class WonderlinPaintingRenderer extends EntityRenderer<WonderlinPaintingEntity> {
	public static final ResourceLocation SMALL_FRAME_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/painting/frame/small.png");
	public static final ResourceLocation MEDIUM_FRAME_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/painting/frame/medium.png");
	public static final ResourceLocation LARGE_FRAME_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/painting/frame/large.png");
	public static final ResourceLocation WIDE_FRAME_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/painting/frame/wide.png");
	public static final ResourceLocation TALL_FRAME_TEXTURE = new ResourceLocation(JerotesVillage.MODID, "textures/painting/frame/tall.png");

	public WonderlinPaintingRenderer(EntityRendererProvider.Context p_174332_) {
		super(p_174332_);
	}

	@Override
	public void render(WonderlinPaintingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
		PaintingVariant variant = entity.getVariant().value();
		Direction direction = entity.getDirection();
		int width = variant.getWidth();
		int height = variant.getHeight();
		Modelwonderlin_painting model;
		ResourceLocation frameTexture;
		try (var ignored = new CloseablePoseStack(poseStack)) {


			if (width == 32 && height == 32) {
				model = new Modelwonderlin_painting(modelSet.bakeLayer(Modelwonderlin_painting.MEDIUM_PAINTING));
				frameTexture = MEDIUM_FRAME_TEXTURE;
				poseStack.translate(0.0F, -0.5F, 0.0F);
				if (direction == Direction.NORTH) poseStack.translate(-0.5F, 0.0F, 0.0F);
				if (direction == Direction.SOUTH) poseStack.translate(0.5F, 0.0F, 0.0F);
				if (direction == Direction.EAST) poseStack.translate(0.0F, 0.0F, -0.1F);
				if (direction == Direction.WEST) poseStack.translate(0.0F, 0.0F, 0.1F);
			} else if (width == 48 && height == 32) {
				model = new Modelwonderlin_painting(modelSet.bakeLayer(Modelwonderlin_painting.LARGE_PAINTING));
				frameTexture = LARGE_FRAME_TEXTURE;
				poseStack.translate(0.0F, -0.5F, 0.0F);
				if (direction == Direction.EAST) poseStack.translate(0.0F, 0.0F, 0.4F);
				if (direction == Direction.WEST) poseStack.translate(0.0F, 0.0F, -0.4F);
			} else if (width == 16 && height == 32) {
				model = new Modelwonderlin_painting(modelSet.bakeLayer(Modelwonderlin_painting.TALL_PAINTING));
				frameTexture = TALL_FRAME_TEXTURE;
				poseStack.translate(0.0F, -0.5F, 0.0F);
				if (direction == Direction.EAST) poseStack.translate(0.0F, 0.0F, 0.4F);
				if (direction == Direction.WEST) poseStack.translate(0.0F, 0.0F, -0.4F);
			} else if (width == 32 && height == 16) {
				model = new Modelwonderlin_painting(modelSet.bakeLayer(Modelwonderlin_painting.WIDE_PAINTING));
				frameTexture = WIDE_FRAME_TEXTURE;
				if (direction == Direction.NORTH) poseStack.translate(-0.4F, 0.0F, 0.0F);
				if (direction == Direction.SOUTH) poseStack.translate(0.4F, 0.0F, 0.0F);
			} else {
				model = new Modelwonderlin_painting(modelSet.bakeLayer(Modelwonderlin_painting.SMALL_PAINTING));
				frameTexture = SMALL_FRAME_TEXTURE;
				if (direction == Direction.EAST) poseStack.translate(0.0F, 0.0F, 0.4F);
				if (direction == Direction.WEST) poseStack.translate(0.0F, 0.0F, -0.4F);
			}

			switch (direction) {
				case NORTH -> poseStack.translate(0.0F, 0.0F, -1/32f);
				case EAST -> poseStack.translate(1/32f, 0.0F, -0.4F);
				case SOUTH -> poseStack.translate(0.0F, 0.0F, 1/32f);
				case WEST -> poseStack.translate(-1/32f, 0.0F, 0.4F);
			}


			poseStack.translate(0.0F, 0.875F, 0.0F);
			poseStack.mulPose(Axis.YN.rotationDegrees(direction.getOpposite().toYRot()));
			poseStack.mulPose(Axis.XP.rotationDegrees(180));
			model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entitySolid(frameTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

			poseStack.translate(0.0F, 0.0F, 0.0005F);
			poseStack.scale(width / 16.0F, height / 16.0F, 1.0F);

			if (width == 32 && height == 32) {
				poseStack.translate(12.0F / 16.0F, -5F / 16.0F, 0.0F);
			} else if (width == 48 && height == 32) {
				poseStack.translate(8.0F / 16.0F, -5F / 16.0F, 0.0F);
			} else if (width == 16 && height == 32) {
				poseStack.translate(8.0F / 16.0F, -5F / 16.0F, 0.0F);
			} else if (width == 32 && height == 16) {
				poseStack.translate(12.0F / 16.0F, 6.0F / 16.0F, 0.0F);
			} else {
				poseStack.translate(8.0F / 16.0F, 6.0F / 16.0F, 0.0F);
			}

			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
			renderPainting(poseStack, vertexConsumer, direction, packedLight, OverlayTexture.NO_OVERLAY);
		}
	}

	private static void renderPainting(PoseStack poseStack, VertexConsumer consumer, Direction dir, int light, int overlay) {
		Vec3i normal = dir.getNormal();
		consumer.vertex(poseStack.last().pose(), 0, 0, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(overlay).uv2(light).normal(poseStack.last().normal(), normal.getX(), normal.getY(), normal.getZ()).endVertex();
		consumer.vertex(poseStack.last().pose(), 0, 1, 0).color(255, 255, 255, 255).uv(0, 1).overlayCoords(overlay).uv2(light).normal(poseStack.last().normal(), normal.getX(), normal.getY(), normal.getZ()).endVertex();
		consumer.vertex(poseStack.last().pose(), -1, 1, 0).color(255, 255, 255, 255).uv(1, 1).overlayCoords(overlay).uv2(light).normal(poseStack.last().normal(), normal.getX(), normal.getY(), normal.getZ()).endVertex();
		consumer.vertex(poseStack.last().pose(), -1, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(overlay).uv2(light).normal(poseStack.last().normal(), normal.getX(), normal.getY(), normal.getZ()).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(WonderlinPaintingEntity entity) {
		return new ResourceLocation(JerotesVillage.MODID, "textures/painting/" + BuiltInRegistries.PAINTING_VARIANT.getKey(entity.getVariant().value()).getPath() + ".png");
	}

	public class CloseablePoseStack implements AutoCloseable {

		private final PoseStack stack;

		public CloseablePoseStack(PoseStack stack) {
			this.stack = stack;
			this.stack.pushPose();
		}

		public void translate(double d, double e, double f) {
			stack.translate(d, e, f);
		}

		public void scale(float f, float g, float h) {
			stack.scale(f, g, h);
		}

		public void mulPose(Quaternionf quaternion) {
			stack.mulPose(quaternion);
		}

		public PoseStack.Pose last() {
			return stack.last();
		}

		public boolean clear() {
			return stack.clear();
		}

		@Override
		public void close() {
			stack.popPose();
		}
	}
}