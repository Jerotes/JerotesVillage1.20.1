package com.jerotes.jerotesvillage.block.PurpleDesert;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HagsCauldron extends Block{
	public HagsCauldron(Properties properties) {
		super(properties);
	}

	private static final VoxelShape INSIDE =
			AbstractCauldronBlock.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
	protected static final VoxelShape SHAPE = Shapes.join(
			Shapes.block(),
			Shapes.or(AbstractCauldronBlock.box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
					AbstractCauldronBlock.box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
					AbstractCauldronBlock.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE),
			BooleanOp.ONLY_FIRST);

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return INSIDE;
	}
}
