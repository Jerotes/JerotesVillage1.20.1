package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class JerotesVillageBiomes {
    public static final ResourceKey<Biome> BITTER_COLD = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "bitter_cold"));
    public static final ResourceKey<Biome> PURPLE_DESERT = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "purple_desert"));
    public static final ResourceKey<Biome> ENDLESS_OCEAN = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "endless_ocean"));
    public static final ResourceKey<Biome> BRIGHT_GRASSLAND = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "bright_grassland"));
    public static final ResourceKey<Biome> DROUGHT_FIRE = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "drought_fire"));
    public static final ResourceKey<Biome> HARMONIOUS_WONDERLAND = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "harmonious_wonderland"));
    public static final ResourceKey<Biome> SAND_BEACH = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "sand_beach"));
    public static final ResourceKey<Biome> DAMAGED_RUINS = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "damaged_ruins"));
    public static final ResourceKey<Biome> UNKNOWN_DEEP = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "unknown_deep"));
    public static final ResourceKey<Biome> VIRTUAL_CAVE = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "virtual_cave"));
    public static final ResourceKey<Biome> WILDERNESS_WOODLAND = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "wilderness_woodland"));
    public static final ResourceKey<Biome> PLAGUE_ZONE = ResourceKey.create(Registries.BIOME, new ResourceLocation(JerotesVillage.MODID, "plague_zone"));
}
