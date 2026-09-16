package com.jerotes.jerotesvillage.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseChestBoatEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ABaseChestBoatRenderer extends BoatRenderer {
	private final Map<ABaseBoatType, Pair<ResourceLocation, ListModel<Boat>>> modBoatResources;

	public ABaseChestBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
		super(renderContext, isChestBoot);
		modBoatResources = Stream.of(ABaseBoatType.values()).collect(ImmutableMap.toImmutableMap((boatType) -> {
			return boatType;
		}, (boatType) -> {
			return Pair.of(
					new ResourceLocation(JerotesVillage.MODID, getTextureLocation(boatType, isChestBoot)),
					createModBoatModel(renderContext, boatType, isChestBoot)
			);
		}));
	}

	public ABaseChestBoatRenderer(EntityRendererProvider.Context renderContext) {
		this(renderContext, true);
	}

	private String getTextureLocation(ABaseBoatType boatType, boolean isChestBoat) {
		return isChestBoat ? "textures/entity/chest_boat/" + boatType.getName() + ".png" : "textures/entity/boat/" + boatType.getName() + ".png";
	}

	private ListModel<Boat> createModBoatModel(EntityRendererProvider.Context renderContext, ABaseBoatType modBoatTypes, boolean isChestBoat) {
		Boat.Type defaultType = Boat.Type.OAK;
		ModelLayerLocation modellayerlocation = ModelLayers.createChestBoatModelName(defaultType);
		ModelPart modelpart = renderContext.bakeLayer(modellayerlocation);
		return new ChestBoatModel(renderContext.bakeLayer(ModelLayers.createChestBoatModelName(defaultType)));
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
		ABaseChestBoatEntity moddedBoat = (ABaseChestBoatEntity) boat;
		return modBoatResources.get(moddedBoat.getModBoatType());
	}
}

