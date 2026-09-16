package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.item.WonderlandChallenge;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.util.SpawnRules;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TyrantOfTheMountainRealmStatue extends BaseEntityBlock {
	public static final int PART_COUNT = 5;
	public static final IntegerProperty PART = IntegerProperty.create("part", 0, PART_COUNT - 1);
	protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public TyrantOfTheMountainRealmStatue() {
		super(Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(6f, 9f).pushReaction(PushReaction.NORMAL)
				.hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, PART);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public void onPlace(BlockState blockState, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockState, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 30);
	}


	@javax.annotation.Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		if (blockState.getValue(PART) != 0) {
			return null;
		}
		return TyrantOfTheMountainRealmStatueBlockEntity.createTableTicker(level, blockEntityType, JerotesVillageBlockEntityType.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		if (blockPos.getY() + PART_COUNT - 1 >= level.getMaxBuildHeight()) {
			return null;
		}
		for (int i = 1; i < PART_COUNT; ++i) {
			if (!level.getBlockState(blockPos.above(i)).canBeReplaced(context)) {
				return null;
			}
		}
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
		for (int i = 1; i < PART_COUNT; ++i) {
			level.setBlock(blockPos.above(i), blockState.setValue(PART, i), 3);
		}
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos neighborPos) {
		int part = blockState.getValue(PART);
		if (direction == Direction.DOWN) {
			if (part == 0 || (neighborState.is(this) && neighborState.getValue(PART) == part - 1)) {
				return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
			}
			return Blocks.AIR.defaultBlockState();
		}
		if (direction == Direction.UP) {
			if (part == PART_COUNT - 1 || (neighborState.is(this) && neighborState.getValue(PART) == part + 1)) {
				return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
			}
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	public static boolean isAdvancementLocked(ItemStack itemStack) {
		return itemStack.is(JerotesVillageItems.MEROR_METAL_HELMET.get())
				|| itemStack.is(JerotesVillageItems.MEROR_METAL_CHESTPLATE.get())
				|| itemStack.is(JerotesVillageItems.MEROR_METAL_LEGGINGS.get())
				|| itemStack.is(JerotesVillageItems.MEROR_METAL_BOOTS.get())
				|| itemStack.is(JerotesVillageItems.MEROR_METAL_JAVELIN.get());
	}

	@Nullable
	public static Item getConvertResult(ItemStack itemStack) {
		if (itemStack.is(JerotesVillageItems.MEROR_METAL_HELMET.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_HELMET.get();
		}
		if (itemStack.is(JerotesVillageItems.MEROR_METAL_CHESTPLATE.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get();
		}
		if (itemStack.is(JerotesVillageItems.MEROR_METAL_LEGGINGS.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get();
		}
		if (itemStack.is(JerotesVillageItems.MEROR_METAL_BOOTS.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get();
		}
		if (itemStack.is(JerotesVillageItems.MEROR_METAL_JAVELIN.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get();
		}
		if (itemStack.is(JerotesVillageItems.CONTINUOUS_CUT_LEAF.get())) {
			return JerotesVillageItems.MOUNTAIN_REALM_NINE_RINGED_DAO.get();
		}
		if (itemStack.is(JerotesVillageItems.CELESTIAL_COILVINE_BROKEN_VINE.get())) {
			return JerotesVillageItems.TYRANT_OF_THE_MOUNTAIN_REALM_HELMET_PLUME.get();
		}
		if (itemStack.is(JerotesVillageItems.MUSIC_DISC_CELESTIAL_COILVINE.get())) {
			return JerotesVillageItems.MUSIC_DISC_TYRANT_OF_THE_MOUNTAIN_REALM.get();
		}
		return null;
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockPos basePos = blockPos.below(blockState.getValue(PART));
		ItemStack itemStack = player.getItemInHand(hand);
		//镇山兑换
		Item convertItem = getConvertResult(itemStack);
		if (convertItem != null) {
			if (level.isClientSide) {
				return InteractionResult.SUCCESS;
			}
			//莫厄金属系列需要先完成密谋或金甲金盔灿烂新
			if (isAdvancementLocked(itemStack) && player instanceof ServerPlayer serverPlayer
					&& !AdvancementEvent.AdvancementDone(serverPlayer, "jerotesvillage:plot")
					&& !AdvancementEvent.AdvancementDone(serverPlayer, "jerotesvillage:golden_armor_tyrant")) {
				player.displayClientMessage(Component.translatable("message.jerotesvillage.convert_locked").withStyle(ChatFormatting.RED), true);
				return InteractionResult.CONSUME;
			}
			if (level.getBlockEntity(basePos) instanceof TyrantOfTheMountainRealmStatueBlockEntity tyrantOfTheMountainRealmStatueEntity) {
				tyrantOfTheMountainRealmStatueEntity.convert(player, itemStack, convertItem);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			}
			return InteractionResult.CONSUME;
		}
		super.use(blockState, level, blockPos, player, hand, hit);
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		//仙境战书
		if (level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
			if (itemStack.getItem() instanceof WonderlandChallenge wonderlandChallenge &&
					!player.getCooldowns().isOnCooldown(wonderlandChallenge)) {
				if (!(level.getBiome(basePos).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND))) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.use_wonderland_challenge").withStyle(ChatFormatting.RED), true);
					return InteractionResult.SUCCESS;
				}
				if (!BossSpawn.ChallengeMiddleBoss(serverPlayer)) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.challenge_middle_boss_fail").withStyle(ChatFormatting.RED), true);
					return InteractionResult.SUCCESS;
				}
				else if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.TYRANT_OF_THE_MOUNTAIN_REALM.get(), player)) {
					BlockEntity blockEntity = level.getBlockEntity(basePos);
					if (blockEntity instanceof TyrantOfTheMountainRealmStatueBlockEntity tyrantOfTheMountainRealmStatueEntity) {
					if (tyrantOfTheMountainRealmStatueEntity.summonTick <= 0) {
						player.swing(InteractionHand.MAIN_HAND);
						if (!level.isClientSide()) {
							tyrantOfTheMountainRealmStatueEntity.summonTick = 1;
						}
						player.getCooldowns().addCooldown(wonderlandChallenge, 1800);
						BossSpawn.TyrantOfTheMountainRealm(player);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						return InteractionResult.SUCCESS;
					}
					}
				}
			}
		}

		return InteractionResult.CONSUME;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos.below(state.getValue(PART)));
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TyrantOfTheMountainRealmStatueBlockEntity(pos, state);
	}

	@Override
	public boolean canSurvive(BlockState blockState, net.minecraft.world.level.LevelReader levelReader, BlockPos blockPos) {
		int part = blockState.getValue(PART);
		if (part == 0) {
			return super.canSurvive(blockState, levelReader, blockPos);
		}
		BlockState belowState = levelReader.getBlockState(blockPos.below());
		return belowState.is(this) && belowState.getValue(PART) == part - 1;
	}

	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos.below(state.getValue(PART)));
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide() && player.isCreative()) {
			BlockPos basePos = pos.below(state.getValue(PART));
			for (int i = 0; i < PART_COUNT; ++i) {
				BlockPos partPos = basePos.above(i);
				if (partPos.equals(pos)) {
					continue;
				}
				BlockState partState = level.getBlockState(partPos);
				if (partState.is(this)) {
					level.setBlock(partPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
					level.levelEvent(player, 2001, partPos, Block.getId(partState));
				}
			}
		}
		super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
		super.playerDestroy(level, player, pos, blockState, blockEntity, itemStack);
	}


	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof TyrantOfTheMountainRealmStatueBlockEntity be) {
				Containers.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return blockState.getValue(PART) == 0 ? RenderShape.MODEL : RenderShape.INVISIBLE;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos.below(blockState.getValue(PART)));
		if (tileentity instanceof TyrantOfTheMountainRealmStatueBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}
