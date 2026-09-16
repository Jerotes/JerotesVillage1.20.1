package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PinkCactusFlower extends BushBlock {
	private static final VoxelShape SHAPE = PurpleSandTallDryGrassBlock.column(14.0, 0.0, 12.0);
	public PinkCactusFlower() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().ignitedByLava().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public VoxelShape getShape(BlockState p_53517_, BlockGetter p_53518_, BlockPos p_53519_, CollisionContext p_53520_) {
		Vec3 vec3 = p_53517_.getOffset(p_53518_, p_53519_);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		BlockState blockState2 = blockGetter.getBlockState(blockPos);
		return blockState2.is(JerotesVillageBlocks.PINK_CACTUS.get()) || blockState2.is(Blocks.FARMLAND) || blockState2.isFaceSturdy(blockGetter, blockPos, Direction.UP, SupportType.CENTER);
	}
}
