package com.jerotes.jerotesvillage.block.WildernessWoodland;

import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MysterestFlower extends FlowerBlock {
	public MysterestFlower() {
		super(MobEffects.LUCK, 6, Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		if (!(world.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD)){
			if (!world.isClientSide) {
				world.setBlock(BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), Blocks.DEAD_BUSH.defaultBlockState(), 3);
			}
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
