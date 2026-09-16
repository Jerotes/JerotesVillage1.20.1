package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;

public class PinkSlimeBlockOn extends SlimeBlock {
	public PinkSlimeBlockOn() {
		super(Properties.of().mapColor(MapColor.COLOR_PINK).friction(0.8f).sound(SoundType.SLIME_BLOCK).noOcclusion());
	}

	@Override
	public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
		if (entity.isSuppressingBounce()) {
			super.fallOn(level, blockState, blockPos, entity, f);
		} else {
			entity.causeFallDamage(f, 3.5f, level.damageSources().fall());
		}
	}
	public boolean isSlimeBlock(BlockState state) {
		return true;
	}
	public boolean isStickyBlock(BlockState state) {
		return true;
	}

	public boolean canStickTo(BlockState state, BlockState other) {
		return !(state.isStickyBlock() && other.isStickyBlock());
	}

	@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		return true;
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(blockstate, level, pos, neighborBlock, fromPos, moving);
		if (!level.hasNeighborSignal(pos) && level instanceof ServerLevel serverLevel) {
			serverLevel.setBlock(pos, JerotesVillageBlocks.PINK_SLIME_BLOCK.get().defaultBlockState(), 2);
		}
	}
}
