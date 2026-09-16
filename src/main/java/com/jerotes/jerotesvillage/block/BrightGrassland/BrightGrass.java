package com.jerotes.jerotesvillage.block.BrightGrassland;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BrightGrass extends TallGrassBlock {
	public BrightGrass() {
		super(Properties.of().mapColor(MapColor.PLANT).
				replaceable().noCollission().instabreak().lightLevel(s -> 3).sound(SoundType.GRASS).
				offsetType(OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY));
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		DoublePlantBlock doublePlantBlock = (DoublePlantBlock) JerotesVillageBlocks.TALL_BRIGHT_GRASS.get();
		if (doublePlantBlock.defaultBlockState().canSurvive(serverLevel, blockPos) && serverLevel.isEmptyBlock(blockPos.above())) {
			DoublePlantBlock.placeAt(serverLevel, doublePlantBlock.defaultBlockState(), blockPos, 2);
		}
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 100;
	}
}
