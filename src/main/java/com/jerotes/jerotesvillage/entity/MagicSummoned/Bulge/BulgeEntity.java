package com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge;

import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.goal.BulgeFloatGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BulgeEntity extends MagicSummonedEntity implements RangedAttackMob, SpellUseEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BulgeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BulgeEntity.class, EntityDataSerializers.INT);

	public BulgeEntity(EntityType<? extends BulgeEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
		} else {
			super.travel(vec3);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		if (!(this instanceof SeawaveEntity)) {
			this.goalSelector.addGoal(0, new BulgeFloatGoal(this, 0.35f));
			this.goalSelector.addGoal(1, new BulgeRangedAttackGoal());
			this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0));
		//	this.goalSelector.addGoal(3, new FollowSeawaveGoal(this, 1.2f));
		}
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
	}

	class BulgeRangedAttackGoal extends RangedAttackGoal {
		public BulgeRangedAttackGoal() {
			super(BulgeEntity.this, 1.0, 60, 20.0f);
		}

		@Override
		public void start() {
			super.start();
			BulgeEntity.this.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			BulgeEntity.this.setAggressive(false);
		}
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("shoot");
		}
		//法术列表-波涛射弹
		OtherSpellList.BulgeBullet(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.30);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24.0);
		return builder;
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BULGE_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BULGE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BULGE_DEATH;
	}
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}

	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
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
		if (Objects.equals(animation, "shoot")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.shootAnimationState);
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
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
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
		if (!(this instanceof SeawaveEntity)) {
			if (ANIM_STATE.equals(entityDataAccessor)) {
				if (this.level().isClientSide()) {
					switch (this.entityData.get(ANIM_STATE)) {
						case 0:
							this.stopAllAnimation();
							break;
						case 1:
							this.shootAnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.shootAnimationState);
							break;
					}
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public int getAmbientSoundInterval() {
		return 480;
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.125f;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		if (!(this instanceof SeawaveEntity)) {
			if (this.isAlive()) {
				this.idleAnimationState.startIfStopped(this.tickCount);
			}
		}
		//
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
}

