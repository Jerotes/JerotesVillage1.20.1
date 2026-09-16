package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomeTypeTags;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.core.BlockPos;
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

public class SerponAttackTargetPlayerGoal<T extends LivingEntity> extends TargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 10;
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public SerponAttackTargetPlayerGoal(Mob mob, Class<T> class_, boolean bl) {
        this(mob, class_, 10, bl, false, null);
    }

    public SerponAttackTargetPlayerGoal(Mob mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 10, bl, false, predicate);
    }

    public SerponAttackTargetPlayerGoal(Mob mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 10, bl, bl2, null);
    }

    public SerponAttackTargetPlayerGoal(Mob mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (JerotesVillageLevelData.get(this.mob.level()) != null && JerotesVillageLevelData.get(this.mob.level()).SerponAway || !this.mob.level().getBiome(BlockPos.containing(this.mob.getX(), this.mob.getY(), this.mob.getZ())).is(JerotesVillageBiomeTypeTags.SERPON_FIND)) {
            return false;
        }
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        this.findTarget();
        boolean bl = this.target != null && !this.target.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get())
                && JerotesVillageLevelData.get(this.mob.level()) != null && !JerotesVillageLevelData.get(this.mob.level()).SerponAway
                && JerotesVillageLevelData.get(this.mob.level()) != null && !JerotesVillageLevelData.get(this.target.level()).SerponAway
                && this.mob.level().getBiome(BlockPos.containing(this.mob.getX(), this.mob.getY(), this.mob.getZ())).is(JerotesVillageBiomeTypeTags.SERPON_FIND)
                && this.target.level().getBiome(BlockPos.containing(this.target.getX(), this.target.getY(), this.target.getZ())).is(JerotesVillageBiomeTypeTags.SERPON_FIND);
        return this.target != null && bl;
    }

    protected AABB getTargetSearchArea(double d) {
        return this.mob.getBoundingBox().inflate(d, d, d);
    }

    protected void findTarget() {
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
                return true;
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

