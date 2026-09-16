package com.jerotes.jerotesvillage.block.WildernessWoodland;

import com.jerotes.jerotes.util.Main;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildernessThorns extends BushBlock {
	private static final VoxelShape AABB = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
	public MapCodec<? extends WildernessThorns> codec() {
		return null;
	}
	public WildernessThorns() {
		super(Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return AABB;
	}

	@Override
	public boolean isPathfindable(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
		return false;
	}

	@Override
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Mob mob) {
		return BlockPathTypes.DAMAGE_OTHER;
	}

	public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && !livingEntity.hasItemInSlot(EquipmentSlot.FEET) && (Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity)) && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
			entity.makeStuckInBlock(blockState, new Vec3((double)0.8F, 0.75D, (double)0.8F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double d0 = Math.abs(entity.getX() - entity.xOld);
				double d1 = Math.abs(entity.getZ() - entity.zOld);
				if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
					entity.hurt(level.damageSources().sweetBerryBush(), 1.0F);
				}
			}
		}
	}
}
