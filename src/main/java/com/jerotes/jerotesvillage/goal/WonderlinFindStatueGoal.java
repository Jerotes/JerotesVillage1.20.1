package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class WonderlinFindStatueGoal extends MoveToBlockGoal {
    protected final PathfinderMob mob;

    public WonderlinFindStatueGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.1f, 64, 32);
        this.mob = pathfinderMob;
    }

    @Override
    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo((double)this.blockPos.getX() + 0.5 + this.mob.getRandom().nextInt(12) - 6, (double)(this.blockPos.getY()), (double)this.blockPos.getZ() + 0.5 + this.mob.getRandom().nextInt(0,12)-6, this.speedModifier);
    }

    @Override
    public double acceptedDistance() {
        return 12.0;
    }

    @Override
    protected int nextStartTick(PathfinderMob pathfinderMob) {
        return reducedTickDelay(60 + pathfinderMob.getRandom().nextInt(80));
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.mob.getTarget() == null;
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(JerotesVillageBlocks.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE.get());
    }
}

