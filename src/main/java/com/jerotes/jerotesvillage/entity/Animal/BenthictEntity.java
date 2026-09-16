package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.control.BenthictMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;

public class BenthictEntity extends Animal implements Bucketable, JerotesEntity, FactionEntity {
	public AnimationState idleAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(JerotesVillageItems.OCEAN_GEMSTONE_FRAGMENTS.get());
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BenthictEntity.class, EntityDataSerializers.BOOLEAN);
	public BenthictEntity(EntityType<? extends BenthictEntity> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new BenthictMoveControl(this);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.1));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<GemstoneMalignasaurEntity>(this, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<Pufferfish>(this, Pufferfish.class, 8.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.1));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		} else {
			super.travel(vec3);
		}
	}

	@Override
	public void spawnChildFromBreeding(ServerLevel serverLevel, Animal animal) {
		for (int i = 0; i < Main.randomReach(this.getRandom(), 1, 3); ++i) {
			AgeableMob ageableMob = this.getBreedOffspring(serverLevel, animal);
			if (ageableMob == null) {
				return;
			}
			ageableMob.setBaby(true);
			ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
			this.finalizeSpawnChildFromBreeding(serverLevel, animal, ageableMob);
			serverLevel.addFreshEntityWithPassengers(ageableMob);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
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
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.BENTHICT_BUCKET.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return JerotesVillageSoundEvents.BENTHICT_AMBIENT;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BENTHICT_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BENTHICT_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BENTHICT_HURT;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4f;
	}

	@Nullable
	@Override
	public BenthictEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.BENTHICT.get().create(serverLevel);
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBucket();
	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromBucket() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FROM_BUCKET, false);
	}
	@Override
	public boolean fromBucket() {
		return this.getEntityData().get(FROM_BUCKET);
	}
	@Override
	public void setFromBucket(boolean bl) {
		this.entityData.set(FROM_BUCKET, bl);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("FromBucket", this.fromBucket());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFromBucket(compoundTag.getBoolean("FromBucket"));
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		return Bucketable.bucketMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
	}

	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		Bucketable.saveDefaultDataToBucketTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
	}
}

