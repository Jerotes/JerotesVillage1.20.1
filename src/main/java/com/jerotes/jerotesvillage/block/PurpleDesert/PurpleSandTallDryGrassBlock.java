package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

public class PurpleSandTallDryGrassBlock extends DeadBushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = column(14.0, 0.0, 16.0);
    public static VoxelShape column(double d, double d2, double d3) {
        return column(d, d, d2, d3);
    }
    public static VoxelShape column(double d, double d2, double d3, double d4) {
        double d5 = d / 2.0;
        double d6 = d2 / 2.0;
        return Block.box(8.0 - d5, d3, 8.0 - d6, 8.0 + d5, d4, 8.0 + d6);
    }
    public PurpleSandTallDryGrassBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).
                replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().
                offsetType(BlockBehaviour.OffsetType.XYZ).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }
    @Override
    public VoxelShape getShape(BlockState p_53517_, BlockGetter p_53518_, BlockPos p_53519_, CollisionContext p_53520_) {
        Vec3 vec3 = p_53517_.getOffset(p_53518_, p_53519_);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

//    @Override
//    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
//        AmbientDesertBlockSoundsPlayer.playAmbientDryGrassSounds(level, blockPos, randomSource);
//    }


    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }
    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos2, BlockState blockState) {
        findSpreadableNeighbourPos(serverLevel, blockPos2, JerotesVillageBlocks.PURPLE_SAND_SHORT_DRY_GRASS.get().defaultBlockState()).ifPresent(blockPos -> serverLevel.setBlockAndUpdate((BlockPos)blockPos, JerotesVillageBlocks.PURPLE_SAND_SHORT_DRY_GRASS.get().defaultBlockState()));
    }


    public static boolean hasSpreadableNeighbourPos(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.stream().toList(), levelReader, blockPos, blockState).isPresent();
    }

    public static Optional<BlockPos> findSpreadableNeighbourPos(Level level, BlockPos blockPos, BlockState blockState) {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.shuffledCopy(level.random), level, blockPos, blockState);
    }

    private static Optional<BlockPos> getSpreadableNeighbourPos(List<Direction> list, LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        for (Direction direction : list) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!levelReader.isEmptyBlock(blockPos2) || !blockState.canSurvive(levelReader, blockPos2)) continue;
            return Optional.of(blockPos2);
        }
        return Optional.empty();
    }

}

