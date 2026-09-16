package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class GiantMonsterWrestleAttackGoal extends Goal {
    protected final GiantMonsterEntity mob;
    private final double speedModifier;
    private final boolean followingNotAttackTargetEvenIfNotSeen;
    private Path path;
    private double pathedNotAttackTargetX;
    private double pathedNotAttackTargetY;
    private double pathedNotAttackTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private final int attackInterval = 20;
    private long lastCanUseCheck;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;

    public GiantMonsterWrestleAttackGoal(GiantMonsterEntity pathfinderMob) {
        this.mob = pathfinderMob;
        this.speedModifier = 1.4;
        this.followingNotAttackTargetEvenIfNotSeen = true;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long l = this.mob.level().getGameTime();
        if (l - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = l;
        LivingEntity livingEntity = this.mob.getNotAttackTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!(livingEntity instanceof GiantMonsterEntity giantMonster)) {
            return false;
        }
        if (!this.mob.isHorn()) {
            return false;
        }
        if (!giantMonster.isHorn()) {
            return false;
        }
        if (this.mob.getWrestleCooldown() < 2000) {
            return false;
        }
        if (giantMonster.getWrestleCooldown() < 2000) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        if (this.mob.getTarget() != null) {
            return false;
        }
        this.path = this.mob.getNavigation().createPath(livingEntity, 0);
        if (this.path != null) {
            return true;
        }
        return this.mob.isWithinMeleeAttackRange(livingEntity);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity = this.mob.getNotAttackTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        if (!(livingEntity instanceof GiantMonsterEntity giantMonster)) {
            return false;
        }
        if (!this.mob.isHorn()) {
            return false;
        }
        if (!giantMonster.isHorn()) {
            return false;
        }
        if (this.mob.getWrestleCooldown() < 2000) {
            return false;
        }
        if (giantMonster.getWrestleCooldown() < 2000) {
            return false;
        }
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!this.followingNotAttackTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        }
        if (!this.mob.isWithinRestriction(livingEntity.blockPosition())) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingEntity = this.mob.getNotAttackTarget();
        this.mob.setNotAttackTarget(null);
        if (livingEntity != null) {
            if (livingEntity instanceof GiantMonsterEntity giantMonster) {
                giantMonster.setNotAttackTarget(null);
            }
        }
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getNotAttackTarget();
        if (livingEntity == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(livingEntity, 30.0f, 30.0f);
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        if ((this.followingNotAttackTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingEntity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedNotAttackTargetX == 0.0 && this.pathedNotAttackTargetY == 0.0 && this.pathedNotAttackTargetZ == 0.0 || livingEntity.distanceToSqr(this.pathedNotAttackTargetX, this.pathedNotAttackTargetY, this.pathedNotAttackTargetZ) >= 1.0 || this.mob.getRandom().nextFloat() < 0.05f)) {
            this.pathedNotAttackTargetX = livingEntity.getX();
            this.pathedNotAttackTargetY = livingEntity.getY();
            this.pathedNotAttackTargetZ = livingEntity.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
            double d = this.mob.distanceToSqr(livingEntity);
            if (d > 1024.0) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (d > 256.0) {
                this.ticksUntilNextPathRecalculation += 5;
            }
            if (!this.mob.getNavigation().moveTo(livingEntity, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }
            this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
        }
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        this.checkAndPerformAttack(livingEntity);
    }

    protected void checkAndPerformAttack(LivingEntity livingEntity) {
        if (this.canPerformAttack(livingEntity)) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.wrestleTarget(livingEntity);
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(LivingEntity livingEntity) {
        return this.isTimeToAttack() && this.mob.isWithinMeleeAttackRange(livingEntity) && this.mob.getSensing().hasLineOfSight(livingEntity);
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }
}

