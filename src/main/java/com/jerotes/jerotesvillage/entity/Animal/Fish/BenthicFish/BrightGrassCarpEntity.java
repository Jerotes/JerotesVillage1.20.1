package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.Other;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.Objects;
import java.util.UUID;


public class BrightGrassCarpEntity extends BaseBenthicFishEntity {
	public AnimationState idleAnimationState = new AnimationState();
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("00cc11c5-1b8e-4474-ac0e-9dd70f715fc1");
	private static final EntityDataAccessor<Boolean> WALKER = SynchedEntityData.defineId(BrightGrassCarpEntity.class, EntityDataSerializers.BOOLEAN);

	public BrightGrassCarpEntity(EntityType<? extends BrightGrassCarpEntity> entityType, Level level) {
		super(entityType, level);
		this.fixupDimensions();
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.BRIGHT_GRASS_CARP_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BRIGHT_GRASS_CARP_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BRIGHT_GRASS_CARP_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BRIGHT_GRASS_CARP_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.BRIGHT_GRASS_CARP_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.BRIGHT_GRASS_CARP_SWIM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
		return builder;
	}
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		int size = this.isWalker() ? 3 : 1;
		return super.getDimensions(pose).scale(1f, size);
	}
	protected void handleAirSupply(int n) {
		if (!this.isWalker()) {
			super.handleAirSupply(n);
		} else {
			this.setAirSupply(300);
		}

	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isWalker()) {
			return 0.8f;
		}
		return super.getStandingEyeHeight(pose, entityDimensions);
	}

	public boolean isWalker() {
		return this.getEntityData().get(WALKER);
	}
	public void setWalker(boolean bl) {
		this.getEntityData().set(WALKER, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).
					addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Walker Movement Speed bonus",
							-0.5, AttributeModifier.Operation.ADDITION));
		}
		this.reapplyPosition();
		this.refreshDimensions();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsWalker", this.isWalker());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setWalker(compoundTag.getBoolean("IsWalker"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(WALKER, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (WALKER.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WaterBoundPathNavigation(this, level);
	}
	public boolean isLandNavigatorType = false;
	public boolean isRainAbout = false;
	@Override
	public void aiStep() {
		super.aiStep();

		boolean rain = this.level().isRainingAt(this.getOnPos());
		if (rain && !isRainAbout) {
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
			isRainAbout = true;
		}
		if (!rain && isRainAbout) {
			this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 8.0F);
			isRainAbout = false;
		}
		if (this.isWalker()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//水陆切换
		//恢复水行
		if (this.isInWater() && this.isLandNavigatorType) {
			this.moveControl = new FishMoveControl(this);
			this.navigation = new WaterBoundPathNavigation(this, this.level());
			this.setWalker(false);
			this.isLandNavigatorType = false;
		}
		//鱼上岸了！！！
		if (!this.isInWater() && rain && !this.isLandNavigatorType) {
			this.moveControl = new MoveControl(this);
			this.navigation = new GroundPathNavigation(this, level());
			this.setWalker(true);
			this.isLandNavigatorType = true;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//
	}


	private static class FishMoveControl extends MoveControl {
		private final AbstractFish fish;

		FishMoveControl(AbstractFish p_27501_) {
			super(p_27501_);
			this.fish = p_27501_;
		}
		public void tick() {
			if (this.fish.isEyeInFluid(FluidTags.WATER)) {
				this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, 0.005, 0.0));
			}
			if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
				float $$0 = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), $$0));
				double $$1 = this.wantedX - this.fish.getX();
				double $$2 = this.wantedY - this.fish.getY();
				double $$3 = this.wantedZ - this.fish.getZ();
				if ($$2 != 0.0) {
					double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
					this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, (double)this.fish.getSpeed() * ($$2 / $$4) * 0.1, 0.0));
				}
				if ($$1 != 0.0 || $$3 != 0.0) {
					float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875) - 90.0F;
					this.fish.setYRot(this.rotlerp(this.fish.getYRot(), $$5, 90.0F));
					this.fish.yBodyRot = this.fish.getYRot();
				}
			} else {
				this.fish.setSpeed(0.0F);
			}
		}
	}
}

