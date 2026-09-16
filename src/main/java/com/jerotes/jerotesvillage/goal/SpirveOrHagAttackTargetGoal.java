package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CohortHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CovenHagEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class SpirveOrHagAttackTargetGoal<T extends LivingEntity> extends TargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 10;
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public SpirveOrHagAttackTargetGoal(Mob mob, Class<T> class_, boolean bl) {
        this(mob, class_, 10, bl, false, null);
    }

    public SpirveOrHagAttackTargetGoal(Mob mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 10, bl, false, predicate);
    }

    public SpirveOrHagAttackTargetGoal(Mob mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 10, bl, bl2, null);
    }

    public SpirveOrHagAttackTargetGoal(Mob mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        this.findTarget();
        return this.target != null;
    }

    protected AABB getTargetSearchArea(double d) {
        return this.mob.getBoundingBox().inflate(d, 4.0, d);
    }

    @Override
    protected double getFollowDistance() {
        if (this.mob instanceof CohortHagEntity)
            return super.getFollowDistance() / 3;
        return super.getFollowDistance();
    }

    protected void findTarget() {
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
                return !EntityFactionFind.isPurpleSandSisterhoodIdentifyWith(p_148152_);
            }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        this.target = livingEntity;
    }
}

