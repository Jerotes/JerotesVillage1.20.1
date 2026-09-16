package com.jerotes.jerotesvillage.block.WildernessWoodland;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlackYellowSapling extends ABaseBlock.ABaseSapling {
	public BlackYellowSapling() {
		super();
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		this.advanceTree(serverLevel, blockPos, blockState, randomSource);
	}

	private static boolean isTwoByTwoSapling(BlockState blockState, Level level, BlockPos blockPos, int n, int n2) {
		Block block = blockState.getBlock();
		return level.getBlockState(blockPos.offset(n, 0, n2)).is(block) && level.getBlockState(blockPos.offset(n + 1, 0, n2)).is(block) && level.getBlockState(blockPos.offset(n, 0, n2 + 1)).is(block) && level.getBlockState(blockPos.offset(n + 1, 0, n2 + 1)).is(block);
	}

	public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
		if (blockState.getValue(STAGE) == 0) {
			serverLevel.setBlock(blockPos, (BlockState)blockState.cycle(STAGE), 4);
		}
		else {
			boolean mega = false;
			Block block = blockState.getBlock();
			for (int i = 0; i >= -1; --i) {
				for (int j = 0; j >= -1; --j) {
					if (isTwoByTwoSapling(blockState, serverLevel, blockPos, i, j)) {
						mega = true;
					}
				}
			}
			if (!mega) {
				int x = blockPos.getX();
				int y = blockPos.getY();
				int z = blockPos.getZ();
				int random = serverLevel.getRandom().nextInt(6) + 6;

				for (int n = 0; n < random; ++n) {
					BlockPos pos = new BlockPos(x, y + n, z);
					BlockState state = serverLevel.getBlockState(pos);
					if ((state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR) || state.getBlock() == JerotesVillageBlocks.BLACK_YELLOW_SAPLING.get()) && n < random - 1) {
						serverLevel.setBlock(pos, JerotesVillageBlocks.BLACK_YELLOW_LOG.get().defaultBlockState(), 3);
					}
					else {
						if (n > 4) {
							serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
							BlockPos pos1 = new BlockPos(x - 1, y + n - 1, z);
							BlockState state1 = serverLevel.getBlockState(pos1);
							if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
								serverLevel.setBlock(pos1, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
							}
							BlockPos pos2 = new BlockPos(x + 1, y + n - 1, z);
							BlockState state2 = serverLevel.getBlockState(pos2);
							if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
								serverLevel.setBlock(pos2, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
							}
							BlockPos pos3 = new BlockPos(x, y + n - 1, z + 1);
							BlockState state3 = serverLevel.getBlockState(pos3);
							if ((state3.is(BlockTags.REPLACEABLE_BY_TREES) || state3.is(Blocks.AIR))) {
								serverLevel.setBlock(pos3, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
							}
							BlockPos pos4 = new BlockPos(x, y + n - 1, z - 1);
							BlockState state4 = serverLevel.getBlockState(pos4);
							if ((state4.is(BlockTags.REPLACEABLE_BY_TREES) || state4.is(Blocks.AIR))) {
								serverLevel.setBlock(pos4, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
							}
							for (int i = -1; i < 2; ++i) {
								for (int j = -1; j < 2; ++j) {
									BlockPos pos5 = new BlockPos(x + i, y + n - 2, z + j);
									BlockState state5 = serverLevel.getBlockState(pos5);
									if ((state5.is(BlockTags.REPLACEABLE_BY_TREES) || state5.is(Blocks.AIR))) {
										serverLevel.setBlock(pos5, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
									}
								}
							}
							for (int i = -2; i < 3; ++i) {
								for (int j = -2; j < 3; ++j) {
									BlockPos pos6 = new BlockPos(x + i, y + n - 3, z + j);
									BlockState state6 = serverLevel.getBlockState(pos6);
									if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
										serverLevel.setBlock(pos6, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
									}
								}
							}
							for (int i = -2; i < 3; ++i) {
								for (int j = -2; j < 3; ++j) {
									BlockPos pos7 = new BlockPos(x + i, y + n - 4, z + j);
									BlockState state7 = serverLevel.getBlockState(pos7);
									if ((state7.is(BlockTags.REPLACEABLE_BY_TREES) || state7.is(Blocks.AIR))) {
										serverLevel.setBlock(pos7, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
									}
								}
							}
						}
						n = 100;
					}
				}
			}
			else {
				int baseX = blockPos.getX();
				int baseY = blockPos.getY();
				int baseZ = blockPos.getZ();

				int random = serverLevel.getRandom().nextInt(24) + 12;
				for (int ii = -1; ii < 2; ++ii) {
					for (int jj = -1; jj < 2; ++jj) {
						BlockPos basePos = new BlockPos(baseX + ii, baseY, baseZ + jj);
						BlockState baseState = serverLevel.getBlockState(basePos);
						if (baseState.getBlock() == JerotesVillageBlocks.BLACK_YELLOW_SAPLING.get()) {
							int x = basePos.getX();
							int y = basePos.getY();
							int z = basePos.getZ();
							for (int n = 0; n < random; ++n) {
								BlockPos pos = new BlockPos(x, y + n, z);
								BlockState state = serverLevel.getBlockState(pos);
								if ((state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR) || state.getBlock() == JerotesVillageBlocks.BLACK_YELLOW_SAPLING.get()) && n < random - 1) {
									serverLevel.setBlock(pos, JerotesVillageBlocks.BLACK_YELLOW_LOG.get().defaultBlockState(), 3);
								}
								else {
									if (n > 6 && n < 12){
										serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);

										for (int i = -1; i < 2; ++i) {
											for (int j = -1; j < 2; ++j) {
												BlockPos pos1 = new BlockPos(x + i, y + n - 2, z + j);
												BlockState state1 = serverLevel.getBlockState(pos1);
												if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
													serverLevel.setBlock(pos1, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
										for (int i = -1; i < 2; ++i) {
											for (int j = -1; j < 2; ++j) {
												BlockPos pos2 = new BlockPos(x + i, y + n - 3, z + j);
												BlockState state2 = serverLevel.getBlockState(pos2);
												if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
													serverLevel.setBlock(pos2, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
									}
									else if (n >= 12){
										serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);

										BlockPos pos1 = new BlockPos(x - 1, y + n - 1, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos2 = new BlockPos(x + 1, y + n - 1, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos3 = new BlockPos(x, y + n - 1, z + 1);
										BlockState state3 = serverLevel.getBlockState(pos3);
										if ((state3.is(BlockTags.REPLACEABLE_BY_TREES) || state3.is(Blocks.AIR))) {
											serverLevel.setBlock(pos3, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos4 = new BlockPos(x, y + n - 1, z - 1);
										BlockState state4 = serverLevel.getBlockState(pos4);
										if ((state4.is(BlockTags.REPLACEABLE_BY_TREES) || state4.is(Blocks.AIR))) {
											serverLevel.setBlock(pos4, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos5 = new BlockPos(x - 1, y + n - 2, z);
										BlockState state5 = serverLevel.getBlockState(pos5);
										if ((state5.is(BlockTags.REPLACEABLE_BY_TREES) || state5.is(Blocks.AIR))) {
											serverLevel.setBlock(pos5, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos6 = new BlockPos(x + 1, y + n - 2, z);
										BlockState state6 = serverLevel.getBlockState(pos6);
										if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
											serverLevel.setBlock(pos6, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos7 = new BlockPos(x, y + n - 2, z + 1);
										BlockState state7 = serverLevel.getBlockState(pos7);
										if ((state7.is(BlockTags.REPLACEABLE_BY_TREES) || state7.is(Blocks.AIR))) {
											serverLevel.setBlock(pos7, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}
										BlockPos pos8 = new BlockPos(x, y + n - 2, z - 1);
										BlockState state8 = serverLevel.getBlockState(pos8);
										if ((state8.is(BlockTags.REPLACEABLE_BY_TREES) || state8.is(Blocks.AIR))) {
											serverLevel.setBlock(pos8, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
										}

										for (int i = -2; i < 3; ++i) {
											for (int j = -2; j < 3; ++j) {
												BlockPos pos9 = new BlockPos(x + i, y + n - 3, z + j);
												BlockState state9 = serverLevel.getBlockState(pos9);
												if ((state9.is(BlockTags.REPLACEABLE_BY_TREES) || state9.is(Blocks.AIR))) {
													serverLevel.setBlock(pos9, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
										for (int i = -2; i < 3; ++i) {
											for (int j = -2; j < 3; ++j) {
												BlockPos pos10 = new BlockPos(x + i, y + n - 4, z + j);
												BlockState state10 = serverLevel.getBlockState(pos10);
												if ((state10.is(BlockTags.REPLACEABLE_BY_TREES) || state10.is(Blocks.AIR))) {
													serverLevel.setBlock(pos10, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
										for (int i = -3; i < 4; ++i) {
											for (int j = -3; j < 4; ++j) {
												BlockPos pos11 = new BlockPos(x + i, y + n - 5, z + j);
												BlockState state11 = serverLevel.getBlockState(pos11);
												if ((state11.is(BlockTags.REPLACEABLE_BY_TREES) || state11.is(Blocks.AIR))) {
													serverLevel.setBlock(pos11, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
										for (int i = -3; i < 4; ++i) {
											for (int j = -3; j < 4; ++j) {
												BlockPos pos12 = new BlockPos(x + i, y + n - 6, z + j);
												BlockState state12 = serverLevel.getBlockState(pos12);
												if ((state12.is(BlockTags.REPLACEABLE_BY_TREES) || state12.is(Blocks.AIR))) {
													serverLevel.setBlock(pos12, JerotesVillageBlocks.BLACK_YELLOW_LEAVES.get().defaultBlockState(), 3);
												}
											}
										}
									}
									n = 100;
								}
							}
						}
					}
				}
			}
		}
	}

}
