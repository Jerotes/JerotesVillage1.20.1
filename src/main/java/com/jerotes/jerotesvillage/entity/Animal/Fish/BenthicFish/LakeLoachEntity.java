package com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;


public class LakeLoachEntity extends BaseBenthicFishEntity {
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(LakeLoachEntity.class, EntityDataSerializers.INT);

	public LakeLoachEntity(EntityType<? extends LakeLoachEntity> entityType, Level level) {
		super(entityType, level);
	}

	public int getMaxSchoolSize() {
		return 5;
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(JerotesVillageItems.LAKE_LOACH_BUCKET.get());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.LAKE_LOACH_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.LAKE_LOACH_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.LAKE_LOACH_HURT;
	}
	@Override
	protected SoundEvent getFlopSound() {
		return JerotesVillageSoundEvents.LAKE_LOACH_FLOP;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.LAKE_LOACH_SWIM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
		builder = builder.add(Attributes.MAX_HEALTH, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
		return builder;
	}

	public void setColor(int n) {
		this.getEntityData().set(COLOR, n);
	}
	public int getColor() {
		return this.getEntityData().get(COLOR);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Color", this.getColor());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setColor(compoundTag.getInt("Color"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(COLOR, 1);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.VIRTUAL_CAVE) || serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.BRIGHT_GRASSLAND)) {
				this.setColor(2);
			} else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
				this.setColor(3);
			} else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
				this.setColor(4);
			} else {
				this.setColor(1);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

