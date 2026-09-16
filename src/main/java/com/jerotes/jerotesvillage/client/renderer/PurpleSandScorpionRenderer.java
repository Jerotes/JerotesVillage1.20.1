package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelpurple_sand_scorpion;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandScorpionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class PurpleSandScorpionRenderer extends MobRenderer<PurpleSandScorpionEntity, Modelpurple_sand_scorpion<PurpleSandScorpionEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_scorpion.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_scorpion_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_scorpion_saddle.png");
	public PurpleSandScorpionRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpurple_sand_scorpion(context.bakeLayer(Modelpurple_sand_scorpion.LAYER_LOCATION)), 1.0f);
		this.addLayer(new TameLayer(this, new Modelpurple_sand_scorpion(context.bakeLayer(Modelpurple_sand_scorpion.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelpurple_sand_scorpion(context.bakeLayer(Modelpurple_sand_scorpion.LAYER_LOCATION)), SADDLE_LOCATION));
	}

	@Override
	protected float getFlipDegrees(PurpleSandScorpionEntity entity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(PurpleSandScorpionEntity entity) {
		return LOCATION;
	}
}

