package com.jerotes.jerotesvillage.entity.Other.MagicAboutMob;

import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UncleanTentacleEntity extends Mob implements JerotesEntity, TraceableEntity, CanBeIllagerFactionEntity, OwnableEntity, MagicAbout {
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(UncleanTentacleEntity.class, EntityDataSerializers.INT);
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState stopAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(UncleanTentacleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(UncleanTentacleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(UncleanTentacleEntity.class, EntityDataSerializers.INT);

	public UncleanTentacleEntity(EntityType<? extends UncleanTentacleEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_TENTACLE_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_TENTACLE_HURT;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
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
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(new Vec3(0, vec3.y, 0));
	}
	@Override
	public boolean isIllagerFaction() {
		return this.getOwner() != null && (EntityFactionFind.isRaider(this.getOwner()));
	}

	@Override
	public void setIllagerFaction(boolean b) {

	}

	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(1.25d, 1.25d, 1.25d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
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

	public int maxTick = 600;
	//动画
	public void setAnimTick(int n){
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick(){
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else if (Objects.equals(animation, "start")){
			return 2;
		}
		else if (Objects.equals(animation, "stop")){
			return 3;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		list.add(this.startAnimationState);
		list.add(this.stopAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != startAnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			state.stop();
		}
	}
	//
	public void setStartTick(int n){
		this.getEntityData().set(START_TICK, n);
	}
	public int getStartTick(){
		return this.getEntityData().get(START_TICK);
	}
	//
	private int spellLevelDamage = 4;
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
		compoundTag.putInt("StartTick", this.getStartTick());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("MaxTick", this.maxTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
		this.setStartTick(compoundTag.getInt("StartTick"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.maxTick = compoundTag.getInt("MaxTick");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(START_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
					case 2:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 3:
						this.stopAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stopAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide()) {
			this.setStartTick(this.getStartTick() + 1);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		if (this.getStartTick() == 10) {
			this.startAnimationState.stop();
		}
		//最大生命
		int trueMaxTick = this.maxTick;
		if (this.maxTick == 0) {
			trueMaxTick = 600;
		}
		if (this.getStartTick() >= trueMaxTick) {
			this.hurt(this.damageSources().genericKill(), 20f);
		}
		//粒子
		if (this.level().isClientSide) {
			for (int i = 0; i < 5; ++i) {
				this.level().addParticle(JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN.get(), this.getRandomX(0.5), this.getY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//近战
		if (this.owner instanceof Mob mob && mob.getTarget() != null) {
			this.getLookControl().setLookAt(mob.getTarget(), 30.0f, 30.0f);
			this.lookAt(mob.getTarget(), 30.0f, 30.0f);
		}
		if (this.getAttackTick() <= -20 && this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget()) && this.getSensing().hasLineOfSight(this.getTarget()) && this.getAttackBoundingBox().intersects(this.getTarget().getBoundingBox()) && this.getSensing().hasLineOfSight(this.getTarget()) && !AttackFind.FindCanNotAttack(this, getTarget())) {
			this.swing(InteractionHand.MAIN_HAND);
			this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
			this.doHurtTarget(this.getTarget());
		}
		else if (this.getAttackTick() <= -20 && this.owner instanceof Mob mob && mob.getTarget() != null && this.isWithinMeleeAttackRange(mob.getTarget()) && this.isWithinMeleeAttackRange(mob.getTarget()) && this.getSensing().hasLineOfSight(mob.getTarget()) && this.getAttackBoundingBox().intersects(mob.getTarget().getBoundingBox()) && this.getSensing().hasLineOfSight(mob.getTarget()) && !AttackFind.FindCanNotAttack(this, mob.getTarget())) {
			this.swing(InteractionHand.MAIN_HAND);
			this.getLookControl().setLookAt(mob.getTarget(), 360.0f, 360.0f);
			this.lookAt(mob.getTarget(), 360.0f, 360.0f);
			this.doHurtTarget(mob.getTarget());
		}
		else if (this.getAttackTick() <= -20 && this.owner != null && this.owner.getLastHurtMob() != null && this.isWithinMeleeAttackRange(this.owner.getLastHurtMob()) && this.isWithinMeleeAttackRange(this.owner.getLastHurtMob()) && this.getSensing().hasLineOfSight(this.owner.getLastHurtMob()) && this.getAttackBoundingBox().intersects(this.owner.getLastHurtMob().getBoundingBox()) && this.getSensing().hasLineOfSight(this.owner.getLastHurtMob()) && !AttackFind.FindCanNotAttack(this, this.owner.getLastHurtMob())) {
			this.swing(InteractionHand.MAIN_HAND);
			this.getLookControl().setLookAt(this.owner.getLastHurtMob(), 360.0f, 360.0f);
			this.lookAt(this.owner.getLastHurtMob(), 360.0f, 360.0f);
			this.doHurtTarget(this.owner.getLastHurtMob());
		}
		else if (this.getAttackTick() <= -20 && this.owner != null && this.owner.getLastHurtByMob() != null && this.isWithinMeleeAttackRange(this.owner.getLastHurtByMob()) && this.isWithinMeleeAttackRange(this.owner.getLastHurtByMob()) && this.getSensing().hasLineOfSight(this.owner.getLastHurtByMob()) && this.getAttackBoundingBox().intersects(this.owner.getLastHurtByMob().getBoundingBox()) && this.getSensing().hasLineOfSight(this.owner.getLastHurtByMob()) && !AttackFind.FindCanNotAttack(this, this.owner.getLastHurtByMob())) {
			this.swing(InteractionHand.MAIN_HAND);
			this.getLookControl().setLookAt(this.owner.getLastHurtByMob(), 360.0f, 360.0f);
			this.lookAt(this.owner.getLastHurtByMob(), 360.0f, 360.0f);
			this.doHurtTarget(this.owner.getLastHurtByMob());
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-20, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 6 && this.deathTime == 0) {
			this.trueHurt();
		}
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.NECROMANCY_WARLOCK_TENTACLE_ATTACK, 1.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.75));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.MOB_ATTACK, this);
			if (this.getOwner() != null) {
				damageSource = AttackFind.findDamageType(this, DamageTypes.MOB_ATTACK, this, this.getOwner());
				if (this.getOwner() instanceof Player) {
					damageSource = AttackFind.findDamageType(this, DamageTypes.PLAYER_ATTACK, this, this.getOwner());
				}
			}
			AttackFind.attackAfterCustomDamageNoEnchantAbout(this, hurt, damageSource, 0.2f + 0.2f * spellLevelDamage, 1.0f, false, 0f);
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -20) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAnimationState("attack");
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.IN_WALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.UNCLEAN_BODY.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("start");
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			this.setAnimTick(20);
			this.setAnimationState("stop");
		}
		++this.deathTime;
		this.idleAnimationState.stop();
		this.attackAnimationState.stop();
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
