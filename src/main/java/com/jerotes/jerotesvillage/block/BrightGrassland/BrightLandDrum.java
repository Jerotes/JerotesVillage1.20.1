package com.jerotes.jerotesvillage.block.BrightGrassland;

import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BrightLandDrum extends Block {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<AttachFace> FACE = FaceAttachedHorizontalDirectionalBlock.FACE;
	public BrightLandDrum() {
		super(Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.METAL).strength(10f, 10f	).requiresCorrectToolForDrops().noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL).setValue(POWERED, false));
	}

	public static void playNote(Entity entity, BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Block block) {
		serverLevel.blockEvent(blockPos, block, 0, 0);
		serverLevel.gameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, blockPos);
	}

	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		boolean bl2 = level.hasNeighborSignal(blockPos);
		if (bl2 != blockState.getValue(POWERED)) {
			if (bl2) {
				this.playNote(null, blockState, level, blockPos);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
			}
			level.setBlock(blockPos, blockState.setValue(POWERED, bl2), 3);
		}
		super.neighborChanged(blockState, level, blockPos, neighborBlock, fromPos, moving);
	}

	public void playNote(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
		level.blockEvent(blockPos, this, 0, 0);
		level.gameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, blockPos);
	}

	@Override
	public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int n, int n2) {
		float voice = (15 - level.getBestNeighborSignal(blockPos)) * 0.5f + 0.1f;
		int x = blockPos.getX();
		int y = blockPos.getY();
		int z = blockPos.getZ();

		float voiceDistance = 10f;
		if (level.getBlockState(blockPos.below()).getBlock() == Blocks.SCAFFOLDING) {
			voiceDistance = 20f;
		}
		level.playSound(null, x, y, z, JerotesVillageSoundEvents.BRIGHT_LAND_DRUM, SoundSource.BLOCKS, voiceDistance, voice);
		level.addParticle(ParticleTypes.NOTE, (double) x + 0.5, (double) y + 1.2, (double) z + 0.5, voice, 0.0, 0.0);
		return true;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, FACE, POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACE, faceForDirection(context.getNearestLookingDirection())).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	private AttachFace faceForDirection(Direction direction) {
		if (direction.getAxis() == Direction.Axis.Y)
			return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
		else
			return AttachFace.WALL;
	}
}
