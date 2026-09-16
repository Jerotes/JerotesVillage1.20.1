package com.jerotes.jerotesvillage.navigation;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class CanGroundWaterBoundPathNavigation extends PathNavigation {
    private boolean allowBreaching;

    public CanGroundWaterBoundPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    protected PathFinder createPathFinder(int p_26598_) {
        this.allowBreaching = this.mob.getType() == JerotesVillageEntityType.PRANCED_DOLPHIN.get();
        this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching);
        return new PathFinder(this.nodeEvaluator, p_26598_);
    }

    protected boolean canUpdatePath() {
        return this.allowBreaching || Main.isInFluid(this.mob);
    }

    protected Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), this.mob.getY(0.5), this.mob.getZ());
    }

    protected double getGroundY(Vec3 p_186136_) {
        return p_186136_.y;
    }

    protected boolean canMoveDirectly(Vec3 p_186138_, Vec3 p_186139_) {
        return isClearForMovementBetween(this.mob, p_186138_, p_186139_, false);
    }

    public boolean isStableDestination(BlockPos blockPos) {
        return !this.level.getBlockState(blockPos).isSolidRender(this.level, blockPos);
    }

    public void setCanFloat(boolean p_26612_) {
    }
}
