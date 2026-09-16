package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowSeawaveGoal extends Goal {
    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
    private final BulgeEntity follower;
    @Nullable
    private SeawaveEntity seawave;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowSeawaveGoal(BulgeEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof SeawaveEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        List<SeawaveEntity> list = this.follower.level().getEntitiesOfClass(SeawaveEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        SeawaveEntity followers = null;
        double d = Double.MAX_VALUE;
        for (SeawaveEntity followers2 : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(followers2)) > d) continue;
            if (!(follower.isAlliedTo(followers2) || follower.getTeam() == null && followers2.getTeam() == null)) continue;
            d = d2;
            followers = followers2;
        }
        if (followers == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.seawave = (SeawaveEntity) followers;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (follower instanceof SeawaveEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        if (!this.seawave.isAlive()) {
            return false;
        }
        if (this.follower.getTarget() != null) {
            return false;
        }
        double d = this.follower.distanceToSqr(this.seawave);
        return !(d < 18.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.seawave = null;
    }

    @Override
    public void tick() {
        if (this.seawave != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.seawave, this.speedModifier);
        }
    }
}

