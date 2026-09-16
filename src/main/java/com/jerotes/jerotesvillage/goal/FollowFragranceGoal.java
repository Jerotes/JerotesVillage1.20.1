package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FollowFragranceGoal extends Goal {
    private final Animal follower;
    @Nullable
    private LivingEntity fragrance;
    private double speedModifier;
    private int timeToRecalcPath;

    public FollowFragranceGoal(Animal follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        //危险处境
        if (follower.getLastHurtByMob() != null || follower.isFreezing() || follower.isOnFire()) {
            return false;
        }
        //战斗状态
        if (follower instanceof Enemy || follower.getTarget() != null || follower.isAggressive()) {
            return false;
        }
        //有主
        if (follower instanceof OwnableEntity ownable && ownable.getOwner() != null) {
            return false;
        }
        //坐姿
        if (follower instanceof TamableAnimal tamableFlower && tamableFlower.isInSittingPose() || follower instanceof TameMobEntity tamableFlowers && tamableFlowers.isInSittingPose()) {
            return false;
        }
        //失能
        if (follower.isNoAi() || !follower.isAlive()) {
            return false;
        }

        List<LivingEntity> list = this.follower.level()
                .getEntitiesOfClass(LivingEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0))
                .stream()
                .filter(entity -> entity.hasEffect(JerotesVillageMobEffects.FRAGRANCE.get()))
                .toList();
        if (list.isEmpty()) {
            return false;
        }
        LivingEntity find = null;
        double speed = this.speedModifier;
        double d = Double.MAX_VALUE;
        for (LivingEntity fragrance : list) {
            //
            int n = 0;
            if (fragrance.hasEffect(JerotesVillageMobEffects.FRAGRANCE.get())) {
               n = Objects.requireNonNull(fragrance.getEffect(JerotesVillageMobEffects.FRAGRANCE.get())).getAmplifier();
            }
            int effectLevel = n + 1;
            double distance = 16 + n * 4;
            if (distance > 64) {
                distance = 64;
            }
            speed = 1.0 + n * 0.1;
            if (speed > 2.5) {
                speed = 2.5;
            }
            //
            double d2;
            if ((d2 = this.follower.distanceTo(fragrance)) > d) continue;
            if (fragrance.distanceTo(this.follower) > distance * 1.5f) continue;
            if (this.follower.getMaxHealth() >= (60 + effectLevel * 20) || this.follower.getHealth() >= (60 + effectLevel * 20)) continue;
            d = d2;
            find = fragrance;
        }
        if (find == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.speedModifier = speed;
        this.fragrance = find;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        //危险处境
        if (follower.getLastHurtByMob() != null || follower.isFreezing() || follower.isOnFire()) {
            return false;
        }
        //战斗状态
        if (follower instanceof Enemy || follower.getTarget() != null || follower.isAggressive()) {
            return false;
        }
        //有主
        if (follower instanceof OwnableEntity ownable && ownable.getOwner() != null) {
            return false;
        }
        //坐姿
        if (follower instanceof TamableAnimal tamableFlower && tamableFlower.isInSittingPose() || follower instanceof TameMobEntity tamableFlowers && tamableFlowers.isInSittingPose()) {
            return false;
        }
        //失能
        if (follower.isNoAi() || !follower.isAlive()) {
            return false;
        }
        if (this.fragrance != null && !this.fragrance.isAlive()) {
            return false;
        }

        double d = 0;
        if (this.fragrance != null) {
            d = this.follower.distanceToSqr(this.fragrance);
        }
        return !(d < 4.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.fragrance = null;
    }

    @Override
    public void tick() {
        if (this.fragrance != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.fragrance, this.speedModifier);
        }
    }
}

