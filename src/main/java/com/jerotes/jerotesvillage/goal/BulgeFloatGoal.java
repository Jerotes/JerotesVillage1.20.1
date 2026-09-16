package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.Main;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BulgeFloatGoal
extends Goal {
    private final Mob mob;
    private final float speed;

    public BulgeFloatGoal(Mob mob, float speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.JUMP));
        mob.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canUse() {
        return Main.isInFluid(this.mob);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextFloat() < 0.8f) {
            this.mob.getJumpControl().jump();
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, this.speed, 0.0));
        }
        if (this.mob.getMobType() == MobType.WATER) {
            if (this.mob.level().isClientSide()) {
                for (int i = 0; i < 8; ++i) {
                    this.mob.level().addParticle(ParticleTypes.BUBBLE, this.mob.getRandomX(0.5), this.mob.getRandomY(), this.mob.getRandomZ(0.5), 0.0, 0.0, 0.0);
                }
            }
        }
    }
}

