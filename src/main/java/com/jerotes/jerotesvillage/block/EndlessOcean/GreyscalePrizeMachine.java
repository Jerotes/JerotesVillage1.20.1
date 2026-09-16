package com.jerotes.jerotesvillage.block.EndlessOcean;

import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.item.Greyscale;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

public class GreyscalePrizeMachine extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final IntegerProperty ELITE = BlockStateProperties.AGE_25;
	public MapCodec<? extends GreyscalePrizeMachine> codec() {
		return null;
	}

	public GreyscalePrizeMachine() {
		super(Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.GLASS).strength(25f, 100f).
				lightLevel(blockState -> blockState.getValue(BlockStateProperties.OPEN) ? 15 : 0));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(OPEN, false).setValue(ELITE, 0));
	}

	protected static final VoxelShape NORTH_AABB = Shapes.or(
			AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			AbstractCauldronBlock.box(0.0, 4.0, 3.0, 16.0, 16.0, 16.0));
	protected static final VoxelShape SOUTH_AABB = Shapes.or(
			AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			AbstractCauldronBlock.box(0.0, 4.0, 0.0, 16.0, 16.0, 13.0));
	protected static final VoxelShape WEST_AABB = Shapes.or(
			AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			AbstractCauldronBlock.box(3.0, 4.0, 0.0, 16.0, 16.0, 16.0));
	protected static final VoxelShape EAST_AABB = Shapes.or(
			AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			AbstractCauldronBlock.box(0.0, 4.0, 0.0, 13.0, 16.0, 16.0));

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		switch (blockState.getValue(FACING)) {
			default: {
				return EAST_AABB;
			}
			case WEST: {
				return WEST_AABB;
			}
			case SOUTH: {
				return SOUTH_AABB;
			}
			case NORTH:
		}
		return NORTH_AABB;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GreyscalePrizeMachineEntity(pos, state);
	}

	public boolean isOpen(BlockState blockState) {
		return blockState.getValue(OPEN);
	}

	public void setOpen(@Nullable Entity entity, Level level, BlockState blockState, BlockPos blockPos, boolean bl) {
		if (!blockState.is(this) || blockState.getValue(OPEN) == bl) {
			return;
		}
		level.setBlock(blockPos, (BlockState)blockState.setValue(OPEN, bl), 10);
		if (bl) {
			level.playSound(null, blockPos, SoundEvents.NOTE_BLOCK_BASS.get(), SoundSource.NEUTRAL, 5.0f, 1.0f);
			level.playSound(null, blockPos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.NEUTRAL, 5.0f, 1.0f);
		}
		else {
			level.playSound(null, blockPos, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundSource.NEUTRAL, 5.0f, 1.0f);
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
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
		if (blockentity instanceof GreyscalePrizeMachineEntity) {
			((GreyscalePrizeMachineEntity)blockentity).recheckOpen();
		}
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (!isOpen(blockState) && player.getItemInHand(interactionHand).getItem() == JerotesVillageItems.GREYSCALE.get()) {
			super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
			if (level.isClientSide()) {
				return InteractionResult.SUCCESS;
			} else {
				if (blockState.getBlock() instanceof GreyscalePrizeMachine greyscalePrizeMachine && player.getItemInHand(interactionHand).getItem() instanceof Greyscale greyscale) {
					greyscalePrizeMachine.setOpen(player, level, blockState, blockPos, true);
					BlockEntity blockentity = level.getBlockEntity(blockPos);
					if (blockentity instanceof GreyscalePrizeMachineEntity greyscalePrizeMachineEntity) {
						greyscalePrizeMachineEntity.fishLevel = greyscale.getFishSize(player.getItemInHand(interactionHand)) + player.getAttributeValue(Attributes.LUCK) * 3;
					}
					if (!player.getAbilities().instabuild) {
						player.getItemInHand(interactionHand).shrink(1);
					}
				}
				return InteractionResult.CONSUME;
			}
		}
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}
	public void useDispense(BlockState blockState, Level level, BlockPos blockPos, ItemStack itemStack) {
		if (!isOpen(blockState) && itemStack.getItem() == JerotesVillageItems.GREYSCALE.get()) {
			if (level.isClientSide()) {
				return;
			}
			else {
				if (blockState.getBlock() instanceof GreyscalePrizeMachine greyscalePrizeMachine && itemStack.getItem() instanceof Greyscale greyscale) {
					greyscalePrizeMachine.setOpen(null, level, blockState, blockPos, true);
					BlockEntity blockentity = level.getBlockEntity(blockPos);
					if (blockentity instanceof GreyscalePrizeMachineEntity greyscalePrizeMachineEntity) {
						greyscalePrizeMachineEntity.fishLevel = greyscale.getFishSize(itemStack);
					}
					itemStack.shrink(1);
				}
			}
		}
	}

		@Override
	public void onPlace(BlockState blockState, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockState, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 30);
	}
	@javax.annotation.Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return GreyscalePrizeMachineEntity.createTableTicker(level, blockEntityType, JerotesVillageBlockEntityType.GREYSCALE_PRIZE_MACHINE.get());
	}
	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED).booleanValue()) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof GreyscalePrizeMachineEntity) {
				((GreyscalePrizeMachineEntity)blockentity).setCustomName(itemStack.getHoverName());
			}
		}
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
		builder.add(FACING, WATERLOGGED, OPEN, ELITE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		boolean flag = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER;
		Level level = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();
		if (blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext)) {
			boolean bl = level.hasNeighborSignal(blockPos) || level.hasNeighborSignal(blockPos.above());
			return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag).setValue(OPEN, bl).setValue(ELITE, 0);
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
