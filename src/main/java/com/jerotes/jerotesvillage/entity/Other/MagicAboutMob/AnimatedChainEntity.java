package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.google.common.annotations.VisibleForTesting;
import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.control.NoRotationControl;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class AnimatedChainEntity extends Mob implements JerotesEntity, TraceableEntity, CanBeIllagerFactionEntity, OwnableEntity {
	private static final EntityDataAccessor<Float> ID_SIZE = SynchedEntityData.defineId(AnimatedChainEntity.class, EntityDataSerializers.FLOAT);

	public AnimatedChainEntity(EntityType<? extends AnimatedChainEntity> type, Level world) {
		super(type, world);
		this.fixupDimensions();
	}
	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CHAIN_BREAK;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.CHAIN_HIT;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
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
	public boolean isOnFire() {
		return false;
	}
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(new Vec3(0, vec3.y, 0));
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new NoRotationControl(this);
	}
	@Override
	public boolean isIllagerFaction() {
		return this.getOwner() != null && (EntityFactionFind.isRaider(this.getOwner()));
	}

	@Override
	public void setIllagerFaction(boolean b) {

	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0f;
	}
	@Override
	protected AABB makeBoundingBox() {
		return AABB.ofSize(new Vec3(this.getX(), this.getY() + this.getSize() / 2, this.getZ()), this.getSize(), this.getSize(), this.getSize());
	}

	public int hasUse;
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
	@Nullable
	private LivingEntity prisoner;
	@Nullable
	private UUID prisonerUUID;
	@Nullable
	public LivingEntity getPrisoner() {
		Entity entity;
		if (this.prisoner == null && this.prisonerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.prisonerUUID)) instanceof LivingEntity) {
			this.prisoner = (LivingEntity)entity;
		}
		return this.prisoner;
	}
	public void setPrisoner(@Nullable LivingEntity livingEntity) {
		this.prisoner = livingEntity;
		this.prisonerUUID = livingEntity == null ? null : livingEntity.getUUID();
		this.hasUse = 1;
		this.setBoundingBox(this.makeBoundingBox());
	}
	@VisibleForTesting
	public void setSize(float f) {
		float f2 = Mth.clamp(f, 0, 256);
		this.entityData.set(ID_SIZE, f2);
		this.setBoundingBox(this.makeBoundingBox());
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public float getSize() {
		return this.entityData.get(ID_SIZE);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("HasUse", this.hasUse);
		compoundTag.putFloat("Size", this.getSize());
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		if (this.prisonerUUID != null) {
			compoundTag.putUUID("Prisoner", this.prisonerUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setSize(compoundTag.getFloat("Size"));
		super.readAdditionalSaveData(compoundTag);
		this.hasUse = compoundTag.getInt("HasUse");
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		if (compoundTag.hasUUID("Prisoner")) {
			this.prisonerUUID = compoundTag.getUUID("Prisoner");
		}
		this.setBoundingBox(this.makeBoundingBox());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ID_SIZE, 1f);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ID_SIZE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}

	@Override
	public void tick() {
		super.tick();
		this.noPhysics = true;
		this.setNoGravity(true);
		this.setJumping(false);
		if (!this.level().isClientSide) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			this.goalSelector.setControlFlag(Goal.Flag.TARGET, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
		//破碎
		if (this.tickCount % 60 == 0) {
			this.hurt(this.damageSources().genericKill() ,4);
		}
		//控制
		if (this.getPrisoner() != null) {
			this.moveTo(this.getPrisoner().getX(), this.getPrisoner().getY(0.5) - this.getSize() / 2, this.getPrisoner().getZ());
			if (!this.getPrisoner().level().isClientSide) {
				this.getPrisoner().addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 5, 0, false, false), this);
			}
		}
		if (this.hasUse == 1 && (this.getPrisoner() == null || !this.getPrisoner().isAlive())) {
			this.hurt(this.damageSources().genericKill() ,20);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return false;
		if (damageSource.is(DamageTypes.IN_WALL))
			return false;
		if (damageSource.is(DamageTypes.DROWN))
			return false;
		if (damageSource.is(DamageTypes.DRY_OUT))
			return false;
		if (damageSource.is(DamageTypes.CRAMMING))
			return false;
		if (damageSource.is(DamageTypes.CACTUS))
			return false;
		if (damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return false;
		if (this.level() instanceof ServerLevel _level) {
			for (int i = 0; i < 24; ++i) {
				_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHAIN.defaultBlockState()), this.getChainX(0.5f), this.getChainY(0.5f), this.getChainZ(0.5f), 0, 0.3, 0, 0.3, 0);
			}
		}
		return super.hurt(damageSource, amount);
	}


	public double getChainX(double d) {
		return this.getX() + (double)this.getSize() * d;
	}
	public double getRandomX(double d) {
		return this.getChainX((2.0D * this.random.nextDouble() - 1.0D) * d);
	}
	public double getChainY(double d) {
		return this.getY() + (double)this.getSize() * d;
	}
	public double getRandomY(double d) {
		return this.getChainY((2.0D * this.random.nextDouble() - 1.0D) * d);
	}
	public double getChainZ(double d) {
		return this.getZ() + (double)this.getSize() * d;
	}
	public double getRandomZ(double d) {
		return this.getChainZ((2.0D * this.random.nextDouble() - 1.0D) * d);
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setSize(1);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
