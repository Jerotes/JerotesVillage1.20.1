package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CarvedEnderEyesLayer;
import com.jerotes.jerotesvillage.client.layer.CarvedVillagerHeadLayer;
import com.jerotes.jerotesvillage.client.layer.CarvedVillagerOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid_wide_or_slim;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;

public class CarvedVillagerRenderer extends HumanoidMobRenderer<CarvedVillagerEntity, Modelhumanoid<CarvedVillagerEntity>> {
	private final RandomSource random = RandomSource.create();
	private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager.png");
	private static final ResourceLocation ILLAGER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_illager.png");
	private static final ResourceLocation PIGLIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_piglin.png");
	private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_enderman.png");
	private static final ResourceLocation ENDERMAN_BABY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_enderman_baby.png");

	private static final ResourceLocation JEROTES_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/jerotes.png");
	private static final ResourceLocation JEROTES_SLIM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/jerotes_slim.png");
	private static final ResourceLocation MAID_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/maid.png");
	private static final ResourceLocation MAID_SLIM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/maid_slim.png");
	private static final ResourceLocation SENTNES_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/sentnes.png");
	private static final ResourceLocation SENTNES_SLIM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/sentnes_slim.png");
	public CarvedVillagerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid_wide_or_slim<>(context.bakeLayer(Modelhumanoid_wide_or_slim.LAYER_LOCATION)), 0.5f);
		this.addLayer(new CarvedVillagerOtherBodyLayer(this, context));
		this.addLayer(new CarvedVillagerHeadLayer(this, context));
		this.addLayer(new CarvedEnderEyesLayer<CarvedVillagerEntity>(this));
		if (!OtherMainConfig.CarvedVillagerHelmetHeightAsVillager) {
			this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		}
		else {
			this.addLayer(new HumanoidArmorLayer<>(this, new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
		}
	}

	@Override
	public Vec3 getRenderOffset(CarvedVillagerEntity carvedVillager, float f) {
		if (carvedVillager.isEndermanRace() && !carvedVillager.isSkin() && carvedVillager.isAggressive()) {
			return new Vec3(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
		}
		return super.getRenderOffset(carvedVillager, f);
	}

	@Override
	protected void scale(CarvedVillagerEntity carvedVillager, PoseStack poseStack, float f) {
		if ((!carvedVillager.isEndermanRace() && !carvedVillager.isPiglinRace()) || carvedVillager.isSkin()) {
			poseStack.scale(0.9375f, 0.9375f, 0.9375f);
		}
		else {
			super.scale(carvedVillager, poseStack, f);
		}
	}

	public static String sanitizeString(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		String lowerCase = input.toLowerCase(Locale.ROOT);
		String sanitized = lowerCase.replaceAll("[^a-z0-9_./-]", "_");
		sanitized = sanitized.replaceAll(" ", "_");
		return sanitized;
	}


	@Override
	public ResourceLocation getTextureLocation(CarvedVillagerEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if (entity.getCustomName() != null) {
			if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameWide.contains(string)) {
				return new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/custom_skin/" + sanitizeString(string) + ".png");
			}
			if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameSlim.contains(string)) {
				return new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/custom_skin/" + sanitizeString(string) + ".png");
			}
		}
		if ("Jerotes".equals(string)) {
			if (entity.IsFemale())
				return JEROTES_SLIM_LOCATION;
			return JEROTES_LOCATION;
		}
		if ("Maid".equals(string)) {
			if (entity.IsFemale())
				return MAID_SLIM_LOCATION;
			return MAID_LOCATION;
		}
		if ("Sentnes".equals(string)) {
			if (entity.IsFemale())
				return SENTNES_SLIM_LOCATION;
			return SENTNES_LOCATION;
		}

		String slim = "wide";
		if (entity.IsFemale()) {
			slim = "slim";
		}
		if ("Steve".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/steve.png");
		}
		if ("Alex".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/alex.png");
		}
		if ("Ari".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/ari.png");
		}
		if ("Kai".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/kai.png");
		}
		if ("Noor".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/noor.png");
		}
		if ("Sunny".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/sunny.png");
		}
		if ("Zuri".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/zuri.png");
		}
		if ("Efe".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/efe.png");
		}
		if ("Makena".equals(string)) {
			return new ResourceLocation("minecraft:textures/entity/player/" + slim + "/makena.png");
		}
		if (entity.isEndermanRace()) {
			if (entity.isBaby()) {
				return ENDERMAN_BABY_LOCATION;
			}
			return ENDERMAN_LOCATION;
		}
		if (entity.isIllagerRace()) {
			return ILLAGER_LOCATION;
		}
		if (entity.isPiglinRace()) {
			return PIGLIN_LOCATION;
		}
		return VILLAGER_LOCATION;
	}


}
