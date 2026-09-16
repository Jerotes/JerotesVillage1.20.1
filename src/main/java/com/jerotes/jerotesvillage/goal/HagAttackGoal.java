package com.jerotes.jerotesvillage.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class HagAttackGoal extends Goal {
    private final Mob hag;
    private final double speedModifier;
    @Nullable
    private LivingEntity target;
    private int attackIntervalMin;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public HagAttackGoal(LivingEntity livingEntity, double d, int n, float f) {
        this.hag = (Mob)livingEntity;
        this.speedModifier = d;
        this.attackIntervalMin = n;
        this.attackRadiusSqr = f * f;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = this.hag.getTarget();
        if (livingEntity != null && livingEntity.isAlive()) {
            this.target = livingEntity;
            return true;
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        return this.canUse() || this.target.isAlive() && !this.hag.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.hag.setAggressive(true);
    }

    @Override
    public void stop() {
        this.hag.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        boolean bl;
        LivingEntity livingEntity = ((Mob)this.hag).getTarget();
        if (livingEntity == null) {
            return;
        }
        double d = ((Entity)this.hag).distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        boolean bl2 = ((Mob)this.hag).getSensing().hasLineOfSight(livingEntity);
        boolean bl3 = bl = this.seeTime > 0;
        if (bl2 != bl) {
            this.seeTime = 0;
        }
        this.seeTime = bl2 ? ++this.seeTime : --this.seeTime;
        if (d > (double)this.attackRadiusSqr || this.seeTime < 20) {
            ((Mob)this.hag).getNavigation().moveTo(livingEntity, this.speedModifier);
            this.strafingTime = -1;
        } else {
            ((Mob)this.hag).getNavigation().stop();
            ++this.strafingTime;
        }
        if (this.strafingTime >= 20) {
            if ((double)((LivingEntity)this.hag).getRandom().nextFloat() < 0.3) {
                boolean bl4 = this.strafingClockwise = !this.strafingClockwise;
            }
            if ((double)((LivingEntity)this.hag).getRandom().nextFloat() < 0.3) {
                this.strafingBackwards = !this.strafingBackwards;
            }
            this.strafingTime = 0;
        }
        if (this.strafingTime > -1) {
            if (d > (double)(this.attackRadiusSqr * 0.75f)) {
                this.strafingBackwards = false;
            } else if (d < (double)(this.attackRadiusSqr * 0.25f)) {
                this.strafingBackwards = true;
            }
            ((Mob)this.hag).getMoveControl().strafe(this.strafingBackwards ? -0.5f : 0.5f, this.strafingClockwise ? 0.5f : -0.5f);
            Entity entity = ((Entity)this.hag).getControlledVehicle();
            if (entity instanceof Mob) {
                Mob hag = (Mob)entity;
                hag.lookAt(livingEntity, 360.0f, 360.0f);
            }
            ((Mob)this.hag).lookAt(livingEntity, 360.0f, 360.0f);
        } else {
            ((Mob)this.hag).getLookControl().setLookAt(livingEntity, 360.0f, 360.0f);
        }
        if (--this.attackTime == 0) {
            if (!bl) {
                return;
            }
            float f = (float)Math.sqrt(d) / this.attackRadiusSqr;
            float f2 = Mth.clamp(f, 0.1f, 1.0f);
            this.attackTime = this.attackIntervalMin;
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d) / (double)this.attackRadiusSqr, (double)this.attackIntervalMin, (double)this.attackIntervalMin));
        }
    }
}