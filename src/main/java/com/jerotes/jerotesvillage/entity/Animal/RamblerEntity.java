package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesAnimalMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesAnimalPanicGoal;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotesvillage.entity.Boss.ProsperousEnvoyEntity;
import com.jerotes.jerotesvillage.goal.FollowProsperousEnvoyGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class RamblerEntity extends Animal implements JerotesEntity, NeutralMob {
	public AnimationState attackAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RamblerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(RamblerEntity.class, EntityDataSerializers.INT);

	public RamblerEntity(EntityType<? extends RamblerEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.3, true));
		if (!(this instanceof WildernessRamblerEntity) && !(this instanceof ProsperousEnvoyEntity)) {
			this.goalSelector.addGoal(1, new PanicGoal(this, 1.3));
			if (!(this instanceof WonderlandRamblerEntity)) {
				this.goalSelector.addGoal(2, new AvoidEntityGoal<BeachTerrorBirdEntity>(this, BeachTerrorBirdEntity.class, 32.0f, 1.3, 1.4, EntitySelector.NO_SPECTATORS::test));
			}
		}
		else {
			this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.3));
		}
		if (!(this instanceof ProsperousEnvoyEntity)) {
			this.goalSelector.addGoal(2, new FollowProsperousEnvoyGoal(this, 1.1f));
			this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
			this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
			this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		}
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		if ((this instanceof WildernessRamblerEntity) || (this instanceof ProsperousEnvoyEntity)) {
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this, RamblerEntity.class));
			if (this instanceof ProsperousEnvoyEntity) {
				this.targetSelector.addGoal(1, new ProsperousEnvoyHelpGoal(LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
			}
		}
		this.targetSelector.addGoal(1, new HelpProsperousEnvoyGoal(LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<RamblerEntity>(this, false));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	class HelpProsperousEnvoyGoal extends NearestAttackableTargetGoal<LivingEntity> {
		@Nullable
		private LivingEntity attack;

		public HelpProsperousEnvoyGoal(Class<LivingEntity> class_, boolean bl, @Nullable boolean bl2, Predicate<LivingEntity> predicate) {
			super(RamblerEntity.this, class_, 10, bl, bl2, predicate);
		}

		@Override
		public boolean canUse() {
			if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			List<ProsperousEnvoyEntity> list = RamblerEntity.this.level().getEntitiesOfClass(ProsperousEnvoyEntity.class, RamblerEntity.this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			double d = Double.MAX_VALUE;
			for (ProsperousEnvoyEntity other : list) {
				if (other == null) continue;
				if (this.mob == other) continue;
				if ((RamblerEntity.this.distanceToSqr(other)) > d) continue;
				if (RamblerEntity.this.getTarget() != null) continue;
				if ((other.getTeam() != null || RamblerEntity.this.getTeam() != null) && !RamblerEntity.this.isAlliedTo(other)) continue;
				if (other.getTarget() != null && RamblerEntity.this.canAttack(other.getTarget()) && RamblerEntity.this.canAttackType(other.getTarget().getType())){
					if (RamblerEntity.this.getTarget() == null || !(RamblerEntity.this.getTarget() instanceof LivingEntity)) {
						if (!(other.getTarget().isAlliedTo(RamblerEntity.this)) && !(other.getTarget().isAlliedTo(other))){
							attack = other.getTarget();
							return true;
						}
					}
				}
			}
			return false;
		}

		@Override
		public void start() {
			this.setTarget(attack);
			super.start();
		}
	}

	class ProsperousEnvoyHelpGoal extends NearestAttackableTargetGoal<LivingEntity> {
		@Nullable
		private LivingEntity attack;

		public ProsperousEnvoyHelpGoal(Class<LivingEntity> class_, boolean bl, @Nullable boolean bl2, Predicate<LivingEntity> predicate) {
			super(RamblerEntity.this, class_, 10, bl, bl2, predicate);
		}

		@Override
		public boolean canUse() {
			if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
				return false;
			}
			if (this.mob.getTarget() != null) {
				return false;
			}
			List<RamblerEntity> list = RamblerEntity.this.level().getEntitiesOfClass(RamblerEntity.class, RamblerEntity.this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			double d = Double.MAX_VALUE;
			for (RamblerEntity other : list) {
				if (other == null) continue;
				if (this.mob == other) continue;
				if ((RamblerEntity.this.distanceToSqr(other)) > d) continue;
				if (RamblerEntity.this.getTarget() != null) continue;
				if ((other.getTeam() != null || RamblerEntity.this.getTeam() != null) && !RamblerEntity.this.isAlliedTo(other)) continue;
				if (other.getLastHurtByMob() != null && RamblerEntity.this.canAttack(other.getLastHurtByMob()) && RamblerEntity.this.canAttackType(other.getLastHurtByMob().getType())){
					if (RamblerEntity.this.getTarget() == null || !(RamblerEntity.this.getTarget() instanceof LivingEntity)) {
						if (!(other.getLastHurtByMob().isAlliedTo(RamblerEntity.this)) && !(other.getLastHurtByMob().isAlliedTo(other))){
							attack = other.getLastHurtByMob();
							return true;
						}
					}
				}
			}
			return false;
		}

		@Override
		public void start() {
			this.setTarget(attack);
			super.start();
		}
	}


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.RAMBLER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.RAMBLER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.RAMBLER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.RAMBLER_WALK, 0.15f, 1.0f);
	}

	@Override
	protected float getSoundVolume() {
		return 0.6f;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	@Nullable
	@Override
	public RamblerEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.RAMBLER.get().create(serverLevel);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.1f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (entity instanceof ProsperousEnvoyEntity) {
			return super.doHurtTarget(entity);
		}
		else {
			attackAnimation();
			boolean bl = super.doHurtTarget(entity);
			if (bl) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.RAMBLER_ATTACK, 1.0f, 1.0f);
				}
			}
			return bl;
		}
	}
	public void attackAnimation() {
		if (!this.level().isClientSide()) {
			this.setAnimTick(5);
			this.setAnimationState("attack");
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
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
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (!(this instanceof ProsperousEnvoyEntity)) {
			if (ANIM_STATE.equals(entityDataAccessor)) {
				if (this.level().isClientSide()) {
					switch (this.entityData.get(ANIM_STATE)) {
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
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Nullable
	private UUID persistentAngerTarget;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}


	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}
}




