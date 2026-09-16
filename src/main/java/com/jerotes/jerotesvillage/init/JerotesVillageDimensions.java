package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class JerotesVillageDimensions {
    public static final ResourceKey<Level> SECOND_ROUND_WORLD = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(JerotesVillage.MODID, "second_round_world"));
}
