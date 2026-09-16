package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SlaverySupervisorEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowSlaverySupervisorGoal extends Goal {
    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
    private final DefectorEntity defector;
    @Nullable
    private SlaverySupervisorEntity slaverySupervisor;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowSlaverySupervisorGoal(DefectorEntity defector, double d) {
        this.defector = defector;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (defector.isAggressive()) {
            return false;
        }
        List<SlaverySupervisorEntity> list = this.defector.level().getEntitiesOfClass(SlaverySupervisorEntity.class, this.defector.getBoundingBox().inflate(64.0, 32.0, 64.0));
        SlaverySupervisorEntity supervisorEntity = null;
        double d = Double.MAX_VALUE;
        for (SlaverySupervisorEntity defectors2 : list) {
            double d2;
            if ((d2 = this.defector.distanceToSqr(defectors2)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(defector, defectors2, false)) continue;
            d = d2;
            supervisorEntity = defectors2;
        }
        if (supervisorEntity == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.slaverySupervisor = (SlaverySupervisorEntity) supervisorEntity;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (defector.isAggressive()) {
            return false;
        }
        if (!this.slaverySupervisor.isAlive()) {
            return false;
        }
        if (this.defector.getTarget() != null) {
            return false;
        }
        double d = this.defector.distanceToSqr(this.slaverySupervisor);
        return !(d < 18.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.slaverySupervisor = null;
    }

    @Override
    public void tick() {
        if (this.slaverySupervisor != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.defector.getNavigation().moveTo(this.slaverySupervisor, this.speedModifier);
        }
    }
}

