package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SwellingStareMeleeAttackGoal extends Goal {
	private final PathfinderMob mob;
	private final double speedModifier;
	private int attackCooldown;
	private int pathCooldown;

	public SwellingStareMeleeAttackGoal(PathfinderMob mob, double speedModifier) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = this.mob.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		return !AttackFind.FindCanNotAttack(this.mob, target);
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity target = this.mob.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		return !AttackFind.FindCanNotAttack(this.mob, target);
	}

	@Override
	public void start() {
		this.attackCooldown = 0;
		this.pathCooldown = 0;
	}

	@Override
	public void stop() {
		this.mob.getNavigation().stop();
	}

	@Override
	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (target == null) {
			return;
		}
		this.mob.getLookControl().setLookAt(target, 360.0F, 360.0F);
		this.mob.lookAt(target, 360.0F, 360.0F);
		if (this.attackCooldown > 0) {
			--this.attackCooldown;
		}
		if (this.mob.isWithinMeleeAttackRange(target)) {
			this.mob.getNavigation().stop();
			if (this.attackCooldown <= 0) {
				this.attackCooldown = this.adjustedTickDelay(20);
				this.mob.doHurtTarget(target);
			}
		}
		else {
			if (this.pathCooldown > 0) {
				--this.pathCooldown;
			}
			if (this.pathCooldown <= 0) {
				this.pathCooldown = this.adjustedTickDelay(10);
				this.mob.getNavigation().moveTo(target, this.speedModifier);
			}
		}
	}
}
