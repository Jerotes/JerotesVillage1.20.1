package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PurpleBlackDeadBush extends DeadBushBlock implements BonemealableBlock {
    public static VoxelShape column(double d, double d2, double d3) {
        return column(d, d, d2, d3);
    }
    public static VoxelShape column(double d, double d2, double d3, double d4) {
        double d5 = d / 2.0;
        double d6 = d2 / 2.0;
        return Block.box(8.0 - d5, d3, 8.0 - d6, 8.0 + d5, d4, 8.0 + d6);
    }
    public PurpleBlackDeadBush() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).
                replaceable().noCollission().instabreak().sound(SoundType.GRASS).
                ignitedByLava().pushReaction(PushReaction.DESTROY));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

//    @Override
//    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
//        AmbientDesertBlockSoundsPlayer.playAmbientDryGrassSounds(level, blockPos, randomSource);
//    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        popResource(serverLevel, blockPos, new ItemStack(JerotesVillageItems.PURPLE_BLACK_DEAD_BUSH.get()));
    }
}

