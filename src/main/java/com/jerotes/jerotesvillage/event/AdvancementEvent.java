package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//1.20.4↑//
//import net.minecraft.advancements.AdvancementHolder;
//1.20.1//
import net.minecraft.advancements.Advancement;


@Mod.EventBusSubscriber
public class AdvancementEvent {
	public static void AdvancementGive(ServerPlayer player, String string) {
		if (AdvancementGet(player, string) != null) {
			AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(AdvancementGet(player, string));
			if (!advancementProgress.isDone()) {
				for (String criteria : advancementProgress.getRemainingCriteria())
					player.getAdvancements().award(AdvancementGet(player, string), criteria);
			}
		}
	}
	public static boolean AdvancementDone(ServerPlayer player, String string) {
		if (AdvancementGet(player, string) != null) {
			AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(AdvancementGet(player, string));
			return advancementProgress.isDone();
		}
		return false;
	}

	//1.20.4↑//
//	public static AdvancementHolder AdvancementGet(ServerPlayer player, String string) {
//		AdvancementHolder advancementHolder = player.server.getAdvancements().get(new ResourceLocation(string));
//		return advancementHolder;
//	}
	//1.20.1//
	public static Advancement AdvancementGet(ServerPlayer player, String string) {
		Advancement advancementHolder = player.server.getAdvancements().getAdvancement(new ResourceLocation(string));
		return advancementHolder;
	}

	@SubscribeEvent
	public static void DeadlyDontChange(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null || livingEntity.level().dimension() != JerotesVillageDimensions.SECOND_ROUND_WORLD)
			return;
		BlockPos blockPos = new BlockPos((int) livingEntity.getX(), (int) livingEntity.getY(), (int) livingEntity.getZ());
		if (livingEntity.level() instanceof ServerLevel serverLevel && serverLevel.isRaided(blockPos)) {
			if (livingEntity instanceof ServerPlayer serverPlayer) {
				AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:deadly_dont_change");
			}
		}
	}
}

