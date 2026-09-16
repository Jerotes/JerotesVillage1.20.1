package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Neutral.CleaningRobotEntity;
import com.jerotes.jerotes.util.Main;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CleaningRobotRandomStrollGoal extends Goal {
    public static final int DEFAULT_INTERVAL = 120;
    protected final CleaningRobotEntity mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected final double speedModifier;
    protected int interval;
    protected boolean forceTrigger;
    private final boolean checkNoActionTime;

    public CleaningRobotRandomStrollGoal(CleaningRobotEntity pathfinderMob, double d) {
        this(pathfinderMob, d, 120);
    }

    public CleaningRobotRandomStrollGoal(CleaningRobotEntity pathfinderMob, double d, int n) {
        this(pathfinderMob, d, n, true);
    }

    public CleaningRobotRandomStrollGoal(CleaningRobotEntity pathfinderMob, double d, int n, boolean bl) {
        this.mob = pathfinderMob;
        this.speedModifier = d;
        this.interval = n;
        this.checkNoActionTime = bl;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Vec3 vec3;
        if (this.mob.isGoingHome()) {
            return false;
        }
        if (this.mob.hasControllingPassenger()) {
            return false;
        }
        if (!this.forceTrigger) {
            if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
                return false;
            }
            if (this.mob.getRandom().nextInt(RandomStrollGoal.reducedTickDelay(this.interval)) != 0) {
                return false;
            }
        }
        if ((vec3 = this.getPosition()) == null) {
            return false;
        }
        if (!this.mob.isOn()) {
            return false;
        }
        this.wantedX = vec3.x;
        this.wantedY = vec3.y;
        this.wantedZ = vec3.z;
        this.forceTrigger = false;
        return true;
    }

    @Nullable
    protected Vec3 getPosition() {
        return Main.findSpawnPositionNearFillOnBlock(this.mob.level(), this.mob.getHomePos(),32).getCenter();
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.mob.isOn()) {
            return false;
        }
        return !this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    public void setInterval(int n) {
        this.interval = n;
    }
}

