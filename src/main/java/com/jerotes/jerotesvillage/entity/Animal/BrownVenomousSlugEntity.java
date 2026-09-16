package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BrownVenomousSlugEntity extends Animal implements JerotesEntity, CatchEntity {
	private static final EntityDataAccessor<Integer> POISON_TICK = SynchedEntityData.defineId(BrownVenomousSlugEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_CATCH = SynchedEntityData.defineId(BrownVenomousSlugEntity.class, EntityDataSerializers.BOOLEAN);

	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesVillage.MODID, "green_humanoid_cane"))
	);
	public BrownVenomousSlugEntity(EntityType<? extends BrownVenomousSlugEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.0));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.1));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.06);
		builder = builder.add(Attributes.MAX_HEALTH, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 12);
		return builder;
	}

	@Override
	protected void doPush(Entity entity) {
		if (entity instanceof LivingEntity living && this.getPoisonTick() > 0 && !living.isAlliedTo(entity) && this.canAttack(living) && this.canAttackType(living.getType())) {
			if (this.level().getDifficulty() == Difficulty.HARD) {
				if (!living.level().isClientSide) {
					living.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 140, 1));
				}
			}
			else if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
				if (!living.level().isClientSide) {
					living.addEffect(new MobEffectInstance(MobEffects.POISON, 140, 1));
				}
			}
		}
		super.doPush(entity);
	}

	@Override
	public ItemStack getCatchItemStack() {
		return new ItemStack(JerotesVillageItems.CAPTURED_BROWN_VENOMOUS_SLUG.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return JerotesVillageSoundEvents.BROWN_VENOMOUS_SLUG_AMBIENT;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromCatch();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromCatch() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}

	public int getPoisonTick() {
		return this.getEntityData().get(POISON_TICK);
	}
	public void setPoisonTick(int n) {
		this.getEntityData().set(POISON_TICK, n);
	}
	@Override
	public boolean fromCatch() {
		return this.entityData.get(FROM_CATCH);
	}
	@Override
	public void setFromCatch(boolean bl) {
		this.entityData.set(FROM_CATCH, bl);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("FromCatch", this.fromCatch());
		compoundTag.putInt("PoisonTick", this.getPoisonTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFromCatch(compoundTag.getBoolean("FromCatch"));
		if (compoundTag.contains("PoisonTick")) {
			this.setPoisonTick(compoundTag.getInt("PoisonTick"));
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(POISON_TICK, 0);
		this.getEntityData().define(FROM_CATCH, false);
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

	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide()) {
			this.setPoisonTick(Math.max(0, this.getPoisonTick() - 1));
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!this.level().isClientSide()) {
			this.setPoisonTick(160);
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.BROWN_VENOMOUS_SLUG_AMBIENT, 0.15f, 0.5f);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BROWN_VENOMOUS_SLUG_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BROWN_VENOMOUS_SLUG_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BROWN_VENOMOUS_SLUG_AMBIENT;
	}

	@Override
	protected float getSoundVolume() {
		return 0.05f;
	}

	@Nullable
	@Override
	public BrownVenomousSlugEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.BROWN_VENOMOUS_SLUG.get().create(serverLevel);
	}
}




