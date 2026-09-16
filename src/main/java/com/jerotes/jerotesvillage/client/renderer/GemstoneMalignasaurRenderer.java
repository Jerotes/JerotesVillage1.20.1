package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GemstoneMalignasaurGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.BeastToughHurtLayer;
import com.jerotes.jerotesvillage.client.model.Modelgemstone_malignasaur;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GemstoneMalignasaurRenderer extends MobRenderer<GemstoneMalignasaurEntity, Modelgemstone_malignasaur<GemstoneMalignasaurEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_malignasaur.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/malignasaur_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/malignasaur_saddle.png");
	private static final ResourceLocation LOCATION_ARMOR = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_malignasaur_armor.png");
	private static final ResourceLocation HURT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_malignasaur_hurt.png");
	public GemstoneMalignasaurRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), 4.5f);
		this.addLayer(new GemstoneMalignasaurGlowOtherBodyLayer<>(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION))));
		this.addLayer(new BeastToughHurtLayer(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), HURT_LOCATION));
		this.addLayer(new TameLayer(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), SADDLE_LOCATION));
	}

	@Override
	protected void scale(GemstoneMalignasaurEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(2f, 2f, 2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public void render(GemstoneMalignasaurEntity gemstoneMalignasaur, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		gemstoneMalignasaur.thisTickRenderTime += 1;
		float waterAnim = gemstoneMalignasaur.getWaterAnim();
		float fs = gemstoneMalignasaur.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		gemstoneMalignasaur.waterAnimProgress = Mth.lerp(smoothFactor, gemstoneMalignasaur.waterAnimProgress, waterAnim);
		super.render(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);

		if (gemstoneMalignasaur.getHungerOf() < -10000) {
			renders(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);
		}
		//饥饿4
		if (gemstoneMalignasaur.getHungerOf() < -6000) {
			renders(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);
		}
		//饥饿3
		if (gemstoneMalignasaur.getHungerOf() < -3000) {
			renders(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);
		}
		//饥饿2
		if (gemstoneMalignasaur.getHungerOf() < -1000) {
			renders(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);
		}
	}

	public void renders(GemstoneMalignasaurEntity gemstoneMalignasaur, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		poseStack.pushPose();
		float f3 = 0.25f;
		poseStack.translate((gemstoneMalignasaur.getRandom().nextFloat() - 0.5f) * f3, (gemstoneMalignasaur.getRandom().nextFloat() - 0.5f) * f3, (gemstoneMalignasaur.getRandom().nextFloat() - 0.5f) * f3);
		super.render(gemstoneMalignasaur, f, f2, poseStack, multiBufferSource, n);
		poseStack.popPose();
	}

	@Override
	protected void setupRotations(GemstoneMalignasaurEntity gemstoneMalignasaur, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(gemstoneMalignasaur, poseStack, f, f2, f3);

		if ((double)gemstoneMalignasaur.walkAnimation.speed() < 0.01) {
			return;
		}

		float f4 = 13f;
		float f7 = 3.25f;
		if (gemstoneMalignasaur.getHungerOf() < -10000) {
			f4 = 5f;
			f7 = 1.25f;
		}
		//饥饿4
		else if (gemstoneMalignasaur.getHungerOf() < -6000) {
			f4 = 8f;
			f7 = 1.75f;
		}
		//饥饿3
		else if (gemstoneMalignasaur.getHungerOf() < -3000) {
			f4 = 8f;
			f7 = 2.25f;
		}
		//饥饿2
		else if (gemstoneMalignasaur.getHungerOf() < -1000) {
			f7 = 2.75f;
		}
		//饥饿1
		else if (gemstoneMalignasaur.getHungerOf() < 0) {
			f7 = 3f;
		}

		float f5 = gemstoneMalignasaur.walkAnimation.position(f3) + 6.0f;
		float f6 = (Math.abs(f5 % f4 - f4/2) - f7) / f7;
		poseStack.mulPose(Axis.ZP.rotationDegrees(f4/2 * f6));
	}

	@Override
	protected float getFlipDegrees(GemstoneMalignasaurEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(GemstoneMalignasaurEntity entity) {
		if (entity.isArmor()){
			return LOCATION_ARMOR;
		}
		return LOCATION;
	}
}
