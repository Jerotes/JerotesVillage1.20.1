package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;

public abstract class VariantZsieinTargetGoal extends Goal {
    private static final int EMPTY_REACH_CACHE = 0;
    private static final int CAN_REACH_CACHE = 1;
    private static final int CANT_REACH_CACHE = 2;
    protected final OldVariantZsieinEntity variantZsiein;
    protected final boolean mustSee;
    private final boolean mustReach;
    private int reachCache;
    private int reachCacheTime;
    private int unseenTicks;
    @Nullable
    protected LivingEntity targetMob;
    protected int unseenMemoryTicks;

    public VariantZsieinTargetGoal(OldVariantZsieinEntity variantZsiein, boolean p_26141_) {
        this(variantZsiein, p_26141_, false);
    }

    public VariantZsieinTargetGoal(OldVariantZsieinEntity variantZsiein, boolean p_26144_, boolean p_26145_) {
        this.unseenMemoryTicks = 60;
        this.variantZsiein = variantZsiein;
        this.mustSee = p_26144_;
        this.mustReach = p_26145_;
    }

    public boolean canContinueToUse() {
        LivingEntity $$0 = this.variantZsiein.getBossZsieinTarget();
        if ($$0 == null) {
            $$0 = this.targetMob;
        }

        if ($$0 == null) {
            return false;
        } else if (!this.variantZsiein.canAttack($$0)) {
            return false;
        } else {
            Team $$1 = this.variantZsiein.getTeam();
            Team $$2 = $$0.getTeam();
            if ($$1 != null && $$2 == $$1) {
                return false;
            } else {
                double $$3 = this.getFollowDistance();
                if (this.variantZsiein.distanceToSqr($$0) > $$3 * $$3) {
                    return false;
                } else {
                    if (this.mustSee) {
                        if (this.variantZsiein.getSensing().hasLineOfSight($$0)) {
                            this.unseenTicks = 0;
                        } else if (++this.unseenTicks > reducedTickDelay(this.unseenMemoryTicks)) {
                            return false;
                        }
                    }

                    this.variantZsiein.setBossZsieinTarget($$0);
                    this.variantZsiein.setDataZsieinTargetId($$0.getId());
                    return true;
                }
            }
        }
    }

    protected double getFollowDistance() {
        return this.variantZsiein.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    public void start() {
        this.reachCache = 0;
        this.reachCacheTime = 0;
        this.unseenTicks = 0;
    }

    public void stop() {
        this.variantZsiein.setBossZsieinTarget((LivingEntity)null);
        this.variantZsiein.setDataZsieinTargetId(-1);
        this.targetMob = null;
    }

    protected boolean canAttack(@Nullable LivingEntity p_26151_, TargetingConditions p_26152_) {
        if (p_26151_ == null) {
            return false;
        } else if (!p_26152_.test(this.variantZsiein, p_26151_)) {
            return false;
        } else if (!this.variantZsiein.isWithinRestriction(p_26151_.blockPosition())) {
            return false;
        } else {
            if (this.mustReach) {
                if (--this.reachCacheTime <= 0) {
                    this.reachCache = 0;
                }

                if (this.reachCache == 0) {
                    this.reachCache = this.canReach(p_26151_) ? 1 : 2;
                }

                if (this.reachCache == 2) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean canReach(LivingEntity p_26149_) {
        this.reachCacheTime = reducedTickDelay(10 + this.variantZsiein.getRandom().nextInt(5));
        Path $$1 = this.variantZsiein.getNavigation().createPath(p_26149_, 0);
        if ($$1 == null) {
            return false;
        } else {
            Node $$2 = $$1.getEndNode();
            if ($$2 == null) {
                return false;
            } else {
                int $$3 = $$2.x - p_26149_.getBlockX();
                int $$4 = $$2.z - p_26149_.getBlockZ();
                return (double)($$3 * $$3 + $$4 * $$4) <= 2.25;
            }
        }
    }

    public VariantZsieinTargetGoal setUnseenMemoryTicks(int p_26147_) {
        this.unseenMemoryTicks = p_26147_;
        return this;
    }
}
