package com.jerotes.jerotesvillage.control;

import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.entity.Interface.StopLook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class CelestialCoilvineLookControl extends GiantLookControl {
    private static final float IDLE_SETTLE_SPEED = 1.5f;

    private float restYaw = 0.0f;

    public CelestialCoilvineLookControl(Mob mob, int n) {
        super(mob, n);
    }

    @Override
    public void tick() {
        if (this.mob instanceof StopLook stopLook && stopLook.stopLookTime()) {
            return;
        }
        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;
            this.getYRotD().ifPresent(f -> {
                this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, f.floatValue(), this.yMaxRotSpeed);
                this.restYaw = this.mob.yHeadRot;
            });
            this.getXRotD().ifPresent(f -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), f.floatValue(), this.xMaxRotAngle)));
        } else {
            if (this.mob.getNavigation().isDone()) {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0f, 5.0f));
            }
            this.mob.yHeadRot = Mth.approachDegrees(this.mob.yHeadRot, this.restYaw, IDLE_SETTLE_SPEED);
        }
    }
}
