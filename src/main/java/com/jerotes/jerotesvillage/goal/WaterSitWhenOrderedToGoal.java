package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.Main;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class WaterSitWhenOrderedToGoal extends Goal {
    private final TamableAnimal mob;

    public WaterSitWhenOrderedToGoal(TamableAnimal tameMob) {
        this.mob = tameMob;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isOrderedToSit();
    }

    @Override
    public boolean canUse() {
        if (!this.mob.isTame()) {
            return false;
        }
        if (!(this.mob.getOwner() instanceof Player)) {
            return false;
        }
        if (!Main.isInFluid(this.mob) && !this.mob.onGround()) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return true;
        }
        if (((Mob)this.mob).distanceToSqr(livingEntity) < 144.0 && livingEntity.getLastHurtByMob() != null) {
            return false;
        }
        return this.mob.isOrderedToSit();
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
        this.mob.setInSittingPose(true);
    }

    @Override
    public void stop() {
        this.mob.setInSittingPose(false);
    }
}

