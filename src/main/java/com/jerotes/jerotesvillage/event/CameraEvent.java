package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.item.*;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class CameraEvent {
	private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");
	private static final ResourceLocation DARK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/gui/dark.png");

	@SubscribeEvent
	public static void FOV(ComputeFovModifierEvent event) {
		Player player = event.getPlayer();
		if (player.isUsingItem()) {
			Item useItem = player.getUseItem().getItem();
			if (useItem instanceof MerorRapier || useItem instanceof SeawaveTrident) {
				float f = player.getTicksUsingItem() / 20.0F;
				f = f > 1.0F ? 1.0F : f * f;
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * (1.0F - f * 0.15F))));
			}
			else if (useItem instanceof HagEye || useItem instanceof PurpleSandHagEye) {
				float f = player.getTicksUsingItem() / 20.0F;
				f = f > 1.0F ? 1.0F : f * f;
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * (1.0F - f * 0.8F))));
			}
			else if (useItem instanceof BrightGoldenMelonHammer) {
				float f = player.getTicksUsingItem() / 25.0F;
				f = f > 1.0F ? 1.0F : f * f;
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * (1.0F - f * 0.15F))));
			}
			else if (useItem instanceof SecondRoundWorldTeleportStone) {
				float f = player.getTicksUsingItem() / 120.0F;
				f = f > 1.0F ? 1.0F : f * f;
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * (1.0F - f * 0.15F))));
			}
			else if (useItem instanceof ContinuousCutLeaf) {
				if (player.getTicksUsingItem() < 20) {
					float f;
					if (player.getTicksUsingItem() < 10) {
						f = Math.max(10, player.getTicksUsingItem()) / 20.0F;
					}
					else {
						f = Math.max(10, 20 - player.getTicksUsingItem()) / 20.0F;
					}
					f = f > 1.0F ? 1.0F : f * f;
					event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * (1.0F - f * 0.8F))));
				}
			}
		}
		else {
			if (player.isShiftKeyDown()) {
				Item handItem = player.getMainHandItem().getItem();
				Minecraft minecraft = Minecraft.getInstance();
				if (handItem instanceof MerorShotgun && minecraft.options.getCameraType().isFirstPerson()) {
					event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * 0.1f)));
				}
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.PRESBYOPIC_GLASSES.get()) {
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * 0.75f)));
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MYOPIA_GLASSES.get()) {
				event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, (event.getFovModifier() * 1.25f)));
			}
		}
	}

	//屏幕震动
	@SubscribeEvent
	public static void Shake(ViewportEvent.ComputeCameraAngles event) {
		Player player = Minecraft.getInstance().player;
		float delta = Minecraft.getInstance().getFrameTime();
		if (player != null && player.getMainHandItem().is(JerotesVillageItems.CONTINUOUS_CUT_LEAF.get())) {
			Minecraft minecraft = Minecraft.getInstance();
			if (player.swinging && MainConfig.ScreenShake && player.getMainHandItem().getItem() instanceof ContinuousCutLeaf continuousCutLeaf && minecraft.options.getCameraType().isFirstPerson()) {
				int time = player.swingTime;
				float times = time/3f;
				if (time > 3) {
					times = 1f - (time-3)/5f;
				}
				if (time > 8) {
					return;
				}
				int type = continuousCutLeaf.getAttackType(player.getMainHandItem());
				if (type == 1) {
					times *= 1;
				}
				if (type == 2) {
					times *= -1.08f;
				}
				if (type == 3) {
					times *= -0.77f;
				}
				if (type == 4) {
					times *= 0.65f;
				}
				if (type == 5) {
					times *= -0.97f;
				}
				if (type == 6) {
					times *= 0.85f;
				}
				event.setYaw(event.getYaw() + times * 0.25f);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void MerorShotgunGui(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		Item item = player.getMainHandItem().getItem();
		Minecraft minecraft = Minecraft.getInstance();
		if (player.isShiftKeyDown() && item instanceof MerorShotgun && minecraft.options.getCameraType().isFirstPerson()) {
			event.getGuiGraphics().blit(new ResourceLocation(JerotesVillage.MODID, "textures/gui/shotgun_spyglass_scope.png"), w / 2 - 256, h / 2 - 256, 0, 0, 512, 512, 512, 512);
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}


	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void InPortal(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		boolean bl = Main.getJerotesPersistentData(player).getDouble("jerotesvillage_second_rounder_portal") > 0;
		if (bl) {
			RenderSystem.setShaderColor(1, 1, 1, (float) (Main.getJerotesPersistentData(player).getDouble("jerotesvillage_second_rounder_portal") / 30));
			event.getGuiGraphics().blit(new ResourceLocation(JerotesVillage.MODID, "textures/gui/second_round_world_portal.png"), 0, 0, -90, 0.0F, 0.0F, event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(), event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void WickedRidiculeNight(RenderGuiEvent.Pre event) {
		int n = event.getWindow().getGuiScaledWidth();
		int n2 = event.getWindow().getGuiScaledHeight();
		float f = (float) OtherMainConfig.WickedRidiculeNightScreenOverlayLevel;
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		boolean bl = player.hasEffect(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get());
		if (bl) {
			event.getGuiGraphics().pose().pushPose();
			float f2 = Mth.lerp(f, 2.0f, 1.0f);
			event.getGuiGraphics().pose().translate((float)n / 2.0f, (float)n2 / 2.0f, 0.0f);
			event.getGuiGraphics().pose().scale(f2, f2, f2);
			event.getGuiGraphics().pose().translate((float)(-n) / 2.0f, (float)(-n2) / 2.0f, 0.0f);
			int i = OtherMainConfig.WickedRidiculeNightScreenOverlayColorChoose;
			float f3 = (float)(i >> 16 & 255) / 255.0F * f;
			float f4 = (float)(i >> 8 & 255) / 255.0F * f;
			float f5 = (float)(i & 255) / 255.0F * f;

			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			event.getGuiGraphics().setColor(f3, f4, f5, 1.0f);
			event.getGuiGraphics().blit(NAUSEA_LOCATION, 0, 0, -90, 0.0f, 0.0f, n, n2, n, n2);
			event.getGuiGraphics().setColor(1.0f, 1.0f, 1.0f, 1.0f);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			event.getGuiGraphics().pose().popPose();
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void ZsieinAbout(RenderGuiEvent.Pre event) {
		int n = event.getWindow().getGuiScaledWidth();
		int n2 = event.getWindow().getGuiScaledHeight();
		float f = 0.2f;
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		boolean bl = player.hasEffect(JerotesVillageMobEffects.ZSIEIN_FOG.get());
		if (bl) {
			event.getGuiGraphics().pose().pushPose();
			float f2 = Mth.lerp(f, 2.0f, 1.0f);
			event.getGuiGraphics().pose().translate((float)n / 2.0f, (float)n2 / 2.0f, 0.0f);
			event.getGuiGraphics().pose().scale(f2, f2, f2);
			event.getGuiGraphics().pose().translate((float)(-n) / 2.0f, (float)(-n2) / 2.0f, 0.0f);
			int i = 0x998af4;
			float f3 = (float)(i >> 16 & 255) / 255.0F * f;
			float f4 = (float)(i >> 8 & 255) / 255.0F * f;
			float f5 = (float)(i & 255) / 255.0F * f;

			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			event.getGuiGraphics().setColor(f3, f4, f5, 1.0f);
			event.getGuiGraphics().blit(NAUSEA_LOCATION, 0, 0, -90, 0.0f, 0.0f, n, n2, n, n2);
			event.getGuiGraphics().setColor(1.0f, 1.0f, 1.0f, 1.0f);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			event.getGuiGraphics().pose().popPose();
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void Sunglasses(RenderGuiEvent.Pre event) {
		int n = event.getWindow().getGuiScaledWidth();
		int n2 = event.getWindow().getGuiScaledHeight();
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		boolean bl = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SUNGLASSES.get();
		if (bl) {
			RenderSystem.setShaderColor(1, 1, 1, 0.5f);
			event.getGuiGraphics().blit(DARK_LOCATION, 0, 0, -90, 0.0F, 0.0F, event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(), event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}


	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void BrightLandBeastWalk(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Player player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		Item item = player.getMainHandItem().getItem();
		if (false) {
			int n = player.tickCount % 100;
			ResourceLocation resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/bright_land_beast_walk/bright_land_beast_walk_"+ n + ".png");
			event.getGuiGraphics().blit(resourceLocation, w / 2 - 6, h / 2 - 17, 0, 0, 837/4, 576/4, 837/4, 576/4);
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
}
