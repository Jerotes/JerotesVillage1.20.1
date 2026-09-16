package com.jerotes.jerotesvillage.control;

import com.jerotes.jerotesvillage.entity.Monster.Elite.FireSecretorEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class FireSecretorMoveControl extends MoveControl {
    private final int maxTurn;
    private final boolean hoversInPlace;
    protected FireSecretorEntity mob;

    public FireSecretorMoveControl(FireSecretorEntity mob, int n, boolean bl) {
        super(mob);
        this.mob = mob;
        this.maxTurn = n;
        this.hoversInPlace = bl;
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.fallTick <= 0) {
                this.mob.setNoGravity(true);
            }
            double d = this.wantedX - this.mob.getX();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = this.wantedZ - this.mob.getZ();
            double d4 = d * d + d2 * d2 + d3 * d3;
            if (d4 < 2.500000277905201E-7) {
                this.mob.setYya(0.0f);
                this.mob.setZza(0.0f);
                return;
            }
            float f = (float)(Mth.atan2(d3, d) * 57.2957763671875) - 90.0f;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 90.0f));
            float f2 = this.mob.onGround() ? (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) : (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 3);
            this.mob.setSpeed(f2);
            double d5 = Math.sqrt(d * d + d3 * d3);
            if (Math.abs(d2) > 9.999999747378752E-6 || Math.abs(d5) > 9.999999747378752E-6) {
                float f3 = (float)(-(Mth.atan2(d2, d5) * 57.2957763671875));
                this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f3, this.maxTurn));
                this.mob.setYya(d2 > 0.0 ? f2 : -f2);
            }
        } else {
            if (!this.hoversInPlace) {
                this.mob.setNoGravity(false);
            }
            this.mob.setYya(0.0f);
            this.mob.setZza(0.0f);
        }
    }
}