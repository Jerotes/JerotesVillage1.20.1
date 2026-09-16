package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.block.HarmoniousWonderland.WonderlandGiantFrogFrogspawnBlock;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class WonderlandGiantFrogLayEggGoal extends MoveToBlockGoal {
    private final WonderlandGiantFrogEntity wonderlandGiantFrog;

    public WonderlandGiantFrogLayEggGoal(WonderlandGiantFrogEntity wonderlandGiantFrog, double d) {
        super(wonderlandGiantFrog, d, 16);
        this.wonderlandGiantFrog = wonderlandGiantFrog;
    }

    @Override
    public boolean canUse() {
        if (this.wonderlandGiantFrog.hasEgg()) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.wonderlandGiantFrog.hasEgg();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) {
            BlockPos blockPos = this.wonderlandGiantFrog.blockPosition();
            Level level = this.wonderlandGiantFrog.level();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos2;
                BlockPos blockPos3 = blockPos.relative(direction);
                if (!level.getBlockState(blockPos3).getCollisionShape(level, blockPos3).getFaceShape(Direction.UP).isEmpty() || !level.getFluidState(blockPos3).is(Fluids.WATER) || !level.getBlockState(blockPos2 = blockPos3.above()).isAir()) continue;
                BlockState blockState = JerotesVillageBlocks.WONDERLAND_GIANT_FROG_FROGSPAWN.get().defaultBlockState();
                level.setBlock(blockPos2, blockState.setValue(WonderlandGiantFrogFrogspawnBlock.FROG_TYPE, this.wonderlandGiantFrog.getFrogType()), 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Context.of(this.wonderlandGiantFrog, blockState));
                level.playSound(null, this.wonderlandGiantFrog, SoundEvents.FROG_LAY_SPAWN, SoundSource.BLOCKS, 1.0f, 1.0f);
                this.wonderlandGiantFrog.setHasEgg(false);
                this.wonderlandGiantFrog.setLayingEgg(false);
                this.wonderlandGiantFrog.setInLoveTime(600);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(Blocks.WATER);
    }
}

