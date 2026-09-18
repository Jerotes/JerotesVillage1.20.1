package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.*;
import com.jerotes.jerotesvillage.entity.Boss.Biome.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, value = Dist.CLIENT)
public class BossBarEvent {
	public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
	private static final ResourceLocation BASE_LOCATION = new ResourceLocation(JerotesVillage.MODID,"textures/gui/new_bars.png");
	private static final ResourceLocation BASE_LOCATION_OLD = new ResourceLocation(JerotesVillage.MODID,"textures/gui/bars.png");

	@SubscribeEvent
	public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
		ResourceLocation barLocation;
		LerpingBossEvent bossEvent = event.getBossEvent();
		Mob boss = null;
		int color = 16777215;
		int colorBar = 1;
		if (!BOSSES.isEmpty()) {
			for (Mob mob : BOSSES) {
				if (mob.getUUID().equals(bossEvent.getId())) {
					boss = mob;
					break;
				}
			}
			if (boss != null) {
				Component bossDesc = null;
				if (boss instanceof JerotesEntity jerotesEntity && jerotesEntity.isExalted()) {
					bossDesc = Component.translatable("boss.jerotes.exalted");
				}
				else if (boss instanceof JerotesEntity jerotesEntity && jerotesEntity.isLegendary()) {
					bossDesc = Component.translatable("boss.jerotes.legendary");
				}
				if (boss instanceof SedimentLordEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/sediment_lord_progress.png");
					color = OtherMainConfig.SedimentLordBossBarNameColor;
					colorBar = OtherMainConfig.SedimentLordBossBarColor;
				}
				else if (boss instanceof EagleBowEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/eagle_bow_progress.png");
					color = OtherMainConfig.EagleBowBossBarNameColor;
					colorBar = OtherMainConfig.EagleBowBossBarColor;
				}
				else if (boss instanceof ProsperousEnvoyEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/prosperous_envoy_progress.png");
					color = OtherMainConfig.ProsperousEnvoyBossBarNameColor;
					colorBar = OtherMainConfig.ProsperousEnvoyBossBarColor;
				}
				else if (boss instanceof OminousBannerProjectionEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/ominous_banner_projection_progress.png");
					color = OtherMainConfig.OminousBannerProjectionBossBarNameColor;
					colorBar = OtherMainConfig.OminousBannerProjectionBossBarColor;
				}
				else if (boss instanceof TyrantOfTheMountainRealmEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/tyrant_of_the_mountain_realm_progress.png");
					color = OtherMainConfig.TyrantOfTheMountainRealmBossBarNameColor;
					colorBar = OtherMainConfig.TyrantOfTheMountainRealmBossBarColor;
				}
				else if (boss instanceof CorrosiverEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/corrosiver_progress.png");
					color = OtherMainConfig.CorrosiverBossBarNameColor;
					colorBar = OtherMainConfig.CorrosiverBossBarColor;
				}
				else if (boss instanceof PurpleSandHagEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/purple_sand_hag_progress.png");
					color = OtherMainConfig.PurpleSandHagBossBarNameColor;
					colorBar = OtherMainConfig.PurpleSandHagBossBarColor;
				}
				else if (boss instanceof GemstoneMalignasaurEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/gemstone_malignasaur_progress.png");
					color = OtherMainConfig.GemstoneMalignasaurBossBarNameColor;
					colorBar = OtherMainConfig.GemstoneMalignasaurBossBarColor;
				}
				else if (boss instanceof BrightLandBeastEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/bright_land_beast_progress.png");
					color = OtherMainConfig.BrightLandBeastBossBarNameColor;
					colorBar = OtherMainConfig.BrightLandBeastBossBarColor;
				}
				else if (boss instanceof SerponCombatExecuterEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/boss_bar/serpon_combat_executer_progress.png");
					color = OtherMainConfig.SerponCombatExecuterBossBarNameColor;
					colorBar = OtherMainConfig.SerponCombatExecuterBossBarColor;
				}
				else if (boss instanceof CelestialCoilvineEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/celestial_coilvine_progress.png");
					color = OtherMainConfig.CelestialCoilvineBossBarNameColor;
					colorBar = OtherMainConfig.CelestialCoilvineBossBarColor;
				}
				else if (boss instanceof OldSecondRounderGolemEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/second_rounder_golem_progress.png");
					color = OtherMainConfig.SecondRounderGolemBossBarNameColor;
					colorBar = OtherMainConfig.SecondRounderGolemBossBarColor;
				}
				else if (boss instanceof OldVariantZsieinEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/variant_zsiein_progress.png");
					color = 1;
					colorBar = 6;
				}
				else {
					return;
				}
				RenderSystem.setShaderTexture(0, BASE_LOCATION_OLD);
				drawBar(event.getGuiGraphics(), event.getX(), event.getY(), bossEvent, barLocation, colorBar);
				Component component = bossEvent.getName();
				int textWidth = Minecraft.getInstance().font.width(component);
				int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
				int textY = event.getY() - 9;
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, textX, textY, color);
				if (bossDesc != null) {
					int descWidth = Minecraft.getInstance().font.width(bossDesc);
					int descX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - descWidth / 2;
					int descY = event.getY() + 5;
					event.getGuiGraphics().drawString(Minecraft.getInstance().font, bossDesc, descX, descY, color);
				}
			}
		}
	}

	public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation, int color) {
		drawBar(guiGraphics, x, y, event, 182, 0, color);
		int i = (int)(event.getProgress() * 183.0f);
		if (i > 0) {
			drawBar(guiGraphics, x, y, event, i, 5, color);
		}
		RenderSystem.setShaderTexture(0, barLocation);
		guiGraphics.blit(barLocation, x - 3, y - 2, 0.0F, 0.0F, 188, 9, 188, 9);
	}

	private static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, int width, int i, int color) {
		guiGraphics.blit(BASE_LOCATION_OLD, x, y, 0, color * 5 * 2 + i, width, 5);
		if (event.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
			RenderSystem.enableBlend();
			guiGraphics.blit(BASE_LOCATION_OLD, x, y, 0, 80 + (event.getOverlay().ordinal() - 1) * 5 * 2 + i, width, 5);
			RenderSystem.disableBlend();
		}
	}

	@SubscribeEvent
	public static void onRenderBossBarWickedRidiculeNight(CustomizeGuiOverlayEvent.BossEventProgress event) {
		ResourceLocation barLocation;
		LerpingBossEvent bossEvent = event.getBossEvent();
		if (WorldEvent.NIGHT_UUID.equals(bossEvent.getId())) {
			event.setCanceled(true);
			barLocation = new ResourceLocation(JerotesVillage.MODID,"textures/gui/sprites/boss_bar/wicked_ridicule_night_progress.png");
			event.setIncrement(12 + 2 * Minecraft.getInstance().font.lineHeight);
			RenderSystem.setShaderTexture(0, BASE_LOCATION_OLD);
			drawBar(event.getGuiGraphics(), event.getX(), event.getY(), bossEvent, barLocation, OtherMainConfig.WickedRidiculeNightBossBarColor);
			Component component = bossEvent.getName();
			int textWidth = Minecraft.getInstance().font.width(component);
			int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
			int textY = event.getY() - 9;
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, textX, textY, OtherMainConfig.WickedRidiculeNightBossBarNameColor);
		}
	}

	@SubscribeEvent
	public static void onRenderBossBarZsiein(CustomizeGuiOverlayEvent.BossEventProgress event) {
		ResourceLocation barLocation;
		LerpingBossEvent bossEvent = event.getBossEvent();
		if (WorldEvent.ZSIEIN_UUID.equals(bossEvent.getId())) {
			event.setCanceled(true);
			event.setIncrement(0);
		}
	}
}