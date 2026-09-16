package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotesvillage.event.AdvancementEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class BossSpawn {
	//中期boss限制
	public static boolean ChallengeMiddleBoss(ServerPlayer player) {
		boolean corrosiver = AdvancementEvent.AdvancementDone(player, "jerotesvillage:snowstorm");
		boolean purpleSandHag = AdvancementEvent.AdvancementDone(player, "jerotesvillage:want_to_escape");
		boolean gemstoneMalignasaur = AdvancementEvent.AdvancementDone(player, "jerotesvillage:lepidosauria");
		boolean brightLandBeast = AdvancementEvent.AdvancementDone(player, "jerotesvillage:stupid_but_brave");

		boolean serponCombatExecuter = AdvancementEvent.AdvancementDone(player, "jerotesvillage:deportation");
		boolean celestialCoilvine = AdvancementEvent.AdvancementDone(player, "jerotesvillage:plot");

		return corrosiver || purpleSandHag || gemstoneMalignasaur || brightLandBeast || serponCombatExecuter || celestialCoilvine;
	}
	//后期boss限制
	public static boolean ChallengeLastBoss(ServerPlayer player) {
		return false;
	}


	public static void SedimentLord(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.sediment_lord", player.getDisplayName()).withStyle(ChatFormatting.DARK_AQUA));
	}
	public static void SedimentLordNoPlayer(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.sediment_lord_no_player").withStyle(ChatFormatting.DARK_AQUA));
	}
	public static void ProsperousEnvoy(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.prosperous_envoy", player.getDisplayName()).withStyle(ChatFormatting.GREEN));
	}
	public static void OminousBannerProjection(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.ominous_banner_projection", player.getDisplayName()).withStyle(ChatFormatting.DARK_RED));
	}
	public static void TyrantOfTheMountainRealm(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.tyrant_of_the_mountain_realm", player.getDisplayName()).withStyle(ChatFormatting.GREEN));
	}
	public static void CelestialCoilvine(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.celestial_coilvine", player.getDisplayName()).withStyle(ChatFormatting.GREEN));
	}

	public static void Corrosiver(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.corrosiver", player.getDisplayName()).withStyle(ChatFormatting.WHITE));
	}
	public static void PurpleSandHag(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.purple_sand_hag", player.getDisplayName()).withStyle(ChatFormatting.LIGHT_PURPLE));
	}
	public static void GemstoneMalignasaur(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.gemstone_malignasaur", player.getDisplayName()).withStyle(ChatFormatting.BLUE));
	}
	public static void BrightLandBeast(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.bright_land_beast", player.getDisplayName()).withStyle(ChatFormatting.DARK_RED));
	}
	public static void SerponCombatExecuter(Player player) {
		player.sendSystemMessage(Component.translatable("boss.jerotesvillage.serpon_combat_executer", player.getDisplayName()).withStyle(ChatFormatting.GOLD));
	}
}
