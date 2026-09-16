package com.jerotes.jerotesvillage.block.WildernessWoodland;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackYellowDoor extends ABaseBlock.ABaseDoorBlock {
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 12.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 12.0D, 16.0D);
    protected static final VoxelShape SOUTH_AABB_DOWN = Block.box(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB_DOWN = Block.box(0.0D, 4.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB_DOWN = Block.box(13.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB_DOWN = Block.box(0.0D, 4.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    public BlackYellowDoor(BlockSetType blockSetType, Properties properties) {
        super(blockSetType, properties);
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return true;
    }


    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        boolean flag = !blockState.getValue(OPEN);
        boolean flag1 = blockState.getValue(HINGE) == DoorHingeSide.RIGHT;
        boolean flag2 = blockState.getValue(HALF) == DoubleBlockHalf.UPPER;
        VoxelShape voxelshape;
        if (flag2) {
            switch (direction) {
                case SOUTH:
                    voxelshape = flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
                    break;
                case WEST:
                    voxelshape = flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
                    break;
                case NORTH:
                    voxelshape = flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
                    break;
                default:
                    voxelshape = flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
            }
        }
        else {
            switch (direction) {
                case SOUTH:
                    voxelshape = flag ? SOUTH_AABB_DOWN : (flag1 ? EAST_AABB_DOWN : WEST_AABB_DOWN);
                    break;
                case WEST:
                    voxelshape = flag ? WEST_AABB_DOWN : (flag1 ? SOUTH_AABB_DOWN : NORTH_AABB_DOWN);
                    break;
                case NORTH:
                    voxelshape = flag ? NORTH_AABB_DOWN : (flag1 ? WEST_AABB_DOWN : EAST_AABB_DOWN);
                    break;
                default:
                    voxelshape = flag ? EAST_AABB_DOWN : (flag1 ? NORTH_AABB_DOWN : SOUTH_AABB_DOWN);
            }
        }

        return voxelshape;
    }
}

