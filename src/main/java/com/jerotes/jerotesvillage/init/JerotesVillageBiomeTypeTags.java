package com.jerotes.jerotesvillage.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public interface JerotesVillageBiomeTypeTags {
	TagKey<Biome> SERPON_FIND = create("jerotesvillage:serpon_find");

	private static TagKey<Biome> create(String string) {
		return TagKey.create(Registries.BIOME, new ResourceLocation(string));
	}
}
