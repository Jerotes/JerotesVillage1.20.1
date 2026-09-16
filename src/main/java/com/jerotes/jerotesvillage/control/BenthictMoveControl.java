package com.jerotes.jerotesvillage.control;

import com.jerotes.jerotesvillage.entity.Animal.BenthictEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class BenthictMoveControl extends MoveControl {
    private final BenthictEntity benthict;

    public BenthictMoveControl(BenthictEntity benthict) {
        super(benthict);
        this.benthict = benthict;
    }

    private void updateSpeed() {
        this.benthict.setSpeed(Math.max(this.benthict.getSpeed(), 0.06f));
    }

    @Override
    public void tick() {
        double d;
        double d2;
        this.updateSpeed();
        if (this.operation != Operation.MOVE_TO || this.benthict.getNavigation().isDone()) {
            this.benthict.setSpeed(0.0f);
            return;
        }
        double d3 = this.wantedX - this.benthict.getX();
        double d4 = Math.sqrt(d3 * d3 + (d2 = this.wantedY - this.benthict.getY()) * d2 + (d = this.wantedZ - this.benthict.getZ()) * d);
        if (d4 < 9.999999747378752E-6) {
            this.mob.setSpeed(0.0f);
            return;
        }
        float f = (float) (Mth.atan2(d, d3) * 57.2957763671875) - 90.0f;
        this.benthict.setYRot(this.rotlerp(this.benthict.getYRot(), f, 90.0f));
        this.benthict.yBodyRot = this.benthict.getYRot();
        float f2 = (float) (this.speedModifier * this.benthict.getAttributeValue(Attributes.MOVEMENT_SPEED));
        this.benthict.setSpeed(Mth.lerp(0.125f, this.benthict.getSpeed(), f2));
        this.benthict.setDeltaMovement(this.benthict.getDeltaMovement().add(0.0, (double) this.benthict.getSpeed() * (d2 /= d4) * 0.1, 0.0));
    }
}


