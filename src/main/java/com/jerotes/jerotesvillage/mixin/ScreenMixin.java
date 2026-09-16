package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler {
    @Shadow public int width;
    @Shadow public int height;
    @Unique
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/gray.png");
    @Unique
    private static final ResourceLocation BITTER_COLD_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/snow_frozen_soil.png");
    @Unique
    private static final ResourceLocation PURPLE_DESERT_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/purple_sand.png");
    @Unique
    private static final ResourceLocation ENDLESS_OCEAN_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/gemstone_gravel.png");
    @Unique
    private static final ResourceLocation BRIGHT_GRASSLAND_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/bright_cobblestone.png");
    @Unique
    private static final ResourceLocation DROUGHT_FIRE_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/drought_fire_stone_top.png");
    @Unique
    private static final ResourceLocation HARMONIOUS_WONDERLAND_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/wonderland_log.png");
    @Unique
    private static final ResourceLocation SAND_BEACH_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/sun_sand.png");
    @Unique
    private static final ResourceLocation DAMAGED_RUINS_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/ruin_stone_bricks.png");
    @Unique
    private static final ResourceLocation UNKNOWN_DEEP_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/deep_dust_block.png");
    @Unique
    private static final ResourceLocation VIRTUAL_CAVE_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/virtual_cave_evil_block_top.png");
    @Unique
    private static final ResourceLocation WILDERNESS_WOODLAND_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/wilderness_dirt_top.png");
    @Unique
    private static final ResourceLocation PLAGUE_ZONE_BACKGROUND_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/block/gray.png");

    @Inject(method = "renderDirtBackground", at = @At("HEAD"), cancellable = true)
    private void renderDirtBackground(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (!OtherMainConfig.SecondRoundWorldSpecialScreen) {
            return;
        }
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (level != null && player != null) {
            if (level.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
                guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
                ResourceLocation resourceLocation = BACKGROUND_LOCATION;
                BlockPos blockPos = player.getOnPos();
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.BITTER_COLD)) {
                    resourceLocation = BITTER_COLD_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.PURPLE_DESERT)) {
                    resourceLocation = PURPLE_DESERT_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.ENDLESS_OCEAN)) {
                    resourceLocation = ENDLESS_OCEAN_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)) {
                    resourceLocation = BRIGHT_GRASSLAND_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.DROUGHT_FIRE)) {
                    resourceLocation = DROUGHT_FIRE_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
                    resourceLocation = HARMONIOUS_WONDERLAND_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.SAND_BEACH)) {
                    resourceLocation = SAND_BEACH_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.DAMAGED_RUINS)) {
                    resourceLocation = DAMAGED_RUINS_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.UNKNOWN_DEEP)) {
                    resourceLocation = UNKNOWN_DEEP_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.VIRTUAL_CAVE)) {
                    resourceLocation = VIRTUAL_CAVE_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
                    resourceLocation = WILDERNESS_WOODLAND_BACKGROUND_LOCATION;
                }
                if (level.getBiome(blockPos).is(JerotesVillageBiomes.PLAGUE_ZONE)) {
                    resourceLocation =PLAGUE_ZONE_BACKGROUND_LOCATION;
                }
                guiGraphics.blit(resourceLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
                guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(((Screen)(Object) this), guiGraphics));
                ci.cancel();
            }
        }
    }
}