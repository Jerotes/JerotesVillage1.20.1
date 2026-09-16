package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.world.region.WorldSeedHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "jerotesvillage")
public class SeedCaptureHandler {
	@SubscribeEvent
	public static void onLevelLoad(LevelEvent.Load event) {
		if (event.getLevel() instanceof ServerLevel level) {
			WorldSeedHolder.set(level.getSeed());
		}
	}
}