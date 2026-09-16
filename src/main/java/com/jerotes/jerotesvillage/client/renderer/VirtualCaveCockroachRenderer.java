package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.layer.VirtualCaveCockroachRandomColorLayer;
import com.jerotes.jerotesvillage.client.model.Modelbright_land_beast;
import com.jerotes.jerotesvillage.client.model.Modelvirtual_cave_cockroach;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.VirtualCaveCockroachEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class VirtualCaveCockroachRenderer extends MobRenderer<VirtualCaveCockroachEntity, Modelvirtual_cave_cockroach<VirtualCaveCockroachEntity>> {
	private static final ResourceLocation LOCATION_SMALL = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_small.png");
	private static final ResourceLocation LOCATION_MEDIUM = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_medium.png");
	private static final ResourceLocation LOCATION_LARGE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_large.png");
	private static final ResourceLocation LOCATION_GIANT = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_giant.png");
	private static final ResourceLocation LOCATION_SMALL_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_small_baby.png");
	private static final ResourceLocation LOCATION_MEDIUM_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_medium_baby.png");
	private static final ResourceLocation LOCATION_LARGE_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_large_baby.png");
	private static final ResourceLocation LOCATION_GIANT_BABY = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_giant_baby.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_saddle.png");
	private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_chest.png");
	private static final ResourceLocation FRIEND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/virtual_cave_cockroach/virtual_cave_cockroach_friend.png");
	public VirtualCaveCockroachRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelvirtual_cave_cockroach(context.bakeLayer(Modelvirtual_cave_cockroach.LAYER_LOCATION)), 0.3f);
		this.addLayer(new VirtualCaveCockroachRandomColorLayer<>(this, new Modelvirtual_cave_cockroach<>(context.bakeLayer(Modelvirtual_cave_cockroach.LAYER_LOCATION))));
		this.addLayer(new SaddleLayer(this, new Modelvirtual_cave_cockroach(context.bakeLayer(Modelvirtual_cave_cockroach.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new ChestLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), CHEST_LOCATION));
	}

	@Override
	protected float getFlipDegrees(VirtualCaveCockroachEntity entity) {
		return 180.0f;
	}

	@Override
	public void render(VirtualCaveCockroachEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		this.shadowRadius = 0.1f * entity.getSize();
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(VirtualCaveCockroachEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(entity.getSize() * 0.1f, entity.getSize() * 0.1f, entity.getSize() * 0.1f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(VirtualCaveCockroachEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if (OtherMainConfig.VirtualCaveCockroachCute || "Insectophobia".equals(string)) {
			return FRIEND_LOCATION;
		}
		if (entity.getSizeLike() > 3) {
			if (entity.isBaby()) {
				return LOCATION_GIANT_BABY;
			}
			return LOCATION_GIANT;
		}
		if (entity.getSizeLike() > 2) {
			if (entity.isBaby()) {
				return LOCATION_LARGE_BABY;
			}
			return LOCATION_LARGE;
		}
		if (entity.getSizeLike() > 1) {
			if (entity.isBaby()) {
				return LOCATION_MEDIUM_BABY;
			}
			return LOCATION_MEDIUM;
		}
		if (entity.isBaby()) {
			return LOCATION_SMALL_BABY;
		}
		return LOCATION_SMALL;
	}
}

