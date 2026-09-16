package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.HornLayer;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.WildernessBullArmorLayer;
import com.jerotes.jerotesvillage.client.model.Modelwilderness_bull;
import com.jerotes.jerotesvillage.entity.Animal.WildernessBullEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class WildernessBullRenderer extends MobRenderer<WildernessBullEntity, Modelwilderness_bull<WildernessBullEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_bull.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_bull_tame.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_bull_baby.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_bull_horn.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_bull_saddle.png");
	public WildernessBullRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwilderness_bull(context.bakeLayer(Modelwilderness_bull.LAYER_LOCATION)), 1.1f);
		this.addLayer(new TameLayer(this, new Modelwilderness_bull(context.bakeLayer(Modelwilderness_bull.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new HornLayer(this, new Modelwilderness_bull(context.bakeLayer(Modelwilderness_bull.LAYER_LOCATION)), HORN_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelwilderness_bull(context.bakeLayer(Modelwilderness_bull.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new WildernessBullArmorLayer(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(WildernessBullEntity entity) {
		if (entity.isBaby()) {
			return BABY_LOCATION;
		}
		return LOCATION;
	}
}

