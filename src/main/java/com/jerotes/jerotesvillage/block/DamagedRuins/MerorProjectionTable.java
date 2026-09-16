package com.jerotes.jerotesvillage.block.DamagedRuins;

import com.jerotes.jerotesvillage.item.ItemBossDrop;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class MerorProjectionTable extends Block implements EntityBlock {
	protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public MerorProjectionTable() {
		super(Properties.of().sound(SoundType.METAL).strength(65f, 1800f).pushReaction(PushReaction.NORMAL)
				.hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 5;
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
		super.use(blockState, level, pos, entity, hand, hit);
		ItemStack itemstack = entity.getItemInHand(hand);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		float facing = 0f;
		if (blockState.getValue(MerorProjectionTable.FACING) == Direction.EAST) {
			facing = 270f;
		} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.NORTH) {
			facing = 180f;
		} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.WEST) {
			facing = 90f;
		} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.SOUTH) {
			facing = 0f;
		}
		if (blockEntity instanceof MerorProjectionTableEntity merorProjectionTable) {
			if (itemstack.getItem() instanceof ItemBossDrop && merorProjectionTable.getItem(0).isEmpty()) {
				merorProjectionTable.setItem(0, itemstack.copyWithCount(1));
				MerorProjectionTableEntity.summonBoss(pos, merorProjectionTable, itemstack, facing, level);
				if (!entity.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
			}
			else if (entity.isShiftKeyDown()) {
				if (!merorProjectionTable.getItem(0).isEmpty())
					MerorProjectionTableEntity.dropItemStackSelf(level, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, merorProjectionTable.getItem(0));
				merorProjectionTable.setItem(0, ItemStack.EMPTY);
				if (level instanceof ServerLevel serverLevel && serverLevel.getEntity(merorProjectionTable.summonBoss) instanceof LivingEntity livingEntity) {
					livingEntity.discard();
				}
				MerorProjectionTableEntity.summonBoss(pos, merorProjectionTable, itemstack, facing, level);
			}
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MerorProjectionTableEntity(pos, state);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof MerorProjectionTableEntity merorProjectionTable) {
				Containers.dropContents(level, pos, merorProjectionTable);
				level.updateNeighbourForOutputSignal(pos, this);
				if (merorProjectionTable.summonBossed && merorProjectionTable.summonBoss != null) {
					if (level instanceof ServerLevel serverLevel && serverLevel.getEntity(merorProjectionTable.summonBoss) instanceof LivingEntity livingEntity) {
						livingEntity.discard();
						merorProjectionTable.summonBoss = null;
					}
				}
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof MerorProjectionTableEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
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