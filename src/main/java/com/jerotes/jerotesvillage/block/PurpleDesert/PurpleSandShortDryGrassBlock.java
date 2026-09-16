package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PurpleSandShortDryGrassBlock extends DeadBushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = PurpleSandTallDryGrassBlock.column(12.0, 0.0, 10.0);

    public PurpleSandShortDryGrassBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).
                replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().
                offsetType(BlockBehaviour.OffsetType.XYZ).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public VoxelShape getShape(BlockState p_53517_, BlockGetter p_53518_, BlockPos p_53519_, CollisionContext p_53520_) {
        Vec3 vec3 = p_53517_.getOffset(p_53518_, p_53519_);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
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
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        PurpleSandTallDryGrassBlock purpleSandTallDryGrassBlock = (PurpleSandTallDryGrassBlock) JerotesVillageBlocks.PURPLE_SAND_TALL_DRY_GRASS.get();
        if (purpleSandTallDryGrassBlock.defaultBlockState().canSurvive(serverLevel, blockPos)) {
            placeAt(serverLevel, purpleSandTallDryGrassBlock.defaultBlockState(), blockPos, 2);
        }
    }

    public static void placeAt(LevelAccessor p_153174_, BlockState p_153175_, BlockPos p_153176_, int p_153177_) {
        p_153174_.setBlock(p_153176_, DoublePlantBlock.copyWaterloggedFrom(p_153174_, p_153176_, p_153175_), p_153177_);
    }
}

