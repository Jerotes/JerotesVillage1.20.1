package com.jerotes.jerotesvillage.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class JerotesDimensionSpecialEffects extends DimensionSpecialEffects {
    public JerotesDimensionSpecialEffects(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor.multiply(daylight * 0.94F + 0.06F, (daylight * 0.94F + 0.06F), (daylight * 0.91F + 0.09F));
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        Player player = Minecraft.getInstance().player;
        Level level = Minecraft.getInstance().level;
//        if (player != null) {
//            Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
//            //泽林雾气
//            if (biomeHolder.is(JerotesVillageBiomes.PLAGUE_ZONE) && WorldEvent.ZsieinLatentInfectedAbove(level)) {
//                return true;
//            }
//            return WorldEvent.ZsieinSevereInfectedAbove(level);
//        }
        return false;
    }

    @Override
    public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {
        return SkyRenderer.renderSky(level, poseStack, projectionMatrix, partialTick, camera, setupFog);
    }
}