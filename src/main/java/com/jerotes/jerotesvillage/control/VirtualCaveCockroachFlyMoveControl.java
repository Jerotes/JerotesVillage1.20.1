package com.jerotes.jerotesvillage.control;

import com.jerotes.jerotesvillage.entity.Animal.VirtualCaveCockroachEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.player.Player;

public class VirtualCaveCockroachFlyMoveControl extends MoveControl {
    private final int maxTurn;

    public VirtualCaveCockroachFlyMoveControl(Mob mob, int n) {
        super(mob);
        this.maxTurn = n;
    }

    public void tick() {
        if (this.mob instanceof AgeableMob ageableMob && ageableMob.isBaby()) {
            super.tick();
            return;
        }
        if (this.mob instanceof VirtualCaveCockroachEntity virtualCaveCockroachEntity && !virtualCaveCockroachEntity.canFlySize()) {
            super.tick();
            return;
        }
        if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            this.mob.setNoGravity(true);
            double $$0 = this.wantedX - this.mob.getX();
            double $$1 = this.wantedY - this.mob.getY();
            double $$2 = this.wantedZ - this.mob.getZ();
            double $$3 = $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
            if ($$3 < 2.500000277905201E-7) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }

            float $$4 = (float)(Mth.atan2($$2, $$0) * 57.2957763671875) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), $$4, 90.0F));
            float $$6;
            if (this.mob.onGround()) {
                $$6 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
                double speed = 2;
                if (this.mob.getAttribute(Attributes.FLYING_SPEED) != null) {
                    speed = this.mob.getAttributeValue(Attributes.FLYING_SPEED);
                }
                $$6 = (float)(this.speedModifier * speed);
            }

            this.mob.setSpeed($$6);
            double $$7 = Math.sqrt($$0 * $$0 + $$2 * $$2);
            if (Math.abs($$1) > 9.999999747378752E-6 || Math.abs($$7) > 9.999999747378752E-6) {
                float $$8 = (float)(-(Mth.atan2($$1, $$7) * 57.2957763671875));
                this.mob.setXRot(this.rotlerp(this.mob.getXRot(), $$8, (float)this.maxTurn));
                this.mob.setYya($$1 > 0.0 ? $$6 : -$$6);
            }
        }
        else {
            if (this.mob instanceof VirtualCaveCockroachEntity virtualCaveCockroach) {
                double d = Math.abs(virtualCaveCockroach.getX() - virtualCaveCockroach.xOld);
                double d2 = Math.abs(virtualCaveCockroach.getZ() - virtualCaveCockroach.zOld);
                this.mob.setNoGravity(virtualCaveCockroach.canFlySize() && virtualCaveCockroach.getControllingPassenger() instanceof Player && virtualCaveCockroach.isFlying());
            }
            else {
                this.mob.setNoGravity(false);
            }
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }
}
