package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import net.minecraft.server.level.ServerPlayer;
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

public class GemstoneMalignasaurTargetGoal<T extends LivingEntity> extends TargetGoal {
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;
    private final double maxSearchRange;

    public GemstoneMalignasaurTargetGoal(Mob mob, Class<T> class_, boolean bl) {
        this(mob, class_, 10, bl, false, null, 32.0);
    }

    public GemstoneMalignasaurTargetGoal(Mob mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 10, bl, false, predicate, 32.0);
    }

    public GemstoneMalignasaurTargetGoal(Mob mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 10, bl, bl2, null, 32.0);
    }

    public GemstoneMalignasaurTargetGoal(Mob mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        this(mob, class_, n, bl, bl2, predicate, 32.0);
    }

    public GemstoneMalignasaurTargetGoal(Mob mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate, double maxSearchRange) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.maxSearchRange = maxSearchRange;
        this.setFlags(EnumSet.of(Flag.TARGET));
        double range = Math.min(this.getFollowDistance(), maxSearchRange);
        this.targetConditions = TargetingConditions.forCombat().range(range).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (((GemstoneMalignasaurEntity)this.mob).getHungerOf() > 0) {
            return false;
        }
        if (this.mob.level().getEntitiesOfClass(LivingEntity.class,
                this.mob.getBoundingBox().inflate(16), e -> true).isEmpty()) {
            return false;
        }
        this.findTarget();
        return this.target != null;
    }

    protected AABB getTargetSearchArea(double d) {
        double range = Math.min(d, this.maxSearchRange);
        return this.mob.getBoundingBox().inflate(range, range, range);
    }

    protected void findTarget() {
        double range = Math.min(this.getFollowDistance(), this.maxSearchRange);
        AABB aabb = this.getTargetSearchArea(range);
        if (this.targetType == Player.class || this.targetType == ServerPlayer.class) {
            this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.target = this.mob.level().getNearestEntity(
                    this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), livingEntity -> true),
                    this.targetConditions,
                    this.mob,
                    this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()
            );
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
