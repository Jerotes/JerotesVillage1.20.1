package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Neutral.CleaningRobotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class CleaningRobotGoHomeGoal extends Goal {
    private final CleaningRobotEntity cleaningRobot;
    private final double speedModifier;
    private boolean stuck;
    private int closeToHomeTryTicks;
    private static final int GIVE_UP_TICKS = 600;

    public CleaningRobotGoHomeGoal(CleaningRobotEntity cleaningRobot, double d) {
        this.cleaningRobot = cleaningRobot;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (this.cleaningRobot.getRandom().nextInt(CleaningRobotGoHomeGoal.reducedTickDelay(700)) != 0) {
            return false;
        }
        return !this.cleaningRobot.getHomePos().closerToCenterThan(this.cleaningRobot.position(), 6.0);
    }

    @Override
    public void start() {
        this.cleaningRobot.setGoingHome(true);
        this.stuck = false;
        this.closeToHomeTryTicks = 0;
    }

    @Override
    public void stop() {
        this.cleaningRobot.setGoingHome(false);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.cleaningRobot.getHomePos().closerToCenterThan(this.cleaningRobot.position(), 6.0) && !this.stuck && this.closeToHomeTryTicks <= this.adjustedTickDelay(600);
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.cleaningRobot.getHomePos();
        if (blockPos.distToCenterSqr(this.cleaningRobot.getOnPos().getCenter()) <= 1024) {
            boolean bl = blockPos.closerToCenterThan(this.cleaningRobot.position(), 16.0);
            if (bl) {
                ++this.closeToHomeTryTicks;
            }
            if (this.cleaningRobot.getNavigation().isDone()) {
                Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);
                Vec3 vec32 = DefaultRandomPos.getPosTowards(this.cleaningRobot, 16, 3, vec3, 0.3141592741012573);
                if (vec32 == null) {
                    vec32 = DefaultRandomPos.getPosTowards(this.cleaningRobot, 8, 7, vec3, 1.5707963705062866);
                }
                if (vec32 != null && !bl && !this.cleaningRobot.level().getBlockState(BlockPos.containing(vec32)).is(Blocks.WATER)) {
                    vec32 = DefaultRandomPos.getPosTowards(this.cleaningRobot, 16, 5, vec3, 1.5707963705062866);
                }
                if (vec32 == null) {
                    this.stuck = true;
                    return;
                }
                this.cleaningRobot.getNavigation().moveTo(vec32.x, vec32.y, vec32.z, this.speedModifier);
            }
        }
    }
}