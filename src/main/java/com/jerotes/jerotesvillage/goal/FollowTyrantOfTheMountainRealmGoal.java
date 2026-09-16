package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowTyrantOfTheMountainRealmGoal extends Goal {
    private final BaseWonderlinEntity follower;
    @Nullable
    private TyrantOfTheMountainRealmEntity lord;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowTyrantOfTheMountainRealmGoal(BaseWonderlinEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof TyrantOfTheMountainRealmEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        List<TyrantOfTheMountainRealmEntity> list = this.follower.level().getEntitiesOfClass(TyrantOfTheMountainRealmEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        BaseWonderlinEntity wonderlin = null;
        double d = Double.MAX_VALUE;
        for (TyrantOfTheMountainRealmEntity tyrant : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(tyrant)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(follower, tyrant, false)) continue;
            d = d2;
            wonderlin = tyrant;
        }
        if (wonderlin == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.lord = (TyrantOfTheMountainRealmEntity) wonderlin;
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

