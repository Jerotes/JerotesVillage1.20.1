package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.DustMothEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowDustMothGoal extends Goal {
    private final DustMothEntity follower;
    @Nullable
    private DustMothEntity lord;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowDustMothGoal(DustMothEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower.isQueen()) {
            return false;
        }
        if (follower.isBaby()) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        List<DustMothEntity> list = this.follower.level()
                .getEntitiesOfClass(DustMothEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        DustMothEntity queen = null;
        double d = Double.MAX_VALUE;
        for (DustMothEntity lords : list) {
            double d2;
            if (!lords.isQueen()) continue;
            if (lords.isBaby()) continue;
            if ((d2 = this.follower.distanceToSqr(lords)) > d) continue;
            if (!(follower.isAlliedTo(lords) || follower.getTeam() == null && lords.getTeam() == null)) continue;
            d = d2;
            queen = lords;
        }
        if (queen == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.lord = (DustMothEntity) queen;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.lord.isAlive()) {
            return false;
        }
        if (this.follower.getTarget() != null) {
            return false;
        }
        double d = this.follower.distanceToSqr(this.lord);
        return !(d < 18.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.lord = null;
    }

    @Override
    public void tick() {
        if (this.lord != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.lord, this.speedModifier);
        }
    }
}

