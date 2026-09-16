package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogTadpoleEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WonderlandGiantFrogFrogspawnBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty FROG_TYPE = BlockStateProperties.AGE_25;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
    private static int minHatchTickDelay = 3600;
    private static int maxHatchTickDelay = 12000;

    public WonderlandGiantFrogFrogspawnBlock() {
        super(Properties.of().mapColor(MapColor.WATER).instabreak().noOcclusion().noCollission().sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY));
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false)));
    }

    public WonderlandGiantFrogFrogspawnBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROG_TYPE,WATERLOGGED);
    }
    public static int getFrogType(BlockState blockState) {
        return blockState.getValue(FROG_TYPE);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        if (blockState.getValue(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(blockState);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos;
        Level level;
        return (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, (level = blockPlaceContext.getLevel()).getFluidState(blockPos = blockPlaceContext.getClickedPos()).getType() == Fluids.WATER));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return WonderlandGiantFrogFrogspawnBlock.mayPlaceOn(levelReader, blockPos.below());
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        level.scheduleTick(blockPos, this, WonderlandGiantFrogFrogspawnBlock.getFrogspawnHatchDelay(level.getRandom()));
    }

    private static int getFrogspawnHatchDelay(RandomSource randomSource) {
        return randomSource.nextInt(minHatchTickDelay, maxHatchTickDelay);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED).booleanValue()) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!this.canSurvive(blockState, serverLevel, blockPos)) {
            this.destroyBlock(serverLevel, blockPos);
            return;
        }
        this.hatchFrogspawn(serverLevel, blockPos, randomSource);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.destroyBlock(level, blockPos);
        }
    }

    public static boolean mayPlaceOn(BlockGetter blockGetter, BlockPos blockPos) {
        FluidState fluidState = blockGetter.getFluidState(blockPos);
        return fluidState.getType() == Fluids.WATER;
    }

    private void hatchFrogspawn(ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.destroyBlock(serverLevel, blockPos);
        serverLevel.playSound(null, blockPos, SoundEvents.FROGSPAWN_HATCH, SoundSource.BLOCKS, 1.0f, 1.0f);
        this.spawnTadpoles(serverLevel, blockPos, randomSource);
    }

    private void destroyBlock(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
    }

    private void spawnTadpoles(ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int n = randomSource.nextInt(1, 4);
        for (int i = 1; i <= n; ++i) {
            WonderlandGiantFrogTadpoleEntity tadpole = JerotesVillageEntityType.WONDERLAND_GIANT_FROG_TADPOLE.get().create(serverLevel);
            if (tadpole == null) continue;
            double d = (double)blockPos.getX() + this.getRandomTadpolePositionOffset(randomSource);
            double d2 = (double)blockPos.getZ() + this.getRandomTadpolePositionOffset(randomSource);
            int n2 = randomSource.nextInt(1, 361);
            tadpole.setFrogType(WonderlandGiantFrogFrogspawnBlock.getFrogType(serverLevel.getBlockState(blockPos)));
            tadpole.moveTo(d, (double)blockPos.getY() - 0.5, d2, n2, 0.0f);
            tadpole.setPersistenceRequired();
            serverLevel.addFreshEntity(tadpole);
        }
    }

    private double getRandomTadpolePositionOffset(RandomSource randomSource) {
        double d = Tadpole.HITBOX_WIDTH / 2.0f;
        return Mth.clamp(randomSource.nextDouble(), d, 1.0 - d);
    }

}

