package com.jerotes.jerotesvillage.block.BrightGrassland;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TallBrightGrass extends TallFlowerBlock {
	public TallBrightGrass() {
		super(Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().lightLevel(s -> 3).sound(SoundType.GRASS).offsetType(OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY));
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 100;
	}
	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		popResource(serverLevel, blockPos, new ItemStack(JerotesVillageItems.BRIGHT_GRASS.get()));
	}
}
