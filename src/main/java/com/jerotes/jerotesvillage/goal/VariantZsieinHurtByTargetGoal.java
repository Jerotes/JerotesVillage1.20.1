package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class VariantZsieinHurtByTargetGoal extends VariantZsieinTargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private static final int ALERT_RANGE_Y = 10;
    private boolean alertSameType;
    private int timestamp;
    private final Class<?>[] toIgnoreDamage;
    @Nullable
    private Class<?>[] toIgnoreAlert;

    public VariantZsieinHurtByTargetGoal(OldVariantZsieinEntity variantZsiein, Class<?>... p_26040_) {
        super(variantZsiein, true);
        this.toIgnoreDamage = p_26040_;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        int $$0 = this.variantZsiein.getLastHurtByMobTimestamp();
        LivingEntity $$1 = this.variantZsiein.getLastHurtByMob();
        if ($$0 != this.timestamp && $$1 != null) {
            if ($$1.getType() == EntityType.PLAYER && this.variantZsiein.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                return false;
            } else {
                Class[] var3 = this.toIgnoreDamage;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Class<?> $$2 = var3[var5];
                    if ($$2.isAssignableFrom($$1.getClass())) {
                        return false;
                    }
                }

                return this.canAttack($$1, HURT_BY_TARGETING);
            }
        } else {
            return false;
        }
    }

    public VariantZsieinHurtByTargetGoal setAlertOthers(Class<?>... p_26045_) {
        this.alertSameType = true;
        this.toIgnoreAlert = p_26045_;
        return this;
    }

    public void start() {
        if (this.variantZsiein.getLastHurtByMob() != null) {
            this.variantZsiein.setBossZsieinTarget(this.variantZsiein.getLastHurtByMob());
            this.variantZsiein.setDataZsieinTargetId(this.variantZsiein.getLastHurtByMob().getId());
        }
        this.targetMob = this.variantZsiein.getBossZsieinTarget();
        this.timestamp = this.variantZsiein.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        if (this.alertSameType) {
            this.alertOthers();
        }

        super.start();
    }

    protected void alertOthers() {
        double $$0 = this.getFollowDistance();
        AABB $$1 = AABB.unitCubeFromLowerCorner(this.variantZsiein.position()).inflate($$0, 10.0, $$0);
        List<? extends Mob> $$2 = this.variantZsiein.level().getEntitiesOfClass(this.variantZsiein.getClass(), $$1, EntitySelector.NO_SPECTATORS);
        Iterator var5 = $$2.iterator();

        while(true) {
            Mob $$3;
            boolean $$4;
            do {
                do {
                    do {
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            $$3 = (Mob) var5.next();
                        } while (this.variantZsiein == $$3);
                    } while ($$3.getTarget() != null);
                } while($$3.isAlliedTo(Objects.requireNonNull(this.variantZsiein.getLastHurtByMob())));

                if (this.toIgnoreAlert == null) {
                    break;
                }

                $$4 = false;
                Class[] var8 = this.toIgnoreAlert;
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Class<?> $$5 = var8[var10];
                    if ($$3.getClass() == $$5) {
                        $$4 = true;
                        break;
                    }
                }
            } while($$4);

            this.alertOther($$3, this.variantZsiein.getLastHurtByMob());
        }
    }

    protected void alertOther(Mob p_26042_, LivingEntity p_26043_) {
        p_26042_.setTarget(p_26043_);
    }
}
