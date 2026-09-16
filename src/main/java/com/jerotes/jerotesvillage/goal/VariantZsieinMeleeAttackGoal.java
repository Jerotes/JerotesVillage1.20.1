package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class VariantZsieinMeleeAttackGoal extends Goal {
    protected final OldVariantZsieinEntity variantZsiein;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private final int attackInterval = 20;
    private long lastCanUseCheck;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;

    public VariantZsieinMeleeAttackGoal(OldVariantZsieinEntity variantZsiein, double d, boolean bl) {
        this.variantZsiein = variantZsiein;
        this.speedModifier = d;
        this.followingTargetEvenIfNotSeen = bl;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long l = this.variantZsiein.level().getGameTime();
        if (l - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = l;
        LivingEntity livingEntity = this.variantZsiein.getBossZsieinTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        this.path = this.variantZsiein.getNavigation().createPath(livingEntity, 0);
        if (this.path != null) {
            return true;
        }
        return this.variantZsiein.isWithinMeleeAttackRange(livingEntity);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity = this.variantZsiein.getBossZsieinTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        if (!this.followingTargetEvenIfNotSeen) {
            return !this.variantZsiein.getNavigation().isDone();
        }
        if (!this.variantZsiein.isWithinRestriction(livingEntity.blockPosition())) {
            return false;
        }
        return (!(livingEntity instanceof Player) || !(livingEntity instanceof Player player && (player.getAbilities().instabuild || player.isSpectator())))  || this.variantZsiein.level().getDifficulty() == Difficulty.HARD;
    }

    @Override
    public void start() {
        this.variantZsiein.getNavigation().moveTo(this.path, this.speedModifier);
        this.variantZsiein.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        this.variantZsiein.setBossZsieinTarget(null);
        this.variantZsiein.setDataZsieinTargetId(-1);
        this.variantZsiein.setAggressive(false);
        this.variantZsiein.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.variantZsiein.getBossZsieinTarget();
        if (livingEntity == null) {
            return;
        }
        this.variantZsiein.getLookControl().setLookAt(livingEntity, 360.0f, 360.0f);
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        if ((this.followingTargetEvenIfNotSeen || this.variantZsiein.getSensing().hasLineOfSight(livingEntity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingEntity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.variantZsiein.getRandom().nextFloat() < 0.05f)) {
            this.pathedTargetX = livingEntity.getX();
            this.pathedTargetY = livingEntity.getY();
            this.pathedTargetZ = livingEntity.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.variantZsiein.getRandom().nextInt(7);
            double d = this.variantZsiein.distanceToSqr(livingEntity);
            if (d > 1024.0) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (d > 256.0) {
                this.ticksUntilNextPathRecalculation += 5;
            }
            if (!this.variantZsiein.getNavigation().moveTo(livingEntity, this.speedModifier)) {
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
            this.variantZsiein.swing(InteractionHand.MAIN_HAND);
            this.variantZsiein.doHurtTarget(livingEntity);
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(LivingEntity livingEntity) {
        return this.isTimeToAttack() && this.variantZsiein.isWithinMeleeAttackRange(livingEntity) && this.variantZsiein.getSensing().hasLineOfSight(livingEntity);
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }
}

