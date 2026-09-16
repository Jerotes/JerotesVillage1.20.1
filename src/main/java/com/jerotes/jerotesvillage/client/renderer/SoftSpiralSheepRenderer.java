package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.HornLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SoftSpiralSheepBaseHairLayer;
import com.jerotes.jerotesvillage.client.layer.SoftSpiralSheepWoolLayer;
import com.jerotes.jerotesvillage.client.model.Modelsoft_spiral_sheep;
import com.jerotes.jerotesvillage.entity.Animal.SoftSpiralSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SoftSpiralSheepRenderer extends MobRenderer<SoftSpiralSheepEntity, Modelsoft_spiral_sheep<SoftSpiralSheepEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/soft_spiral_sheep.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/soft_spiral_sheep_baby.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/soft_spiral_sheep_horn.png");
	public SoftSpiralSheepRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsoft_spiral_sheep(context.bakeLayer(Modelsoft_spiral_sheep.LAYER_LOCATION)), 0.7f);
		this.addLayer(new SoftSpiralSheepBaseHairLayer<>(this, new Modelsoft_spiral_sheep(context.bakeLayer(Modelsoft_spiral_sheep.LAYER_LOCATION))));
		this.addLayer(new SoftSpiralSheepWoolLayer<>(this, new Modelsoft_spiral_sheep(context.bakeLayer(Modelsoft_spiral_sheep.LAYER_LOCATION))));
		this.addLayer(new HornLayer<>(this, new Modelsoft_spiral_sheep(context.bakeLayer(Modelsoft_spiral_sheep.LAYER_LOCATION)), HORN_LOCATION));
	}

	@Override
	public ResourceLocation getTextureLocation(SoftSpiralSheepEntity entity) {
		if (entity.isBaby()) {
			return BABY_LOCATION;
		}
		return LOCATION;
	}
}

