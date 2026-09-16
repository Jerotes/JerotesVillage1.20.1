package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.control.BabyCanNotFlyMoveControl;
import com.jerotes.jerotesvillage.goal.FollowDustMothGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;


public class DustMothEntity extends Animal implements JerotesEntity, CatchEntity {
	private static final EntityDataAccessor<Boolean> DATA_COLOR_A_ID = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_COLOR_B_ID = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FROM_CATCH = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> QUEEN = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PUPA = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DUST_COOLDOWN = SynchedEntityData.defineId(DustMothEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState dustAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(ItemTags.LEAVES);
	private static final Ingredient FOOD_ITEMS_OTHER = Ingredient.of(new ItemStack(Items.MOSS_BLOCK));

	public DustMothEntity(EntityType<? extends DustMothEntity> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new BabyCanNotFlyMoveControl(this, 10, true);
		this.fixupDimensions();
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.3));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.1));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS_OTHER, false));
		this.goalSelector.addGoal(3, new FollowDustMothGoal(this, 1.1));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.FLYING_SPEED, 1.8);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.1);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Override
	protected float getSoundVolume() {
		return 0.1f;
	}
	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack) || FOOD_ITEMS_OTHER.test(itemStack);
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.DUST_MOTH_HURT;
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.DUST_MOTH_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.DUST_MOTH_DEATH;
	}
	@Override
	public ItemStack getCatchItemStack() {
		return new ItemStack(JerotesVillageItems.CAPTURED_DUST_MOTH.get());
	}
	@Override
	protected Component getTypeName() {
		if (this.isQueen())
			return Component.translatable("entity.jerotesvillage.dust_moth.queen");
		return Component.translatable(this.getType().getDescriptionId());
	}
	@Override
	public SoundEvent getPickupSound() {
		return JerotesVillageSoundEvents.DUST_MOTH_AMBIENT;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromCatch();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromCatch() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}
	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
	}

	@Override
	public @NotNull PathNavigation getNavigation() {
		Entity entity = this.getControlledVehicle();
		if (entity instanceof Mob) {
			return super.getNavigation();
		} else {
			if (this.isBaby()) {
				return new GroundPathNavigation(this, this.level());
			}
			return super.getNavigation();
		}
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		int size = this.isBaby() ? 2 : 1;
		return super.getDimensions(pose).scale(1f * size);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isQueen()) {
			float height = 0.225f;
			if (this.isBaby()) {
				return height / 2;
			}
			return height;
		}
		float height = 0.15f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FlyingPathNavigation(this, level);
	}
	@Override
	protected void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
		this.resetFallDistance();
	}

	@Override
	public boolean fromCatch() {
		return this.entityData.get(FROM_CATCH);
	}
	@Override
	public void setFromCatch(boolean bl) {
		this.entityData.set(FROM_CATCH, bl);
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
	public boolean isQueen() {
		return this.getEntityData().get(QUEEN);
	}
	public void setQueen(boolean bl) {
		this.getEntityData().set(QUEEN, bl);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	public boolean isPupa() {
		return this.getEntityData().get(PUPA);
	}
	public void setPupa(boolean bl) {
		this.getEntityData().set(PUPA, bl);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}
	public int getDustCooldown() {
		return this.getEntityData().get(DUST_COOLDOWN);
	}
	public void setDustCooldown(int n) {
		this.getEntityData().set(DUST_COOLDOWN, n);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("FromCatch", this.fromCatch());
		compoundTag.putBoolean("IsColorA", this.isColorA());
		compoundTag.putBoolean("IsColorB", this.isColorB());
		compoundTag.putBoolean("IsQueen", this.isQueen());
		compoundTag.putBoolean("IsPupa", this.isPupa());
		compoundTag.putInt("DustCooldown", this.getDustCooldown());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFromCatch(compoundTag.getBoolean("FromCatch"));
		if (compoundTag.contains("IsColorA")) {
			this.setColorA(compoundTag.getBoolean("IsColorA"));
		}
		if (compoundTag.contains("IsColorB")) {
			this.setColorB(compoundTag.getBoolean("IsColorB"));
		}
		if (compoundTag.contains("IsQueen")) {
			this.setQueen(compoundTag.getBoolean("IsQueen"));
		}
		if (compoundTag.contains("IsPupa")) {
			this.setPupa(compoundTag.getBoolean("IsPupa"));
		}
		if (compoundTag.contains("DustCooldown")) {
			this.setDustCooldown(compoundTag.getInt("DustCooldown"));
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FROM_CATCH, false);
		this.getEntityData().define(QUEEN, false);
		this.getEntityData().define(PUPA, false);
		this.getEntityData().define(DATA_COLOR_A_ID, false);
		this.getEntityData().define(DATA_COLOR_B_ID, false);
		this.getEntityData().define(DUST_COOLDOWN, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (QUEEN.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (PUPA.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public void saveToCatchTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromCatchTag(CompoundTag compoundTag) {
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}
	
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		return CatchEntity.catchMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isPupa()) {
			this.setJumping(false);
			if (!this.level().isClientSide()) {
				this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
				this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.isBaby()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//尘粉
		if (!this.level().isClientSide()) {
			this.setDustCooldown(Math.max(0, this.getDustCooldown() - 1));
		}
		//水中失去尘粉
		if (this.isInWaterOrRain() || this.isInWaterOrBubble()) {
			if (this.getDustCooldown() < 1600) {
				if (!this.level().isClientSide()) {
					this.setDustCooldown(this.getDustCooldown() + 100);
				}
			}
		}
		//蛹化
		if (this.isBaby() && this.getAge() > -2400) {
			if (!this.isPupa()) {
				if (!this.level().isClientSide()) {
					this.setPupa(true);
					if (this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
						this.spawnAtLocation(Items.STRING, 1);
						if (this.isQueen()) {
							this.spawnAtLocation(JerotesItems.HIGH_STRENGTH_STRING.get(), 1);
						}
					}
				}
			}
		}
		else {
			if (this.isPupa()) {
				if (!this.level().isClientSide()) {
					this.setPupa(false);
				}
			}
		}
	}

	@Nullable
	@Override
	public DustMothEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		DustMothEntity dustMoth = JerotesVillageEntityType.DUST_MOTH.get().create(serverLevel);
		if (dustMoth != null && ageableMob instanceof DustMothEntity dustMoth2) {
			if (this.random.nextBoolean()) {
				dustMoth.setColorA(this.isColorA());
				dustMoth.setColorB(this.isColorB());
			}
			else {
				dustMoth.setColorA(dustMoth2.isColorA());
				dustMoth.setColorB(dustMoth2.isColorB());
			}
			if (this.isQueen()) {
				dustMoth.setQueen(isQueen());
			}
			else if (dustMoth2.isQueen()) {
				dustMoth.setQueen(dustMoth2.isQueen());
			}
			else {
				dustMoth.setQueen(false);
			}
		}
		else if (dustMoth != null) {
			dustMoth.setColorA(this.isColorA());
			dustMoth.setColorB(this.isColorB());
			dustMoth.setQueen(isQueen());
		}
		return dustMoth;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.PRURITUS.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount > 0 && damageSource.getEntity() != null && damageSource.getEntity() != this) {
			this.cloud();
			if (this.isQueen()) {
				List<DustMothEntity> list = this.level().getEntitiesOfClass(DustMothEntity.class, this.getBoundingBox().inflate(3, 3, 3));
				for (DustMothEntity find : list) {
					if (find == null) continue;
					if (find.getTeam() != null && find.getTeam() != this.getTeam()) continue;
					find.cloud();
				}
			}
		}
		return super.hurt(damageSource, amount);
	}

	public boolean cloud() {
		if (this.getDustCooldown() > 0 || this.isBaby() || !this.isAlive()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setDustCooldown(1600);
		}
		this.dustAnimationState.start(this.tickCount);
		int scale = this.isQueen() ? 2 : 1;
		AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
		areaEffectCloud.setOwner(this);
		areaEffectCloud.setParticle(ParticleTypes.CLOUD);
		areaEffectCloud.setRadius(scale / 2f);
		areaEffectCloud.setDuration((int) (60 * scale));
		areaEffectCloud.setRadiusPerTick((2.0f - areaEffectCloud.getRadius()) / (float)areaEffectCloud.getDuration());
		areaEffectCloud.addEffect(new MobEffectInstance(JerotesMobEffects.PRURITUS.get(), 240 * scale, scale - 1));
		this.playSound(JerotesVillageSoundEvents.DUST_MOTH_CLOUD, 1.0f, 1.0f);
		this.level().addFreshEntity(areaEffectCloud);
		return true;
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			if (randomSourceSkin.nextFloat() < 0.25f) {
				this.setColorA(true);
			}
			if (randomSourceSkin.nextFloat() < 0.3f) {
				this.setColorB(true);
			}
			if (randomSourceSkin.nextFloat() < 0.05f) {
				this.setQueen(true);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

