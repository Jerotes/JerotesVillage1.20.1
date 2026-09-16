package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class AnimatedArmorGoHomeGoal extends Goal {
    private final SecondRounderAnimatedArmorEntity animatedArmor;
    private final double speedModifier;

    public AnimatedArmorGoHomeGoal(SecondRounderAnimatedArmorEntity animatedArmor, double d) {
        this.animatedArmor = animatedArmor;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (this.animatedArmor.stopPoseTick > 0) {
            return false;
        }
        if (this.animatedArmor.isStop()) {
            return false;
        }
        if (this.animatedArmor.isTame()) {
            return false;
        }
        return !this.animatedArmor.getHomePos().closerToCenterThan(this.animatedArmor.position(), 0);
    }

    @Override
    public void start() {
        this.animatedArmor.setGoingHome(true);
    }

    @Override
    public void stop() {
        this.animatedArmor.setGoingHome(false);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.animatedArmor.stopPoseTick > 0) {
            return false;
        }
        if (this.animatedArmor.isStop()) {
            return false;
        }
        if (this.animatedArmor.isTame()) {
            return false;
        }
        return !this.animatedArmor.getHomePos().closerToCenterThan(this.animatedArmor.position(), 0);
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.animatedArmor.getHomePos();
        if (blockPos.distToCenterSqr(this.animatedArmor.getOnPos().getCenter()) <= 1024) {
            this.animatedArmor.getNavigation().moveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, this.speedModifier);
        }
    }
}