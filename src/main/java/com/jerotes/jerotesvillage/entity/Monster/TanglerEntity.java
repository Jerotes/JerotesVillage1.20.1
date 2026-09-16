package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.ShiftKeyDownEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesNoFireFleeSunGoal;
import com.jerotes.jerotes.goal.JerotesShiftKeyDownGoal;
import com.jerotes.jerotes.goal.JerotesTryFindWaterGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TanglerEntity extends Monster implements JerotesEntity, ShiftKeyDownEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(TanglerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(TanglerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> WANT_TANGLE = SynchedEntityData.defineId(TanglerEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();

	public TanglerEntity(EntityType<? extends TanglerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		this.xpReward = 15;
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new JerotesTryFindWaterGoal(this));
		this.goalSelector.addGoal(0, new RestrictSunGoal(this));
		this.goalSelector.addGoal(0, new JerotesNoFireFleeSunGoal(this, 1.0));
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.2, 120));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 32);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		return builder;
	}

	@Override
	public boolean shouldShiftKeyDown() {
		return this.baseShouldShiftKeyDown() || (this.getTarget() != null && this.isInWater() && this.canTangleEntity(this.getTarget()) && this.canTangle == 1);
	}
	@Override
	protected SoundEvent getAmbientSound() {
		if (this.isWantTangle()) {
			return null;
		}
		return JerotesVillageSoundEvents.TANGLER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return JerotesVillageSoundEvents.TANGLER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.TANGLER_DEATH;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 120;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public void travel(Vec3 vec3) {
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		} else {
			super.travel(vec3);
		}
	}
	@Override
	protected float getSoundVolume() {
		return 1.6f;
	}
	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
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
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = true;

	public int canTangle;
	private Entity control;
	public boolean isWantTangle() {
		return this.getEntityData().get(WANT_TANGLE);
	}
	public void setWantTangle(boolean bl) {
		this.getEntityData().set(WANT_TANGLE, bl);
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
		compoundTag.putInt("CanTangle", this.canTangle);
		compoundTag.putBoolean("IsWantTangle", this.isWantTangle());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.canTangle = compoundTag.getInt("CanTangle");
		this.setWantTangle(compoundTag.getBoolean("IsWantTangle"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(WANT_TANGLE, false);
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

	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.2f, true);
			this.navigation = new AmphibiousPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			this.moveControl = new MoveControl(this);
			this.navigation = new GroundPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
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

		//发现目标
		if (this.getTarget() != null && this.isInWater() && this.canTangleEntity(this.getTarget())) {
			if (this.canTangle == 1) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.0075, 0.0));
			}
			if (this.canTangle == 0 && this.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
				this.setWantTangle(true);
				this.canTangle = 1;
			}
		}
		if (this.getTarget() == null || !this.isInWater() || !this.canTangleEntity(this.getTarget())) {
			this.setWantTangle(false);
			this.control = null;
			this.canTangle = 2;
			if (this.getTarget() == null) {
				this.canTangle = 0;
			}
		}

		//纠缠判定
		if (this.control != null && this.isAlive()) {
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 5; ++i) {
					serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getRandomX(0.7), this.getRandomY(), this.getRandomZ(0.7), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if (this.control.isInWater() && this.getRandom().nextInt(800) != 1) {
				if (!this.level().isClientSide && this.control instanceof LivingEntity livingEntity) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0), this);
				}
				this.setPosRaw(this.control.getX(), this.control.getY(), this.control.getZ());
			}
			else {
				this.canTangle = 2;
				this.control = null;
				this.setWantTangle(false);
			}
		}

		//隐形
		if (this.getTarget() != null && this.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 5; ++i) {
					serverLevel.sendParticles(ParticleTypes.UNDERWATER, this.getRandomX(0.7), this.getRandomY(), this.getRandomZ(0.7), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if ((this.distanceTo(this.getTarget()) > 4 || this.isWantTangle()) && this.canTangle != 2 && this.control == null) {
				if (!this.level().isClientSide) {
					this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5, 0, false, false), this);
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.canTangle == 1 && this.isWantTangle()) {
			if (this.control == null) {
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TANGLER_TANGLE, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
				this.control = entity;
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.TANGLER_TANGLE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
			}
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		return bl;
	}

	public boolean canTangleEntity(Entity entity) {
		return Main.mobSizeSmall(entity) || Main.mobSizeMedium(entity);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isWaterDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		this.canTangle = 2;
		this.control = null;
		this.setWantTangle(false);
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		Creeper creeper;
		super.dropCustomDeathLoot(damageSource, n, bl);
		Entity entity = damageSource.getEntity();
		if (entity instanceof Creeper && (creeper = (Creeper)entity).canDropMobsSkull()) {
			creeper.increaseDroppedSkulls();
			this.spawnAtLocation(JerotesVillageItems.TANGLER_HEAD.get());
		}
	}
}

