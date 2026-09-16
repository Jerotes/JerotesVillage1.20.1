package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Animal.WildernessBullEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WildernessBullChargeGoal extends Goal {
    private final WildernessBullEntity bull;
    private int chargeCooldown;
    private int chargeTime;

    public WildernessBullChargeGoal(WildernessBullEntity bull) {
        this.bull = bull;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!bull.isHurtByGoat()) {
            return false;
        }

        return chargeCooldown <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return chargeTime > 0 && bull.getNotAttackTarget() != null && bull.getNotAttackTarget().isAlive() && bull.distanceTo(bull.getNotAttackTarget()) <= 128;
    }

    @Override
    public void start() {
        this.chargeTime = 200;
        this.bull.setAggressive(true);
        this.bull.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.chargeCooldown = 0;
        this.bull.setHurtByGoat(false);
        this.bull.setGoatGiveSelfDamage(0);
        this.bull.setNotAttackTarget(null);
        this.bull.setAggressive(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (bull.getNotAttackTarget() == null || !bull.getNotAttackTarget().isAlive() || chargeTime <= 0) {
            this.stop();
            return;
        }
        chargeTime--;
        boolean bl = bull.getGoatGiveSelfDamage() > bull.getHealth() / 8;
        Vec3 chargeVec = bull.getNotAttackTarget().position()
                .subtract(bull.position())
                .normalize()
                .scale(bl ? 1.3f: 1.1f);

        bull.getLookControl().setLookAt(bull.getNotAttackTarget(), 360.0F, 360.0F);

        if (chargeTime >= 180) {
            bull.lookAt(bull.getNotAttackTarget(), 360.0F, 360.0F);
        }
        else if (chargeTime >= 160) {
            Vec3 retreatVec = bull.position()
                    .subtract(bull.getNotAttackTarget().position())
                    .normalize()
                    .scale(0.5);

            if (bull.onGround()) {
                bull.setDeltaMovement(retreatVec.x, 0.2, retreatVec.z);
            }
            if (bull.level() instanceof ServerLevel serverLevel) {
                if (bl) {
                    serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                            bull.getRandomX(0.5), bull.getRandomY() + 1.0, bull.getRandomZ(0.5),
                            0, 0, 0, 0, 0);
                }
            }
        }
        else if (chargeTime > 0) {
            if (bull.onGround()) {
                bull.setDeltaMovement(chargeVec);
            }
            if (bull.getBoundingBox().inflate(1.0).intersects(bull.getNotAttackTarget().getBoundingBox())) {
                if (bl) {

                    DamageSource damageSource = AttackFind.findDamageType(bull, DamageTypes.MOB_ATTACK_NO_AGGRO, bull);
                    AttackFind.attackBegin(bull, bull.getNotAttackTarget());
                    AttackFind.attackAfterCustomDamage(bull, bull.getNotAttackTarget(), damageSource,1f, 1f, false, 0);
                }
                Vec3 knockbackVec = chargeVec.normalize().scale(bl ? 6.5f: 4.0f);
                bull.getNotAttackTarget().push(knockbackVec.x, 1.0, knockbackVec.z);
                this.stop();
            }
        }
        else {
            this.stop();
        }
    }
}