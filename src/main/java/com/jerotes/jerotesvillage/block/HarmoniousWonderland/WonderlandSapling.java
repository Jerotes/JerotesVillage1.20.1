package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WonderlandSapling extends ABaseBlock.ABaseSapling {
	public WonderlandSapling() {
		super();
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		this.advanceTree(serverLevel, blockPos, blockState, randomSource);
	}

	public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
		if (blockState.getValue(STAGE) == 0) {
			serverLevel.setBlock(blockPos, (BlockState)blockState.cycle(STAGE), 3);
		}
		else {
			int x = blockPos.getX();
			int y = blockPos.getY();
			int z = blockPos.getZ();
			int random = serverLevel.getRandom().nextInt(4) + 14;

			for (int n = 0; n < random; ++n) {
				BlockPos pos = new BlockPos(x, y + n, z);
				BlockState state = serverLevel.getBlockState(pos);
				//主杆
				if ((state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.AIR) || state.getBlock() == JerotesVillageBlocks.WONDERLAND_SAPLING.get()) && n < random - 1) {
					serverLevel.setBlock(pos, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
				}
				else {
					//矮小型
					if (n > 4 && n < 8){
						//顶层树叶
						serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
						//其他树叶
						for (int i = -1; i < 2; ++i) {
							for (int j = -1; j < 2; ++j) {
								for (int k = -2; k > -4; --k) {
									BlockPos pos1 = new BlockPos(x + i, y + n + k, z + j);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									}
								}
							}
						}
					}
					//高大型
					else if (n >= 8) {
						//判定第一个枝杈高度(主干相关)
						int firstAbout = serverLevel.getRandom().nextInt(3) + 5;
						int firstHeight = y + n - firstAbout;
						//判定枝杈方向
						float chance = serverLevel.getRandom().nextFloat();
						int howLong = serverLevel.getRandom().nextInt(3, 6);
						int secondAbout = serverLevel.getRandom().nextInt(1, 3) + 1;
						int howLongTwo = serverLevel.getRandom().nextInt(2, 3);
						//生成枝杈相关
						if (chance > 0.75f) {
							//一折
							if (serverLevel.getRandom().nextFloat() > 0.5f){
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x + i, firstHeight, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < firstAbout - 1; ++h) {
									BlockPos pos2 = new BlockPos(x + howLong - 1, firstHeight + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x + howLong - 1, y + n - 1, z);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
							else {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x + i, firstHeight, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < secondAbout; ++h) {
									BlockPos pos2 = new BlockPos(x + howLong - 1, firstHeight + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//继续横
								for (int i = 1; i < howLongTwo; ++i) {
									BlockPos pos1 = new BlockPos(x + howLong - 1 + i, firstHeight + secondAbout - 1, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < (y + n - (firstHeight + secondAbout - 1)); ++h) {
									BlockPos pos2 = new BlockPos(x + howLong + howLongTwo - 2, firstHeight + secondAbout - 1 + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x + howLong + howLongTwo - 2, y + n - 1, z);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
						}
						else if (chance > 0.5f) {
							//一折
							if (serverLevel.getRandom().nextFloat() > 0.5f) {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x - i, firstHeight, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < firstAbout - 1; ++h) {
									BlockPos pos2 = new BlockPos(x - howLong + 1, firstHeight + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x - howLong + 1, y + n - 1, z);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
							else {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x - i, firstHeight, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < secondAbout; ++h) {
									BlockPos pos2 = new BlockPos(x - howLong + 1, firstHeight + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//继续横
								for (int i = 1; i < howLongTwo; ++i) {
									BlockPos pos1 = new BlockPos(x - howLong + 1 - i, firstHeight + secondAbout - 1, z);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
									}
								}
								//继续向上
								for (int h = 1; h < (y + n - (firstHeight + secondAbout - 1)); ++h) {
									BlockPos pos2 = new BlockPos(x - howLong - howLongTwo + 2, firstHeight + secondAbout - 1 + h, z);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x - howLong - howLongTwo + 2, y + n - 1, z);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
						}
						else if (chance > 0.25f) {
							//一折
							if (serverLevel.getRandom().nextFloat() > 0.5f) {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight, z + i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < firstAbout - 1; ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + h, z + howLong - 1);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x, y + n - 1, z + howLong - 1);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
							else {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight, z + i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < secondAbout; ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + h, z + howLong - 1);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//继续横
								for (int i = 1; i < howLongTwo; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight + secondAbout - 1, z + howLong - 1 + i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < (y + n - (firstHeight + secondAbout - 1)); ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + secondAbout - 1 + h, z + howLong + howLongTwo - 2);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x, y + n - 1, z + howLong + howLongTwo - 2);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
						}
						else {
							//一折
							if (serverLevel.getRandom().nextFloat() > 0.5f) {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight, z - i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < firstAbout - 1; ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + h, z - howLong + 1);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x, y + n - 1, z - howLong + 1);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
							else {
								//横
								for (int i = 1; i < howLong; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight, z - i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < secondAbout; ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + h, z - howLong + 1);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//继续横
								for (int i = 1; i < howLongTwo; ++i) {
									BlockPos pos1 = new BlockPos(x, firstHeight + secondAbout - 1, z - howLong + 1 - i);
									BlockState state1 = serverLevel.getBlockState(pos1);
									if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
										serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
									}
								}
								//继续向上
								for (int h = 1; h < (y + n - (firstHeight + secondAbout - 1)); ++h) {
									BlockPos pos2 = new BlockPos(x, firstHeight + secondAbout - 1 + h, z - howLong - howLongTwo + 2);
									BlockState state2 = serverLevel.getBlockState(pos2);
									if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
										serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
									}
								}
								//树叶
								BlockPos pos3 = new BlockPos(x, y + n - 1, z - howLong - howLongTwo + 2);
								serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
								addLeaves(serverLevel, pos3);
							}
						}
						//有无第二个枝杈高度
						boolean haveSecond = serverLevel.getRandom().nextFloat() > 0.5f;
                        if (haveSecond) {
							//判定第二个枝杈高度
							int secondHeight = firstHeight - 1;
							//其他
							int howLongSecond = serverLevel.getRandom().nextInt(3, 6);
							int secondAboutSecond = serverLevel.getRandom().nextInt(1, 3) + 1;
							int howLongSecondTwo = serverLevel.getRandom().nextInt(2, 3);
							if (chance > 0.75f) {
								//一折
								if (serverLevel.getRandom().nextFloat() > 0.5f) {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x - i, secondHeight, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < firstAbout - 1; ++h) {
										BlockPos pos2 = new BlockPos(x - howLongSecond + 1, secondHeight + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x - howLongSecond + 1, y + n - 1, z);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
								else {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x - i, secondHeight, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < secondAboutSecond; ++h) {
										BlockPos pos2 = new BlockPos(x - howLongSecond + 1, secondHeight + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//继续横
									for (int i = 1; i < howLongSecondTwo; ++i) {
										BlockPos pos1 = new BlockPos(x - howLongSecond + 1 - i, secondHeight + secondAboutSecond - 1, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < (y + n - (secondHeight + secondAboutSecond - 1)); ++h) {
										BlockPos pos2 = new BlockPos(x - howLongSecond - howLongSecondTwo + 2, secondHeight + secondAboutSecond - 1 + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x - howLongSecond - howLongSecondTwo + 2, y + n - 1, z);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
							}
							else if (chance > 0.5f) {
								//一折
								if (serverLevel.getRandom().nextFloat() > 0.5f){
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x + i, secondHeight, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < firstAbout - 1; ++h) {
										BlockPos pos2 = new BlockPos(x + howLongSecond - 1, secondHeight + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x + howLongSecond - 1, y + n - 1, z);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
								else {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x + i, secondHeight, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < secondAboutSecond; ++h) {
										BlockPos pos2 = new BlockPos(x + howLongSecond - 1, secondHeight + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//继续横
									for (int i = 1; i < howLongSecondTwo; ++i) {
										BlockPos pos1 = new BlockPos(x + howLongSecond - 1 + i, secondHeight + secondAboutSecond - 1, z);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
										}
									}
									//继续向上
									for (int h = 1; h < (y + n - (secondHeight + secondAboutSecond - 1)); ++h) {
										BlockPos pos2 = new BlockPos(x + howLongSecond + howLongSecondTwo - 2, secondHeight + secondAboutSecond - 1 + h, z);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x + howLongSecond + howLongSecondTwo - 2, y + n - 1, z);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
							}
							else if (chance > 0.25f) {
								//一折
								if (serverLevel.getRandom().nextFloat() > 0.5f) {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight, z - i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < firstAbout - 1; ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + h, z - howLongSecond + 1);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x, y + n - 1, z - howLongSecond + 1);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
								else {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight, z - i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < secondAboutSecond; ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + h, z - howLongSecond + 1);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//继续横
									for (int i = 1; i < howLongSecondTwo; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight + secondAboutSecond - 1, z - howLongSecond + 1 - i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < (y + n - (secondHeight + secondAboutSecond - 1)); ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + secondAboutSecond - 1 + h, z - howLongSecond - howLongSecondTwo + 2);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x, y + n - 1, z - howLongSecond - howLongSecondTwo + 2);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
							}
							else {
								//一折
								if (serverLevel.getRandom().nextFloat() > 0.5f) {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight, z + i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < firstAbout - 1; ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + h, z + howLongSecond - 1);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x, y + n - 1, z + howLongSecond - 1);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
								else {
									//横
									for (int i = 1; i < howLongSecond; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight, z + i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < secondAboutSecond; ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + h, z + howLongSecond - 1);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//继续横
									for (int i = 1; i < howLongSecondTwo; ++i) {
										BlockPos pos1 = new BlockPos(x, secondHeight + secondAboutSecond - 1, z + howLongSecond - 1 + i);
										BlockState state1 = serverLevel.getBlockState(pos1);
										if ((state1.is(BlockTags.REPLACEABLE_BY_TREES) || state1.is(Blocks.AIR))) {
											serverLevel.setBlock(pos1, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
										}
									}
									//继续向上
									for (int h = 1; h < (y + n - (secondHeight + secondAboutSecond - 1)); ++h) {
										BlockPos pos2 = new BlockPos(x, secondHeight + secondAboutSecond - 1 + h, z + howLongSecond + howLongSecondTwo - 2);
										BlockState state2 = serverLevel.getBlockState(pos2);
										if ((state2.is(BlockTags.REPLACEABLE_BY_TREES) || state2.is(Blocks.AIR))) {
											serverLevel.setBlock(pos2, JerotesVillageBlocks.WONDERLAND_LOG.get().defaultBlockState(), 3);
										}
									}
									//树叶
									BlockPos pos3 = new BlockPos(x, y + n - 1, z + howLongSecond + howLongSecondTwo - 2);
									serverLevel.setBlock(pos3, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
									addLeaves(serverLevel, pos3);
								}
							}
						}
						//有无主干
						if (serverLevel.getRandom().nextFloat() > 0.75f) {
							//有主干
							//顶层树叶
							serverLevel.setBlock(new BlockPos(x, y + n - 1, z), JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
							addLeaves(serverLevel, new BlockPos(x, y + n - 1, z));
						} else {
							//无主干
							for (int i = 1; i < firstAbout; ++i) {
								BlockPos pos1 = new BlockPos(x, firstHeight + i, z);
								BlockState state1 = serverLevel.getBlockState(pos1);
								if ((state1.is(JerotesVillageBlocks.WONDERLAND_LOG.get()))) {
									serverLevel.setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
					}
					n = 100;
				}
			}
		}
	}

	public void addLeaves(ServerLevel serverLevel, BlockPos blockPos) {
		int x = blockPos.getX();
		int y = blockPos.getY() + 2;
		int z = blockPos.getZ();
		//顶层
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				BlockPos pos5 = new BlockPos(x + i, y - 2, z + j);
				BlockState state5 = serverLevel.getBlockState(pos5);
				if ((state5.is(BlockTags.REPLACEABLE_BY_TREES) || state5.is(Blocks.AIR))) {
					if (i == 0 || j == 0 || serverLevel.getRandom().nextFloat() > 0.5f) {
						serverLevel.setBlock(pos5, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
					}
				}
			}
		}
		//中心
		for (int n = 3; n < 7; ++n) {
			for (int i = -2; i < 3; ++i) {
				for (int j = -2; j < 3; ++j) {
					BlockPos pos6 = new BlockPos(x + i, y - n, z + j);
					BlockState state6 = serverLevel.getBlockState(pos6);
					if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
						serverLevel.setBlock(pos6, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
					}
				}
			}
		}
		//两边
		for (int n = 4; n < 6; ++n) {
			for (int i = -2; i < 3; ++i) {
				for (int j = -3; j < 4; ++j) {
					BlockPos pos6 = new BlockPos(x + i, y - n, z + j);
					BlockState state6 = serverLevel.getBlockState(pos6);
					if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
						if (n == 4 || serverLevel.getRandom().nextFloat() > 0.25f) {
							serverLevel.setBlock(pos6, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
						}
					}
				}
			}
		}
		for (int n = 4; n < 6; ++n) {
			for (int i = -3; i < 4; ++i) {
				for (int j = -2; j < 3; ++j) {
					BlockPos pos6 = new BlockPos(x + i, y - n, z + j);
					BlockState state6 = serverLevel.getBlockState(pos6);
					if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
						if (n == 4 || serverLevel.getRandom().nextFloat() > 0.25f) {
							serverLevel.setBlock(pos6, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
						}
					}
				}
			}
		}
		//底部
		for (int i = -2; i < 3; ++i) {
			for (int j = -2; j < 3; ++j) {
				BlockPos pos6 = new BlockPos(x + i, y - 7, z + j);
				BlockState state6 = serverLevel.getBlockState(pos6);
				if ((state6.is(BlockTags.REPLACEABLE_BY_TREES) || state6.is(Blocks.AIR))) {
					if (i != 0 && j != 0 && i != -1 && j != -1 && i != 1 && j != 1 && serverLevel.getRandom().nextFloat() > 0.75f) {
						serverLevel.setBlock(pos6, JerotesVillageBlocks.WONDERLAND_LEAVES.get().defaultBlockState(), 3);
					}
				}
			}
		}
	}
}