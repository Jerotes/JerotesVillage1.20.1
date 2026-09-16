package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelwonderland_giant_frog;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class WonderlandGiantFrogRenderer extends MobRenderer<WonderlandGiantFrogEntity, Modelwonderland_giant_frog<WonderlandGiantFrogEntity>> {
	private static final ResourceLocation SPRING_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_spring.png");
	private static final ResourceLocation SUMMER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_summer.png");
	private static final ResourceLocation AUTUMN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_autumn.png");
	private static final ResourceLocation WINTER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_winter.png");

	private static final ResourceLocation BOSS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_fake_boss.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_giant_frog_saddle.png");
	public WonderlandGiantFrogRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwonderland_giant_frog(context.bakeLayer(Modelwonderland_giant_frog.LAYER_LOCATION)), 1.2f);
		this.addLayer(new TameLayer(this, new Modelwonderland_giant_frog(context.bakeLayer(Modelwonderland_giant_frog.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelwonderland_giant_frog(context.bakeLayer(Modelwonderland_giant_frog.LAYER_LOCATION)), SADDLE_LOCATION));
	}

	@Override
	protected boolean isShaking(WonderlandGiantFrogEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity.isFakeBoss()) || super.isShaking(entity);
	}
	@Override
	protected float getFlipDegrees(WonderlandGiantFrogEntity entity) {
		if (entity.isFakeBoss()) {
			return 0.0f;
		}
		return super.getFlipDegrees(entity);
	}

	@Override
	protected void scale(WonderlandGiantFrogEntity entity, PoseStack poseStack, float f) {
		float scale = 1f;
		if (entity.isFakeBoss())
			scale = 4f;
		else if (entity.getFrogType() == 2)
			scale = 0.75f;
		else if (entity.getFrogType() == 3)
			scale = 0.85f;
		else if (entity.getFrogType() == 4)
			scale = 0.8f;
		poseStack.scale(1.5f * scale, 1.5f * scale, 1.5f * scale);
		super.scale(entity, poseStack, f);
	}

	@Override
	public void render(WonderlandGiantFrogEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		float scale = 1f;
		if (entity.isFakeBoss())
			scale = 4f;
		else if (entity.getFrogType() == 2)
			scale = 0.75f;
		else if (entity.getFrogType() == 3)
			scale = 0.85f;
		else if (entity.getFrogType() == 4)
			scale = 0.8f;
		this.shadowRadius = 1.2f * scale;
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	public ResourceLocation getTextureLocation(WonderlandGiantFrogEntity entity) {
		if (entity.isFakeBoss())
			return BOSS_LOCATION;
		if (entity.getFrogType() == 1)
			return SPRING_LOCATION;
		if (entity.getFrogType() == 2)
			return SUMMER_LOCATION;
		if (entity.getFrogType() == 3)
			return AUTUMN_LOCATION;
		if (entity.getFrogType() == 4)
			return WINTER_LOCATION;
		return SPRING_LOCATION;
	}
}

