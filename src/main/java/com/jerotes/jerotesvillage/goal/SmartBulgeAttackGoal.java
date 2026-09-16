package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Smart.SmartBulgeEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SmartBulgeAttackGoal extends Goal {
    private final SmartBulgeEntity mob;
    private final RangedAttackMob rangedAttackMob;
    @Nullable
    private LivingEntity target;
    private int attackTime;
    private final double speedModifier;
    private int seeTime;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;

    public SmartBulgeAttackGoal(SmartBulgeEntity p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
    }

    public SmartBulgeAttackGoal(SmartBulgeEntity p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
        this.attackTime = -1;
        if (!(p_25773_ instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.rangedAttackMob = p_25773_;
            this.mob = (SmartBulgeEntity)p_25773_;
            this.speedModifier = p_25774_;
            this.attackIntervalMin = p_25775_;
            this.attackIntervalMax = p_25776_;
            this.attackRadius = p_25777_;
            this.attackRadiusSqr = p_25777_ * p_25777_;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }
    }

    public boolean canUse() {
        LivingEntity $$0 = this.mob.getTarget();
        if ($$0 != null && $$0.isAlive()) {
            this.target = $$0;
            return true;
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        return this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.setAggressive(false);
    }
    public boolean requiresUpdateEveryTick() {
        return true;
    }


//    public float distanceToTarget = 8;            // 与目标距离
//    public boolean hasLineOfSightWithBlock = true;   // 与目标是否没被方块格挡
//    public boolean hasLineOfSightWithEntity = true;  // 与目标是否没被实体格挡
//    public boolean canMoveTo = true;                 // 是否无法到达
//    public boolean targetPositionDanger = false;      // 目标位置危险
//    public boolean thisPositionDanger = false;        // 自身位置危险
//    public float heightWithTarget = 0;            // 与目标的高低差
//    //
//    public float targetHealthPercent = 1;         // 目标生命值百分比
//    public float targetAttributeLevel = 2000;        // 目标属性水平（综合力量）
//    public float targetHealthTrendShort = 0;      // 目标生命值短期变化趋势
//    public float targetHealthTrendLong = 0;       // 目标生命值长期变化趋势
//    public int targetPotionLevel = 0;             // 目标药水水平
//    public float targetSizeCompare = 1;           // 目标体型与自身对比
//    private Vec3 lastSelfPos = null;
//    private final Map<UUID, Vec3> lastTargetPos = new HashMap<>();
//    public float targetMoveState = 1;            // 目标目前移动状态
//    public Vec3 targetMoveDirection = Vec3.ZERO;          // 目标目前移动方向
//    public int targetAlliesNearby = 0;            // 周围目标的同伴数量
//    public ItemStack targetMainHandItem = ItemStack.EMPTY;      // 目标手持物品
//    public ItemStack targetUseItem = ItemStack.EMPTY;           // 目标使用物品（如拉弓/喝药水）// 记录目标攻击自己的历史
//    private final Deque<SmartEntity.DistanceRecord> targetAttackHistory = new ArrayDeque<>();
//    // 记录自己攻击目标的历史
//    private final Deque<SmartEntity.DistanceRecord> selfAttackHistory = new ArrayDeque<>();
//    public float avgTargetAttackDistance = getAvgTargetAttackDistance();              // 目标攻击自己的平均距离
//    public float avgSelfAttackDistance = getAvgSelfAttackDistance();              // 自己攻击目标的平均距离
//    public float selfHealthPercent = 1;           // 自身生命值百分比
//    public float selfAttributeLevel = 2000;          // 自身属性水平
//    public float selfHealthTrendShort = 0;        // 自身生命值短期变化趋势
//    public float selfHealthTrendLong = 0;        // 自身生命值长期变化趋势
//    public int selfPotionLevel = calculatePotionLevel(this);              // 自身药水水平
//    public float selfMoveState = 1;              // 自身目前移动状态
//    public int selfUsableTypes = 1;               // 自身能使用的类型（近战/远程/技能）
//    public int selfAlliesNearby = 0;              // 周围自身的同伴数量
//    public boolean incomingProjectileDanger = false;  // 未察觉的危险射弹靠近
//    public Entity extraAttacker = null;              // 多余的攻击来自谁
//    public boolean needRest = false;                  // 自身是否灵感枯竭需要休息
//    public int selfIntelligenceLevel = getSmartLevel();         // 自身智力等级
//

    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public void tick() {
        if (this.target == null)
            return;

        double distanceSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean canSee = this.mob.hasLineOfSightWithBlock && this.mob.hasLineOfSightWithEntity;
        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }
        if (this.strafingTime >= 20) {
            if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                boolean bl4 = this.strafingClockwise = !this.strafingClockwise;
            }
            if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                this.strafingBackwards = !this.strafingBackwards;
            }
            this.strafingTime = 0;
        }

        //行为
        {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.target.position());
            //如果距离太远选择靠近
            if (distanceSqr > (double) this.attackRadiusSqr) {
                if (this.mob.canMoveTo) {
                    this.mob.getNavigation().moveTo(this.target, this.speedModifier);
                }
            }
            //如果距离太进并且目标靠近选择远离
            else if ((this.mob.distanceToTarget < (Math.max(12, this.target.getBbWidth() / 0.6f)) ||
                    this.mob.distanceToTarget < (Math.max(16, this.target.getBbWidth() / 0.3f)) && this.mob.targetMoveState == 1) &&
                    this.mob.canMoveTo && vec3 != null && !(this.target.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.target.distanceToSqr(this.mob))) {
                PathNavigation pathNav = this.mob.getNavigation();
                Path path = pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                if (path == null || path.isDone()) {
                    this.mob.getNavigation().moveTo(path, this.speedModifier);
                }
            }
            //首选走位·不可见·无法到达·被靠近
            else if (!canSee || this.mob.heightWithTarget > -5 && !this.mob.canMoveTo || (this.mob.distanceToTarget < (Math.max(12, this.target.getBbWidth() / 0.6f)))) {
                this.mob.getNavigation().stop();
                ++this.strafingTime;
                if (this.strafingTime > -1) {
                    if (distanceSqr > (double) (this.attackRadiusSqr * 0.75f)) {
                        this.strafingBackwards = false;
                    } else if (distanceSqr < (double) (this.attackRadiusSqr * 0.25f)) {
                        this.strafingBackwards = true;
                    }
                    this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5f : 0.5f, this.strafingClockwise ? 0.5f : -0.5f);
                }
            }
            //比较低
            else if (this.mob.heightWithTarget <= -5 && this.mob.canMoveTo) {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }
            //距离够进，并且可以看见停下
             else if (distanceSqr <= (double) this.attackRadiusSqr && this.seeTime >= 5) {
                this.mob.getNavigation().stop();
            }
            //距离不够进靠近
            else {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }
        }

        //注视
        this.mob.getLookControl().setLookAt(this.target, 360.0F, 360.0F);
        this.mob.lookAt(this.target, 360.0F, 360.0F);

        //发射
        if (--this.attackTime == 0) {
            if (!canSee) {
                return;
            }
            float $$2 = (float)Math.sqrt(distanceSqr) / this.attackRadius;
            float $$3 = Mth.clamp($$2, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, $$3);
            this.attackTime = Mth.floor($$2 * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distanceSqr) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
        }

    }
}