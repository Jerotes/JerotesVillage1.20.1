package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Boss.EagleBowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class EagleBowDodgeProjectileGoal extends Goal {
	private final EagleBowEntity mob;
	private Projectile targetProjectile;
	private int cooldown;
	private static final double DODGE_THRESHOLD = 0.9;
	private static final double MIN_PROJECTILE_SPEED = 0.5;

	public EagleBowDodgeProjectileGoal(EagleBowEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		if (cooldown > 0) {
			cooldown--;
			return false;
		}
		List<Projectile> projectiles = mob.level()
				.getEntitiesOfClass(Projectile.class,
						mob.getBoundingBox().inflate(5.0),
						p -> isValidProjectile(p));
		targetProjectile = projectiles.stream()
				.max(Comparator.comparingDouble(p -> {
					Vec3 dir = p.getDeltaMovement();
					Vec3 toMob = mob.position().subtract(p.position());
					return dir.dot(toMob) / (dir.length() * toMob.length());
				}))
				.orElse(null);
		return targetProjectile != null;
	}

	private boolean isValidProjectile(Projectile projectile) {
		if (projectile.getOwner() instanceof LivingEntity owner && owner.isAlliedTo(mob)) {
			return false;
		}

		if (projectile.onGround())
			return false;

		Vec3 velocity = projectile.getDeltaMovement();
		if (velocity.lengthSqr() < MIN_PROJECTILE_SPEED * MIN_PROJECTILE_SPEED) {
			return false;
		}

		Vec3 toMob = mob.position().subtract(projectile.position());
		Vec3 direction = velocity.normalize();
		double angle = direction.dot(toMob.normalize());
		if (angle < DODGE_THRESHOLD) {
			return false;
		}

		double timeToClosest = toMob.dot(direction);
		if (timeToClosest <= 0.0) {
			return false;
		}
		double missDistance = toMob.subtract(direction.scale(timeToClosest)).length();
		return missDistance < mob.getBbWidth() + 1.0;
	}

	@Override
	public void start() {
		if (targetProjectile == null) return;

		Vec3 dodgeDir = calculateDodgeDirection();

		performDodge(dodgeDir);

		cooldown = 20 + mob.getRandom().nextInt(10);

		mob.setShootAnim(Math.max(20, mob.getShootAnim()));
		mob.setShadowAnim(Math.max(40, mob.getShadowAnim()));
	}

	private Vec3 calculateDodgeDirection() {
		Vec3 projectileDir = targetProjectile.getDeltaMovement().normalize();
		Vec3 toMob = mob.position().subtract(targetProjectile.position()).normalize();

		Vec3 baseDir = projectileDir.cross(new Vec3(0, 1, 0)).normalize();

		double verticalFactor = 0;
		if (projectileDir.y > 0.3) {
			verticalFactor = -0.5;
		} else if (projectileDir.y < -0.3) {
			verticalFactor = 0.5;
		}

		if (mob.getRandom().nextBoolean()) {
			baseDir = baseDir.scale(-1);
		}

		return new Vec3(
				baseDir.x * 0.8,
				baseDir.y * 0.5 + verticalFactor,
				baseDir.z * 0.8
		);
	}

	private void performDodge(Vec3 dodgeDir) {
		mob.setDeltaMovement(dodgeDir);

		mob.getLookControl().setLookAt(
				targetProjectile.getX(),
				targetProjectile.getY(),
				targetProjectile.getZ(),
				30.0F, 30.0F);

		if (!mob.isNoGravity() && dodgeDir.y > 0.2) {
			mob.setJumping(true);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}
}