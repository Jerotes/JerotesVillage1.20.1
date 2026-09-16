package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class CarvedFindLodestoneGoal extends MoveToBlockGoal {
    protected final PathfinderMob mob;

    public CarvedFindLodestoneGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.1f, 64, 32);
        this.mob = pathfinderMob;
    }

    @Override
    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo((double)this.blockPos.getX() + 0.5 + this.mob.getRandom().nextInt(0,12) - 6, (double)(this.blockPos.getY()), (double)this.blockPos.getZ() + 0.5 + this.mob.getRandom().nextInt(0,12)-6, this.speedModifier);
    }

    @Override
    public double acceptedDistance() {
        return 6.0;
    }

    @Override
    protected int nextStartTick(PathfinderMob pathfinderMob) {
        return reducedTickDelay(60 + pathfinderMob.getRandom().nextInt(80));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof TamableAnimal carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof TameMobEntity carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof OwnableEntity carvedHound && carvedHound.getOwner() != null) {
            return false;
        }
        if (this.mob instanceof CarvedEntity carvedAllay && carvedAllay.isCarvedAlly() && !this.mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        return super.canUse() && this.mob.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob instanceof TamableAnimal carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof TameMobEntity carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof OwnableEntity carvedHound && carvedHound.getOwner() != null) {
            return false;
        }
        if (this.mob instanceof CarvedEntity carvedAllay && carvedAllay.isCarvedAlly() && !this.mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        return super.canContinueToUse() && this.mob.getTarget() == null;
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(JerotesVillageBlocks.CARVED_LODESTONE.get());
    }

}

