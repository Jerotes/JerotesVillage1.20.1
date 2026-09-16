package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SecondRounderGolemSwordEntity extends Mob implements JerotesEntity, TraceableEntity, OwnableEntity {
	public AnimationState swordAttackAnimationState = new AnimationState();

	public SecondRounderGolemSwordEntity(EntityType<? extends SecondRounderGolemSwordEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 430);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		return builder;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 10.0f;
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean canBeSeenAsEnemy() {
		return false;
	}

	private int idleTick;
	public double attackDamage = 24;
	private int life;
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;

	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	@Nullable
	@Override
	public LivingEntity getOwner() {
		Entity entity;
		if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
			this.owner = (LivingEntity)entity;
		}
		return this.owner;
	}
	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity;
		this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.getOwner() != null) {
			LivingEntity livingEntity = this.getOwner();
			if (entity == livingEntity) {
				return true;
			}
			if (livingEntity != null) {
				return livingEntity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Life", this.life);
		compoundTag.putInt("IdleTick", this.idleTick);
		compoundTag.putDouble("AttackDamage", this.attackDamage);
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.life = compoundTag.getInt("Life");
		this.idleTick = compoundTag.getInt("IdleTick");
		this.attackDamage = compoundTag.getDouble("AttackDamage");
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (this.idleTick > 0) {
			this.idleTick -= 1;
		}
		if (this.idleTick <= 0 && this.deathTime == 0) {
			this.swordAttackAnimationState.start(this.tickCount);
			this.idleTick = 35;
		}
		if (this.idleTick == 7 || this.idleTick == 22) {
			this.swordAttack();
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 48; ++i) {
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getRandomX(1.2f), this.getRandomY(), this.getRandomZ(1.2f), 5, 0, 0, 0, 0);
				}
			}
		}
		if (!this.swordAttackAnimationState.isStarted()) {
			this.idleTick = 0;
		}
		this.life += 1;
		if (this.life >= 240) {
			this.discard();
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return false;
	}

	public void swordAttack() {
		if (this.level() instanceof ServerLevel) {
			if (this.deathTime == 0) {
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_ATTACK_2, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5));
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (hurt == this || hurt == this.getOwner())
						continue;
					if (hurt instanceof SecondRounderGolemSwordEntity)
						continue;
					if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (this.getOwner() != null	&& AttackFind.FindCanNotAttack(this.getOwner(), hurt)) continue;
					float damages = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
					float percentage = (float) OtherMainConfig.SecondRounderGolemSwordAttackPercentage;
					if (this.getOwner() != null) {
						if (AttackFind.FindCanNotAttack(this.getOwner(), hurt)) continue;
						AttackFind.attackBegin(this.getOwner(), hurt);
						AttackFind.attackAfter(this.getOwner(), hurt, 0, 1.0f, true, damages + (hurt.getMaxHealth() * (percentage/ 100)));

					}
					else {
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						AttackFind.attackBegin(this, hurt);
						AttackFind.attackAfter(this, hurt, 0, 1.0f, true, damages + (hurt.getMaxHealth() * (percentage/ 100)));
					}
				}
			}
		}
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		this.swordAttackAnimationState.stop();
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
