package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CelestialCoilvineArmoryLayer;
import com.jerotes.jerotesvillage.client.layer.CelestialCoilvineGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelcelestial_coilvine;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CelestialCoilvineRenderer extends MobRenderer<CelestialCoilvineEntity, Modelcelestial_coilvine<CelestialCoilvineEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_glow.png");
	private static final ResourceLocation ARMOR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_armor.png");
	private static final ResourceLocation START_0_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_start_0.png");
	private static final ResourceLocation START_1_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_start_1.png");
	private static final ResourceLocation START_2_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_start_2.png");
	private static final ResourceLocation START_3_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_start_3.png");
	private static final ResourceLocation START_4_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/celestial_coilvine/celestial_coilvine_start_4.png");
	public CelestialCoilvineRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcelestial_coilvine<>(context.bakeLayer(Modelcelestial_coilvine.LAYER_LOCATION)), 2f);
		this.addLayer(new CelestialCoilvineGlowOtherBodyLayer<>(this, new Modelcelestial_coilvine(context.bakeLayer(Modelcelestial_coilvine.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CelestialCoilvineArmoryLayer<>(this, new Modelcelestial_coilvine(context.bakeLayer(Modelcelestial_coilvine.LAYER_LOCATION)), ARMOR_LOCATION));
	}

	@Override
	protected void scale(CelestialCoilvineEntity entity, PoseStack poseStack, float f) {
		if (entity.isHide()) {
			poseStack.scale(0.5f, -0.02f, 0.5f);
		}
		poseStack.scale(4/3f, 4/3f, 4/3f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public void render(CelestialCoilvineEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (t.tickCount < 15 || t.isHide() || !t.isAlive()) {
			this.shadowRadius = 0f;
		}
		else {
			this.shadowRadius = 2f;
		}
		t.thisTickRenderTime += 1;
		float attackAnim = t.getHeadXRotAnim();
		float fs = t.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		t.headXRotAnimProgress = Mth.lerp(smoothFactor, t.headXRotAnimProgress, attackAnim);
		super.render(t, f, f2, poseStack, multiBufferSource, n);
	}
	@Override
	protected float getFlipDegrees(CelestialCoilvineEntity entity) {
		return 0.0f;
	}

	@Override
	protected boolean isShaking(CelestialCoilvineEntity entity) {
		return super.isShaking(entity) || entity.isHide() || entity.getStartTick() < 18 || !entity.isAlive() && entity.deathTime < 35;
	}

	@Override
	public ResourceLocation getTextureLocation(CelestialCoilvineEntity entity) {
		if (entity.isAlive()) {
			if (entity.getStartTick() < 8) {
				return START_0_LOCATION;
			}
			if (entity.getStartTick() < 10) {
				return START_1_LOCATION;
			}
			if (entity.getStartTick() < 12) {
				return START_2_LOCATION;
			}
			if (entity.getStartTick() < 14) {
				return START_3_LOCATION;
			}
			if (entity.getStartTick() < 18) {
				return START_4_LOCATION;
			}
		}
		else {
			if (entity.deathTime < 15) {
				return LOCATION;
			}
			if (entity.deathTime < 20) {
				return START_4_LOCATION;
			}
			if (entity.deathTime < 25) {
				return START_3_LOCATION;
			}
			if (entity.deathTime < 30) {
				return START_2_LOCATION;
			}
			if (entity.deathTime < 35) {
				return START_1_LOCATION;
			}
			if (entity.deathTime < 45) {
				return START_0_LOCATION;
			}
		}
		return LOCATION;
	}
}

