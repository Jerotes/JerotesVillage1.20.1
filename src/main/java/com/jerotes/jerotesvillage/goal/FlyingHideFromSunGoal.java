package com.jerotes.jerotesvillage.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FlyingHideFromSunGoal extends Goal {
    protected final PathfinderMob mob;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final double speedModifier;
    private final Level level;

    public FlyingHideFromSunGoal(PathfinderMob pathfinderMob, double d) {
        this.mob = pathfinderMob;
        this.speedModifier = d;
        this.level = pathfinderMob.level();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!this.level.isDay()) {
            return false;
        }
        if (!this.level.canSeeSky(this.mob.blockPosition())) {
            return false;
        }
        if (!this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            return false;
        }
        return this.setWantedPos();
    }

    protected boolean setWantedPos() {
        Vec3 vec3 = this.getHidePos();
        if (vec3 == null) {
            return false;
        }
        this.wantedX = vec3.x;
        this.wantedY = vec3.y;
        this.wantedZ = vec3.z;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Nullable
    protected Vec3 getHidePos() {
        RandomSource randomSource = this.mob.getRandom();
        BlockPos blockPos = this.mob.blockPosition();
        double bestScore = Double.MAX_VALUE;
        Vec3 best = null;
        for (int i = 0; i < 24; ++i) {
            BlockPos pos = blockPos.offset(randomSource.nextInt(25) - 12, randomSource.nextInt(9) - 6, randomSource.nextInt(25) - 12);
            if (this.level.canSeeSky(pos)) {
                continue;
            }
            AABB box = this.mob.getType().getDimensions().makeBoundingBox(Vec3.atBottomCenterOf(pos));
            if (!this.level.noCollision(this.mob, box)) {
                continue;
            }
            if (this.level.getBlockState(pos).isSuffocating(this.level, pos)) {
                continue;
            }
            double dist = this.mob.position().distanceToSqr(Vec3.atBottomCenterOf(pos));
            if (dist < bestScore) {
                bestScore = dist;
                best = Vec3.atBottomCenterOf(pos);
            }
        }
        return best;
    }
}
