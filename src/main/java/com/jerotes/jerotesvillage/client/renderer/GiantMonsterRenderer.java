package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.*;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelgiant_monster;
import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class GiantMonsterRenderer extends MobRenderer<GiantMonsterEntity, Modelgiant_monster<GiantMonsterEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster.png");
	private static final ResourceLocation NO_HAIR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_no_hair.png");
	private static final ResourceLocation ILLAGER_FACTION_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_illager_faction.png");
	private static final ResourceLocation NO_HAIR_ILLAGER_FACTION_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_no_hair_illager_faction.png");
	private static final ResourceLocation BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_baby.png");
	private static final ResourceLocation BABY_ILLAGER_FACTION_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_baby_illager_faction.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_tame.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_horn.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_saddle.png");
	private static final ResourceLocation CHAIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_chain.png");
	private static final ResourceLocation BELL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_bell.png");
	private static final ResourceLocation BITTER_COLD_BELL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/giant_monster/giant_monster_bitter_cold_bell.png");
	public GiantMonsterRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), 1.8f);
		this.addLayer(new TameLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new HornLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), HORN_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new GiantMonsterChainLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), CHAIN_LOCATION));
		this.addLayer(new GiantMonsterBellLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), BELL_LOCATION));
		this.addLayer(new GiantMonsterBitterColdBellLayer(this, new Modelgiant_monster(context.bakeLayer(Modelgiant_monster.LAYER_LOCATION)), BITTER_COLD_BELL_LOCATION));
		this.addLayer(new GiantMonsterArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(GiantMonsterEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.4f, 1.4f, 1.4f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(GiantMonsterEntity entity) {
		if (entity.isBaby()) {
			if (entity.isIllagerFaction()) {
				return BABY_ILLAGER_FACTION_LOCATION;
			}
			return BABY_LOCATION;
		}
		if (entity.isNoHair()) {
			if (entity.isIllagerFaction()) {
				return NO_HAIR_ILLAGER_FACTION_LOCATION;
			}
			return NO_HAIR_LOCATION;
		}
		if (entity.isIllagerFaction()) {
			return ILLAGER_FACTION_LOCATION;
		}
		return LOCATION;
	}
}

