package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageGameRules {
	public static GameRules.Key<GameRules.BooleanValue> JEROTES_VILLAGE_BRIGHT_LAND_BEAST_TO_GIANT_ENTITY;
	public static GameRules.Key<GameRules.BooleanValue> JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR;
	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		JEROTES_VILLAGE_BRIGHT_LAND_BEAST_TO_GIANT_ENTITY = GameRules.register("JerotesVillageBrightLandBeastToGiantEntity", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
		JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR = GameRules.register("JerotesVillageSomeEliteHasBossBar", GameRules.Category.MOBS, GameRules.BooleanValue.create(OtherMainConfig.SomeEliteHasBossBarBaseInGameRule));
	}
}
