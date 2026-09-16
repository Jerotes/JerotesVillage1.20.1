package com.jerotes.jerotesvillage.block.BrightGrassland;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SecondRoundAloeCrop extends CropBlock {
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 3.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 5.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 7.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 9.0, 11.0)};

	public SecondRoundAloeCrop() {
		super(Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return JerotesVillageItems.SECOND_ROUND_ALOE_LEAF.get();
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE_BY_AGE[this.getAge(blockState)];
	}
}
