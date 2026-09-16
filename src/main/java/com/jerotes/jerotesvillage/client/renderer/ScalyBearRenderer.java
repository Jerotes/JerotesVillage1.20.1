package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.layer.GiantMonsterArmorLayer;
import com.jerotes.jerotesvillage.client.layer.ScalyBearArmorLayer;
import com.jerotes.jerotesvillage.client.model.Modelscaly_bear;
import com.jerotes.jerotesvillage.entity.Animal.ScalyBearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class ScalyBearRenderer extends MobRenderer<ScalyBearEntity, Modelscaly_bear<ScalyBearEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_tame.png");
	private static final ResourceLocation SCALY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_scaly.png");
	private static final ResourceLocation SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_snow.png");
	private static final ResourceLocation SCALY_SNOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_scaly_snow.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_saddle.png");
	public ScalyBearRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelscaly_bear(context.bakeLayer(Modelscaly_bear.LAYER_LOCATION)), 1.2f);
		this.addLayer(new TameLayer(this, new Modelscaly_bear(context.bakeLayer(Modelscaly_bear.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelscaly_bear(context.bakeLayer(Modelscaly_bear.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new ScalyBearArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(ScalyBearEntity entity, PoseStack poseStack, float f) {
		if (entity.isSnow()) {
			poseStack.scale(1.4f, 1.4f, 1.4f);
		}
		else {
			poseStack.scale(1.3f, 1.3f, 1.3f);
		}
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(ScalyBearEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("First".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_first.png");
		}
		if ("Second".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_second.png");
		}
		if (!entity.isNoScale()) {
			if (entity.isSnow())
				return SCALY_SNOW_LOCATION;
			return SCALY_LOCATION;
		}
		if (entity.isSnow())
			return SNOW_LOCATION;
		return LOCATION;
	}
}

