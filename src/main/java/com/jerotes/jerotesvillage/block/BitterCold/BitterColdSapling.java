package com.jerotes.jerotesvillage.block.BitterCold;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BitterColdSapling extends ABaseBlock.ABaseSapling {
	public BitterColdSapling() {
		super();
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		this.advanceTree(serverLevel, blockPos, blockState, randomSource);
	}

	public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
		if (blockState.getValue(STAGE) == 0) {
			serverLevel.setBlock(blockPos, (BlockState)blockState.cycle(STAGE), 4);
		}
		else {
			int x = blockPos.getX();
			int y = blockPos.getY();
			int z = blockPos.getZ();
			int random = serverLevel.getRandom().nextInt(16) + 5;

			for (int n = 0; n < random; ++n) {
				BlockPos pos = new BlockPos(x, y + n, z);
				BlockState state = serverLevel.getBlockState(pos);
				if ((state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR) || state.getBlock() == JerotesVillageBlocks.BITTER_COLD_SAPLING.get()) && n < random - 1) {
					serverLevel.setBlock(pos, JerotesVillageBlocks.BITTER_COLD_LOG.get().defaultBlockState(), 3);
				}
				else{
					if (n > 4 && n < 8){
						placeLeafLayer(serverLevel, x, y + n - 3, z, 2, true);
						placeLeafLayer(serverLevel, x, y + n - 2, z, 2, true);
						placeLeafLayer(serverLevel, x, y + n - 1, z, 1, false);
						placeLeafLayer(serverLevel, x, y + n - 1, z, 0, false);
					}
					else if (n >= 8){
						serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);

						for (int i = -2; i < 3; ++i) {
							for (int j = -2; j < 3; ++j) {
								BlockPos pos1 = new BlockPos(x + i, y + n - 1, z + j);
								BlockState state1 = serverLevel.getBlockState(pos1);
								if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
									serverLevel.setBlock(pos1, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
								}
							}
						}
						BlockPos pos2 = new BlockPos(x, y + n - 1, z - 3);
						BlockPos pos3 = new BlockPos(x, y + n - 1, z + 3);
						BlockPos pos4 = new BlockPos(x + 3, y + n - 1, z);
						BlockPos pos5 = new BlockPos(x - 3, y + n - 1, z);
						BlockState state2 = serverLevel.getBlockState(pos2);
						BlockState state3 = serverLevel.getBlockState(pos3);
						BlockState state4 = serverLevel.getBlockState(pos4);
						BlockState state5 = serverLevel.getBlockState(pos5);
						if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
							serverLevel.setBlock(pos2, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state3.is(BlockTags.REPLACEABLE_BY_TREES) || state3.is(Blocks.AIR))) {
							serverLevel.setBlock(pos3, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state4.is(BlockTags.REPLACEABLE_BY_TREES) || state4.is(Blocks.AIR))) {
							serverLevel.setBlock(pos4, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state5.is(BlockTags.REPLACEABLE_BY_TREES) || state5.is(Blocks.AIR))) {
							serverLevel.setBlock(pos5, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						for (int i = -3; i < 4; ++i) {
							for (int j = -3; j < 4; ++j) {
								BlockPos pos6 = new BlockPos(x + i, y + n - 2, z + j);
								BlockState state6 = serverLevel.getBlockState(pos6);
								if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
									serverLevel.setBlock(pos6, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
								}
							}
						}
						BlockPos pos7 = new BlockPos(x + 3, y + n - 2, z - 3);
						BlockPos pos8 = new BlockPos(x - 3, y + n - 2, z + 3);
						BlockPos pos9 = new BlockPos(x + 3, y + n - 2, z + 3);
						BlockPos pos10 = new BlockPos(x - 3, y + n - 2, z - 3);
						BlockPos pos11 = new BlockPos(x, y + n - 2, z - 4);
						BlockPos pos12 = new BlockPos(x, y + n - 2, z + 4);
						BlockPos pos13 = new BlockPos(x + 4, y + n - 2, z);
						BlockPos pos14 = new BlockPos(x - 4, y + n - 2, z);
						BlockState state7 = serverLevel.getBlockState(pos7);
						BlockState state8 = serverLevel.getBlockState(pos8);
						BlockState state9 = serverLevel.getBlockState(pos9);
						BlockState state10 = serverLevel.getBlockState(pos10);
						BlockState state11 = serverLevel.getBlockState(pos11);
						BlockState state12 = serverLevel.getBlockState(pos12);
						BlockState state13 = serverLevel.getBlockState(pos13);
						BlockState state14 = serverLevel.getBlockState(pos14);
						if ((state7.getBlock() == JerotesVillageBlocks.BITTER_COLD_LEAVES.get())) {
							serverLevel.setBlock(pos7, Blocks.AIR.defaultBlockState(), 3);
						}
						if ((state8.getBlock() == JerotesVillageBlocks.BITTER_COLD_LEAVES.get())) {
							serverLevel.setBlock(pos8, Blocks.AIR.defaultBlockState(), 3);
						}
						if ((state9.getBlock() == JerotesVillageBlocks.BITTER_COLD_LEAVES.get())) {
							serverLevel.setBlock(pos9, Blocks.AIR.defaultBlockState(), 3);
						}
						if ((state10.getBlock() == JerotesVillageBlocks.BITTER_COLD_LEAVES.get())) {
							serverLevel.setBlock(pos10, Blocks.AIR.defaultBlockState(), 3);
						}
						if ((state11.is(BlockTags.REPLACEABLE_BY_TREES) || state11.is(Blocks.AIR))) {
							serverLevel.setBlock(pos11, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state12.is(BlockTags.REPLACEABLE_BY_TREES) || state12.is(Blocks.AIR))) {
							serverLevel.setBlock(pos12, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state13.is(BlockTags.REPLACEABLE_BY_TREES) || state13.is(Blocks.AIR))) {
							serverLevel.setBlock(pos13, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
						if ((state14.is(BlockTags.REPLACEABLE_BY_TREES) || state14.is(Blocks.AIR))) {
							serverLevel.setBlock(pos14, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
						}
					}
					n = 100;
				}
			}
		}
	}

	private void placeLeafLayer(ServerLevel level, int cx, int cy, int cz, int radius, boolean roundCorners) {
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				if (roundCorners && Math.abs(dx) == radius && Math.abs(dz) == radius) continue;
				BlockPos pos = new BlockPos(cx + dx, cy, cz + dz);
				BlockState state = level.getBlockState(pos);
				if (state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR)) {
					level.setBlock(pos, JerotesVillageBlocks.BITTER_COLD_LEAVES.get().defaultBlockState(), 3);
				}
			}
		}
	}
}