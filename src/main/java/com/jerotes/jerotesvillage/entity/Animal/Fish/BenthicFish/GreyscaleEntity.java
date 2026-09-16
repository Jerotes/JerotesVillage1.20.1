package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;


public class GreyscaleEntity extends BaseBenthicFishEntity {
	private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(GreyscaleEntity.class, EntityDataSerializers.INT);

	public GreyscaleEntity(EntityType<? extends GreyscaleEntity> entityType, Level level) {
		super(entityType, level);
	}

	public int getMaxSchoolSize() {
		return 5;
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.GREYSCALE_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.GREYSCALE_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.GREYSCALE_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.GREYSCALE_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.GREYSCALE_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.GREYSCALE_SWIM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
		return builder;
	}

	public void setSize(int n) {
		this.getEntityData().set(SIZE, Math.max(1, n));
	}
	public int getSize() {
		return this.getEntityData().get(SIZE);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Size", this.getSize());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setSize(compoundTag.getInt("Size"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SIZE, 5);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			if (serverLevelAccessor.getRandom().nextFloat() > 0.6f) {
				this.setSize(Main.randomReach(serverLevelAccessor.getRandom(), 4, 6));
			}
			else if (serverLevelAccessor.getRandom().nextFloat() > 0.6f) {
				this.setSize(Main.randomReach(serverLevelAccessor.getRandom(), 7, 8));
			}
			else if (serverLevelAccessor.getRandom().nextFloat() > 0.6f) {
				this.setSize(Main.randomReach(serverLevelAccessor.getRandom(), 9, 10));
			}
			else if (serverLevelAccessor.getRandom().nextFloat() > 0.8f) {
				this.setSize(Main.randomReach(serverLevelAccessor.getRandom(), 11, 12));
			}
			else {
				this.setSize(3);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack, float f) {
		if (itemStack.isEmpty()) {
			return null;
		} else if (this.level().isClientSide) {
			return null;
		} else {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY() + (double)f, this.getZ(), itemStack);
			itementity.setDefaultPickUpDelay();
			if (itementity.getItem().getItem() == JerotesVillageItems.GREYSCALE.get()) {
				ItemStack itemStack1 = itementity.getItem();
				CompoundTag compoundtag = itemStack1.hasTag() ? itemStack1.getTag().copy() : new CompoundTag();
				compoundtag.putInt("FishSize", this.getSize());
				itemStack1.setTag(compoundtag);
				itementity.setItem(itemStack1);
			}
			if (captureDrops() != null) captureDrops().add(itementity);
			else {
				this.level().addFreshEntity(itementity);
			}
			return itementity;
		}
	}
}

