package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowSedimentLordGoal extends Goal {
    private final ResurrecterEntity follower;
    @Nullable
    private SedimentLordEntity lord;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowSedimentLordGoal(ResurrecterEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof SedimentLordEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        List<SedimentLordEntity> list = this.follower.level().getEntitiesOfClass(SedimentLordEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        ResurrecterEntity resurrecter = null;
        double d = Double.MAX_VALUE;
        for (SedimentLordEntity lords : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(lords)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(follower, lords, false)) continue;
            d = d2;
            resurrecter = lords;
        }
        if (resurrecter == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.lord = (SedimentLordEntity) resurrecter;
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

