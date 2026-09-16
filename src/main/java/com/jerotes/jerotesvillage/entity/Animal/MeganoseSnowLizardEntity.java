package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesAnimalMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesAnimalPanicGoal;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
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
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MeganoseSnowLizardEntity extends Animal implements JerotesEntity, NeutralMob {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(MeganoseSnowLizardEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(MeganoseSnowLizardEntity.class, EntityDataSerializers.INT);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/grass_foods"))
	);

	public MeganoseSnowLizardEntity(EntityType<? extends MeganoseSnowLizardEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.1, true));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<CorrosiverEntity>(this, CorrosiverEntity.class, 32.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.2));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<MeganoseSnowLizardEntity>(this, false));
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.25);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.MEGANOSE_SNOW_LIZARD_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.MEGANOSE_SNOW_LIZARD_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MEGANOSE_SNOW_LIZARD_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MEGANOSE_SNOW_LIZARD_WALK, 0.15f, 1.0f);
	}

	@Override
	protected float getSoundVolume() {
		return 0.6f;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
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
		return aabb1.inflate(0.15d, 0.15d, 0.15d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	@Nullable
	@Override
	public MeganoseSnowLizardEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.MEGANOSE_SNOW_LIZARD.get().create(serverLevel);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.2f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 15) {
				this.setAnimationState("attack1");
			}
			else {
				this.setAnimationState("attack2");
			}
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.MEGANOSE_SNOW_LIZARD_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (!this.level().isClientSide) {
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
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
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource))
			amount = amount / freezeAmount;
		return super.hurt(damageSource, amount);
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




