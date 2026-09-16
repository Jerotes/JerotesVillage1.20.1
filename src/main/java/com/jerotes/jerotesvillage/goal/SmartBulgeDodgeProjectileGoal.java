package com.jerotes.jerotesvillage.goal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SmartBulgeDodgeProjectileGoal extends Goal {
	private final Mob mob;
	private Projectile targetProjectile;
	private int cooldown;
	private static final double DODGE_THRESHOLD = 0.7;
	private static final double MIN_PROJECTILE_SPEED = 0.5;

	public SmartBulgeDodgeProjectileGoal(Mob mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		if (cooldown > 0) {
			cooldown--;
			return false;
		}
		List<Projectile> projectiles = mob.level().getEntitiesOfClass(Projectile.class, mob.getBoundingBox().inflate(5.0), p -> isValidProjectile(p));

		targetProjectile = projectiles.stream()
				.min(Comparator.comparingDouble(p -> {
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

		Vec3 toMob = mob.position().subtract(projectile.position()).normalize();
		double angle = velocity.normalize().dot(toMob);
		return angle >= DODGE_THRESHOLD;
	}

	@Override
	public void start() {
		if (targetProjectile == null) return;

		Vec3 dodgeDir = calculateDodgeDirection();

		performDodge(dodgeDir);

		cooldown = 5;

		playDodgeEffects();
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

	private void playDodgeEffects() {
		mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(),
				SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE,
				0.5F, 1.5F + mob.getRandom().nextFloat() * 0.2F);
		for (int i = 0; i < 8; i++) {
			mob.level().addParticle(ParticleTypes.CLOUD,
					mob.getX(), mob.getY() + 0.5, mob.getZ(),
					(mob.getRandom().nextDouble() - 0.5) * 0.2,
					0.1,
					(mob.getRandom().nextDouble() - 0.5) * 0.2);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}
}