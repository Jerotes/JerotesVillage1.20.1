
package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.PetitionerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class RushToOverlapMeleeGoal extends Goal {
    private enum State {
        BRAKE, AIM, CHARGE, RECOVER, ORBIT
    }

    private static final double CHARGE_OVERTRAVEL = 3.0;
    private static final double ORBIT_RADIUS = 3.0;

    private static final double CHARGE_MAX_SPEED = 0.75;
    private static final double CHARGE_ACCELERATION = 0.10;
    private static final double BRAKE_ACCELERATION = 0.12;

    private static final double RECOVER_MAX_SPEED = 0.18;
    private static final double ORBIT_ANGULAR_SPEED = Math.PI / 60.0;
    private static final double ORBIT_RADIAL_CORRECTION = 0.10;
    private static final double ORBIT_MAX_RADIAL_SPEED = 0.045;

    private static final double HEIGHT_RESPONSE = 0.20;
    private static final double MAX_VERTICAL_SPEED = 0.10;
    private static final double VERTICAL_ACCELERATION = 0.025;

    private static final float BODY_TURN_SPEED = 5.0F;
    private static final float HEAD_TURN_SPEED = 8.0F;
    private static final float ORBIT_BODY_TURN_SPEED = 12.0F;

    private static final float AIM_ANGLE = 5.0F;
    private static final int AIM_STABLE_TICKS = 5;
    private static final int AIM_TIMEOUT = 45;
    private static final int BRAKE_TIMEOUT = 8;
    private static final int RECOVER_TIMEOUT = 12;
    private static final int ORBIT_DURATION = 30;

    private static final double STOP_SPEED = 0.015;
    private static final double ORBIT_RADIUS_TOLERANCE = 0.12;

    private final PathfinderMob mob;

    private State state = State.BRAKE;
    private State nextStateAfterBrake = State.AIM;

    private Vec3 chargeDirection = Vec3.ZERO;

    private int timer;
    private int aimStableTicks;
    private int orbitSign = 1;

    private double chargeTargetX;
    private double chargeTargetZ;

    private boolean chargeHit;
    private boolean specialHold;

    public RushToOverlapMeleeGoal(PathfinderMob mob, double ignoredSpeedModifier) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return !(target instanceof Player player) || !player.isCreative() && !player.isSpectator();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.mob.setAggressive(true);
        this.mob.setNoGravity(true);
        this.mob.getNavigation().stop();
        this.mob.getMoveControl().setWantedPosition(
                this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0
        );
        this.clearMoveInput();
        this.specialHold = false;
        this.beginBrake(State.AIM);
    }

    @Override
    public void stop() {
        this.mob.setAggressive(false);
        this.mob.setDeltaMovement(Vec3.ZERO);
        this.mob.getNavigation().stop();
        this.mob.getMoveControl().setWantedPosition(
                this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0
        );
        this.clearMoveInput();
    }

    private void clearMoveInput() {
        this.mob.setNoGravity(true);
        this.mob.setSpeed(0.0F);
        this.mob.setXxa(0.0F);
        this.mob.setYya(0.0F);
        this.mob.setZza(0.0F);
    }

    private double flightY(LivingEntity target) {
        return target.getEyeY() - (double) this.mob.getBbHeight() * 0.5;
    }

    private double verticalVelocity(LivingEntity target) {
        Vec3 movement = this.mob.getDeltaMovement();
        double heightError = this.flightY(target) - this.mob.getY();

        double wanted = Mth.clamp(
                heightError * HEIGHT_RESPONSE,
                -MAX_VERTICAL_SPEED,
                MAX_VERTICAL_SPEED
        );

        return movement.y + Mth.clamp(
                wanted - movement.y,
                -VERTICAL_ACCELERATION,
                VERTICAL_ACCELERATION
        );
    }

    private void setHorizontalVelocity(double x, double z, LivingEntity target) {
        this.mob.setDeltaMovement(x, this.verticalVelocity(target), z);
        this.clearMoveInput();
    }

    private void holdHorizontal(LivingEntity target) {
        this.setHorizontalVelocity(0.0, 0.0, target);
    }

    private void hardStop() {
        this.mob.setDeltaMovement(Vec3.ZERO);
        this.clearMoveInput();
    }

    private void beginBrake(State nextState) {
        this.state = State.BRAKE;
        this.nextStateAfterBrake = nextState;
        this.timer = 0;
        this.aimStableTicks = 0;

        if (nextState == State.RECOVER) {
            this.orbitSign = this.mob.getRandom().nextBoolean() ? 1 : -1;
        }
    }

    private float targetYaw(LivingEntity target) {
        double dx = target.getX() - this.mob.getX();
        double dz = target.getZ() - this.mob.getZ();

        return (float) (
                Mth.atan2(-dx, dz) * (180.0 / Math.PI)
        );
    }

    private float directionYaw(Vec3 direction) {
        return (float) (
                Mth.atan2(-direction.x, direction.z) * (180.0 / Math.PI)
        );
    }

    private float approachYaw(float current, float target, float maxChange) {
        float difference = Mth.wrapDegrees(target - current);
        return current + Mth.clamp(difference, -maxChange, maxChange);
    }

    private void rotateBodyTo(float yaw, float maxChange) {
        float nextYaw = this.approachYaw(
                this.mob.getYRot(),
                yaw,
                maxChange
        );

        this.mob.setYRot(nextYaw);
        this.mob.setYBodyRot(nextYaw);
        this.mob.setXRot(0.0F);
    }

    private void lookAtTarget(LivingEntity target) {
        this.mob.getLookControl().setLookAt(
                target.getX(),
                target.getEyeY(),
                target.getZ(),
                HEAD_TURN_SPEED,
                HEAD_TURN_SPEED
        );
    }

    private void beginCharge(LivingEntity target) {
        this.chargeTargetX = target.getX();
        this.chargeTargetZ = target.getZ();

        Vec3 direction = new Vec3(
                this.chargeTargetX - this.mob.getX(),
                0.0,
                this.chargeTargetZ - this.mob.getZ()
        );

        if (direction.lengthSqr() < 1.0E-6) {
            float yaw = this.mob.getYRot() * ((float) Math.PI / 180.0F);
            direction = new Vec3(
                    -Mth.sin(yaw),
                    0.0,
                    Mth.cos(yaw)
            );
        }

        this.chargeDirection = direction.normalize();

        float yaw = this.directionYaw(this.chargeDirection);

        this.mob.setYRot(yaw);
        this.mob.setYBodyRot(yaw);
        this.mob.setYHeadRot(yaw);

        this.chargeHit = false;
        this.timer = 0;
        this.state = State.CHARGE;
    }

    private void beginOrbit() {
        this.state = State.ORBIT;
        this.timer = 0;
    }

    private void tickBrake(LivingEntity target) {
        this.timer++;

        Vec3 movement = this.mob.getDeltaMovement();

        double horizontalSpeed = Math.sqrt(
                movement.x * movement.x + movement.z * movement.z
        );

        double nextSpeed = Math.max(
                0.0,
                horizontalSpeed - BRAKE_ACCELERATION
        );

        double scale = horizontalSpeed > 1.0E-8
                ? nextSpeed / horizontalSpeed
                : 0.0;

        this.setHorizontalVelocity(
                movement.x * scale,
                movement.z * scale,
                target
        );

        if (nextSpeed <= STOP_SPEED || this.timer >= BRAKE_TIMEOUT) {
            this.holdHorizontal(target);

            if (this.nextStateAfterBrake == State.CHARGE) {
                this.beginCharge(target);
            } else {
                this.state = this.nextStateAfterBrake;
                this.timer = 0;
                this.aimStableTicks = 0;
            }
        }
    }

    private void tickAim(LivingEntity target) {
        this.timer++;

        this.holdHorizontal(target);

        float wantedYaw = this.targetYaw(target);

        this.rotateBodyTo(wantedYaw, BODY_TURN_SPEED);
        this.lookAtTarget(target);

        if (Math.abs(Mth.wrapDegrees(wantedYaw - this.mob.getYRot())) <= AIM_ANGLE) {
            this.aimStableTicks++;
        } else {
            this.aimStableTicks = 0;
        }

        if (this.aimStableTicks >= AIM_STABLE_TICKS || this.timer >= AIM_TIMEOUT) {
            this.beginCharge(target);
        }
    }

    private void tickCharge(LivingEntity target) {
        this.timer++;

        double speed = Math.min(
                CHARGE_MAX_SPEED,
                this.currentHorizontalSpeed() + CHARGE_ACCELERATION
        );

        this.setHorizontalVelocity(
                this.chargeDirection.x * speed,
                this.chargeDirection.z * speed,
                target
        );

        this.rotateBodyTo(
                this.directionYaw(this.chargeDirection),
                BODY_TURN_SPEED
        );

        this.lookAtTarget(target);

        if (!this.chargeHit && this.mob.isWithinMeleeAttackRange(target)) {
            this.chargeHit = true;
            this.mob.doHurtTarget(target);
        }

        double passed =
                (this.mob.getX() - this.chargeTargetX) * this.chargeDirection.x
                        + (this.mob.getZ() - this.chargeTargetZ) * this.chargeDirection.z;

        double stoppingDistance = this.estimateBrakingDistance(speed);

        if (passed >= CHARGE_OVERTRAVEL - stoppingDistance || this.timer >= 100) {
            this.beginBrake(State.RECOVER);
        }
    }

    private double estimateBrakingDistance(double speed) {
        double distance = speed;
        double afterMoveSpeed = speed * 0.91;

        for (int i = 0; i < 12 && afterMoveSpeed > STOP_SPEED; i++) {
            double nextSpeed = Math.max(
                    0.0,
                    afterMoveSpeed - BRAKE_ACCELERATION
            );

            distance += nextSpeed;
            afterMoveSpeed = nextSpeed * 0.91;
        }

        return distance;
    }

    private void tickRecover(LivingEntity target) {
        this.timer++;

        double dx = this.mob.getX() - target.getX();
        double dz = this.mob.getZ() - target.getZ();

        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance < 1.0E-6) {
            dx = this.chargeDirection.x;
            dz = this.chargeDirection.z;
            distance = Math.sqrt(dx * dx + dz * dz);
        }

        if (distance < 1.0E-6) {
            dx = 1.0;
            dz = 0.0;
            distance = 1.0;
        }

        double error = distance - ORBIT_RADIUS;

        if (Math.abs(error) <= ORBIT_RADIUS_TOLERANCE || this.timer >= RECOVER_TIMEOUT) {
            this.holdHorizontal(target);
            this.beginOrbit();
            return;
        }

        double radialX = dx / distance;
        double radialZ = dz / distance;

        double radialSpeed = Mth.clamp(
                -error * 0.25,
                -RECOVER_MAX_SPEED,
                RECOVER_MAX_SPEED
        );

        this.setHorizontalVelocity(
                radialX * radialSpeed,
                radialZ * radialSpeed,
                target
        );

        this.rotateBodyTo(
                this.directionYaw(
                        new Vec3(radialX * radialSpeed, 0.0, radialZ * radialSpeed)
                ),
                BODY_TURN_SPEED
        );

        this.lookAtTarget(target);
    }

    private void tickOrbit(LivingEntity target) {
        this.timer++;

        double dx = this.mob.getX() - target.getX();
        double dz = this.mob.getZ() - target.getZ();

        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance < 1.0E-6) {
            dx = this.chargeDirection.x;
            dz = this.chargeDirection.z;
            distance = Math.sqrt(dx * dx + dz * dz);
        }

        if (distance < 1.0E-6) {
            dx = 1.0;
            dz = 0.0;
            distance = 1.0;
        }

        double radialX = dx / distance;
        double radialZ = dz / distance;

        double tangentX = -radialZ * this.orbitSign;
        double tangentZ = radialX * this.orbitSign;

        double radialError = distance - ORBIT_RADIUS;

        double radialSpeed = Mth.clamp(
                -radialError * ORBIT_RADIAL_CORRECTION,
                -ORBIT_MAX_RADIAL_SPEED,
                ORBIT_MAX_RADIAL_SPEED
        );

        double tangentSpeed = distance * ORBIT_ANGULAR_SPEED / 0.91;

        double velocityX = tangentX * tangentSpeed + radialX * radialSpeed;
        double velocityZ = tangentZ * tangentSpeed + radialZ * radialSpeed;

        this.setHorizontalVelocity(
                velocityX,
                velocityZ,
                target
        );

        this.rotateBodyTo(
                this.targetYaw(target),
                ORBIT_BODY_TURN_SPEED
        );

        this.lookAtTarget(target);

        if (this.mob.isWithinMeleeAttackRange(target)) {
            this.mob.doHurtTarget(target);
        }

        if (this.timer >= ORBIT_DURATION) {
            this.beginBrake(State.CHARGE);
        }
    }

    private double currentHorizontalSpeed() {
        Vec3 movement = this.mob.getDeltaMovement();

        return Math.sqrt(
                movement.x * movement.x + movement.z * movement.z
        );
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();

        if (target == null || !target.isAlive()) {
            this.hardStop();
            return;
        }

        this.mob.setNoGravity(true);

        boolean special = this.mob instanceof PetitionerEntity petitioner
                && petitioner.specialAction();

        if (special) {
            this.specialHold = true;
            this.timer = 0;
            this.hardStop();
            return;
        }

        if (this.specialHold) {
            this.specialHold = false;
            this.beginBrake(State.AIM);
        }

        switch (this.state) {
            case BRAKE -> this.tickBrake(target);
            case AIM -> this.tickAim(target);
            case CHARGE -> this.tickCharge(target);
            case RECOVER -> this.tickRecover(target);
            case ORBIT -> this.tickOrbit(target);
        }
    }
}
