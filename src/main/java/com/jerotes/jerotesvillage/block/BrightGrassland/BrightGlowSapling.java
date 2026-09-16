package com.jerotes.jerotesvillage.block.BrightGrassland;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BrightGlowSapling extends SaplingBlock {
	public BrightGlowSapling() {
		super(new OakTreeGrower(), Properties.of().lightLevel(s -> 3).mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		this.advanceTree(serverLevel, blockPos, blockState, randomSource);
	}

	public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
		if (blockState.getValue(STAGE) == 0) {
			serverLevel.setBlock(blockPos, (BlockState)blockState.cycle(STAGE), 4);
		} else {
			int x = blockPos.getX();
			int y = blockPos.getY();
			int z = blockPos.getZ();
			int trunkHeight = serverLevel.getRandom().nextInt(3) + 7;

			for (int n = 0; n < trunkHeight - 1; n++) {
				BlockPos pos = new BlockPos(x, y + n, z);
				BlockState state = serverLevel.getBlockState(pos);
				if (state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR) || state.getBlock() == JerotesVillageBlocks.BRIGHT_GLOW_SAPLING.get()) {
					serverLevel.setBlock(pos, JerotesVillageBlocks.BRIGHT_GLOW_LOG.get().defaultBlockState(), 3);
				} else {
					return;
				}
			}

			placeLeafLayer(serverLevel, x, y + trunkHeight - 4, z, 2, true);
			placeLeafLayer(serverLevel, x, y + trunkHeight - 3, z, 2, true);
			placeLeafLayer(serverLevel, x, y + trunkHeight - 2, z, 1, false);
			placeLeafCross(serverLevel, x, y + trunkHeight - 1, z);
		}
	}

	private void placeLeafLayer(ServerLevel level, int cx, int cy, int cz, int radius, boolean roundCorners) {
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				if (roundCorners && Math.abs(dx) == radius && Math.abs(dz) == radius) continue;
				BlockPos pos = new BlockPos(cx + dx, cy, cz + dz);
				BlockState state = level.getBlockState(pos);
				if (state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR)) {
					level.setBlock(pos, JerotesVillageBlocks.BRIGHT_GLOW_LEAVES.get().defaultBlockState(), 3);
				}
			}
		}
	}

	private void placeLeafCross(ServerLevel level, int cx, int cy, int cz) {
		BlockPos[] cross = {
				new BlockPos(cx, cy, cz),
				new BlockPos(cx + 1, cy, cz),
				new BlockPos(cx - 1, cy, cz),
				new BlockPos(cx, cy, cz + 1),
				new BlockPos(cx, cy, cz - 1)
		};
		for (BlockPos pos : cross) {
			BlockState state = level.getBlockState(pos);
			if (state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR)) {
				level.setBlock(pos, JerotesVillageBlocks.BRIGHT_GLOW_LEAVES.get().defaultBlockState(), 3);
			}
		}
	}
}