package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelirishtail_chicken;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;

public class IrishtailChickenRenderer extends MobRenderer<Chicken, Modelirishtail_chicken<Chicken>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/irishtail_chicken.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/irishtail_chicken_baby.png");
	public IrishtailChickenRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelirishtail_chicken<>(context.bakeLayer(Modelirishtail_chicken.LAYER_LOCATION)), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(Chicken entity) {
		if (entity.isBaby()) {
			return BABY_LOCATION;
		}
		return LOCATION;
	}


	protected float getBob(Chicken p_114000_, float p_114001_) {
		float $$2 = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
		float $$3 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
		return (Mth.sin($$2) + 1.0F) * $$3;
	}
}

