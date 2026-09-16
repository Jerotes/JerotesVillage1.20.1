package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.*;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelbig_wool_gemsbok;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class BigWoolGemsbokRenderer extends MobRenderer<BigWoolGemsbokEntity, Modelbig_wool_gemsbok<BigWoolGemsbokEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_tame.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_baby.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_horn.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_saddle.png");
	private static final ResourceLocation REIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_rein.png");
	private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/big_wool_gemsbok_chest.png");
	public BigWoolGemsbokRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), 1.2f);
		this.addLayer(new TameLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new BigWoolGemsbokBaseHairLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION))));
		this.addLayer(new BigWoolGemsbokWoolLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION))));
		this.addLayer(new HornLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), HORN_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new ReinLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), REIN_LOCATION));
		this.addLayer(new ChestLayer(this, new Modelbig_wool_gemsbok(context.bakeLayer(Modelbig_wool_gemsbok.LAYER_LOCATION)), CHEST_LOCATION));
	}

	@Override
	protected void scale(BigWoolGemsbokEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.3f, 1.3f, 1.3f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BigWoolGemsbokEntity entity) {
		if (entity.isBaby()) {
			return BABY_LOCATION;
		}
		return LOCATION;
	}
}

