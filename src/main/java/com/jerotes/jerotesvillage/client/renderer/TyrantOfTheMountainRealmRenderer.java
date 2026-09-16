package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.*;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modeltyrant_of_the_mountain_realm;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TyrantOfTheMountainRealmRenderer extends MobRenderer<TyrantOfTheMountainRealmEntity, Modeltyrant_of_the_mountain_realm<TyrantOfTheMountainRealmEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm.png");
	private static final ResourceLocation GOLD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_gold.png");
	private static final ResourceLocation LIGHT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_light.png");
	public TyrantOfTheMountainRealmRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeltyrant_of_the_mountain_realm<>(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION)), 1.55f);
		this.addLayer(new TyrantOfTheMountainRealmGoldenSwordLayer<>(this, new Modeltyrant_of_the_mountain_realm(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION)), LIGHT_LOCATION));
		this.addLayer(new TyrantOfTheMountainRealmArmorLayer<>(this, new Modeltyrant_of_the_mountain_realm(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION))));
		this.addLayer(new TyrantOfTheMountainRealmHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new TyrantOfTheMountainRealmItemInHandLayer<>(this,  context.getItemInHandRenderer(), new Modeltyrant_of_the_mountain_realm(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION))));
		this.addLayer(new TyrantOfTheMountainRealmRushBodyLayer<>(this, new Modeltyrant_of_the_mountain_realm(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION)), GOLD_LOCATION));
		this.addLayer(new TyrantOfTheMountainRealmGoldenBodyLayer<>(this, new Modeltyrant_of_the_mountain_realm(context.bakeLayer(Modeltyrant_of_the_mountain_realm.LAYER_LOCATION)), GOLD_LOCATION));
	}

	@Override
	protected void scale(TyrantOfTheMountainRealmEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.3f, 1.3f, 1.3f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(TyrantOfTheMountainRealmEntity entity) {
		return 0.0f;
	}


	@Override
	public ResourceLocation getTextureLocation(TyrantOfTheMountainRealmEntity entity) {
		return LOCATION;
	}
}

