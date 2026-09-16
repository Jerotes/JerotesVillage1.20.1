package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class PinkCactus extends CactusBlock {
	public PinkCactus() {
		super(Properties.of().mapColor(MapColor.COLOR_PINK).randomTicks().strength(0.4f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
			if (!blockState2.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) continue;
			return false;
		}
		BlockState blockState3 = levelReader.getBlockState(blockPos.below());
		return (blockState3.is(JerotesVillageBlocks.PINK_CACTUS.get()) || blockState3.is(BlockTags.SAND)) && !levelReader.getBlockState(blockPos.above()).liquid();
	}


	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockPos blockPos2 = blockPos.above();
		if (!serverLevel.isEmptyBlock(blockPos2)) {
			return;
		}
		int n = 1;
		int n2 = blockState.getValue(AGE);
		while (serverLevel.getBlockState(blockPos.below(n)).is(this)) {
			if (++n != 3 || n2 != 15) continue;
			return;
		}
		if (n2 == 8 && this.canSurvive(this.defaultBlockState(), serverLevel, blockPos.above())) {
			double d;
			double d2 = d = n >= 3 ? 0.25 : 0.1;
			if (randomSource.nextDouble() <= d) {
				serverLevel.setBlockAndUpdate(blockPos2, JerotesVillageBlocks.PINK_CACTUS_FLOWER.get().defaultBlockState());
			}
		} else if (n2 == 15 && n < 3) {
			serverLevel.setBlockAndUpdate(blockPos2, this.defaultBlockState());
			BlockState blockState2 = (BlockState)blockState.setValue(AGE, 0);
			serverLevel.setBlock(blockPos, blockState2, 260);
			serverLevel.neighborChanged(blockState2, blockPos2, this, blockPos, false);
		}
		if (n2 < 15) {
			serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(AGE, n2 + 1), 260);
		}
	}
}
