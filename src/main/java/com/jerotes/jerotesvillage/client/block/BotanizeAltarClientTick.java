package com.jerotes.jerotesvillage.client.block;

import com.jerotes.jerotesvillage.block.HarmoniousWonderland.BotanizeAltarEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.WeakHashMap;

public class BotanizeAltarClientTick {
	private static final Map<BotanizeAltarEntity, Integer> TICK_COUNTS = new WeakHashMap<>();

	public static void tick(BotanizeAltarEntity botanizeAltarEntity) {
		Player player = Minecraft.getInstance().player;
		if (player == null || !(BotanizeAltarEntity.canOffer(player.getMainHandItem()) || BotanizeAltarEntity.canOffer(player.getOffhandItem()))) {
			return;
		}
		Level level = botanizeAltarEntity.getLevel();
		if (level == null) {
			return;
		}
		int tickCount = TICK_COUNTS.getOrDefault(botanizeAltarEntity, 0) + 1;
		TICK_COUNTS.put(botanizeAltarEntity, tickCount);
		if (tickCount % 5 != 0) {
			return;
		}
		BlockPos blockPos = botanizeAltarEntity.getBlockPos();
		level.addParticle(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
				blockPos.getX() + 0.5D, blockPos.getY() + 1.2D, blockPos.getZ() + 0.5D,
				(level.random.nextFloat() / 5 - 0.1f) * 0.05f, (0.15f + level.random.nextFloat() / 5) * 0.05f, (level.random.nextFloat() / 5 - 0.1f) * 0.05f);
	}
}
