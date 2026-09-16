package com.jerotes.jerotesvillage.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public interface JerotesVillageMobEffectTags {

	private static TagKey<MobEffect> create(String string) {
		return TagKey.create(Registries.MOB_EFFECT, new ResourceLocation(string));
	}
}
