package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.ArchnosedHornbeastEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraftforge.common.ForgeMod;

import java.util.EnumSet;

public class BrightLandBeastFloatGoal extends Goal {
    private final ArchnosedHornbeastEntity mob;

    public BrightLandBeastFloatGoal(ArchnosedHornbeastEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.JUMP));
        mob.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWater() || this.mob.isInLava();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
            if (this.mob.getAirSupply() > 300) {
                this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.05, 0.0));
            }
            else if (this.mob.getRandom().nextFloat() < 0.8f) {
                this.mob.getJumpControl().jump();
            }
        }
    }
}

