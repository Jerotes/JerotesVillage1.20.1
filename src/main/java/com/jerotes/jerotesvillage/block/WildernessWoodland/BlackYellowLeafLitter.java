package com.jerotes.jerotesvillage.block.WildernessWoodland;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlackYellowLeafLitter extends BushBlock {
	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty AMOUNT = BlockStateProperties.AGE_4;
	private final Function<BlockState, VoxelShape> shapes;

	public BlackYellowLeafLitter() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
		this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).
				setValue(FACING, Direction.NORTH)).
				setValue(AMOUNT, 1));
		this.shapes = this.makeShapes();
	}
	private Function<BlockState, VoxelShape> makeShapes() {
		return getShapeForEachStates(this.getShapeCalculator(FACING, AMOUNT));
	}
	protected Function<BlockState, VoxelShape> getShapeForEachStates(Function<BlockState, VoxelShape> function) {
		Map<BlockState, VoxelShape> shapeMap = this.stateDefinition.getPossibleStates().stream()
				.collect(Collectors.toMap(
						Function.identity(),
						function,
						(v1, v2) -> v1,
						HashMap::new
				));

		return shapeMap::get;
	}
	@Override
	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return (BlockState)blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}
	@Override
	public BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
	}
	@Override
	public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
		if (this.canBeReplaced(blockState, blockPlaceContext, AMOUNT)) {
			return true;
		}
		return super.canBeReplaced(blockState, blockPlaceContext);
	}
	public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext, IntegerProperty integerProperty) {
		return !blockPlaceContext.isSecondaryUseActive() && blockPlaceContext.getItemInHand().is(blockState.getBlock().asItem()) && blockState.getValue(integerProperty) < 4;
	}
	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		BlockPos blockPos2 = blockPos.below();
		return levelReader.getBlockState(blockPos2).isFaceSturdy(levelReader, blockPos2, Direction.UP);
	}
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.shapes.apply(blockState);
	}
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return this.getStateForPlacement(blockPlaceContext, this, AMOUNT, FACING);
	}
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext, Block block, IntegerProperty integerProperty, EnumProperty<Direction> enumProperty) {
		BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
		if (blockState.is(block)) {
			return (BlockState)blockState.setValue(integerProperty, Math.min(4, blockState.getValue(integerProperty) + 1));
		}
		return (BlockState)block.defaultBlockState().setValue(enumProperty, blockPlaceContext.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, AMOUNT);
	}
	public Function<BlockState, VoxelShape> getShapeCalculator(EnumProperty<Direction> enumProperty, IntegerProperty integerProperty) {
		Map<Direction, VoxelShape> shapeMap = new EnumMap<>(Direction.class);
		double height = this.getShapeHeight();
		shapeMap.put(Direction.NORTH, Block.box(0.0, 0.0, 0.0, 8.0, height, 8.0));
		shapeMap.put(Direction.EAST, Block.box(8.0, 0.0, 0.0, 16.0, height, 8.0));
		shapeMap.put(Direction.SOUTH, Block.box(8.0, 0.0, 8.0, 16.0, height, 16.0));
		shapeMap.put(Direction.WEST, Block.box(0.0, 0.0, 8.0, 8.0, height, 16.0));

		return blockState -> {
			VoxelShape result = Shapes.empty();
			Direction direction = blockState.getValue(enumProperty);
			int count = blockState.getValue(integerProperty);

			for (int i = 0; i < count; i++) {
				result = Shapes.or(result, shapeMap.get(direction));
				direction = direction.getCounterClockWise();
			}
			return result;
		};
	}

	public double getShapeHeight() {
		return 1.0;
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
