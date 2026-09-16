package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbrown_venomous_slug;
import com.jerotes.jerotesvillage.entity.Animal.BrownVenomousSlugEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrownVenomousSlugRenderer extends MobRenderer<BrownVenomousSlugEntity, Modelbrown_venomous_slug<BrownVenomousSlugEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/brown_venomous_slug.png");
	private static final ResourceLocation POISON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/brown_venomous_slug_poison.png");
	public BrownVenomousSlugRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbrown_venomous_slug(context.bakeLayer(Modelbrown_venomous_slug.LAYER_LOCATION)), 0.2f);
	}

	@Override
	public ResourceLocation getTextureLocation(BrownVenomousSlugEntity entity) {
		if (entity.getPoisonTick() > 0) {
			return POISON_LOCATION;
		}
		return LOCATION;
	}
}