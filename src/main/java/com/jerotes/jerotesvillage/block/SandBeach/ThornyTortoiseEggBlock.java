package com.jerotes.jerotesvillage.block.SandBeach;

import com.jerotes.jerotesvillage.entity.Animal.ThornyTortoiseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThornyTortoiseEggBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    public ThornyTortoiseEggBlock() {
        super(Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(2f, 0.5f).sound(SoundType.METAL).noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    public int getHatchLevel(BlockState blockState) {
        return blockState.getValue(HATCH);
    }

    private boolean isReadyToHatch(BlockState blockState) {
        return this.getHatchLevel(blockState) == 2;
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!this.isReadyToHatch(blockState)) {
            serverLevel.playSound(null, blockPos, JerotesVillageSoundEvents.THORNY_TORTOISE_EGG_CRACK, SoundSource.BLOCKS, 0.7f, 0.9f + randomSource.nextFloat() * 0.2f);
            serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(HATCH, this.getHatchLevel(blockState) + 1), 2);
            return;
        }
        serverLevel.playSound(null, blockPos, JerotesVillageSoundEvents.THORNY_TORTOISE_EGG_HATCH, SoundSource.BLOCKS, 0.7f, 0.9f + randomSource.nextFloat() * 0.2f);
        serverLevel.destroyBlock(blockPos, false);
        ThornyTortoiseEntity thornyTortoise = JerotesVillageEntityType.THORNY_TORTOISE.get().create(serverLevel);
        if (thornyTortoise != null) {
            Vec3 vec3 = blockPos.getCenter();
            thornyTortoise.setBaby(true);
            thornyTortoise.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(serverLevel.random.nextFloat() * 360.0f), 0.0f);
            serverLevel.addFreshEntity(thornyTortoise);
        }
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!level.isClientSide()) {
            level.levelEvent(3009, blockPos, 0);
        }
        int n = 12000;
        int n2 = n / 3;
        level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(blockState));
        level.scheduleTick(blockPos, this, n2 + level.random.nextInt(300));
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        return false;
    }

}

