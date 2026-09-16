package com.jerotes.jerotesvillage.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class WaterLandMoveControl extends MoveControl {
    private final Mob drowned;

    public WaterLandMoveControl(Mob drowned) {
        super(drowned);
        this.drowned = drowned;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.drowned.getTarget();
        if (this.drowned.isInWater()) {
            if (livingEntity != null && livingEntity.getY() > this.drowned.getY()) {
                this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, 0.002, 0.0));
            }
            if (this.operation != Operation.MOVE_TO || this.drowned.getNavigation().isDone()) {
                this.drowned.setSpeed(0.0f);
                return;
            }
            double d = this.wantedX - this.drowned.getX();
            double d2 = this.wantedY - this.drowned.getY();
            double d3 = this.wantedZ - this.drowned.getZ();
            double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
            float f = (float)(Mth.atan2(d3, d) * 57.2957763671875) - 90.0f;
            this.drowned.setYRot(this.rotlerp(this.drowned.getYRot(), f, 90.0f));
            this.drowned.yBodyRot = this.drowned.getYRot();
            float f2 = (float)(this.speedModifier * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float f3 = Mth.lerp(0.125f, this.drowned.getSpeed(), f2);
            this.drowned.setSpeed(f3);
            this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add((double)f3 * d * 0.005, (double)f3 * (d2 /= d4) * 0.1, (double)f3 * d3 * 0.005));
        } else {
            if (!this.drowned.onGround()) {
                this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, -0.008, 0.0));
            }
            super.tick();
        }
    }
}