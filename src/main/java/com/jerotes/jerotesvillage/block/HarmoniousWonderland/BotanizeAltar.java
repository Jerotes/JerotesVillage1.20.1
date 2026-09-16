package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BotanizeAltar extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape BOUNDING_BOX = Block.box(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);
	public MapCodec<? extends BotanizeAltar> codec() {
		return null;
	}

	public BotanizeAltar() {
		super(Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.WOOD).strength(6f, 9f));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new BotanizeAltarEntity(p_153215_, p_153216_);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return BotanizeAltarEntity.getTicker(level, blockState, blockEntityType);
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
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		//仙境战书
		if (level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
			if (itemStack.getItem() instanceof WonderlandChallenge wonderlandChallenge &&
					!player.getCooldowns().isOnCooldown(wonderlandChallenge)) {
				if (!(level.getBiome(blockPos).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND))) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.use_wonderland_challenge").withStyle(ChatFormatting.RED), true);
					return InteractionResult.SUCCESS;
				}
				if (!BossSpawn.ChallengeMiddleBoss(serverPlayer)) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.challenge_middle_boss_fail").withStyle(ChatFormatting.RED), true);
					return InteractionResult.SUCCESS;
				}
				else if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.CELESTIAL_COILVINE.get(), player)) {
					BlockEntity blockEntity = level.getBlockEntity(blockPos);
					if (blockEntity instanceof BotanizeAltarEntity botanizeAltarEntity) {
						if (botanizeAltarEntity.getSummonTick() <= 0) {
							player.swing(InteractionHand.MAIN_HAND);
							botanizeAltarEntity.setSummonTick(1);
							player.getCooldowns().addCooldown(wonderlandChallenge, 1800);
							BossSpawn.CelestialCoilvine(player);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							return InteractionResult.SUCCESS;
						}
					}
				}
			}
		}
		if (!BotanizeAltarEntity.canOffer(itemStack)) {
			return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
		}
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (level.getBlockEntity(blockPos) instanceof BotanizeAltarEntity botanizeAltarEntity) {
			botanizeAltarEntity.offer(player, itemStack);
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return BOUNDING_BOX;
	}
}
