package com.jerotes.jerotesvillage.world.teleporter;

import com.jerotes.jerotesvillage.block.Other.SecondRoundWorldPortalBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class SecondRoundWorldPortalShape {
	private static final int MIN_WIDTH = 2;
	public static final int MAX_WIDTH = 256;
	private static final int MIN_HEIGHT = 2;
	public static final int MAX_HEIGHT = 256;
	private static final BlockBehaviour.StatePredicate FRAME = (p_77720_, p_77721_, p_77722_) -> {
		return p_77720_.getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get();
	};
	private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
	private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = 1.0D;
	private final LevelAccessor level;
	private final Direction.Axis axis;
	private final Direction rightDir;
	private int numPortalBlocks;
	@Nullable
	private BlockPos bottomLeft;
	private int height;
	private final int width;

	public static Optional<SecondRoundWorldPortalShape> findEmptyPortalShape(LevelAccessor levelAccessor, BlockPos blockPos, Direction.Axis axis) {
		return findPortalShape(levelAccessor, blockPos, (portalShape) -> {
			return portalShape.isValid() && portalShape.numPortalBlocks == 0;
		}, axis);
	}

	public static Optional<SecondRoundWorldPortalShape> findPortalShape(LevelAccessor levelAccessor, BlockPos blockPos, Predicate<SecondRoundWorldPortalShape> predicate, Direction.Axis axis) {
		Optional<SecondRoundWorldPortalShape> optional = Optional.of(new SecondRoundWorldPortalShape(levelAccessor, blockPos, axis)).filter(predicate);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(new SecondRoundWorldPortalShape(levelAccessor, blockPos, axis1)).filter(predicate);
		}
	}

	public SecondRoundWorldPortalShape(LevelAccessor levelAccessor, BlockPos blockPos, Direction.Axis axis) {
		this.level = levelAccessor;
		this.axis = axis;
		this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		this.bottomLeft = this.calculateBottomLeft(blockPos);
		if (this.bottomLeft == null) {
			this.bottomLeft = blockPos;
			this.width = 1;
			this.height = 1;
		} else {
			this.width = this.calculateWidth();
			if (this.width > 0) {
				this.height = this.calculateHeight();
			}
		}
	}

	@Nullable
	private BlockPos calculateBottomLeft(BlockPos blockPos) {
		for (int i = Math.max(this.level.getMinBuildHeight(), blockPos.getY() - 256); blockPos.getY() > i && isEmpty(this.level.getBlockState(blockPos.below())); blockPos = blockPos.below()) {
		}
		Direction direction = this.rightDir.getOpposite();
		int j = this.getDistanceUntilEdgeAboveFrame(blockPos, direction) - 1;
		return j < 0 ? null : blockPos.relative(direction, j);
	}

	private int calculateWidth() {
		int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
		return i >= 2 && i <= 256 ? i : 0;
	}

	private int getDistanceUntilEdgeAboveFrame(BlockPos blockPos, Direction direction) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		for (int i = 0; i <= 256; ++i) {
			blockpos$mutableblockpos.set(blockPos).move(direction, i);
			BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
			if (!isEmpty(blockstate)) {
				if (FRAME.test(blockstate, this.level, blockpos$mutableblockpos)) {
					return i;
				}
				break;
			}
			BlockState blockstate1 = this.level.getBlockState(blockpos$mutableblockpos.move(Direction.DOWN));
			if (!FRAME.test(blockstate1, this.level, blockpos$mutableblockpos)) {
				break;
			}
		}
		return 0;
	}

	private int calculateHeight() {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int i = this.getDistanceUntilTop(blockpos$mutableblockpos);
		return i >= 2 && i <= 256 && this.hasTopFrame(blockpos$mutableblockpos, i) ? i : 0;
	}

	private boolean hasTopFrame(BlockPos.MutableBlockPos blockPos, int n) {
		for (int i = 0; i < this.width; ++i) {
			BlockPos.MutableBlockPos mutableBlockPos = blockPos.set(this.bottomLeft).move(Direction.UP, n).move(this.rightDir, i);
			if (!FRAME.test(this.level.getBlockState(mutableBlockPos), this.level, mutableBlockPos)) {
				return false;
			}
		}
		return true;
	}

	private int getDistanceUntilTop(BlockPos.MutableBlockPos mutableBlockPos) {
		for (int i = 0; i < 256; ++i) {
			mutableBlockPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
			if (!FRAME.test(this.level.getBlockState(mutableBlockPos), this.level, mutableBlockPos)) {
				return i;
			}
			mutableBlockPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
			if (!FRAME.test(this.level.getBlockState(mutableBlockPos), this.level, mutableBlockPos)) {
				return i;
			}
			for (int j = 0; j < this.width; ++j) {
				mutableBlockPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
				BlockState blockstate = this.level.getBlockState(mutableBlockPos);
				if (!isEmpty(blockstate)) {
					return i;
				}
				if (blockstate.getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK.get()) {
					++this.numPortalBlocks;
				}
			}
		}
		return 256;
	}

	private static boolean isEmpty(BlockState blockState) {
		return blockState.isAir() || blockState.getBlock() == JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK.get();
	}

	public boolean isValid() {
		return this.bottomLeft != null && this.width >= 2 && this.width <= 256 && this.height >= 2 && this.height <= 256;
	}

	public void createPortalBlocks() {
		BlockState blockstate = JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK.get().defaultBlockState().setValue(SecondRoundWorldPortalBlock.AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((p_77725_) -> {
			this.level.setBlock(p_77725_, blockstate, 18);
			if (this.level instanceof ServerLevel)
				((ServerLevel) this.level).getPoiManager().add(p_77725_, SecondRoundWorldTeleporter.poi);
		});
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public static Vec3 getRelativePosition(BlockUtil.FoundRectangle foundRectangle, Direction.Axis axis, Vec3 vec3, EntityDimensions entityDimensions) {
		double d0 = (double) foundRectangle.axis1Size - (double) entityDimensions.width;
		double d1 = (double) foundRectangle.axis2Size - (double) entityDimensions.height;
		BlockPos blockpos = foundRectangle.minCorner;
		double d2;
		if (d0 > 0.0D) {
			float f = (float) blockpos.get(axis) + entityDimensions.width / 2.0F;
			d2 = Mth.clamp(Mth.inverseLerp(vec3.get(axis) - (double) f, 0.0D, d0), 0.0D, 1.0D);
		} else {
			d2 = 0.5D;
		}
		double d4;
		if (d1 > 0.0D) {
			Direction.Axis direction$axis = Direction.Axis.Y;
			d4 = Mth.clamp(Mth.inverseLerp(vec3.get(direction$axis) - (double) blockpos.get(direction$axis), 0.0D, d1), 0.0D, 1.0D);
		} else {
			d4 = 0.0D;
		}
		Direction.Axis axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		double d3 = vec3.get(axis1) - ((double) blockpos.get(axis1) + 0.5D);
		return new Vec3(d2, d4, d3);
	}

	public static PortalInfo createPortalInfo(ServerLevel level, BlockUtil.FoundRectangle foundRectangle, Direction.Axis axis, Vec3 v, Entity entity, Vec3 v2, float f, float f2) {
		BlockPos blockpos = foundRectangle.minCorner;
		BlockState blockstate = level.getBlockState(blockpos);
		Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
		double d0 = (double) foundRectangle.axis1Size;
		double d1 = (double) foundRectangle.axis2Size;
		EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
		int i = axis == direction$axis ? 0 : 90;
		Vec3 vec3 = axis == direction$axis ? v2 : new Vec3(v2.z, v2.y, -v2.x);
		double d2 = (double) entitydimensions.width / 2.0D + (d0 - (double) entitydimensions.width) * v.x();
		double d3 = (d1 - (double) entitydimensions.height) * v.y();
		double d4 = 0.5D + v.z();
		boolean flag = direction$axis == Direction.Axis.X;
		Vec3 vec31 = new Vec3((double) blockpos.getX() + (flag ? d2 : d4), (double) blockpos.getY() + d3, (double) blockpos.getZ() + (flag ? d4 : d2));
		Vec3 vec32 = findCollisionFreePosition(vec31, level, entity, entitydimensions);
		return new PortalInfo(vec32, vec3, f + (float) i, f2);
	}

	private static Vec3 findCollisionFreePosition(Vec3 v, ServerLevel level, Entity entity, EntityDimensions entityDimensions) {
		if (!(entityDimensions.width > 4.0F) && !(entityDimensions.height > 4.0F)) {
			double d0 = (double) entityDimensions.height / 2.0D;
			Vec3 vec3 = v.add(0.0D, d0, 0.0D);
			VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, (double) entityDimensions.width, 0.0D, (double) entityDimensions.width).expandTowards(0.0D, 1.0D, 0.0D).inflate(1.0E-6D));
			Optional<Vec3> optional = level.findFreePosition(entity, voxelshape, vec3, (double) entityDimensions.width, (double) entityDimensions.height, (double) entityDimensions.width);
			Optional<Vec3> optional1 = optional.map((p_259019_) -> {
				return p_259019_.subtract(0.0D, d0, 0.0D);
			});
			return optional1.orElse(v);
		}
		return v;
	}
}
