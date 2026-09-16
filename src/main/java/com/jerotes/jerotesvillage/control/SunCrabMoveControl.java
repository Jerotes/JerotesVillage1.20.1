package com.jerotes.jerotesvillage.control;

import com.jerotes.jerotesvillage.entity.Animal.SunCrabEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class SunCrabMoveControl extends MoveControl {
    private final SunCrabEntity sunCrab;

    public SunCrabMoveControl(SunCrabEntity sunCrab) {
        super(sunCrab);
        this.sunCrab = sunCrab;
    }

    private void updateSpeed() {
        this.sunCrab.setSpeed(Math.max(this.sunCrab.getSpeed(), 0.06f));
    }

    @Override
    public void tick() {
        double d;
        double d2;
        this.updateSpeed();
        if (this.operation != Operation.MOVE_TO || this.sunCrab.getNavigation().isDone()) {
            this.sunCrab.setSpeed(0.0f);
            return;
        }
        double d3 = this.wantedX - this.sunCrab.getX();
        double d4 = Math.sqrt(d3 * d3 + (d2 = this.wantedY - this.sunCrab.getY()) * d2 + (d = this.wantedZ - this.sunCrab.getZ()) * d);
        if (d4 < 9.999999747378752E-6) {
            this.mob.setSpeed(0.0f);
            return;
        }
        float f = (float) (Mth.atan2(d, d3) * 57.2957763671875) - 90.0f;
        this.sunCrab.setYRot(this.rotlerp(this.sunCrab.getYRot(), f, 90.0f));
        this.sunCrab.yBodyRot = this.sunCrab.getYRot();
        float f2 = (float) (this.speedModifier * this.sunCrab.getAttributeValue(Attributes.MOVEMENT_SPEED));
        this.sunCrab.setSpeed(Mth.lerp(0.125f, this.sunCrab.getSpeed(), f2));
        this.sunCrab.setDeltaMovement(this.sunCrab.getDeltaMovement().add(0.0, (double) this.sunCrab.getSpeed() * (d2 /= d4) * 0.1, 0.0));
    }
}


