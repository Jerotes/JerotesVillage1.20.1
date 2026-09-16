package com.jerotes.jerotesvillage.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class JerotesVillageBiomeHolders {
    public static List<Holder<Biome>> ALL;
    public static Holder<Biome> BITTER_COLD;
    public static Holder<Biome> PURPLE_DESERT;
    public static Holder<Biome> ENDLESS_OCEAN;
    public static Holder<Biome> BRIGHT_GRASSLAND;
    public static Holder<Biome> DROUGHT_FIRE;
    public static Holder<Biome> HARMONIOUS_WONDERLAND;
    public static Holder<Biome> SAND_BEACH;
    public static Holder<Biome> DAMAGED_RUINS;
    public static Holder<Biome> UNKNOWN_DEEP;
    public static Holder<Biome> VIRTUAL_CAVE;
    public static Holder<Biome> WILDERNESS_WOODLAND;
    public static Holder<Biome> PLAGUE_ZONE;

    public static void bootstrap(RegistryAccess access) {
        Registry<Biome> biomes = access.registryOrThrow(Registries.BIOME);

        BITTER_COLD = biomes.getHolderOrThrow(JerotesVillageBiomes.BITTER_COLD);
        PURPLE_DESERT = biomes.getHolderOrThrow(JerotesVillageBiomes.PURPLE_DESERT);
        ENDLESS_OCEAN = biomes.getHolderOrThrow(JerotesVillageBiomes.ENDLESS_OCEAN);
        BRIGHT_GRASSLAND = biomes.getHolderOrThrow(JerotesVillageBiomes.BRIGHT_GRASSLAND);
        DROUGHT_FIRE = biomes.getHolderOrThrow(JerotesVillageBiomes.DROUGHT_FIRE);
        HARMONIOUS_WONDERLAND = biomes.getHolderOrThrow(JerotesVillageBiomes.HARMONIOUS_WONDERLAND);
        SAND_BEACH = biomes.getHolderOrThrow(JerotesVillageBiomes.SAND_BEACH);
        DAMAGED_RUINS = biomes.getHolderOrThrow(JerotesVillageBiomes.DAMAGED_RUINS);
        UNKNOWN_DEEP = biomes.getHolderOrThrow(JerotesVillageBiomes.UNKNOWN_DEEP);
        VIRTUAL_CAVE = biomes.getHolderOrThrow(JerotesVillageBiomes.VIRTUAL_CAVE);
        WILDERNESS_WOODLAND = biomes.getHolderOrThrow(JerotesVillageBiomes.WILDERNESS_WOODLAND);
        PLAGUE_ZONE = biomes.getHolderOrThrow(JerotesVillageBiomes.PLAGUE_ZONE);

        ALL = List.of(
                BITTER_COLD, PURPLE_DESERT, ENDLESS_OCEAN, BRIGHT_GRASSLAND,
                DROUGHT_FIRE, HARMONIOUS_WONDERLAND, SAND_BEACH, DAMAGED_RUINS,
                UNKNOWN_DEEP, VIRTUAL_CAVE, WILDERNESS_WOODLAND, PLAGUE_ZONE
        );
    }
}
