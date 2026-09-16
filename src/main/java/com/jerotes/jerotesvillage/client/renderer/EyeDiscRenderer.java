package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.EyeDiscLayer;
import com.jerotes.jerotesvillage.client.model.Modeleye_disc;
import com.jerotes.jerotesvillage.entity.Monster.EyeDiscEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class EyeDiscRenderer extends MobRenderer<EyeDiscEntity, Modeleye_disc<EyeDiscEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/eye_disc.png");
	private static final ResourceLocation SHOOT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/eye_disc_shoot.png");
	private static final ResourceLocation BLIND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/eye_disc_blind.png");
	private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/eye_disc_glow.png");
	public EyeDiscRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeleye_disc(context.bakeLayer(Modeleye_disc.LAYER_LOCATION)), 0f);
		this.addLayer(new EyeDiscLayer<>(this, new Modeleye_disc(context.bakeLayer(Modeleye_disc.LAYER_LOCATION))));
	}

	@Override
	protected void scale(EyeDiscEntity entity, PoseStack poseStack, float f) {
		float xMax = 1.75f;
		float yMax = 1.75f;
		float zMax = 1.75f;
		float xMin = 0.75f;
		float yMin = 0.75f;
		float zMin = 0.75f;
		int maxTick = 40;
		int tickX = entity.tickCount % maxTick;
		int tickY = (entity.tickCount + (maxTick/4)) % maxTick;
		int tickZ = (entity.tickCount + (maxTick/8)) % maxTick;
		float x = 1.25f;
		float y = 1.25f;
		float z = 1.25f;

		if (!entity.isBlind() && entity.getShootTick() > 0){
			if (tickX > maxTick / 2f) {
				x = xMin + tickX * ((xMax - xMin) / (maxTick));
			} else {
				x = xMax - tickX * ((xMax - xMin) / (maxTick));
			}
			if (tickY > maxTick / 2f) {
				y = yMin + tickY * ((yMax - yMin) / (maxTick));
			} else {
				y = yMax - tickY * ((yMax - yMin) / (maxTick));
			}
			if (tickZ > maxTick / 2f) {
				z = zMin + tickZ * ((zMax - zMin) / (maxTick));
			} else {
				z = zMax - tickZ * ((zMax - zMin) / (maxTick));
			}
		}

		poseStack.scale(x, y, z);
//		if (!entity.isBlind() && entity.getShootTick() > 0) {
//			poseStack.scale(1.25f, 1.25f, 1.25f);
//		}
//		else {
//			poseStack.scale(1.25f, 1.25f, 1.25f);
//		}
		super.scale(entity, poseStack, f);
	}

	@Override
	public void render(EyeDiscEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (!entity.isBlind() && entity.getShootTick() > 0){
            int spellLevel = entity.getSpellLevelOf();
			for (int i = 0; i < spellLevel; ++i) {
				poseStack.pushPose();
				poseStack.translate(
						spellLevel * 0.5f * (entity.getRandom().nextFloat() - 0.5f),
						spellLevel * 0.5f * (entity.getRandom().nextFloat() - 0.5f),
						spellLevel * 0.5f * (entity.getRandom().nextFloat() - 0.5f)
				);
				super.render(entity, f, f2, poseStack, multiBufferSource, n);
				poseStack.popPose();
			}
			super.render(entity, f, f2, poseStack, multiBufferSource, n);
		} else {
			super.render(entity, f, f2, poseStack, multiBufferSource, n);
		}
	}

	@Override
	protected int getBlockLightLevel(EyeDiscEntity entity, BlockPos blockPos) {
		if (!entity.isBlind() && entity.getShootTick() > 0){
			return 12;
		}
		return super.getBlockLightLevel(entity, blockPos);
	}

	@Override
	public ResourceLocation getTextureLocation(EyeDiscEntity entity) {
		if (entity.isBlind()){
			return BLIND_LOCATION;
		}
		if (entity.getShootTick() > 0){
			return SHOOT_LOCATION;
		}
		return LOCATION;
	}
}