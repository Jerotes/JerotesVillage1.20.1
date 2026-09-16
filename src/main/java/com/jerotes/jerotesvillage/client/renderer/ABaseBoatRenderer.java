package com.jerotes.jerotesvillage.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatEntity;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ABaseBoatRenderer extends BoatRenderer {
	private final Map<ABaseBoatType, Pair<ResourceLocation, ListModel<Boat>>> modBoatResources;

	public ABaseBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
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

	public ABaseBoatRenderer(EntityRendererProvider.Context renderContext) {
		this(renderContext, false);
	}

	private String getTextureLocation(ABaseBoatType boatType, boolean isChestBoat) {
		return isChestBoat ? "textures/entity/chest_boat/" + boatType.getName() + ".png" : "textures/entity/boat/" + boatType.getName() + ".png";
	}

	private ListModel<Boat> createModBoatModel(EntityRendererProvider.Context renderContext, ABaseBoatType modBoatTypes, boolean isChestBoat) {
		Boat.Type defaultType = Boat.Type.OAK;
		ModelLayerLocation modellayerlocation = isChestBoat ? ModelLayers.createChestBoatModelName(defaultType) : ModelLayers.createBoatModelName(defaultType);
		ModelPart modelpart = renderContext.bakeLayer(modellayerlocation);
		return isChestBoat ? new ChestBoatModel(renderContext.bakeLayer(ModelLayers.createChestBoatModelName(defaultType))) : new BoatModel(renderContext.bakeLayer(ModelLayers.createBoatModelName(defaultType)));
	}

	private ModelLayerLocation createModBoatModelName(ABaseBoatType modBoatTypes) {
		return new ModelLayerLocation(new ResourceLocation("minecraft", "boat/"  + modBoatTypes.getName()), "main");
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
		ABaseBoatEntity moddedBoat = (ABaseBoatEntity) boat;
		return modBoatResources.get(moddedBoat.getModBoatType());
	}
}

