package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.PurpleSandHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.BaseHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CovenHagEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowPurpleSandHagGoal extends Goal {
    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
    private final CovenHagEntity follower;
    @Nullable
    private PurpleSandHagEntity purplesandhag;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowPurpleSandHagGoal(CovenHagEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        List<PurpleSandHagEntity> list = this.follower.level().getEntitiesOfClass(PurpleSandHagEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        BaseHagEntity hags = null;
        double d = Double.MAX_VALUE;
        for (PurpleSandHagEntity hags2 : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(hags2)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(follower, hags2, false)) continue;
            d = d2;
            hags = hags2;
        }
        if (hags == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.purplesandhag = (PurpleSandHagEntity) hags;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.purplesandhag.isAlive()) {
            return false;
        }
        if (this.follower.getTarget() != null) {
            return false;
        }
        double d = this.follower.distanceToSqr(this.purplesandhag);
        return !(d < 18.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.purplesandhag = null;
    }

    @Override
    public void tick() {
        if (this.purplesandhag != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.purplesandhag, this.speedModifier);
        }
    }
}

