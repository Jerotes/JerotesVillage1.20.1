package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class GiantMonsterWrestleTargetGoal<T extends GiantMonsterEntity> extends GiantMonsterTargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 180;
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected GiantMonsterEntity target;
    protected TargetingConditions targetConditions;

    public GiantMonsterWrestleTargetGoal(GiantMonsterEntity mob, Class<T> class_, boolean bl) {
        this(mob, class_, 360, bl, false, null);
    }

    public GiantMonsterWrestleTargetGoal(GiantMonsterEntity mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 360, bl, false, predicate);
    }

    public GiantMonsterWrestleTargetGoal(GiantMonsterEntity mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 360, bl, bl2, null);
    }

    public GiantMonsterWrestleTargetGoal(GiantMonsterEntity mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (this.mob.getWrestleCooldown() > 0) {
            return false;
        }
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (this.mob.getNotAttackTarget() != null) {
            return false;
        }
        if (this.mob.isTame()) {
            return false;
        }
        if (this.mob.isBaby()) {
            return false;
        }
        if (this.mob.isInLove()) {
            return false;
        }
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        this.findTarget();
        return this.target != null
                && this.target.getTarget() == null
                && this.target.getNotAttackTarget() == null
                && this.target.getWrestleCooldown() <= 0
                && !this.target.isTame()
                && !this.target.isBaby()
                && !this.target.isInLove();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (this.mob.getWrestleCooldown() < 2000) {
            return false;
        }
        if (this.mob.isTame()) {
            return false;
        }
        if (this.mob.isBaby()) {
            return false;
        }
        if (this.mob.isInLove()) {
            return false;
        }
        return super.canContinueToUse();
    }


    protected AABB getNotAttackTargetSearchArea(double d) {
        return this.mob.getBoundingBox().inflate(d, 4.0, d);
    }

    protected void findTarget() {
        this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getNotAttackTargetSearchArea(this.getFollowDistance()), livingEntity ->
                true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    @Override
    public void start() {
        if (this.target != null && this.mob != null) {
            if (!this.mob.level().isClientSide()) {
                this.mob.setNotAttackTarget(this.target);
                this.mob.setWrestleCooldown(2400);
                this.target.setNotAttackTarget(this.mob);
                this.target.setWrestleCooldown(2400);
            }
        }
        super.start();
    }
}

