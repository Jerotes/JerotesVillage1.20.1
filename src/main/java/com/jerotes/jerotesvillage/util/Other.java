package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotes.entity.Other.FallingBlock.JerotesEarthrendBlock;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.item.HagEye;
import com.jerotes.jerotesvillage.item.MerorShotgun;
import com.jerotes.jerotesvillage.item.PurpleSandHagEye;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class Other {

	//其他类型观察
	public static boolean isOtherScoping(LivingEntity player) {
		Minecraft minecraft = Minecraft.getInstance();
		return ((player.isUsingItem() &&
				(player.getUseItem().getItem() instanceof HagEye
						|| player.getUseItem().getItem() instanceof PurpleSandHagEye))
				|| (player.isShiftKeyDown() && player.getMainHandItem().getItem() instanceof MerorShotgun && minecraft.options.getCameraType().isFirstPerson()));
	}
	//袭击Boss数量
	public static int getRaidBossCount(LivingEntity boss) {
		if (OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCount
				&& (OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss
				|| OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss.contains(boss.getEncodeId()))) {
			double reach = OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
			List<Player> listTeam = boss.level().getEntitiesOfClass(Player.class, boss.getBoundingBox().inflate(reach, reach, reach));
			if (listTeam.isEmpty()) {
				return 1;
			}
			else return Math.min(listTeam.size(), OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount);
		}
		return 1;
	}

	public static void spawnEarthquakeFallingBlockByPos(ServerLevel level, BlockPos pos) {
		RandomSource random = RandomSource.create();
		BlockPos abovePos = (new BlockPos(pos)).above();
		BlockState block = level.getBlockState(pos);
		BlockState blockAbove = level.getBlockState(abovePos);
		if (!block.isAir() && block.isRedstoneConductor(level, pos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
			JerotesEarthrendBlock fallingBlock = new JerotesEarthrendBlock(level, (double)pos.getX() + 0.5, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5, block, 10);
			fallingBlock.push(0.0, 0.2 + random.nextGaussian() * 0.2, 0.0);
			level.addFreshEntity(fallingBlock);
		}
	}
	public static void spawnEarthquakeFallingBlockByPos(ServerLevel level, BlockPos pos, LivingEntity caster) {
		RandomSource random = RandomSource.create();
		BlockPos abovePos = (new BlockPos(pos)).above();
		BlockState block = level.getBlockState(pos);
		BlockState blockAbove = level.getBlockState(abovePos);
		if (!block.isAir() && block.isRedstoneConductor(level, pos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
			JerotesEarthrendBlock fallingBlock = new JerotesEarthrendBlock(level, (double)pos.getX() + 0.5, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5, block, 10);
			fallingBlock.setOwner(caster);
			fallingBlock.setPushTick(1800);
			fallingBlock.push(0.0, 0.2 + random.nextGaussian() * 0.2, 0.0);
			level.addFreshEntity(fallingBlock);
		}
	}
	public static void spawnEarthquakeFallingBlockByPosSameHeight(ServerLevel level, BlockPos pos, LivingEntity caster, float height) {
		RandomSource random = RandomSource.create();
		BlockPos abovePos = (new BlockPos(pos)).above();
		BlockState block = level.getBlockState(pos);
		BlockState blockAbove = level.getBlockState(abovePos);
		if (!block.isAir() && block.isRedstoneConductor(level, pos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
			JerotesEarthrendBlock fallingBlock = new JerotesEarthrendBlock(level, (double)pos.getX() + 0.5, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5, block, 10);
			fallingBlock.setOwner(caster);
			fallingBlock.setPushTick(1800);
			fallingBlock.push(0.0, height, 0.0);
			level.addFreshEntity(fallingBlock);
		}
	}
}