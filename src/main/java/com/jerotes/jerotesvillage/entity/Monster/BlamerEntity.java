package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlamerEntity extends Monster implements JerotesEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BlamerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BlamerEntity.class, EntityDataSerializers.INT);

	public BlamerEntity(EntityType<? extends BlamerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		this.xpReward = 20;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new RestrictSunGoal(this));
		this.goalSelector.addGoal(0, new JerotesNoFireFleeSunGoal(this, 1.0));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 32);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		return builder;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BLAMER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BLAMER_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 1.6f;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

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
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception) {
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
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
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isPsychicDamage(damageSource))
			amount *= 2;
		if (AttackFind.MagicResistance(damageSource))
			return super.hurt(damageSource, amount);
		if (AttackFind.isFireDamage(damageSource))
			return super.hurt(damageSource, amount);
		if (damageSource.is(DamageTypeTags.IS_EXPLOSION))
			return super.hurt(damageSource, amount);
		return super.hurt(damageSource, amount/4);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.725f;
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
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
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (this.level().isClientSide()) {
			this.level().addParticle(JerotesVillageParticleTypes.BLAMER_SOUL.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(7);
			this.setAnimationState("attack");
		}
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.BLAMER_AMBIENT, 1.0f, 1.0f);
		}
		return super.doHurtTarget(entity);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		Creeper creeper;
		super.dropCustomDeathLoot(damageSource, n, bl);
		Entity entity = damageSource.getEntity();
		if (entity instanceof Creeper && (creeper = (Creeper)entity).canDropMobsSkull()) {
			creeper.increaseDroppedSkulls();
			this.spawnAtLocation(JerotesVillageItems.BLAMER_HEAD.get());
		}
	}
}

