package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.annotations.VisibleForTesting;
import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class HarmoniousSlimeEntity extends Animal implements NeutralMob, JerotesEntity, FactionEntity, Bucketable {
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(HarmoniousSlimeEntity.class, EntityDataSerializers.INT);
	public float targetSquish;
	public float squish;
	public float oSquish;
	private boolean wasOnGround;

	public HarmoniousSlimeEntity(EntityType<? extends HarmoniousSlimeEntity> entityType, Level level) {
		super(entityType, level);
		this.fixupDimensions();
		this.moveControl = new SlimeMoveControl(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SlimeFloatGoal(this));
		this.goalSelector.addGoal(2, new SlimeAttackGoal(this));
		this.goalSelector.addGoal(3, new SlimeRandomDirectionGoal(this));
		this.goalSelector.addGoal(4, new TemptGoal(this, 0.9,  Ingredient.of(ItemTags.FLOWERS), false));
		this.goalSelector.addGoal(5, new SlimeKeepOnJumpingGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Slime>(this, Slime.class, true));
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.HARMONIOUS_SLIME_BUCKET.get());
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ID_SIZE, 1);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		return builder;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@VisibleForTesting
	public void setSize(int n, boolean bl) {
		int n2 = Mth.clamp(n, 1, 127);
		this.entityData.set(ID_SIZE, n2);
		this.reapplyPosition();
		this.refreshDimensions();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(n2 * n2);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2f + 0.1f * (float)n2);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(n2);
		if (bl) {
			this.setHealth(this.getMaxHealth());
		}
		this.xpReward = 0;
	}

	public int getSize() {
		return this.entityData.get(ID_SIZE);
	}

	public int eat;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Size", this.getSize() - 1);
		compoundTag.putBoolean("wasOnGround", this.wasOnGround);
		compoundTag.putInt("Eat", this.eat);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setSize(compoundTag.getInt("Size") + 1, false);
		super.readAdditionalSaveData(compoundTag);
		this.wasOnGround = compoundTag.getBoolean("wasOnGround");
		this.eat = compoundTag.getInt("Eat");
	}
	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	public void saveToBucketTagOther(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		compoundTag.putInt("Size", 1);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}

	public boolean isTiny() {
		return this.getSize() <= 1;
	}

	protected ParticleOptions getParticleType() {
		return ParticleTypes.CHERRY_LEAVES;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(2.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
	}

	@Override
	public void tick() {
		this.squish += (this.targetSquish - this.squish) * 0.5f;
		this.oSquish = this.squish;
		super.tick();
		if (this.onGround() && !this.wasOnGround) {
			int n = this.getSize();
			for (int i = 0; i < n * 8; ++i) {
				float f = this.random.nextFloat() * 6.2831855f;
				float f2 = this.random.nextFloat() * 0.5f + 0.5f;
				float f3 = Mth.sin(f) * (float)n * 0.5f * f2;
				float f4 = Mth.cos(f) * (float)n * 0.5f * f2;
				this.level().addParticle(this.getParticleType(), this.getX() + (double)f3, this.getY(), this.getZ() + (double)f4, 0.0, 0.0, 0.0);
			}
			this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
			this.targetSquish = -0.5f;
		} else if (!this.onGround() && this.wasOnGround) {
			this.targetSquish = 1.0f;
		}
		this.wasOnGround = this.onGround();
		this.decreaseSquish();
	}

	protected void decreaseSquish() {
		this.targetSquish *= 0.6f;
	}

	protected int getJumpDelay() {
		return this.random.nextInt(20) + 10;
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
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ID_SIZE.equals(entityDataAccessor)) {
			this.refreshDimensions();
			this.setYRot(this.yHeadRot);
			this.yBodyRot = this.yHeadRot;
			if (this.isInWater() && this.random.nextInt(20) == 1) {
				this.doWaterSplashEffect();
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	public EntityType<? extends HarmoniousSlimeEntity> getType() {
		return (EntityType<? extends HarmoniousSlimeEntity>) super.getType();
	}

	@Override
	public void remove(RemovalReason removalReason) {
		int n = this.getSize();
		if (!this.level().isClientSide && n > 1 && this.isDeadOrDying()) {
			Component component = this.getCustomName();
			boolean bl = this.isNoAi();
			float f = (float)n / 4.0f;
			int n2 = n / 2;
			int n3 = 2 + this.random.nextInt(3);
			for (int i = 0; i < n3; ++i) {
				float f2 = ((float)(i % 2) - 0.5f) * f;
				float f3 = ((float)(i / 2) - 0.5f) * f;
				HarmoniousSlimeEntity slime = this.getType().create(this.level());
				if (slime == null) continue;
				if (this.isPersistenceRequired()) {
					slime.setPersistenceRequired();
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					PlayerTeam teams = (PlayerTeam) this.getTeam();
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(slime.getStringUUID(), teams);
					}
					if (this.getTarget() != null) {
						slime.setTarget(this.getTarget());
					}
				}
				slime.setCustomName(component);
				slime.setNoAi(bl);
				slime.setInvulnerable(this.isInvulnerable());
				slime.setSize(n2, true);
				slime.moveTo(this.getX() + (double)f2, this.getY() + 0.5, this.getZ() + (double)f3, this.random.nextFloat() * 360.0f, 0.0f);
				this.level().addFreshEntity(slime);
			}
		}
		super.remove(removalReason);
	}

	@Override
	public void push(Entity entity) {
		super.push(entity);
		if (entity == this.getTarget() && this.isDealsDamage()) {
			this.dealDamage((LivingEntity)entity);
		}
	}

	protected void dealDamage(LivingEntity livingEntity) {
		if (this.isAlive()) {
			int n = this.getSize();
			if (this.distanceToSqr(livingEntity) < 0.6 * (double)n * (0.6 * (double)n) && this.hasLineOfSight(livingEntity)) {
				AttackFind.attackBegin(this, livingEntity);
				boolean bl = AttackFind.attackAfter(this, livingEntity, 1f, 0f, false, 0f);
				if (bl) {
					this.playSound(SoundEvents.SLIME_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
				}
			}
		}
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.625f * entityDimensions.height;
	}

	protected boolean isDealsDamage() {
		return !this.isTiny() && this.isEffectiveAi();
	}

	protected float getAttackDamage() {
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isTiny()) {
			return SoundEvents.SLIME_HURT_SMALL;
		}
		return SoundEvents.SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		if (this.isTiny()) {
			return SoundEvents.SLIME_DEATH_SMALL;
		}
		return SoundEvents.SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		if (this.isTiny()) {
			return SoundEvents.SLIME_SQUISH_SMALL;
		}
		return SoundEvents.SLIME_SQUISH;
	}


	@Override
	protected float getSoundVolume() {
		return 0.4f * (float)this.getSize();
	}

	@Override
	public int getMaxHeadXRot() {
		return 0;
	}

	protected boolean doPlayJumpSound() {
		return this.getSize() > 0;
	}

	@Override
	protected void jumpFromGround() {
		Vec3 vec3 = this.getDeltaMovement();
		this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
		this.hasImpulse = true;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		int n = randomSource.nextInt(3);
		if (n < 2 && randomSource.nextFloat() < 0.5f * difficultyInstance.getSpecialMultiplier()) {
			++n;
		}
		int n2 = 1 << n;
		this.setSize(n2, true);
		if (mobSpawnType == MobSpawnType.BUCKET) {
			this.setSize(1, true);
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	float getSoundPitch() {
		float f = this.isTiny() ? 1.4f : 0.8f;
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * f;
	}

	protected SoundEvent getJumpSound() {
		return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(0.255f * (float)this.getSize());
	}

	static class SlimeMoveControl
			extends MoveControl {
		private float yRot;
		private int jumpDelay;
		private final HarmoniousSlimeEntity slime;
		private boolean isAggressive;

		public SlimeMoveControl(HarmoniousSlimeEntity slime) {
			super(slime);
			this.slime = slime;
			this.yRot = 180.0f * slime.getYRot() / 3.1415927f;
		}

		public void setDirection(float f, boolean bl) {
			this.yRot = f;
			this.isAggressive = bl;
		}

		public void setWantedMovement(double d) {
			this.speedModifier = d;
			this.operation = Operation.MOVE_TO;
		}

		@Override
		public void tick() {
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0f));
			this.mob.yHeadRot = this.mob.getYRot();
			this.mob.yBodyRot = this.mob.getYRot();
			if (this.operation != Operation.MOVE_TO) {
				this.mob.setZza(0.0f);
				return;
			}
			this.operation = Operation.WAIT;
			if (this.mob.onGround()) {
				this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				if (this.jumpDelay-- <= 0) {
					this.jumpDelay = this.slime.getJumpDelay();
					if (this.isAggressive) {
						this.jumpDelay /= 3;
					}
					this.slime.getJumpControl().jump();
					if (this.slime.doPlayJumpSound()) {
						this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
					}
				} else {
					this.slime.xxa = 0.0f;
					this.slime.zza = 0.0f;
					this.mob.setSpeed(0.0f);
				}
			} else {
				this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
			}
		}
	}

	static class SlimeFloatGoal
			extends Goal {
		private final HarmoniousSlimeEntity slime;

		public SlimeFloatGoal(HarmoniousSlimeEntity slime) {
			this.slime = slime;
			this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
			slime.getNavigation().setCanFloat(true);
		}

		@Override
		public boolean canUse() {
			return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SlimeMoveControl;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			MoveControl moveControl;
			if (this.slime.getRandom().nextFloat() < 0.8f) {
				this.slime.getJumpControl().jump();
			}
			if ((moveControl = this.slime.getMoveControl()) instanceof SlimeMoveControl) {
				SlimeMoveControl slimeMoveControl = (SlimeMoveControl)moveControl;
				slimeMoveControl.setWantedMovement(1.2);
			}
		}
	}

	static class SlimeAttackGoal extends Goal {
		private final HarmoniousSlimeEntity slime;
		private int growTiredTimer;

		public SlimeAttackGoal(HarmoniousSlimeEntity slime) {
			this.slime = slime;
			this.setFlags(EnumSet.of(Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			LivingEntity livingEntity = this.slime.getTarget();
			if (livingEntity == null) {
				return false;
			}
			if (!this.slime.canAttack(livingEntity) || !this.slime.canAttackType(livingEntity.getType())) {
				return false;
			}
			return this.slime.getMoveControl() instanceof SlimeMoveControl;
		}

		@Override
		public void start() {
			this.growTiredTimer = SlimeAttackGoal.reducedTickDelay(300);
			super.start();
		}

		@Override
		public boolean canContinueToUse() {
			LivingEntity livingEntity = this.slime.getTarget();
			if (livingEntity == null) {
				return false;
			}
			if (!this.slime.canAttack(livingEntity) || !this.slime.canAttackType(livingEntity.getType())) {
				return false;
			}
			return --this.growTiredTimer > 0;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			MoveControl moveControl;
			LivingEntity livingEntity = this.slime.getTarget();
			if (livingEntity != null) {
				this.slime.lookAt(livingEntity, 10.0f, 10.0f);
			}
			if ((moveControl = this.slime.getMoveControl()) instanceof SlimeMoveControl) {
				SlimeMoveControl slimeMoveControl = (SlimeMoveControl)moveControl;
				slimeMoveControl.setDirection(this.slime.getYRot(), this.slime.isDealsDamage());
			}
		}
	}

	static class SlimeRandomDirectionGoal
			extends Goal {
		private final HarmoniousSlimeEntity slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public SlimeRandomDirectionGoal(HarmoniousSlimeEntity slime) {
			this.slime = slime;
			this.setFlags(EnumSet.of(Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof SlimeMoveControl;
		}

		@Override
		public void tick() {
			MoveControl moveControl;
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
				this.chosenDegrees = this.slime.getRandom().nextInt(360);
			}
			if ((moveControl = this.slime.getMoveControl()) instanceof SlimeMoveControl) {
				SlimeMoveControl slimeMoveControl = (SlimeMoveControl)moveControl;
				slimeMoveControl.setDirection(this.chosenDegrees, false);
			}
		}
	}

	static class SlimeKeepOnJumpingGoal
			extends Goal {
		private final HarmoniousSlimeEntity slime;

		public SlimeKeepOnJumpingGoal(HarmoniousSlimeEntity slime) {
			this.slime = slime;
			this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return !this.slime.isPassenger();
		}

		@Override
		public void tick() {
			MoveControl moveControl = this.slime.getMoveControl();
			if (moveControl instanceof SlimeMoveControl) {
				SlimeMoveControl slimeMoveControl = (SlimeMoveControl)moveControl;
				slimeMoveControl.setWantedMovement(1.0);
			}
		}
	}

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.HARMONIOUS_SLIME.get().create(serverLevel);
	}

	public static Optional<InteractionResult> bucketMobPickup(Player p_148829_, InteractionHand p_148830_, HarmoniousSlimeEntity harmoniousSlimeEntity) {
		ItemStack itemstack = p_148829_.getItemInHand(p_148830_);
		if (itemstack.getItem() == Items.BUCKET && harmoniousSlimeEntity.isAlive()) {
			if (harmoniousSlimeEntity.getSize() <= 2) {
				harmoniousSlimeEntity.playSound(harmoniousSlimeEntity.getPickupSound(), 1.0F, 1.0F);
				ItemStack itemstack1 = harmoniousSlimeEntity.getBucketItemStack();
				harmoniousSlimeEntity.saveToBucketTag(itemstack1);
				ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, p_148829_, itemstack1, false);
				p_148829_.setItemInHand(p_148830_, itemstack2);
				Level level = harmoniousSlimeEntity.level();
				if (!level.isClientSide) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) p_148829_, itemstack1);
				}
				harmoniousSlimeEntity.discard();
				return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
			}
			else {
				harmoniousSlimeEntity.playSound(harmoniousSlimeEntity.getPickupSound(), 1.0F, 1.0F);
				ItemStack itemstack1 = harmoniousSlimeEntity.getBucketItemStack();
				harmoniousSlimeEntity.saveToBucketTagOther(itemstack1);
				ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, p_148829_, itemstack1, false);
				p_148829_.setItemInHand(p_148830_, itemstack2);
				Level level = harmoniousSlimeEntity.level();
				if (!level.isClientSide) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) p_148829_, itemstack1);
				}
				harmoniousSlimeEntity.setSize(harmoniousSlimeEntity.getSize() - 1, true);
				return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
			}
		} else {
			return Optional.empty();
		}
	}

	public InteractionResult superMobInteract(Player p_218703_, InteractionHand p_218704_) {
		return (InteractionResult) bucketMobPickup(p_218703_, p_218704_, this).orElse(super.mobInteract(p_218703_, p_218704_));
	}
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(ItemTags.FLOWERS)) {
			player.playSound(SoundEvents.SLIME_BLOCK_PLACE, 1.0f, 1.0f);
			ItemStack itemStack2 = new ItemStack(JerotesVillageItems.PINK_SLIME_BALL.get(), 1);
			if (!player.getInventory().add(itemStack2)) {
				player.drop(itemStack2, false);
			}
			this.heal(3.0f);
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.HEART, this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		else if (isFood(itemStack)) {
			this.eat += 1;
			if ((getSize() * getSize() * getSize()) <= eat) {
				this.eat = 0;
				this.setSize(this.getSize() + 1, true);
			}
			player.playSound(SoundEvents.SLIME_BLOCK_PLACE, 1.0f, 1.0f);
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return superMobInteract(player, interactionHand);
	}

	@Override
	public boolean fromBucket() {
		return true;
	}
	@Override
	public void setFromBucket(boolean bl) {
	}
	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL;
	}
	
	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(ItemTags.create(new ResourceLocation("forge:slimeballs")));
	}
}
