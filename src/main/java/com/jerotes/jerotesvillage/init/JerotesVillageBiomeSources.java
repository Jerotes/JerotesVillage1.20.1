package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.world.biome.SecondRoundWorldBiomeSource;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageBiomeSources {
	public static final DeferredRegister<Codec<? extends BiomeSource>> BIOME_SOURCES =
			DeferredRegister.create(Registries.BIOME_SOURCE, JerotesVillage.MODID);

	public static final RegistryObject<Codec<SecondRoundWorldBiomeSource>> SECOND_ROUND_WORLD =
			BIOME_SOURCES.register("second_round_world", () -> SecondRoundWorldBiomeSource.CODEC);

	public static void register(IEventBus bus) {
		BIOME_SOURCES.register(bus);
	}
}