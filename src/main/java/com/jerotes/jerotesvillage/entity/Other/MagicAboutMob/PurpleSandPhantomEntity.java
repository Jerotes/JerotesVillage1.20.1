package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class PurpleSandPhantomEntity extends Mob implements JerotesEntity, TraceableEntity, OwnableEntity, MagicAbout {
	public PurpleSandPhantomEntity(EntityType<? extends PurpleSandPhantomEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		return builder;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
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
	protected void doPush(Entity entity) {
		if (entity instanceof LivingEntity && !(entity instanceof PurpleSandPhantomEntity) && entity != this.getOwner()) {
			this.discardAttack();
		}
		super.doPush(entity);
	}

	public int spellLevelDamage = 4;
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
	//
	@Override
	public int getSpellLevel() {
		return this.spellLevelDamage;
	}
	public void setSpellLevelDamage(int n) {
		this.spellLevelDamage = n;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
		compoundTag.putInt("Life", this.life);
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
		this.life = compoundTag.getInt("Life");
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.life += 1;
		if (this.life >= 240) {
			this.discardAttack();
		}
		//主人死亡
		if (this.getOwner() != null && !this.getOwner().isAlive()) {
			this.discardAttack();
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.getEntity() != null && damageSource.getEntity() != this.getOwner()) {
			this.discardAttack();
		}
		return true;
	}

	public void discardAttack() {
		if (this.level() instanceof ServerLevel serverLevel) {
			if (this.isAlive()) {
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2, 2, 2));
				list.removeIf(livingEntity -> livingEntity == this.getOwner());
				list.removeIf(livingEntity -> this.getOwner() != null && livingEntity.isAlliedTo(this.getOwner()));
				list.removeIf(livingEntity -> livingEntity instanceof PurpleSandPhantomEntity);
				list.removeIf(livingEntity -> AttackFind.FindCanNotAttack(this, livingEntity));
				list.removeIf(livingEntity -> this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), livingEntity));
				if (!list.isEmpty() || this.life > 240) {
					for (LivingEntity livingEntitys : list) {
						DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.WITHER, this, this.getOwner());
						livingEntitys.hurt(damageSource,
								Main.rollDice(spellLevelDamage, 16, this.random)
						);
					}
					for (int i = 0; i < 6; ++i) {
						serverLevel.sendParticles(ParticleTypes.WITCH, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 5, 0, 0, 0, 0);
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.TELEPORT, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
					this.discard();
				}
			}
		}
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
