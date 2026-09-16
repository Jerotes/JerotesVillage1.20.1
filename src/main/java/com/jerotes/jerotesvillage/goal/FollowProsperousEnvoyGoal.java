package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.RamblerEntity;
import com.jerotes.jerotesvillage.entity.Boss.ProsperousEnvoyEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowProsperousEnvoyGoal extends Goal {
    private final RamblerEntity follower;
    @Nullable
    private ProsperousEnvoyEntity lord;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowProsperousEnvoyGoal(RamblerEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof ProsperousEnvoyEntity) {
            return false;
        }
        List<ProsperousEnvoyEntity> list = this.follower.level().getEntitiesOfClass(ProsperousEnvoyEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        RamblerEntity rambler = null;
        double d = Double.MAX_VALUE;
        for (ProsperousEnvoyEntity lords : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(lords)) > d) continue;
            if (!(follower.isAlliedTo(lords) || follower.getTeam() == null && lords.getTeam() == null)) continue;
            d = d2;
            rambler = lords;
        }
        if (rambler == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.lord = (ProsperousEnvoyEntity) rambler;
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

