package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.*;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCombatPlatformEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class FollowSerponCombatExecuterGoal extends Goal {
    private final Mob follower;
    @Nullable
    private SerponCombatExecuterEntity serponCombatExecuter;
    private final double speedModifier;
    private int timeToRecalcPath;

    public FollowSerponCombatExecuterGoal(Mob follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof SerponCombatExecuterEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        if (follower instanceof OwnableEntity ownable && ownable.getOwner() != null && !OtherEntityFactionFind.isFactionSerponCombatTeam(ownable.getOwner())) {
            return false;
        }
        if (follower instanceof SerponCombatPlatformEntity serponCombatPlatform) {
            if (!serponCombatPlatform.isNotSerpon() && serponCombatPlatform.getControllingPassenger() != null) {
                if (!(serponCombatPlatform.getControllingPassenger() instanceof Mob mob && OtherEntityFactionFind.isSerpon(mob.getType()))) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        if (follower instanceof SerponChariotEntity serponChariot) {
            if (!serponChariot.isNotSerpon() && serponChariot.getControllingPassenger() != null) {
                if (!(serponChariot.getControllingPassenger() instanceof Mob mob && OtherEntityFactionFind.isSerpon(mob.getType()))) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        if (follower instanceof SerponCrossbowTurretEntity serponCrossbowTurret) {
            return false;
        }
        List<SerponCombatExecuterEntity> list = this.follower.level().getEntitiesOfClass(SerponCombatExecuterEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        Mob mob = null;
        double d = Double.MAX_VALUE;
        for (SerponCombatExecuterEntity executer : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(executer)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(follower, executer, false)) continue;
            d = d2;
            mob = executer;
        }
        if (mob == null) {
            return false;
        }
        if (d < 128.0) {
            return false;
        }
        this.serponCombatExecuter = (SerponCombatExecuterEntity) mob;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.serponCombatExecuter.isAlive()) {
            return false;
        }
        if (this.follower.getTarget() != null) {
            return false;
        }
        double d = this.follower.distanceTo(this.serponCombatExecuter);
        return !(d < 16.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.serponCombatExecuter = null;
    }

    @Override
    public void tick() {
        if (this.serponCombatExecuter != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.serponCombatExecuter, this.speedModifier);
        }
    }
}

