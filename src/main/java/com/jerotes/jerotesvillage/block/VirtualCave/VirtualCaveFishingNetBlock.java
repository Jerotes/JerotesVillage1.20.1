package com.jerotes.jerotesvillage.block.VirtualCave;

import com.jerotes.jerotesvillage.entity.Animal.DownySpiderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class VirtualCaveFishingNetBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public VirtualCaveFishingNetBlock() {
		super(Properties.of().sound(SoundType.WOOL).strength(1f, 1f).forceSolidOn().noCollission().pushReaction(PushReaction.NORMAL).speedFactor(0.4f).jumpFactor(0.4f).noOcclusion().dynamicShape());
		this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false)));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return box(2, 0, 2, 14, 16, 14);
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		if (blockState.getValue(WATERLOGGED).booleanValue()) {
			return Fluids.WATER.getSource(false);
		}
		return super.getFluidState(blockState);
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED).booleanValue()) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockPos blockPos;
		Level level;
		return (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, (level = blockPlaceContext.getLevel()).getFluidState(blockPos = blockPlaceContext.getClickedPos()).getType() == Fluids.WATER));
	}

	@Override
	public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		entity.makeStuckInBlock(blockState, new Vec3(0.25, 0.05, 0.25));
		if (!level.isClientSide) {
			if (entity instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
				if (Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity)) {
					if ((livingEntity instanceof AbstractFish)) {
						livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 3));
					}
					if (!(livingEntity instanceof Spider) && !(livingEntity instanceof DownySpiderEntity)) {
						if (livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0.3) {
							if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 2));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.5) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 1));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.8) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 0));
							}
						} else if (livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0.5) {
							if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 1));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.5) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 1));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.8) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 0, 0));
							}
						} else if (livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) > 0.5) {
							if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 0));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.5) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 1, 0));
							} else if (livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) <= 0.8) {
								livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 0, 0));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isPathfindable(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
		return false;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 30;
	}
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}
}