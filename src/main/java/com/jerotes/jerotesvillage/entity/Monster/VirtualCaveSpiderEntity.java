package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.CanNotSkeletonSpiderEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirtualCaveSpiderEntity extends Spider implements JerotesEntity, CanNotSkeletonSpiderEntity {
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState climbAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(VirtualCaveSpiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(VirtualCaveSpiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_COLOR_A_ID = SynchedEntityData.defineId(VirtualCaveSpiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_COLOR_B_ID = SynchedEntityData.defineId(VirtualCaveSpiderEntity.class, EntityDataSerializers.BOOLEAN);

	public VirtualCaveSpiderEntity(EntityType<? extends VirtualCaveSpiderEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		this.xpReward = 25;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(4, new SpiderAttackGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new SpiderTargetGoal<Player>(this, Player.class));
		this.targetSelector.addGoal(3, new SpiderTargetGoal<IronGolem>(this, IronGolem.class));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45);
		builder = builder.add(Attributes.MAX_HEALTH, 48);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
		return builder;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return false;
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(7);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (entity instanceof LivingEntity livingEntity) {
				int n = 2;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					n = 4;
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					n = 8;
					if (!entity.level().isClientSide) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, n * 20, 1), this);
					}
				}
				if (!entity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), n * 20, 1), this);
				}
			}
		}
		return bl;
	}

	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		if (!blockState.is(Blocks.COBWEB)) {
			super.makeStuckInBlock(blockState, vec3);
		}
		this.resetFallDistance();
	}


	public boolean isColorA() {
		return this.getEntityData().get(DATA_COLOR_A_ID);
	}
	public boolean isColorB() {
		return this.getEntityData().get(DATA_COLOR_B_ID);
	}
	public void setColorA(boolean bl) {
		this.getEntityData().set(DATA_COLOR_A_ID, bl);
	}
	public void setColorB(boolean bl) {
		this.getEntityData().set(DATA_COLOR_B_ID, bl);
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
		compoundTag.putBoolean("IsColorA", this.isColorA());
		compoundTag.putBoolean("IsColorB", this.isColorB());
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("IsColorA")) {
			this.setColorA(compoundTag.getBoolean("IsColorA"));
		}
		if (compoundTag.contains("IsColorB")) {
			this.setColorB(compoundTag.getBoolean("IsColorB"));
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_COLOR_A_ID, false);
		this.getEntityData().define(DATA_COLOR_B_ID, false);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_COLOR_A_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (DATA_COLOR_B_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		Object object;
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (randomSource.nextInt(40) == 1 && (object =  JerotesVillageEntityType.PREDATION_TOOTHED_FLOWER.get().create(this.level())) != null) {
			((Entity)object).moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
			((Mob)object).finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null, null);
			if (!this.level().isClientSide) {
				((Entity) object).startRiding(this);
			}
		}
		RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			if (randomSourceSkin.nextFloat() < 0.25f) {
				this.setColorA(true);
			}
			if (randomSourceSkin.nextFloat() < 0.3f) {
				this.setColorB(true);
			}
		}
		return spawnGroupData;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isClimbing()) {
			this.climbAnimationState.start(this.tickCount);
		}
		else {
			this.climbAnimationState.stop();
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
	}


	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.8f;
	}
	@Override
	public int getMaxHeadYRot() {
		return 15;
	}
	@Override
	public int getMaxHeadXRot() {
		return 15;
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	static class SpiderAttackGoal extends JerotesMeleeAttackGoal {
		public SpiderAttackGoal(Spider spider) {
			super(spider, 1.0, true);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && !this.mob.isVehicle();
		}

		@Override
		public boolean canContinueToUse() {
			float f = this.mob.getLightLevelDependentMagicValue();
			if (f >= 0.5f && this.mob.getRandom().nextInt(100) == 1) {
				this.mob.setTarget(null);
				return false;
			}
			return super.canContinueToUse();
		}
	}

	static class SpiderTargetGoal<T extends LivingEntity>
			extends NearestAttackableTargetGoal<T> {
		public SpiderTargetGoal(Spider spider, Class<T> class_) {
			super(spider, class_, true);
		}

		@Override
		public boolean canUse() {
			float f = this.mob.getLightLevelDependentMagicValue();
			if (f >= 0.5f) {
				return false;
			}
			return super.canUse();
		}
	}
}

