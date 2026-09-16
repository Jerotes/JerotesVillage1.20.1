package com.jerotes.jerotesvillage.block.BitterCold;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TorchOfFirmIce extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final IntegerProperty ELITE = BlockStateProperties.AGE_25;

	private static final VoxelShape OPEN_BOUNDING_BOX_DOWN = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

	private static final VoxelShape OPEN_BOUNDING_BOX_UP = Block.box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);


	protected static final VoxelShape BOUNDING_BOX_DOWN = Shapes.or(
			AbstractCauldronBlock.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0),
			AbstractCauldronBlock.box(4.0, 2.0, 4.0, 12.0, 4.0, 12.0),
			AbstractCauldronBlock.box(6.0, 4.0, 6.0, 10.0, 16.0, 10.0));

	protected static final VoxelShape BOUNDING_BOX_UP = Shapes.or(
			AbstractCauldronBlock.box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0),
			AbstractCauldronBlock.box(5.0, 2.0, 5.0, 11.0, 4.0, 11.0),
			AbstractCauldronBlock.box(3.0, 4.0, 3.0, 13.0, 6.0, 13.0),
			AbstractCauldronBlock.box(4.0, 6.0, 4.0, 12.0, 10.0, 12.0),
			AbstractCauldronBlock.box(2.0, 10.0, 2.0, 14.0, 12.0, 14.0));


	public MapCodec<? extends TorchOfFirmIce> codec() {
		return null;
	}

	public TorchOfFirmIce() {
		super(Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.GLASS).strength(25f, 100f).lightLevel(blockState -> 10));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false).setValue(OPEN, false).setValue(ELITE, 0));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		boolean up = state.getValue(HALF) == DoubleBlockHalf.UPPER;
		return new TorchOfFirmIceEntity(pos, state, up);
	}
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		boolean open = blockState.getValue(OPEN);

		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			BlockPos blockPos2 = blockPos.below();
			BlockState blockState2 = blockGetter.getBlockState(blockPos2);
			if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
				open = blockState2.getValue(OPEN);
			}
			Object object = blockGetter.getBlockEntity(blockPos2);
			if (object instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
				open = torchOfFirmIceEntity.open < 40;
			}
		}
		else {
			Object object = blockGetter.getBlockEntity(blockPos);
			if (object instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
				open = torchOfFirmIceEntity.open < 40;
			}
		}

		if (open) {
			if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
				return OPEN_BOUNDING_BOX_UP;
			}
			return OPEN_BOUNDING_BOX_DOWN;
		}
		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			return BOUNDING_BOX_UP;
		}
		return BOUNDING_BOX_DOWN;
	}
	@Override
	public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		boolean open = blockState.getValue(OPEN);

		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			BlockPos blockPos2 = blockPos.below();
			BlockState blockState2 = blockGetter.getBlockState(blockPos2);
			if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
				open = blockState2.getValue(OPEN);
			}
			Object object = blockGetter.getBlockEntity(blockPos2);
			if (object instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
				open = torchOfFirmIceEntity.open < 40;
			}
		}
		else {
			Object object = blockGetter.getBlockEntity(blockPos);
			if (object instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
				open = torchOfFirmIceEntity.open < 40;
			}
		}

		if (open) {
			if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
				return OPEN_BOUNDING_BOX_UP;
			}
			return OPEN_BOUNDING_BOX_DOWN;
		}
		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			return BOUNDING_BOX_UP;
		}
		return BOUNDING_BOX_DOWN;
	}

	//1.20.4↑//
//	@Override
//	public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
//		if (!(level.isClientSide() || !player.isCreative() && player.hasCorrectToolForDrops(blockState))) {
//			preventDropFromBottomPart(level, blockPos, blockState, player);
//		}
//		return super.playerWillDestroy(level, blockPos, blockState, player);
//	}
	//1.20.1//
	public void playerWillDestroy(Level p_52755_, BlockPos p_52756_, BlockState p_52757_, Player p_52758_) {
		if (!p_52755_.isClientSide && p_52758_.isCreative()) {
			preventDropFromBottomPart(p_52755_, p_52756_, p_52757_, p_52758_);
		}

		super.playerWillDestroy(p_52755_, p_52756_, p_52757_, p_52758_);
	}

	protected static void preventDropFromBottomPart(Level p_52904_, BlockPos p_52905_, BlockState p_52906_, Player p_52907_) {
		DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)p_52906_.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = p_52905_.below();
			BlockState blockstate = p_52904_.getBlockState(blockpos);
			if (blockstate.is(p_52906_.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				p_52904_.setBlock(blockpos, blockstate1, 35);
				p_52904_.levelEvent(p_52907_, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	public boolean isOpen(BlockState blockState) {
		return blockState.getValue(OPEN);
	}

	public void setOpen(@Nullable Entity entity, Level level, BlockState blockState, BlockPos blockPos, boolean bl) {
		if (!blockState.is(this) || blockState.getValue(OPEN) == bl) {
			return;
		}
		level.setBlock(blockPos, (BlockState)blockState.setValue(OPEN, bl), 10);

		if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
			BlockPos blockPos2 = blockPos.above();
			BlockState blockState2 = level.getBlockState(blockPos2);
			if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.UPPER) {
				level.setBlock(blockPos2, (BlockState) blockState2.setValue(OPEN, bl), 10);
			}
		}

		if (bl) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SEAWAVE_SHOCKWAVE.get(), blockPos.getX() + 0.5f, blockPos.getY() + 0.1f, blockPos.getZ() + 0.5f, 0, 0, 0, 0, 0);
			}
			level.playSound(null, blockPos, JerotesSoundEvents.SPELL, SoundSource.NEUTRAL, 5.0f, 1.0f);
		}
		level.gameEvent(entity, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, blockPos);
	}


	public int getElite(BlockState blockState) {
		return blockState.getValue(ELITE);
	}
	public void setElite(@Nullable Entity entity, Level level, BlockState blockState, BlockPos blockPos, int n) {
		if (!blockState.is(this) || blockState.getValue(ELITE) == n) {
			return;
		}
		level.setBlock(blockPos, (BlockState)blockState.setValue(ELITE, n), 10);

		if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
			BlockPos blockPos2 = blockPos.above();
			BlockState blockState2 = level.getBlockState(blockPos2);
			if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.UPPER) {
				level.setBlock(blockPos2, (BlockState) blockState2.setValue(ELITE, n), 10);
			}
		}
	}

	@Override
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!blockState.is(blockState2.getBlock())) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof Container) {
				Containers.dropContents(level, blockPos, (Container)blockentity);
				level.updateNeighbourForOutputSignal(blockPos, this);
			}
			super.onRemove(blockState, level, blockPos, blockState2, bl);
		}
	}

	@Override
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
		if (blockentity instanceof TorchOfFirmIceEntity) {
			((TorchOfFirmIceEntity)blockentity).recheckOpen();
		}
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			BlockPos blockPos2 = blockPos.below();
			BlockState blockState2 = level.getBlockState(blockPos2);
			if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
				return use(blockState2, level, blockPos2, player, interactionHand, blockHitResult);
			}
			return InteractionResult.PASS;
		}

		if (!isOpen(blockState)) {
			super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
			if (level.isClientSide()) {
				return InteractionResult.SUCCESS;
			} else {
				BlockEntity blockEntity = level.getBlockEntity(blockPos);
				if (blockEntity instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
					player.openMenu(torchOfFirmIceEntity);
				}
				return InteractionResult.CONSUME;
			}
		}
		else {
			Object object = level.getBlockEntity(blockPos);
			if (object instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
				player.displayClientMessage(Component.translatable("message.jerotesvillage.torch_of_firm_ice_cooldown", (int)(torchOfFirmIceEntity.canUseCooldown / 20)).withStyle(ChatFormatting.AQUA), true);
			}
		}
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}


	@Override
	public void onPlace(BlockState blockState, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockState, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 30);
	}
	@javax.annotation.Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return TorchOfFirmIceEntity.createTableTicker(level, blockEntityType, JerotesVillageBlockEntityType.TORCH_OF_FIRM_ICE.get());
	}

	@Override
	public long getSeed(BlockState blockState, BlockPos blockPos) {
		return Mth.getSeed(blockPos.getX(), blockPos.below(blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)blockState.getValue(HALF);
		if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || blockState2.is(this) && blockState2.getValue(HALF) != doubleblockhalf) {
			return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		BlockPos blockPos2 = blockPos.above();
		level.setBlock(blockPos2, DoublePlantBlock.copyWaterloggedFrom(level, blockPos2, (BlockState)this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)), 3);
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof TorchOfFirmIceEntity) {
				((TorchOfFirmIceEntity)blockentity).setCustomName(itemStack.getHoverName());
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			BlockState blockState2 = levelReader.getBlockState(blockPos.below());
			return blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER;
		}
		return super.canSurvive(blockState, levelReader, blockPos);
	}

	@Override
	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, WATERLOGGED, OPEN, ELITE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		boolean flag = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER;
		Level level = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();
		if (blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext)) {
			boolean bl = level.hasNeighborSignal(blockPos) || level.hasNeighborSignal(blockPos.above());
			return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, flag).setValue(OPEN, bl).setValue(ELITE, 0);
		}
		return null;
	}

	@Override
	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return blockState.getValue(OPEN);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
